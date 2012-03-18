		<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
	        <g:hasErrors bean="${customerInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${customerInstance}" as="list" />
	            </div>
            </g:hasErrors>
	        <g:form action="save">
		        <div class="field">
					<label for="name"><g:message code="customer.name.label" default="name" /></label>
	                <g:textField name="name" value="${customerInstance?.name}" />
	                <g:submitButton name="create" class="button" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</div>
				<div class="clearer"></div>
	        </g:form>
    	</div>