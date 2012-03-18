package de.eleon.timejoe.user

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNewUser() {
		mockDomain(User)
		def userInstance = new User()
		assertFalse "Validierung sollte scheitern.", userInstance.validate()
		assertEquals "nullable", userInstance.errors.username
		
		userInstance.username = "mail@example.com"
		assertFalse "Validierung sollte scheitern.", userInstance.validate()
		assertEquals "nullable", userInstance.errors.password
		
		userInstance.password = "password"
		assertTrue "Validierung sollte erfolgreich sein.", userInstance.validate()
    }
	
}
