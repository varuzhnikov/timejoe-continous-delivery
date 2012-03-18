package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.user.User

class WorkTimeEntryTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNewWorkTimeEntry() {
		mockDomain(WorkTimeEntry)
		mockDomain(User)
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date()
			assertTrue "Validierung sollte erfolgreich sein.", workTimeEntryInstance.validate()
			workTimeEntryInstance.end = new Date() + 8.hours
		}
		assertTrue "Validierung sollte erfolgreich sein.", workTimeEntryInstance.validate()
    }
	
	void testEndDateNotBeforeStartDate() {
		mockDomain(WorkTimeEntry)
		mockDomain(User)
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date()
			workTimeEntryInstance.end = new Date() - 1.hours
		}
		assertFalse "Validierung sollte am EndDate scheitern.", workTimeEntryInstance.validate()
		assertEquals "error.end.isBeforeStartDate", workTimeEntryInstance.errors.end
	}
	
	void testTimeEntryNotMoreThan24Hours() {
		mockDomain(WorkTimeEntry)
		mockDomain(User)
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date() - 12.hours
			workTimeEntryInstance.end = new Date()
		}
		assertTrue "Validierung sollte erfolgreich sein.", workTimeEntryInstance.validate()
		
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date() - 25.hours
			workTimeEntryInstance.end = new Date()
		}
		assertFalse "Validierung sollte am EndDate scheitern.", workTimeEntryInstance.validate()
		assertEquals "error.end.moreThan24Hours", workTimeEntryInstance.errors.end
	}
	
	void testCompleteEntries() {
		mockDomain(WorkTimeEntry)
		mockDomain(User)
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date()
		}
		assertFalse "Sollte nicht -complete- sein", workTimeEntryInstance.complete
		
		use (TimeCategory) {
			workTimeEntryInstance.end = new Date() + 8.hours
		}
		assertTrue "Sollte -complete- sein", workTimeEntryInstance.complete
	}
}
