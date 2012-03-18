package de.eleon.timejoe.tracking

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_AUTOCOMPLETE_MANAGER'])
class AutoCompleteController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static defaultAction = "list"

    def list = { 
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def autoCompleteInstanceList = AutoCompleteElem.list(params)
		def autoCompleteInstanceTotal = AutoCompleteElem.count()
		
		[autoCompleteInstanceList: autoCompleteInstanceList, autoCompleteInstanceTotal: autoCompleteInstanceTotal]
	}
	
	def edit = {
		def tableCounter = params.tableCounter
		def autoCompleteElemInstance = AutoCompleteElem.findByCustomerAndText(Customer.read(params.customer as Long), params.text)
		
		if (!autoCompleteElemInstance) {
			log.error "No autoCompleteElemInstance found with params: " + params.customer + ", " + params.oldText
			return
		}
			
		render(template: "edit", model: [autoCompleteElemInstance: autoCompleteElemInstance, tableCounter: tableCounter])
	}
	
	def update = {
		def tableCounter = params.tableCounter
		def customerInstance = Customer.get(params.customer as Long)
		def autoCompleteElemInstance = AutoCompleteElem.findByCustomerAndText(customerInstance, params.oldText)
		def newAutoCompleteElem
		
		if (!autoCompleteElemInstance) {
			log.error "No autoCompleteElemInstance found with params: " + params.customer + ", " + params.oldText
			return
		}
		
		if (params.version) {
			def version = params.version.toLong()
			if (autoCompleteElemInstance.version > version) {
				
				autoCompleteElemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'autoCompleteElem.label', default: 'AutoCompleteElem')] as Object[], "Another user has updated this Element while you were editing")
				render(template: "edit", model: [autoCompleteElemInstance: autoCompleteElemInstance, tableCounter: tableCounter])
				return
			}
		}
			
		if (params.text && !autoCompleteElemInstance.hasErrors()) {
			autoCompleteElemInstance.delete(flush:true)
			newAutoCompleteElem = new AutoCompleteElem(customer: customerInstance, text: params.text).save(flush: true)
		}
		
		if (params.refactorTimeEntries) {
			ProjectTimeEntry.findAllByCustomerAndComment(customerInstance, params.oldText).each {
				it.comment = newAutoCompleteElem.text
				it.save()
			}
		}
		
		render(template: "listElemAfterEdit", model: [newAutoCompleteElem: newAutoCompleteElem, tableCounter: tableCounter])
	}
	
	def delete = {
		def autoCompleteElemInstance = AutoCompleteElem.findByCustomerAndText(Customer.read(params.customer as Long), params.text)
		if (!autoCompleteElemInstance) {
			log.error "No autoCompleteElemInstance found with params: " + params.customer + ", " + params.oldText
			return
		}
		
		try {
			autoCompleteElemInstance.delete(flush: true)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'autoCompleteElem.label', default: 'AutoCompleteElem'), params.text])}"
			redirect(action: "list")
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'autoCompleteElem.label', default: 'AutoCompleteElem'), params.text])}"
			redirect(action: "list")
		}
	}
}
