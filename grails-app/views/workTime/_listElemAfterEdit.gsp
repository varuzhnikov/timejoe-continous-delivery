<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.start}"/></td>
<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.end}"/></td>
<td><timejoe:duration duration="${workTimeEntryInstance?.duration}" /></td>
<td class="last right">
	<g:form method="post">
		<g:hiddenField name="id" value="${workTimeEntryInstance?.id}" />
		<g:hiddenField name="date" value="${date}" />
		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${workTimeEntryInstance?.id}" onComplete="addTimeEntry()" />
		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</g:form>
</td>