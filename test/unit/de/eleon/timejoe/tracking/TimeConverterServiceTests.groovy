package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import groovy.time.TimeDuration

class TimeConverterServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMillisToTimeDuration() {
		def timeConverterService = new TimeConverterService()
		
		TimeDuration timeDuration
		TimeDuration timeDurationCompare
		long timeDurationMillis
		
		def date1 = new Date()
		def date2
		use (TimeCategory) {
			date2 = date1 + 1.day + 3.hours + 25.minutes + 11.seconds
			assert date1 != date2
			
			timeDuration = (date2 - date1)
			timeDurationMillis = timeDuration.toMilliseconds()
		}
		timeDurationCompare = timeConverterService.convertMillisToTimeDuration(timeDurationMillis)
		
		assert timeDuration.getDays() == timeDurationCompare.getDays()
		assert timeDuration.getHours() == timeDurationCompare.getHours()
		assert timeDuration.getMinutes() == timeDurationCompare.getMinutes()
		assert timeDuration.getSeconds() == timeDurationCompare.getSeconds()
    }
}
