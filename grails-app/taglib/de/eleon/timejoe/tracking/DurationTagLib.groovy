package de.eleon.timejoe.tracking

import groovy.time.TimeDuration

class DurationTagLib {
	
	static namespace = "timejoe"
	
	def duration = { attrs, body ->
		def duration = attrs?.duration
		if (duration) {
			out << """
				<nobr>
				${duration?.getHours() >= 12 ? "<span class='error'>" : ""}
				${duration?.getHours() <= 9 ? "0" : ""}${duration?.getHours()} ${message(code: 'timeentry.hours')} ${duration?.getMinutes() <= 9 ? "0" : ""}${duration?.getMinutes()} ${message(code: 'timeentry.minutes')}
				${duration?.getHours() >= 12 ? "</span>" : ""}
				</nobr>
				"""
		} else {
			out << ""
		}
	}

}
