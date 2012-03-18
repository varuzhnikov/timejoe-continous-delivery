
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="de.eleon.timejoe.tracking.Customer" %>
<%@ page import="de.eleon.timejoe.tracking.AutoCompleteElem" %>
<%@ page import="de.eleon.timejoe.user.User" %>
<html>
    <head>
        <title>Timejoe App</title>
        <meta name="layout" content="main" />
        <script type="text/javascript">
	    	$(document).ready(function() {
	    		$('.short').TooLong({ 'len' : 20 });
	    		$('.shortComment').TooLong({ 'len' : 30 });
			});
	    </script>
	    <style type="text/css">
	    	h2 { margin-bottom: 12px; }
	    	.box { padding: 20px; }
	    </style>
    </head>
    <body>
    	<h1><g:message code="page.index.headline" default="welcome" /></h1>
    	<sec:ifNotLoggedIn>
    		<p class="big"><g:message code="page.index.signin" default="signin" /></p>
    		<g:render template="/login/loginForm" />
    		<g:if env="development">
    		<h2>Test Login Data</h2>
	    		<p>If a user doesn't work, maybe someone has edited the account data. After next restart the original data will be restored.</p>
	    		<ul>
	    			<li>Administrator: admin@timejoe.com / password</li>
	    			<li>Project Manager: projectmanager@timejoe.com / password</li>
	    			<li>Team Members: member1@timejoe.com / password, member2@timejoe.com / password</li>
	    		</ul>
    		</g:if>
		</sec:ifNotLoggedIn>
		<sec:ifLoggedIn>
			<p class="big">You're signed in as <sec:loggedInUserInfo field="username" /></p>
			
			<sec:ifAnyGranted roles="ROLE_TRACKER">
				<g:include controller="projectTime" action="latest" />
				<g:include controller="workTime" action="latest" />
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_STATISTICS">
				<g:include controller="statistics" action="latest" />
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_CUSTOMER_MANAGER">
				<h2>Customer Summary</h2>
				<div class="box fullsize">
					<g:set var="customerCount" value="${Customer.count()}" />
					<p style="margin-bottom: 0;">
						<g:if test="${customerCount < 2}">
							Actually there is ${customerCount} Customer in the System.
						</g:if>
						<g:else>
							Actually there are ${customerCount} Customers in the System.
						</g:else>
					</p>
				</div>
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_AUTOCOMPLETE_MANAGER">
				<h2>Autocomplete Summary</h2>
				<div class="box fullsize">
					<g:set var="autoCompleteCount" value="${AutoCompleteElem.count()}" />
					<p style="margin-bottom: 0;">
						<g:if test="${autoCompleteCount < 2}">
							Actually there is ${autoCompleteCount} Autocomplete Element in the System.
						</g:if>
						<g:else>
							Actually there are ${autoCompleteCount} Autocomplete Elements in the System.
						</g:else>
					</p>
				</div>
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_USER_MANAGER">
				<h2>User Summary</h2>
				<div class="box fullsize">
					<g:set var="userCount" value="${User.count()}" />
					<p style="margin-bottom: 0;">
						<g:if test="${userCount < 2}">
							Actually there is ${userCount} User in the System.
						</g:if>
						<g:else>
							Actually there are ${userCount} User in the System.
						</g:else>
					</p>
				</div>
			</sec:ifAnyGranted>

		</sec:ifLoggedIn>
    </body>
</html>
