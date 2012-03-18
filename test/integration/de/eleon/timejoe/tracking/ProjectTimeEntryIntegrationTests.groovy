package de.eleon.timejoe.tracking

import grails.test.*
import groovy.time.TimeCategory
import de.eleon.timejoe.user.User

class ProjectTimeEntryIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAutoCompleteCreation() {
		def userInstance = new User(username: "mail@example.com", password: "password").save()
		def customerInstance = new Customer(name: "Customer").save()
		
		def projectTimeEntryInstance = new ProjectTimeEntry()
		projectTimeEntryInstance.user = userInstance
		projectTimeEntryInstance.customer = customerInstance
		projectTimeEntryInstance.comment = "Comment"
		use (TimeCategory) {
			projectTimeEntryInstance.start = new Date()
			projectTimeEntryInstance.end = new Date() + 8.hours
		}
		projectTimeEntryInstance.save()
		
		assertTrue "AutoCompleteElem sollte jetzt den Eintrag -Comment- enthalten.", AutoCompleteElem.findAllByCustomerAndText(customerInstance, projectTimeEntryInstance.comment).size() == 1
    }
	
	void testDuplicateAutoCompleteCreation() {
		def userInstance = new User(username: "mail@example.com", password: "password").save()
		def customerInstance = new Customer(name: "Customer").save()
		
		def projectTimeEntry1 = new ProjectTimeEntry()
		projectTimeEntry1.user = userInstance
		projectTimeEntry1.customer = customerInstance
		projectTimeEntry1.comment = "Testcomment"
		use (TimeCategory) {
			projectTimeEntry1.start = new Date()
			projectTimeEntry1.end = new Date() + 8.hours
		}
		projectTimeEntry1.save()
		
		assertTrue "AutoCompleteElem sollte jetzt den Eintrag -Comment- enthalten.", AutoCompleteElem.list().size() == 1
		
		// gleicher Text
		def projectTimeEntry2 = new ProjectTimeEntry()
		projectTimeEntry2.user = userInstance
		projectTimeEntry2.customer = customerInstance
		projectTimeEntry2.comment = "Testcomment"
		use (TimeCategory) {
			projectTimeEntry2.start = new Date()
			projectTimeEntry2.end = new Date() + 8.hours
		}
		projectTimeEntry2.save()
		
		assertTrue "AutoCompleteElem sollte nur einen Eintrag enthalten, Duplikat wird nicht angelegt.", AutoCompleteElem.list().size() == 1
		
		// anderer Text
		def projectTimeEntry3 = new ProjectTimeEntry()
		projectTimeEntry3.user = userInstance
		projectTimeEntry3.customer = customerInstance
		projectTimeEntry3.comment = "Another Testcomment"
		use (TimeCategory) {
			projectTimeEntry3.start = new Date()
			projectTimeEntry3.end = new Date() + 8.hours
		}
		projectTimeEntry3.save()
		
		assertTrue "AutoCompleteElem sollte jetzt zwei Einträge enthalten.", AutoCompleteElem.list().size() == 2
		
		// anderer Kunde
		def anotherCustomer = new Customer(name: "Another Customer").save()
		def projectTimeEntry4 = new ProjectTimeEntry()
		projectTimeEntry4.user = userInstance
		projectTimeEntry4.customer = anotherCustomer
		projectTimeEntry4.comment = "Another Testcomment"
		use (TimeCategory) {
			projectTimeEntry4.start = new Date()
			projectTimeEntry4.end = new Date() + 8.hours
		}
		projectTimeEntry4.save()
		
		assertTrue "AutoCompleteElem sollte jetzt drei Einträge enthalten.", AutoCompleteElem.list().size() == 3
	}
}
