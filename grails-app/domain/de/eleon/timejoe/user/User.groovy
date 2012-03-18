package de.eleon.timejoe.user

import de.eleon.timejoe.tracking.ProjectTimeEntry
import de.eleon.timejoe.tracking.WorkTimeEntry


class User {
	
	def springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	static constraints = {
		username blank: false, unique: true, email: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	def beforeInsert() { 
		encodePassword()
	} 
	
	def beforeUpdate() { 
		if (isDirty('password')) 
			encodePassword()
	}
	
	private void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	def beforeDelete() {
		UserRole.withNewSession {
			UserRole.findAllByUser(this).each { it.delete(flush: true) }
		}
		WorkTimeEntry.withNewSession {
			WorkTimeEntry.findAllByUser(this).each { it.delete(flush: true) }
		}
		ProjectTimeEntry.withNewSession {
			ProjectTimeEntry.findAllByUser(this).each { it.delete(flush: true) }
		}
	}
	
	public String toString() {
		return this.username
	}
}
