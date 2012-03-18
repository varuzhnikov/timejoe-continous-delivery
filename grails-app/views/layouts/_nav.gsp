<ul id="navigation">
	<sec:ifAnyGranted roles="ROLE_TRACKER">
		<li class="${(params.controller == "workTime") ? "hit" : ""}"><g:link controller="workTime"><g:message code="navigation.worktime" default="worktime" /></g:link></li>
		<li class="${(params.controller == "projectTime") ? "hit" : ""}"><g:link controller="projectTime"><g:message code="navigation.projecttime" default="projecttime" /></g:link></li>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_TRACKER, ROLE_STATISTICS">
		<li class="${(params.controller == "statistics") ? "hit" : ""}"><g:link controller="statistics"><g:message code="navigation.statistics" default="statistics" /></g:link>
			<g:render template="/statistics/nav" />
		</li>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_CUSTOMER_MANAGER, ROLE_AUTOCOMPLETE_MANAGER, ROLE_USER_MANAGER">
		<li class="${(params.controller == "settings" || params.controller == "customer" || params.controller == "autoComplete" || params.controller == "user") ? "hit" : ""}"><g:link controller="settings"><g:message code="navigation.settings" default="settings" /></g:link>
			<g:render template="/settings/nav" />
		</li>
	</sec:ifAnyGranted>
	
	<li class="logout"><g:link controller="logout"><g:message code="navigation.logout" default="logout" /></g:link></li>
</ul>
<div class="clearer"></div>