
<%@ page import="de.eleon.timejoe.tracking.Customer" %>
<%@ page import="de.eleon.timejoe.tracking.AutoCompleteElem" %>
<html>
    <head>
        <title>Timejoe Manage Autocomplete</title>
        <meta name="layout" content="main" />
    </head>
    <body>
    	
    	<g:if test="${autoCompleteInstanceList}">
	    	<div class="list">
	    		<table>
	    			<thead>
	    				<tr>
	    					<g:sortableColumn property="customer" title="${message(code: 'customer.label', default: 'customer')}" />
		    				<g:sortableColumn property="text" title="${message(code: 'autoCompleteElem.text.label', default: 'text')}" />
		    				<th></th>
	    				</tr>
	    			</thead>
	    			<tbody>
	    				<g:each in="${autoCompleteInstanceList}" status="tableCounter" var="autoCompleteElemInstance">
	                        <tr id="entry${tableCounter}">
	                        	<td style="width: 150px;">${autoCompleteElemInstance?.customer?.name}</td>
	                            <td>${fieldValue(bean: autoCompleteElemInstance, field: "text")}</td>
	                            <td class="last right">
	                            	<g:form method="post">
	                            		<g:hiddenField name="tableCounter" value="${tableCounter}" />
	                            		<g:hiddenField name="customer" value="${autoCompleteElemInstance?.customer?.id}" />
	                            		<g:hiddenField name="text" value="${autoCompleteElemInstance?.text}" />
	                            		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${tableCounter}" />
	                            		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	                            	</g:form>
	                            </td>
	                        </tr>
	                    </g:each>
	    			</tbody>
	    		</table>
	    		<g:if test="${autoCompleteInstanceTotal > 10}">
		    		<div class="paginateButtons">
		                <g:paginate total="${autoCompleteInstanceTotal}" />
		            </div>
	            </g:if>
	    	</div>
    	</g:if>
    	<g:else>
    		<p>No Autocomplete Elements. Track Project Time to create Autocomplete Elements.</p>
    	</g:else>
    	
    </body>
</html>
