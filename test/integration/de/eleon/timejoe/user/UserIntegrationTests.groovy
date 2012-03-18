package de.eleon.timejoe.user

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.tracking.WorkTimeEntry

class UserIntegrationTests extends GroovyTestCase {
	
	def springSecurityService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testInitialPasswordEncoding() {
		def userInstance = new User()
		
		userInstance.username = "test@example.com"
		userInstance.password = "password"
		userInstance.save()
		
		assert userInstance.password != "password"
	}
	
	void testMailDuplicates() {
		def user1 = new User()
		user1.username = "1@example.com"
		user1.password = "password"
		assertTrue "Validierung sollte erfolgreich sein.", user1.validate()
		user1.save()
		
		def user2 = new User()
		user2.username = "1@example.com"
		user2.password = "password"
		assertFalse "Validierung sollte scheitern.", user2.validate()
		//assertEquals "unique", user2.errors.username
		
		def user3 = new User()
		user3.username = "3@example.com"
		user3.password = "password"
		assertTrue "Validierung sollte erfolgreich sein.", user3.validate()
		user3.save()
	}
	
	void testCascadingDeletion() {
		def user = new User(username: "WillNotLiveLongTime@example.com", password: "password")
		user.save(flush: true)
		assertEquals 1, User.findAllByUsername("WillNotLiveLongTime@example.com").size()
		
		def workTimeEntryInstance = new WorkTimeEntry()
		workTimeEntryInstance.user = user
		use (TimeCategory) {
			workTimeEntryInstance.start = new Date()
			workTimeEntryInstance.end = new Date() + 8.hours
		}
		workTimeEntryInstance.save(flush: true)
		assertEquals 1, WorkTimeEntry.findAllByUser(user).size()
		
		user.delete(flush: true)
		assertEquals 0, User.findAllByUsername("WillNotLiveLongTime@example.com").size()
		assertEquals 0, WorkTimeEntry.findAllByUser(user).size()
	}
	
}
