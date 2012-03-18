package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.user.User

class WorkTimeIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNewWorkTimeEntry() {
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = new User(username: "testNewWorkTimeEntry@example.com", password: "password").save()
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date()
			workTimeEntryInstance.end = new Date() + 8.hours
		}
		workTimeEntryInstance.save(flush: true)
		assertEquals 1, WorkTimeEntry.findAllByUser(User.findByUsername("testNewWorkTimeEntry@example.com")).size()
    }
}
