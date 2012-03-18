package de.eleon.timejoe.tracking

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_TRACKER', 'ROLE_STATISTICS', 'ROLE_CUSTOMER_MANAGER', 'ROLE_AUTOCOMPLETE_MANAGER', 'ROLE_USER_MANAGER'])
class SettingsController {

    def index = { }
}
