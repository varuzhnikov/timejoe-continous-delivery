package de.eleon.timejoe.tracking

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import de.eleon.timejoe.user.Role
import de.eleon.timejoe.user.User
import de.eleon.timejoe.user.UserRole

class StatisticsDataService {

    static transactional = false
	
	def grailsApplication
	def timeConverterService
	
	public List<ProjectTimeEntry> getProjectTimeEntries(Date from, Date to, User user, Customer customer) {
		def timeEntryList
		
		if (from && to && !user && !customer) {
			timeEntryList = ProjectTimeEntry.findAllByStartBetween(from, to, [cache: true])
		
		} else if (from && to && user && !customer) {
			timeEntryList = ProjectTimeEntry.findAllByUserAndStartBetween(user, from, to, [cache: true])
		
		} else if (from && to && !user && customer) {
			timeEntryList = ProjectTimeEntry.findAllByCustomerAndStartBetween(customer, from, to, [cache: true])
		
		} else if (from && to && user && customer) {
			timeEntryList = ProjectTimeEntry.executeQuery(
				'select e from ProjectTimeEntry e where e.user = :user and e.customer = :customer and e.start between :from and :to',
				[user: user, customer: customer, from: from, to: to],
				[cache: true]
			)
		}
		
		return timeEntryList
	}
	
	public TimeDuration getProjectTimeDurationSum(Date from, Date to, User user, Customer customer) {
		def durationSumMillis
		def durationSum
		
		if (from && to && !user && !customer) {
			durationSumMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry e where e.start between :from and :to and e.end is not null',
				[from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		
		} else if (from && to && user && !customer) {
			durationSumMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry e where e.user = :user and e.start between :from and :to and e.end is not null',
				[user: user, from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		
		} else if (from && to && !user && customer) {
			durationSumMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry e where e.customer = :customer and e.start between :from and :to and e.end is not null',
				[customer: customer, from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		
		} else if (from && to && user && customer) {
			durationSumMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry e where e.user = :user and e.customer = :customer and e.start between :from and :to and e.end is not null',
				[user: user, customer: customer, from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		}
		
		return durationSum
	}
	
	public List<WorkTimeEntry> getWorkTimeEntries(Date from, Date to, User user) {
		def timeEntryList
		
		if (from && to && !user) {
			timeEntryList = WorkTimeEntry.findAllByStartBetween(from, to, [cache: true])
		
		} else if (from && to && user) {
			timeEntryList = WorkTimeEntry.findAllByUserAndStartBetween(user, from, to, [cache: true])
		
		}
		
		return timeEntryList
	}
	
	public TimeDuration getWorkTimeDurationSum(Date from, Date to, User user) {
		def durationSumMillis
		def durationSum
		
		if (from && to && !user) {
			durationSumMillis = WorkTimeEntry.executeQuery(
				'select sum(e.durationMillis) from WorkTimeEntry e where e.start between :from and :to and e.end is not null',
				[from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		
		} else if (from && to && user) {
			durationSumMillis = WorkTimeEntry.executeQuery(
				'select sum(e.durationMillis) from WorkTimeEntry e where e.user = :user and e.start between :from and :to and e.end is not null',
				[user: user, from: from, to: to],
				[cache: true]
			)
			if (durationSumMillis[0]) durationSum = timeConverterService.convertMillisToTimeDuration(durationSumMillis[0])
		
		}
		
		return durationSum
	}
	
	public HashMap<String, Float> getLatestCustomerProjecttime(User user) {
		HashMap customerProjecttime = new HashMap<String, Float>()
		
		Calendar cal = Calendar.getInstance()
		Date end = cal.getTime()
		
		def durationInMillis
		def durationInHours
		
		int i = 0
		Customer.list().each {
			durationInMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry as e where e.start is not null and e.start between :start and :end and e.end is not null and e.customer = :customer and e.user = :user',
				[start: end-9, end: end, customer: it, user: user],
				[cache: true]
			)
			
			if (durationInMillis[0]) {
				durationInHours = ( durationInMillis[0] / 1000 / 3600 ) as Float
				
				customerProjecttime.addEntry(i, it.name, durationInHours.round(2), i)
				i++
			}
		}
		
		return customerProjecttime
	}
	
	public HashMap<String, Float> getLatestCustomerProjecttime() {
		HashMap customerProjecttime = new HashMap<String, Float>()
		
		Calendar cal = Calendar.getInstance()
		Date end = cal.getTime()
		
		def durationInMillis
		def durationInHours
		
		int i = 0
		Customer.list().each {
			durationInMillis = ProjectTimeEntry.executeQuery(
				'select sum(e.durationMillis) from ProjectTimeEntry as e where e.start is not null and e.start between :start and :end and e.end is not null and e.customer = :customer',
				[start: end-9, end: end, customer: it],
				[cache: true]
			)
			
			if (durationInMillis[0]) {
				durationInHours = ( durationInMillis[0] / 1000 / 3600 ) as Float
				
				customerProjecttime.addEntry(i, it.name, durationInHours.round(2), i)
				i++
			}
		}
		
		return customerProjecttime
	}
	
	
	public LinkedHashMap<String, Float> getProjectHoursOfMembers(Date from, Date to) {
		return getHoursOfMembers(from, to, "de.eleon.timejoe.tracking.ProjectTimeEntry")
	}
	
	public LinkedHashMap<String, Float> getWorkHoursOfMembers(Date from, Date to) {
		return getHoursOfMembers(from, to, "de.eleon.timejoe.tracking.WorkTimeEntry")
	}
	
	private LinkedHashMap<String, Float> getHoursOfMembers(Date from, Date to, String classname) {
		LinkedHashMap membersHoursList = new HashMap<String, Float>()
		def durationInMillis
		def durationInHours
		def trackerRole = Role.findByAuthority("ROLE_TRACKER")
		
		int i = 0
		String hql = 'select sum(e.durationMillis) from ' + classname + ' as e where e.start is not null and e.start between :start and :end and e.end is not null and e.user = :user'
		User.list().each {
			if (UserRole.findByUserAndRole(it, trackerRole)) {
				durationInMillis = ProjectTimeEntry.executeQuery(
					hql,
					[start: from, end: to, user: it],
					[cache: true]
				)
					
				if (!durationInMillis[0])
					durationInHours = 0.00001F
				else
					durationInHours = (durationInMillis[0] / 1000 / 3600) as Float
					
				membersHoursList.addEntry(i, it.username, durationInHours.round(2), i)
				i++
			}
		}
		
		return membersHoursList
	}
	
	
	public LinkedHashMap<Date, Float> getLatestWorkHours(User user) {
		return getLatestHours(user, "de.eleon.timejoe.tracking.WorkTimeEntry")
	}
	
	public LinkedHashMap<Date, Float> getLatestProjectHours(User user) {
		return getLatestHours(user, "de.eleon.timejoe.tracking.ProjectTimeEntry")
	}
	
	private LinkedHashMap<Date, Float> getLatestHours(User user, String classname) {
		LinkedHashMap latestDailyHoursList = new LinkedHashMap<Date, Float>()
		
		def durationOfDayInMillis
		def durationOfDayInHours
		
		int i = 0
		for (day in getLast10Days()) {
			durationOfDayInMillis = grailsApplication.getArtefact("Domain", classname).getClazz().durationSumForUserAndDay(user, getCalendarFromDate(day))
			if (!durationOfDayInMillis)
				durationOfDayInHours = 0.00001F
			else
				durationOfDayInHours = (durationOfDayInMillis / 1000 / 3600) as Float
				
			latestDailyHoursList.addEntry(i, day, durationOfDayInHours.round(2), i)
			i++
		}
		
		return latestDailyHoursList
	}
	
	
	public LinkedHashMap<Date, Float> getLatestWorkHours() {
		return getLatestHours("de.eleon.timejoe.tracking.WorkTimeEntry")
	}
	
	public LinkedHashMap<Date, Float> getLatestProjectHours() {
		return getLatestHours("de.eleon.timejoe.tracking.ProjectTimeEntry")
	}
	
	private LinkedHashMap<Date, Float> getLatestHours(String classname) {
		LinkedHashMap latestDailyHoursList = new LinkedHashMap<Date, Float>()
		
		def durationOfDayInMillis
		def durationOfDayInHours
		
		int i = 0
		String hql = 'select sum(e.durationMillis) from ' + classname + ' as e where year(e.start) = :year and month(e.start) = :month and day(e.start) = :day and e.end is not null'
		for (day in getLast10Days()) {
			durationOfDayInMillis = grailsApplication.getArtefact("Domain", classname).getClazz().executeQuery(
				hql,
				[year: getCalendarFromDate(day).get(Calendar.YEAR), month: getCalendarFromDate(day).get(Calendar.MONTH)+1, day: getCalendarFromDate(day).get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
			if (!durationOfDayInMillis[0])
				durationOfDayInHours = 0.00001F
			else
				durationOfDayInHours = ( durationOfDayInMillis[0] / 1000 / 3600 ) as Float
				
			latestDailyHoursList.addEntry(i, day, durationOfDayInHours.round(2), i)
			i++
		}
		
		return latestDailyHoursList
	}
	
	
	private Range<Date> getLast10Days() {
		Calendar cal = Calendar.getInstance()
		
		Date start
		Date end
		
		end = cal.getTime()
		
		use (TimeCategory) {
			start = end - 9.days
		}
		
		return start..end
	}
	
	private Calendar getCalendarFromDate(Date date) {
		def cal = Calendar.getInstance()
		cal.setTime(date)
		return cal
	}
	
}
