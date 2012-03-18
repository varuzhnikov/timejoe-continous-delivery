		<ul id="subnav">
			<sec:ifAnyGranted roles="ROLE_USER_MANAGER">
    			<li class="${(params.controller == "user" && (params.action == "list" || params.action == "save")) ? "hit" : ""}"><g:link controller="user">Manage User</g:link></li>
    		</sec:ifAnyGranted>
    		<sec:ifAnyGranted roles="ROLE_CUSTOMER_MANAGER">
    			<li class="${(params.controller == "customer") ? "hit" : ""}"><g:link controller="customer">Manage Customers</g:link></li>
    		</sec:ifAnyGranted>
    		<sec:ifAnyGranted roles="ROLE_AUTOCOMPLETE_MANAGER">
    			<li class="${(params.controller == "autoComplete") ? "hit" : ""}"><g:link controller="autoComplete">Manage Autocomplete</g:link></li>
    		</sec:ifAnyGranted>
    	</ul>
    	<div class="clearer"></div>