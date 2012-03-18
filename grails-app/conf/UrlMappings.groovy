class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/workTime/date/$date"(controller: 'workTime', action: 'date')
		"/projectTime/date/$date"(controller: 'projectTime', action: 'date')

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
