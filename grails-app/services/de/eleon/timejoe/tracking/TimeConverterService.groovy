package de.eleon.timejoe.tracking

import groovy.time.TimeDuration

class TimeConverterService {

    static transactional = false

    public TimeDuration convertMillisToTimeDuration(long milliseconds) {
		long millis = milliseconds
		def timeDuration
		long days = millis / (24 * 60 * 60 * 1000);
		millis -= days * 24 * 60 * 60 * 1000;
		int hours = (int) (millis / (60 * 60 * 1000));
		millis -= hours * 60 * 60 * 1000;
		int minutes = (int) (millis / (60 * 1000));
		millis -= minutes * 60 * 1000;
		int seconds = (int) (millis / 1000);
		millis -= seconds * 1000;
		return new TimeDuration((int) days, hours, minutes, seconds, (int) millis);
	}
}
