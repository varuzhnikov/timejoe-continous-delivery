package de.eleon.timejoe.tracking

import de.eleon.timejoe.user.User

class ProjectTimeEntry extends TimeEntry {

	Customer customer
	String comment
	
    static constraints = {
		customer nullable: false
		comment nullable: true, blank: true, size: 0..255
    }
	
	def afterInsert() {
		AutoCompleteElem.withNewSession {
			if (!AutoCompleteElem.findByCustomerAndText(this.customer, this.comment))
				new AutoCompleteElem(customer: this.customer, text: this.comment).save()
		}
	}
	
	public static List<ProjectTimeEntry> allForUserAndDay(User user, Calendar date) {
		return allForUserAndDay(user, date, "ProjectTimeEntry")
	}
	
	public static List<ProjectTimeEntry> completedForUserAndDay(User user, Calendar date) {
		return completedForUserAndDay(user, date, "ProjectTimeEntry")
	}
	
	public static ProjectTimeEntry runningForUserAndDay(User user, Calendar date) {
		return runningForUserAndDay(user, date, "ProjectTimeEntry")
	}
	
	public static Long durationSumForUserAndDay(User user, Calendar date) {
		return durationSumForUserAndDay(user, date, "ProjectTimeEntry")
	}
}
