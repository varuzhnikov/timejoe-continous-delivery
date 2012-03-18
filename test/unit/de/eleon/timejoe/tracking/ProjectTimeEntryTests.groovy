package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.user.User

class ProjectTimeEntryTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNewProjectTimeEntry() {
		mockDomain(ProjectTimeEntry)
		mockDomain(User)
		mockDomain(Customer)
		
		def projectTimeEntryInstance = new ProjectTimeEntry()
		projectTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		projectTimeEntryInstance.customer = new Customer(name: "Customer")
		projectTimeEntryInstance.comment = "Comment"
		
		use (TimeCategory) {
			projectTimeEntryInstance.start = new Date()
		}
		assertTrue "Validierung sollte erfolgreich sein.", projectTimeEntryInstance.validate()
		
		use (TimeCategory) {
			projectTimeEntryInstance.end = new Date() + 8.hours
		}
		assertTrue "Validierung sollte erfolgreich sein.", projectTimeEntryInstance.validate()
    }
	
	void testCompleteEntries() {
		mockDomain(ProjectTimeEntry)
		mockDomain(User)
		mockDomain(Customer)
		
		def projectTimeEntryInstance = new ProjectTimeEntry()
		projectTimeEntryInstance.user = new User(username: "test@example.com", password: "password")
		projectTimeEntryInstance.customer = new Customer(name: "Customer")
		projectTimeEntryInstance.comment = "Comment"
		
		use (TimeCategory) {
			projectTimeEntryInstance.start = new Date()
		}
		assertFalse "Sollte nicht -complete- sein", projectTimeEntryInstance.complete
		
		use (TimeCategory) {
			projectTimeEntryInstance.end = new Date() + 8.hours
		}
		assertTrue "Sollte -complete- sein", projectTimeEntryInstance.complete
	}
}
