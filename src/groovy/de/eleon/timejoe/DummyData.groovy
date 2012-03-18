package de.eleon.timejoe

import groovy.time.TimeCategory
import de.eleon.timejoe.tracking.AutoCompleteElem
import de.eleon.timejoe.tracking.Customer
import de.eleon.timejoe.tracking.ProjectTimeEntry
import de.eleon.timejoe.tracking.WorkTimeEntry
import de.eleon.timejoe.user.Role
import de.eleon.timejoe.user.User
import de.eleon.timejoe.user.UserRole

class DummyData {

	public static void cleanUpAndCreateDummyData() {
		cleanUp()
		createDummyData()
	}
	
	private static void cleanUp() {
		UserRole.executeUpdate('delete UserRole ur')
		AutoCompleteElem.executeUpdate('delete AutoCompleteElem ace')
		ProjectTimeEntry.executeUpdate('delete ProjectTimeEntry pte')
		Customer.executeUpdate('delete Customer c')
		WorkTimeEntry.executeUpdate('delete WorkTimeEntry wte')
		User.executeUpdate('delete User u')
	}
	
	private static void createDummyData()  {
		def admin = User.findByUsername('admin@timejoe.com')
		if (!admin) {
			admin = new User(username: 'admin@timejoe.com', enabled: true, password: 'password').save()
			Role.list().each {
				UserRole.create admin, it, true
			}
		}
		
		def customerA = new Customer(name: 'Customer A').save()
		def customerB = new Customer(name: 'Customer B').save()
		def customerC = new Customer(name: 'Customer C').save()
		
		def member1 = new User(username: 'member1@timejoe.com', enabled: true, password: 'password').save()
		UserRole.create member1, Role.findByAuthority('ROLE_TRACKER'), true
		def member2 = new User(username: 'member2@timejoe.com', enabled: true, password: 'password').save()
		UserRole.create member2, Role.findByAuthority('ROLE_TRACKER'), true
		def projectmanager = new User(username: 'projectmanager@timejoe.com', enabled: true, password: 'password').save()
		UserRole.create projectmanager, Role.findByAuthority('ROLE_TRACKER'), true
		UserRole.create projectmanager, Role.findByAuthority('ROLE_STATISTICS'), true
		UserRole.create projectmanager, Role.findByAuthority('ROLE_CUSTOMER_MANAGER'), true
		UserRole.create projectmanager, Role.findByAuthority('ROLE_AUTOCOMPLETE_MANAGER'), true
		
		def today = new Date()
		today.putAt(Calendar.HOUR_OF_DAY, 9)
		today.putAt(Calendar.MINUTE, 0)
		def beforeTenDays = today-10
		
		for (date in beforeTenDays..today) {
			new WorkTimeEntry( user: member1, start: date, end: use(TimeCategory) { date + 8.hours } ).save()
			new WorkTimeEntry( user: member2, start: date, end: use(TimeCategory) { date + 8.hours } ).save()
			new WorkTimeEntry( user: projectmanager, start: date, end: use(TimeCategory) { date + 8.hours } ).save()
			new WorkTimeEntry( user: admin, start: date, end: use(TimeCategory) { date + 8.hours } ).save()
			
			new ProjectTimeEntry( user: member1, customer: customerA, comment: 'Customer Portal; Frontend Development', start: date, end: use(TimeCategory) { date + 3.hours } ).save()
			new ProjectTimeEntry( user: member1, customer: customerB, comment: 'Website Relaunch; AJAX Search Form', start: date, end: use(TimeCategory) { date + 4.hours } ).save()
			
			new ProjectTimeEntry( user: member2, customer: customerA, comment: 'Customer Portal; Refactoring Message Boards', start: date, end: use(TimeCategory) { date + 3.hours + 30.minutes } ).save()
			new ProjectTimeEntry( user: member2, customer: customerC, comment: 'Support', start: date, end: use(TimeCategory) { date + 3.hours } ).save()
			
			new ProjectTimeEntry( user: projectmanager, customer: customerA, comment: 'Customer Portal; Organize Product Backlog', start: date, end: use(TimeCategory) { date + 3.hours } ).save()
			new ProjectTimeEntry( user: projectmanager, customer: customerA, comment: 'Customer Portal; Meetings with Client', start: date, end: use(TimeCategory) { date + 1.hours } ).save()
			new ProjectTimeEntry( user: projectmanager, customer: customerC, comment: 'Support', start: date, end: use(TimeCategory) { date + 10.minutes } ).save()
	
		}
	}
}
