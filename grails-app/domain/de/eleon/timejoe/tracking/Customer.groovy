package de.eleon.timejoe.tracking


class Customer {
	
	String name
	boolean bookable = true
	
	static mapping = {
		cache true
		sort name: "asc"
	}
	
    static constraints = {
		name blank: false, unique: true, size: 1..255
    }
	
	def beforeDelete() {
		ProjectTimeEntry.withNewSession {
			ProjectTimeEntry.findAllByCustomer(this).each { it.delete() }
		}
		AutoCompleteElem.withNewSession {
			AutoCompleteElem.findAllByCustomer(this).each { it.delete() }
		}
	}
	
	public String toString() {
		return this.name
	}
}
