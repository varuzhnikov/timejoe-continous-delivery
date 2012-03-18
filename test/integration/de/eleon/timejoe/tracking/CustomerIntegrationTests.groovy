package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.user.User

class CustomerIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCascadingDeletion() {
		def customerInstance = new Customer(name: "Testcustomer").save(flush: true)
		def userInstance = new User(username: "testuser@example.com", password: "password").save()
		
		def projectTimeEntryInstance = new ProjectTimeEntry()
		projectTimeEntryInstance.user = userInstance
		projectTimeEntryInstance.customer = customerInstance
		projectTimeEntryInstance.comment = "Comment"
		use (TimeCategory) {
			projectTimeEntryInstance.start = new Date()
			projectTimeEntryInstance.end = new Date() + 8.hours
		}
		projectTimeEntryInstance.save(flush: true)
		
		assertEquals 1, ProjectTimeEntry.findAllByCustomerAndComment(customerInstance, projectTimeEntryInstance.comment).size()
		assertEquals 1, AutoCompleteElem.findAllByCustomerAndText(customerInstance, projectTimeEntryInstance.comment).size()
		
		customerInstance.delete(flush: true)
		assertEquals 0, ProjectTimeEntry.findAllByCustomerAndComment(customerInstance, projectTimeEntryInstance.comment).size
		assertEquals 0, AutoCompleteElem.findAllByCustomerAndText(customerInstance, projectTimeEntryInstance.comment).size()
    }
}
