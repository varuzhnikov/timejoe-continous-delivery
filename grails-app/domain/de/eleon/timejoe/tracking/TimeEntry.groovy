package de.eleon.timejoe.tracking

import java.util.Calendar;
import java.util.List;

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import de.eleon.timejoe.user.User

abstract class TimeEntry {
	
	Date start
	Date end
	Long durationMillis
	
	static belongsTo = [user: User]
	
	static transients = ['complete', 'duration']
	
	static mapping = {
		cache true
		sort start: "asc"
		end column: "endDate"
	}

    static constraints = {
		start nullable: false
		end nullable: true, validator: { val, obj ->
			if ( val && (val.after(obj.start + 1)) )
				return "error.end.moreThan24Hours"
			if ( val && val.before(obj.start) )
				return "error.end.isBeforeStartDate"
		}
		durationMillis nullable: true
		user nullable: false
    }
	
	def beforeInsert() {
			this.durationMillis = this.duration?.toMilliseconds()
	}
	
	def beforeUpdate() {
		if (isDirty('start') || isDirty('end'))
			this.durationMillis = this.duration?.toMilliseconds()
	}
	
	public boolean getComplete() {
		if (this.start && this.end)
			return true
		return false
	}
	
	public TimeDuration getDuration() {
		if (!this.complete)
			return null
		
		use (TimeCategory) {
			return this.end - this.start
		}
	}
	
	private static List<TimeEntry> allForUserAndDay(User user, Calendar date, String classname) {
		String hql = 'from ' + classname + ' as e where e.user = :user and year(e.start) = :year and month(e.start) = :month and day(e.start) = :day'
		def trackingDayList
		
		if (classname == "WorkTimeEntry") {
			trackingDayList = WorkTimeEntry.findAll(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		} else if (classname == "ProjectTimeEntry") {
			trackingDayList = ProjectTimeEntry.findAll(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		}
		
		return trackingDayList
	}
	
	private static List<TimeEntry> completedForUserAndDay(User user, Calendar date, String classname) {
		String hql = 'from ' + classname + ' as e where e.user = :user and year(e.start) = :year and month(e.start) = :month and day(e.start) = :day and e.end is not null'
		def trackingDayList 
		
		if (classname == "WorkTimeEntry") {
			trackingDayList = WorkTimeEntry.findAll(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			) 
		} else if (classname == "ProjectTimeEntry") {
			trackingDayList = ProjectTimeEntry.findAll(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		}
		
		return trackingDayList
	}
	
	private static TimeEntry runningForUserAndDay(User user, Calendar date, String classname) {
		String hql = 'from ' + classname + ' as e where e.user = :user and year(e.start) = :year and month(e.start) = :month and day(e.start) = :day and e.end is null'
		def runningEntry 
		
		if (classname == "WorkTimeEntry") {
			runningEntry = WorkTimeEntry.find(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		} else if (classname == "ProjectTimeEntry") {
			runningEntry = ProjectTimeEntry.find(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		}
		return runningEntry
	}
	
	private static Long durationSumForUserAndDay(User user, Calendar date, String classname) {
		String hql = 'select sum(e.durationMillis) from ' + classname + ' as e where e.user = :user and year(e.start) = :year and month(e.start) = :month and day(e.start) = :day and e.end is not null'
		def durationSumMillis
		
		if (classname == "WorkTimeEntry") {
			durationSumMillis = WorkTimeEntry.executeQuery(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		} else if (classname == "ProjectTimeEntry") {
			durationSumMillis = ProjectTimeEntry.executeQuery(
				hql,
				[user: user, year: date.get(Calendar.YEAR), month: date.get(Calendar.MONTH)+1, day: date.get(Calendar.DAY_OF_MONTH)],
				[cache: true]
			)
		}
		
		return durationSumMillis[0]
	}
}
