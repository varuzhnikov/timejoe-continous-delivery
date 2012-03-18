<td style="width: 250px;">${fieldValue(bean: userInstance, field: "username")}</td>
<td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
<td class="last right">
	<g:form method="post">
	<g:hiddenField name="id" value="${userInstance?.id}" />
	<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${userInstance?.id}" />
	<g:if test="${userInstance.enabled}">
		<g:submitToRemote class="textButton" action="deactivate" value="${message(code: 'default.button.deactivate.label', default: 'Deactivate')}" update="entry${userInstance?.id}" />
	</g:if>
	<g:else>
		<g:submitToRemote class="textButton" action="activate" value="${message(code: 'default.button.activate.label', default: 'Activate')}" update="entry${userInstance?.id}" />
</g:else>
	<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
</g:form>
</td>