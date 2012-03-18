			<div class="list">
	    		<table>
	    			<thead>
	    				<tr>
		    				<g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'username')}" />
		    				<g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'enabled')}" />
		    				<th></th>
	    				</tr>
	    			</thead>
	    			<tbody>
	    				<g:each in="${userInstanceList}" status="i" var="userInstance">
	                        <tr id="entry${userInstance?.id}">
	                        	<td style="width: 250px;">${fieldValue(bean: userInstance, field: "username")}</td>
	                            <td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
	                            <td class="last right">
	                            	<g:form method="post">
	                            		<g:hiddenField name="id" value="${userInstance?.id}" />
	                            		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${userInstance?.id}" onComplete="passwordStrength()" />
	                            		<g:if test="${userInstance.enabled}">
	                            			<g:submitToRemote class="textButton" action="deactivate" value="${message(code: 'default.button.deactivate.label', default: 'Deactivate')}" update="entry${userInstance?.id}" />
	                            		</g:if>
	                            		<g:else>
	                            			<g:submitToRemote class="textButton" action="activate" value="${message(code: 'default.button.activate.label', default: 'Activate')}" update="entry${userInstance?.id}" />
	                       				</g:else>
	                            		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	                            	</g:form>
	                            </td>
	                        </tr>
	                    </g:each>
	    			</tbody>
	    		</table>
	    		<g:if test="${userInstanceTotal > 10}">
		    		<div class="paginateButtons">
		                <g:paginate total="${userInstanceTotal}" />
		            </div>
	            </g:if>
	    	</div>