package de.eleon.timejoe.user

import grails.plugins.springsecurity.Secured
import org.apache.commons.logging.LogFactory

@Secured(['ROLE_USER_MANAGER'])
class UserController {


	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static defaultAction = "list"

    def mailService

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def userInstance = new User()
		userInstance.properties = params

		def userInstanceList = User.list(params)
		def userInstanceTotal = User.count()
		def roleList = Role.list()

		[userInstance: userInstance, userInstanceList: userInstanceList, userInstanceTotal: userInstanceTotal, roleList: roleList]
	}

	def save = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def userInstance = new User(params)
		
		userInstance.enabled = true
		
		def roleList = Role.list()
		println userInstance
		if (userInstance.save(flush: true)) {
			
			roleList.each {
				if (params["auth[" + it.id + "]"])
					UserRole.create userInstance, it, true
			}
		}

        mailService.sendMail {
            to userInstance.username
            subject "Hello, AgileDays"
            body "Some text"
        }

		render(view: "list", model: [userInstance: userInstance, userInstanceList: User.list(params), userInstanceTotal: User.count(), roleList: roleList])
	}

	def edit = {
		def userInstance = User.get(params.id)
		def roleList = Role.list()

		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
			return
		}

		userInstance.properties = params
		render(template: "edit", model: [userInstance: userInstance, roleList: roleList])
	}

	def update = {
		def userInstance = User.get(params.id)
		def roleList = Role.list()

		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (userInstance.version > version) {

				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'user.label', default: 'User')]
				as Object[], "Another user has updated this User while you were editing")
				render(template: "edit", model: [userInstance: userInstance, roleList: roleList])
				return
			}
		}

		def userManagerRole = Role.findByAuthority("ROLE_USER_MANAGER")
		def userManagerCount = UserRole.countByRole(userManagerRole)
		if (UserRole.findByUserAndRole(userInstance, userManagerRole) && userManagerCount < 2) {
			if (!params["auth[" + userManagerRole.id + "]"]) {
				render(text: """<td style="width: 220px;">${userInstance.username}</td><td colspan="2"><div class="errors" style="margin: 0;">There must be at least one User Manager</div></td>""")
				return
			}
		}

		userInstance.properties = params

		if (params.setPassword)
			userInstance.password = params.newPassword

		if (userInstance.save(flush: true)) {

			roleList.each {
				if (params["auth[" + it.id + "]"]) {
					if (!UserRole.findByUserAndRole(userInstance, it))
						UserRole.create userInstance, it, true
				} else {
					UserRole.findByUserAndRole(userInstance, it)?.delete(flush:true)
				}
			}

			render(template: "listElemAfterEdit", model: [userInstance: userInstance])
		} else {
			render(template: "edit", model: [userInstance: userInstance, roleList: roleList])
		}
	}

	def activate = {
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
			return
		}
		userInstance.enabled = true
		userInstance.save(flush: true)
		render(template: "listElemAfterEdit", model: [userInstance: userInstance])
	}

	def deactivate = {
		def userInstance = User.get(params.id)

		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
			return
		}

		def userManagerRole = Role.findByAuthority("ROLE_USER_MANAGER")
		def userManagerCount = UserRole.countByRole(userManagerRole)
		if (UserRole.findByUserAndRole(userInstance, userManagerRole) && userManagerCount < 2) {
			render(text: """<td style="width: 220px;">${userInstance.username}</td><td colspan="2"><div class="errors" style="margin: 0;">Cannot deactivate the one and only User Manager</div></td>""")
			return
		}

		userInstance.enabled = false
		userInstance.save(flush: true)
		render(template: "listElemAfterEdit", model: [userInstance: userInstance])
	}

	def delete = {
		def userInstance = User.get(params.id)

		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
			return
		}

		def userManagerRole = Role.findByAuthority("ROLE_USER_MANAGER")
		def userManagerCount = UserRole.countByRole(userManagerRole)
		if (UserRole.findByUserAndRole(userInstance, userManagerRole) && userManagerCount < 2) {
			flash.message = "Cannot delete the one and only User Manager"
			redirect(action: "list")
			return
		}

		try {
			userInstance.delete(flush: true)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list", id: params.id)
		}
	}
}
