package de.eleon.timejoe.tracking

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_CUSTOMER_MANAGER'])
class CustomerController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static defaultAction = "list"
	
    def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def customerInstance = new Customer()
		customerInstance.properties = params
		
		def customerInstanceList = Customer.list(params)
		def customerInstanceTotal = Customer.count()
		
		[customerInstance: customerInstance, customerInstanceList: customerInstanceList, customerInstanceTotal: customerInstanceTotal]
	}
	
	def save = {
		def customerInstance = new Customer(params)
		if (customerInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])}"
			redirect(action: "list")
		} else {
			render(view: "list", model: [customerInstance: customerInstance])
		}
	}
	
	def edit = {
		def customerInstance = Customer.get(params.id)
		if (!customerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
			return
		}
			
		customerInstance.properties = params
		render(template: "edit", model: [customerInstance: customerInstance])
	}
	
	def update = {
		def customerInstance = Customer.get(params.id)
		
		if (!customerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
			return
		}
		
		if (params.version) {
			def version = params.version.toLong()
			if (customerInstance.version > version) {
				
				customerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'customer.label', default: 'Customer')] as Object[], "Another user has updated this Customer while you were editing")
				render(template: "edit", model: [customerInstance: customerInstance])
				return
			}
		}
		
		customerInstance.properties = params
		
		if (customerInstance.save(flush: true)) {
			render(template: "listElemAfterEdit", model: [customerInstance: customerInstance])
		} else {
			render(template: "edit", model: [customerInstance: customerInstance])
		}
	}
	
	def activate = {
		def customerInstance = Customer.get(params.id)
		if (!customerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
		}
		customerInstance.bookable = true
		customerInstance.save(flush: true)
		render(template: "listElemAfterEdit", model: [customerInstance: customerInstance])
	}
	
	def deactivate = {
		def customerInstance = Customer.get(params.id)
		if (!customerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
		}
		customerInstance.bookable = false
		customerInstance.save(flush: true)
		render(template: "listElemAfterEdit", model: [customerInstance: customerInstance])
	}
	
	def delete = {
		def customerInstance = Customer.get(params.id)
		if (!customerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
		}
		
		try {
			customerInstance.delete(flush: true)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list")
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])}"
			redirect(action: "list", id: params.id)
		}
	}
}
