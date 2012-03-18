package de.eleon.timejoe.tracking

import grails.plugins.springsecurity.Secured
import groovy.time.TimeDuration

@Secured(['ROLE_TRACKER'])
class WorkTimeController {
	
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
		
		def trackingDayList = WorkTimeEntry.completedForUserAndDay(userInstance, date)
		def trackingDayCount = trackingDayList.size()
		
		def runningWorkTimeEntry
		runningWorkTimeEntry = WorkTimeEntry.runningForUserAndDay(userInstance, date)
		
		def durationSum
		def durationSumMillis = WorkTimeEntry.durationSumForUserAndDay(userInstance, date)
		if (durationSumMillis > 0) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis)
		
		[today: today.format("yyyy-MM-dd"), date: date.format("yyyy-MM-dd"), runningWorkTimeEntry: runningWorkTimeEntry, trackingDayList: trackingDayList, trackingDayCount: trackingDayCount, durationSum: durationSum]
	}
	
	def start = {
		def userInstance = springSecurityService.currentUser
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = userInstance
		
		def date = parseDate(params.date)
		workTimeEntryInstance.start = new Date(date.timeInMillis)
		workTimeEntryInstance.save(flush: true)
		
		render(template: "stopForm", model: [runningWorkTimeEntry: workTimeEntryInstance])
	}
	
	def stop = {
		def workTimeEntryInstance = WorkTimeEntry.get(params.id)
		
		if (!workTimeEntryInstance || workTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}

		workTimeEntryInstance.end = new Date()
		workTimeEntryInstance.save(flush: true)
		redirect(action: "today")
	}
	
	def date = {
		def userInstance = springSecurityService.currentUser
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.properties = params
		
		def today = Calendar.getInstance()
		def date = parseDate(params.date)
		
		if (today.format("yyyy-MM-dd") == date.format("yyyy-MM-dd")) {
			redirect(action: "today")
		}
		
		def trackingDayList = WorkTimeEntry.allForUserAndDay(userInstance, date)
		def trackingDayCount = trackingDayList.size()
		
		def durationSum
		def durationSumMillis = WorkTimeEntry.durationSumForUserAndDay(userInstance, date)
		if (durationSumMillis > 0) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis)
		
		[workTimeEntryInstance: workTimeEntryInstance, today: today.format("yyyy-MM-dd"), date: date.format("yyyy-MM-dd"), trackingDayList: trackingDayList, trackingDayCount: trackingDayCount, durationSum: durationSum]
	}
	
	def save = {
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = springSecurityService.currentUser
		try {
			workTimeEntryInstance.start = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.start)
			workTimeEntryInstance.end = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.stop)
		} catch (Exception e) {
			flash.message = e.message
			redirect(action: "date", params: [date: params.date])
			return
		}
		
		if (workTimeEntryInstance.save(flush: true)) {
			redirect(action: "date", params: [date: params.date])
		} else {
			render(view: "date", params: [date: params.date], model: [workTimeEntryInstance: workTimeEntryInstance])
		}
	}
	
	def edit = {
		def workTimeEntryInstance = WorkTimeEntry.get(params.id)
		def date = params.date
		
		if (!workTimeEntryInstance || workTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}
		
		render(template: "edit", model: [workTimeEntryInstance: workTimeEntryInstance, date: date])
	}
	
	def update = {
		def workTimeEntryInstance = WorkTimeEntry.get(params.id)
		
		if (!workTimeEntryInstance || workTimeEntryInstance.user != springSecurityService.currentUser) {
			render "<td colspan='4'>Not found or security exception</td>"
			return
		}
		
		try {
			workTimeEntryInstance.start = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.start)
			workTimeEntryInstance.end = new Date().parse("yyyy-MM-dd HH:mm", params.date + " " + params.stop)
		} catch (Exception e) {
			log.error "error parsing start and end date " + e.message
		}
		
		if (workTimeEntryInstance.save(flush: true)) {
			render(template: "listElemAfterEdit", model: [workTimeEntryInstance: workTimeEntryInstance, date: params.date])
		} else {
			render(template: "edit", model: [workTimeEntryInstance: workTimeEntryInstance, date: params.date])
		}
	}
	
	def delete = {
		def workTimeEntryInstance = WorkTimeEntry.get(params.id)
		
		if (!workTimeEntryInstance || workTimeEntryInstance.user != springSecurityService.currentUser) {
			flash.message = "Not found"
			redirect(action: "today")
		}
		
		try {
			workTimeEntryInstance.delete(flush: true)
			redirect(action: "date", params: [date: workTimeEntryInstance.start.format("yyyy-MM-dd")])
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'worktimeentry.label', default: 'WorkTimeEntry'), params.id])}"
			redirect(action: "date", params: [date: workTimeEntryInstance.start.format("yyyy-MM-dd")])
		}
	}
	
	def latest = {
		def userInstance = springSecurityService.currentUser
		def latestWorkTimeList = WorkTimeEntry.findAllByUser(userInstance, [max: 3, sort: "start", order:"desc", cache: true])
		[latestWorkTimeList: latestWorkTimeList]
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
