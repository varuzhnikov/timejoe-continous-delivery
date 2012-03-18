			<div class="list">
	    		<table>
	    			<thead>
	    				<tr>
		    				<g:sortableColumn property="name" title="${message(code: 'customer.name.label', default: 'name')}" />
		    				<g:sortableColumn property="bookable" title="${message(code: 'customer.bookable.label', default: 'bookable')}" />
		    				<th></th>
	    				</tr>
	    			</thead>
	    			<tbody>
	    				<g:each in="${customerInstanceList}" status="i" var="customerInstance">
	                        <tr id="entry${customerInstance?.id}">
	                        	<td style="width: 220px;">${fieldValue(bean: customerInstance, field: "name")}</td>
	                            <td><g:formatBoolean boolean="${customerInstance.bookable}" /></td>
	                            <td class="last right">
	                            	<g:form method="post">
	                            		<g:hiddenField name="id" value="${customerInstance?.id}" />
	                            		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${customerInstance?.id}" />
	                            		<g:if test="${customerInstance.bookable}">
	                            			<g:submitToRemote class="textButton" action="deactivate" value="${message(code: 'default.button.deactivate.label', default: 'Deactivate')}" update="entry${customerInstance?.id}" />
	                            		</g:if>
	                            		<g:else>
	                            			<g:submitToRemote class="textButton" action="activate" value="${message(code: 'default.button.activate.label', default: 'Activate')}" update="entry${customerInstance?.id}" />
	                       				</g:else>
	                            		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	                            	</g:form>
	                            </td>
	                        </tr>
	                    </g:each>
	    			</tbody>
	    		</table>
	    		<g:if test="${customerInstanceTotal > 10}">
		    		<div class="paginateButtons">
		                <g:paginate total="${customerInstanceTotal}" />
		            </div>
	            </g:if>
	    	</div>