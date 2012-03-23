package de.eleon.timejoe.tracking

import grails.test.*

class CustomerTests extends GrailsUnitTestCase {
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNewCustomer() {
		mockDomain(Customer)
		def customerInstance = new Customer()
		assertFalse "Validierung sollte scheitern.", customerInstance.validate()
		assertEquals "nullable", customerInstance.errors.name
		
		customerInstance.name = "Customer"
		assertTrue "Validierung sollte erfolgreich sein.", customerInstance.validate()
    }
	
	void testDuplicateCustomers() {
		mockDomain(Customer)
		
		def customer1 = new Customer()
		customer1.name = "Customer"
		assertFalse "Validierung sollte erfolgreich sein.", customer1.validate()
		customer1.save()
		
		def customer2 = new Customer()
		customer2.name = "Customer"
		assertFalse "Validierung sollte wegen Duplikat scheitern.", customer2.validate()
	}
	
}
