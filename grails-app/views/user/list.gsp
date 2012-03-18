
<%@ page import="de.eleon.timejoe.user.User" %>
<html>
    <head>
        <title>Timejoe User</title>
        <meta name="layout" content="main" />
         <link type="text/css" href="${resource(dir:'css',file:'password_strength.css')}" rel="Stylesheet" />	
        <script type="text/javascript" src="${resource(dir:'js',file:'password_strength.js')}"></script>
        <script type="text/javascript">
	        $(document).ready(function() {
	    		passwordStrength();
	        });
	        function passwordStrength() {
	        	$('.password').password_strength();
			}
        </script>
        <style type="text/css">
	    	.field label { width: auto; }
	    	.field input { margin-left: 10px; margin-right: 10px; }
	    	.field input.checkbox { margin-left: 0; }
	    	.field .checkboxField { display: block; float: left; }
	    	.field .checkboxField label { margin-right: 10px; }
	    	.box { position: relative; }
	    	#mainFormButton { position: absolute; bottom: 20px; right: 20px; }
	    </style>
    </head>
    <body>
    	
   		<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
	        <g:hasErrors bean="${userInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${userInstance}" as="list" />
	            </div>
            </g:hasErrors>
	        <g:form action="save" autocomplete="off">
		        <div class="field">
					<label for="username"><g:message code="user.username.label" default="username" /></label>
	                <g:textField name="username" value="" />
	                <label for="password"><g:message code="user.password.label" default="password" /></label>
	                <input type="password" name="password" class="password" value="" style="margin-right: 0;" />
				</div>
				<div class="field" style="margin-bottom: 8px;">
					<g:each var="auth" in="${roleList}">
                    	<div class="checkboxField">
	                    	<input type="checkbox" name="auth[${auth.id}]" value="1" class="checkbox" />
	                    	<label><g:message code="role.${auth.authority}.label" default="${auth.authority}" /></label>
	                    </div>
                   	</g:each>
                   	<div class="clearer"></div>
				</div>
				<g:submitButton id="mainFormButton" name="create" class="button" value="${message(code: 'default.button.create.label', default: 'Create')}" />
	        </g:form>
    	</div>
    	
    	<g:if test="${userInstanceList}">
	    	<g:render template="/user/list" />
    	</g:if>
    	
    </body>
</html>
