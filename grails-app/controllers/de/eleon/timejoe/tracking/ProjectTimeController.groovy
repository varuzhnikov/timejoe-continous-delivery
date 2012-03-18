package de.eleon.timejoe.tracking

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import grails.util.GrailsUtil
import groovy.time.TimeDuration

import java.util.Calendar

@Secured(['ROLE_TRACKER'])
class ProjectTimeController {
	
	static defaultAction = "today"
	
	def springSecurityService
	def timeConverterService

    def today = {
		def userInstance = springSecurityService.currentUser
		
		def today = Calendar.getInstance()
		def date = parseDate(params.date)
		
		if (today.format("yyyy-MM-dd") != date.format("yyyy-MM-dd")) {
			redirect(action: "date", params: [date: date.format("yyyy-MM-dd")])
		}
		
		def trackingDayList = ProjectTimeEntry.completedForUserAndDay(userInstance, date)
		def trackingDayCount = trackingDayList.size()
		
		def runningProjectTimeEntry = ProjectTimeEntry.runningForUserAndDay(userInstance, date)
		
		def durationSum
		def durationSumMillis = ProjectTimeEntry.durationSumForUserAndDay(userInstance, date)
		if (durationSumMillis > 0) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis)
		
		def customerList = Customer.findAllByBookable(true, [cache:true])
		
		[today: today.format("yyyy-MM-dd"), date: date.format("yyyy-MM-dd"), runningProjectTimeEntry: runningProjectTimeEntry, trackingDayList: trackingDayList, trackingDayCount: trackingDayCount, durationSum: durationSum, customerList: customerList]
	}
	
	def start = {
		def userInstance = springSecurityService.currentUser
		def projectTimeEntryInstance = new ProjectTimeEntry()
		def customerList = Customer.findAllByBookable(true, [cache:true])
		
		projectTimeEntryInstance.user = userInstance
		if (params.customer != "")
			projectTimeEntryInstance.customer = Customer.get(params.customer as Long)
		projectTimeEntryInstance.comment = params.comment
		
		def date = parseDate(params.date)
		projectTimeEntryInstance.start = new Date(date.timeInMillis)
		
		if (projectTimeEntryInstance.save(flush: true))
			render(template: "stopForm", model: [runningProjectTimeEntry: projectTimeEntryInstance, customerList: customerList])
		else 
			render(template: "startForm", model: [date: date.format("yyyy-MM-dd"), runningProjectTimeEntry: projectTimeEntryInstance, customerList: customerList])
		}
	
	def stop = {
		def projectTimeEntryInstance = ProjectTimeEntry.get(params.id)
		
		if (!projectTimeEntryInstance || projectTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}

		projectTimeEntryInstance.end = new Date()
		projectTimeEntryInstance.save(flush: true)
		redirect(action: "today")
	}
	
	def autoCompleteList = {
		if(GrailsUtil.isDevelopmentEnv())
			Thread.currentThread().sleep(1*1000)
			
		def autoCompleteList
		
		if (params.id != "" && params.term == "")
			autoCompleteList = AutoCompleteElem.findAllByCustomer(Customer.read(params.id as Long), [cache: true]).text
		
		if (params.id != "" && params.term != "")
		autoCompleteList = AutoCompleteElem.findAllByCustomerAndTextIlike(Customer.read(params.id as Long), "%${params.term}%").text
		
		if (autoCompleteList)
			render autoCompleteList as JSON
	}
	
	def date = {
		def userInstance = springSecurityService.currentUser
		
		def projectTimeEntryInstance = new ProjectTimeEntry()
		projectTimeEntryInstance.properties = params
		
		def today = Calendar.getInstance()
		def date = parseDate(params.date)
		
		if (today.format("yyyy-MM-dd") == date.format("yyyy-MM-dd")) {
			redirect(action: "today")
		}
		
		def trackingDayList = ProjectTimeEntry.allForUserAndDay(userInstance, date)
		def trackingDayCount = trackingDayList.size()
		
		def durationSum
		def durationSumMillis = ProjectTimeEntry.durationSumForUserAndDay(userInstance, date)
		if (durationSumMillis > 0) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis)
		
		def customerList = Customer.findAllByBookable(true, [cache:true])
		
		[projectTimeEntryInstance: projectTimeEntryInstance, today: today.format("yyyy-MM-dd"), date: date.format("yyyy-MM-dd"), trackingDayList: trackingDayList, trackingDayCount: trackingDayCount, durationSum: durationSum, customerList: customerList]
	}
	
	def save = {
		def projectTimeEntryInstance = new ProjectTimeEntry()
		def userInstance = springSecurityService.currentUser
		
		projectTimeEntryInstance.user = userInstance
		projectTimeEntryInstance.comment = params.comment
		if (params.customer != "")
			projectTimeEntryInstance.customer = Customer.get(params.customer as Long)
		try {
			projectTimeEntryInstance.start = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.start)
			projectTimeEntryInstance.end = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.stop)
		} catch (Exception e) {
			flash.message = e.message
			redirect(action: "date", params: [date: params.date])
			return
		}
		
		if (projectTimeEntryInstance.save(flush: true)) {
			redirect(action: "date", params: [date: params.date])
		} else {
			if (!projectTimeEntryInstance?.customer)
				flash.message = "${message(code: 'please.enter.customer')}"
			redirect(action: "date", params: [date: params.date])		}
	}
	
	def edit = {
		def projectTimeEntryInstance = ProjectTimeEntry.get(params.id)
		def date = params.date
		
		if (!projectTimeEntryInstance || projectTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}
		
		render(template: "edit", model: [projectTimeEntryInstance: projectTimeEntryInstance, date: date])
	}
	
	def update = {
		def projectTimeEntryInstance = ProjectTimeEntry.get(params.id)
		
		if (!projectTimeEntryInstance || projectTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}
		
		try {
			projectTimeEntryInstance.start = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.start)
			projectTimeEntryInstance.end = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.stop)
		} catch (Exception e) {
			log.error "error parsing start and end date " + e.message
		}
		
		if (projectTimeEntryInstance.save(flush: true)) {
			render(template: "listElemAfterEdit", model: [projectTimeEntryInstance: projectTimeEntryInstance, date: params.date])
		} else {
			render(template: "edit", model: [projectTimeEntryInstance: projectTimeEntryInstance, date: params.date])
		}
	}
	
	def delete = {
		def projectTimeEntryInstance = ProjectTimeEntry.get(params.id)
		
		if (!projectTimeEntryInstance || projectTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}
		
		try {
			projectTimeEntryInstance.delete(flush: true)
			redirect(action: "date", params: [date: projectTimeEntryInstance.start.format("yyyy-MM-dd")])
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'projecttimeentry.label', default: 'ProjectTimeEntry'), params.id])}"
			redirect(action: "date", params: [date: projectTimeEntryInstance.start.format("yyyy-MM-dd")])
		}
	}
	
	def latest = {
		def userInstance = springSecurityService.currentUser
		def latestProjectTimeList = ProjectTimeEntry.findAllByUser(userInstance, [max: 3, sort: "start", order:"desc", cache: true])
		[latestProjectTimeList: latestProjectTimeList]
	}
	
	private Calendar parseDate(String dateString) {
		def calendar
		def date = Calendar.getInstance()
		def today = Calendar.getInstance()
		if ("${dateString}" ==~ /\d\d\d\d-\d\d-\d\d/) {
			date.set(dateString.substring(0,4) as Integer, (dateString.substring(5,7) as Integer)-1, dateString.substring(8,10) as Integer)
		
			if (date > today)
				calendar = today
			else
				calendar = date
		} else {
			calendar = today
		}
		
		return calendar
	}
}
