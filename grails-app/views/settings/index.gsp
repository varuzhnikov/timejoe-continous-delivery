<html>
    <head>
        <title>Timejoe Settings</title>
        <meta name="layout" content="main" />
    </head>
    <body>
    	<h1>Settings</h1>
    	<p class="big">Please select an option to continue.</p>
    	
    	<ul>
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
    	
    </body>
</html>
