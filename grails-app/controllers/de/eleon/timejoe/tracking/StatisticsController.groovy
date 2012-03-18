package de.eleon.timejoe.tracking

import grails.plugins.springsecurity.Secured

import java.util.Calendar

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import de.eleon.timejoe.user.Role
import de.eleon.timejoe.user.User

class StatisticsController {
	
	def springSecurityService
	def statisticsDataService
	def timeConverterService
	
	@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS'])
    def index = {
		if (SpringSecurityUtils.ifAnyGranted('ROLE_TRACKER'))
			redirect(action: "userSummary")
		else if (SpringSecurityUtils.ifAnyGranted('ROLE_TRACKER'))
			redirect(action: "teamSummary")
	}
	
	@Secured(['ROLE_TRACKER'])
	def userSummary = {
		def userInstance = springSecurityService.currentUser
		def latestUserWorktimeList
		def latestUserProjecttimeList
		def latestUserCustomerProjecttimeList
		
		latestUserWorktimeList = statisticsDataService.getLatestWorkHours(userInstance)
		latestUserProjecttimeList = statisticsDataService.getLatestProjectHours(userInstance)
		latestUserCustomerProjecttimeList = statisticsDataService.getLatestCustomerProjecttime(userInstance)
		
		[latestUserWorktimeList: latestUserWorktimeList, latestUserProjecttimeList: latestUserProjecttimeList, latestUserCustomerProjecttimeList: latestUserCustomerProjecttimeList]
	}
	
	@Secured(['ROLE_STATISTICS'])
	def teamSummary = {
		def latestTeamWorktimeList
		def latestTeamProjecttimeList
		def latestTeamCustomerProjecttimeList
		
		latestTeamWorktimeList = statisticsDataService.getLatestWorkHours()
		latestTeamProjecttimeList = statisticsDataService.getLatestProjectHours()
		latestTeamCustomerProjecttimeList = statisticsDataService.getLatestCustomerProjecttime()
		
		[latestTeamWorktimeList: latestTeamWorktimeList, latestTeamProjecttimeList: latestTeamProjecttimeList, latestTeamCustomerProjecttimeList: latestTeamCustomerProjecttimeList]
	}
	
	@Secured(['ROLE_STATISTICS'])
	def memberComparison = {
		def today = Calendar.getInstance()
		
		[today: today.format("yyyy-MM-dd")]
	}
	
	@Secured(['ROLE_STATISTICS'])
	def memberComparisonData = {
		def today = Calendar.getInstance()
		def membersWorkHoursList
		def membersProjectHoursList
		def from
		def to
		
		if (!params.from || !params.to) {
			flash.message = "Please select start date (From) and end date (To)"
			redirect(action: "memberComparison")
			return
		}
		
		from = new Date().parse("yyyy-MM-dd HH:mm:ss", params.from + " 00:00:00")
		to = new Date().parse("yyyy-MM-dd HH:mm:ss", params.to + " 23:59:59")
		
		if (from.after(to)) {
			flash.message = "FROM must be before TO"
			render(view: "memberComparison", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd")])
			return
		}
		
		membersWorkHoursList = statisticsDataService.getWorkHoursOfMembers(from, to)
		membersProjectHoursList = statisticsDataService.getProjectHoursOfMembers(from, to)
		
		render(view: "memberComparison", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd"), membersWorkHoursList: membersWorkHoursList, membersProjectHoursList: membersProjectHoursList])
	}
	
	@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS'])
	def projectTimes = {
		def today = Calendar.getInstance()
		def customerList
		def userList
		
		customerList = Customer.list()
		if (SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS')) 
			userList = findUsers()
		
		[today: today.format("yyyy-MM-dd"), customerList: customerList, userList: userList]
	}
	
	@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS'])
	def projectTimesData = {
		def today = Calendar.getInstance()
		def customerList
		def userList
		def from
		def to
		def customer
		def user
		def timeEntryList
		def durationSumMillis
		def durationSum
		
		customerList = Customer.list()
		if (SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			userList = findUsers()
		
		if (params.customer)
			customer = Customer.get(params.customer as Long)
		else
			customer = null
		
		if (params.user && SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			user = User.get(params.user as Long)
		else if (!params.user && SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			user = null
		else
			user = springSecurityService.currentUser
		
		if (!params.from || !params.to) {
			flash.message = "Please select start date (From) and end date (To)"
			render(view: "projectTimes", model: [today: today.format("yyyy-MM-dd"), customerList: customerList, userList: userList, customer: customer, user: user])
			return
		}
		
		from = new Date().parse("yyyy-MM-dd HH:mm:ss", params.from + " 00:00:00")
		to = new Date().parse("yyyy-MM-dd HH:mm:ss", params.to + " 23:59:59")
		
		if (from.after(to)) {
			flash.message = "FROM must be before TO"
			render(view: "projectTimes", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd"), customerList: customerList, userList: userList, customer: customer, user: user])
			return
		}
		
		timeEntryList = statisticsDataService.getProjectTimeEntries(from, to, user, customer)
		durationSum = statisticsDataService.getProjectTimeDurationSum(from, to, user, customer)
		
		if (params.format == "xls") {
			response.setHeader("Content-disposition", "attachment; filename=projectTimes_${today.format('yyyyMMddHHmm')}.csv")
			response.contentType = "application/vnd.ms-excel"
			
			def outs = response.outputStream
			def cols = [:]
			
			outs << "${message(code: 'timeentry.customer')};${message(code: 'timeentry.comment')};${message(code: 'timeentry.user')};${message(code: 'timeentry.date.label')};${message(code: 'timeentry.start.label')};${message(code: 'timeentry.end.label')};${message(code: 'timeentry.duration.label')}"
			outs << "\n"
			timeEntryList.each() {
				outs << """"${it.customer?.name}";"${it?.comment}";${it?.user?.username};${it?.start.format('yyyy-MM-dd')};${it?.start?.format('HH:mm')};${it?.end?.format('HH:mm')};${it?.duration?.getHours()} h ${it?.duration?.getMinutes()} min"""
				outs << "\n"
			}
			outs << ";;;;;;${durationSum?.getDays()} days ${durationSum?.getHours()} h ${durationSum?.getMinutes()} min"
			outs << "\n"
			outs.flush()
			outs.close()
			return
		}
			
		render(view: "projectTimes", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd"), customerList: customerList, userList: userList, customer: customer, user: user, timeEntryList: timeEntryList, durationSum: durationSum])
	}
	
	@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS'])
	def workTimes = {
		def today = Calendar.getInstance()
		def userList
		
		if (SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			userList = findUsers()
		
		[today: today.format("yyyy-MM-dd"), userList: userList]
	}
	
	@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS'])
	def workTimesData = {
		def today = Calendar.getInstance()
		def userList
		def from
		def to
		def user
		def timeEntryList
		def durationSumMillis
		def durationSum
		
		if (SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			userList = findUsers()
		
		if (params.user && SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			user = User.get(params.user as Long)
		else if (!params.user && SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS'))
			user = null
		else
			user = springSecurityService.currentUser
		
		if (!params.from || !params.to) {
			flash.message = "Please select start date (From) and end date (To)"
			render(view: "workTimes", model: [today: today.format("yyyy-MM-dd"), userList: userList, user: user])
			return
		}
		
		from = new Date().parse("yyyy-MM-dd HH:mm:ss", params.from + " 00:00:00")
		to = new Date().parse("yyyy-MM-dd HH:mm:ss", params.to + " 23:59:59")
		
		if (from.after(to)) {
			flash.message = "FROM must be before TO"
			render(view: "workTimes", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd"), userList: userList, user: user])
			return
		}
		
		timeEntryList = statisticsDataService.getWorkTimeEntries(from, to, user)
		durationSum = statisticsDataService.getWorkTimeDurationSum(from, to, user)
		
		if (params.format == "xls") {
			response.setHeader("Content-disposition", "attachment; filename=workTimes_${today.format('yyyyMMddHHmm')}.csv")
			response.contentType = "application/vnd.ms-excel"
			
			def outs = response.outputStream
			def cols = [:]
			
			outs << "${message(code: 'timeentry.user')};${message(code: 'timeentry.date.label')};${message(code: 'timeentry.start.label')};${message(code: 'timeentry.end.label')};${message(code: 'timeentry.duration.label')}"
			outs << "\n"
			timeEntryList.each() {
				outs << """${it?.user?.username};${it?.start.format('yyyy-MM-dd')};${it?.start?.format('HH:mm')};${it?.end?.format('HH:mm')};${it?.duration?.getHours()} h ${it?.duration?.getMinutes()} min"""
				outs << "\n"
			}
			outs << ";;;;${durationSum?.getDays()} days ${durationSum?.getHours()} h ${durationSum?.getMinutes()} min"
			outs << "\n"
			outs.flush()
			outs.close()
			return
		}
			
		render(view: "workTimes", model: [today: today.format("yyyy-MM-dd"), from: from.format("yyyy-MM-dd"), to: to.format("yyyy-MM-dd"), userList: userList, user: user, timeEntryList: timeEntryList, durationSum: durationSum])

	}
	
	@Secured(['ROLE_STATISTICS'])
	def latest = {
		def workTimePreviousDay
		def projectTimePreviousDay
		def yesterdayMorning = new Date().parse("yyyy-MM-dd HH:mm:ss", (new Date() - 1).format("yyyy-MM-dd") + " 00:00:00")
		def yesterDayEvening = new Date().parse("yyyy-MM-dd HH:mm:ss", (new Date() - 1).format("yyyy-MM-dd") + " 23:59:59")
		
		workTimePreviousDay = statisticsDataService.getWorkTimeDurationSum(yesterdayMorning, yesterDayEvening, null)
		projectTimePreviousDay = statisticsDataService.getProjectTimeDurationSum(yesterdayMorning, yesterDayEvening, null, null)
		
		[workTimePreviousDay: workTimePreviousDay, projectTimePreviousDay: projectTimePreviousDay]
	}
	
	private List<User> findUsers() {
		def userList = []
		if (SpringSecurityUtils.ifAnyGranted('ROLE_STATISTICS')) {
			User.list().each {
				if ( it.authorities.each { obj -> obj.id == Role.findByAuthority("ROLE_TRACKER") } )
					userList << it
			}
		} else {
			userList << springSecurityService.currentUser
		}
		return userList
	}
}
