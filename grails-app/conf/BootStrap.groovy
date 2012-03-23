import de.eleon.timejoe.DummyData
import de.eleon.timejoe.user.Role
import de.eleon.timejoe.user.User
import de.eleon.timejoe.user.UserRole

class BootStrap {
	
    def init = { servletContext ->
		
		String[] roles = [
			"ROLE_TRACKER",
			"ROLE_STATISTICS",
			"ROLE_CUSTOMER_MANAGER",
			"ROLE_AUTOCOMPLETE_MANAGER",
			"ROLE_USER_MANAGER"
			]
		roles.each { 
			if (!Role.findByAuthority("${it}"))
				new Role(authority: "${it}").save(flush: true)
		}
		
		environments {
            development {
                DummyData.cleanUpAndCreateDummyData()
            }
            test {
                DummyData.cleanUpAndCreateDummyData()
            }
            production {
				if (!UserRole.findByRole(Role.findByAuthority("ROLE_USER_MANAGER"))) {
					def admin = new User(username: 'admin@timejoe.com', enabled: true, password: 'password').save()
					Role.list().each {
						UserRole.create admin, it, true
					}
				}
			}
		}
		
    }
	
    def destroy = {
    }
}
