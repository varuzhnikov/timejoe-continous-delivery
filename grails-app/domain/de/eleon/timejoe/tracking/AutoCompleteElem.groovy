package de.eleon.timejoe.tracking

class AutoCompleteElem implements Serializable {
	
	String text
	
	static belongsTo = [customer: Customer]
	
	static mapping = {
		cache true
		version false
		sort text: "asc"
		id composite:['customer', 'text']
	}

    static constraints = {
		customer nullable: false
		text blank: false, size: 1..255
    }
}
