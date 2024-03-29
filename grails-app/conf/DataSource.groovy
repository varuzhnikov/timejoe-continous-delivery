dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
} 
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
        	dbCreate = "update"
			url = "jdbc:hsqldb:file:devDB;shutdown=true"    
        }
    }
    test {
        dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:testDB;shutdown=true"
        }
    }
    production {
        dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodDB;shutdown=true"
        }
    }
}
