package de.eleon.timejoe.tracking

import java.util.Calendar;
import java.util.List;

import de.eleon.timejoe.user.User;

class WorkTimeEntry extends TimeEntry {
	
	public static List<WorkTimeEntry> allForUserAndDay(User user, Calendar date) {
		return allForUserAndDay(user, date, "WorkTimeEntry")
	}
	
	public static List<WorkTimeEntry> completedForUserAndDay(User user, Calendar date) {
		return completedForUserAndDay(user, date, "WorkTimeEntry")
	}
	
	public static WorkTimeEntry runningForUserAndDay(User user, Calendar date) {
		return runningForUserAndDay(user, date, "WorkTimeEntry")
	}
	
	public static Long durationSumForUserAndDay(User user, Calendar date) {
		return durationSumForUserAndDay(user, date, "WorkTimeEntry")
	}

}
