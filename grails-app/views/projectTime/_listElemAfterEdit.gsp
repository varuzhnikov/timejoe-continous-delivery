<td><span class="customer" title="${projectTimeEntryInstance?.customer?.name}">${projectTimeEntryInstance?.customer?.name}</span></td>
<td><span class="comment" title="${projectTimeEntryInstance?.comment}">${projectTimeEntryInstance?.comment}</span></td>
<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.start}"/></td>
<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.end}"/></td>
<td><timejoe:duration duration="${projectTimeEntryInstance?.duration}" /></td>
<td class="last right">
	<g:form method="post">
		<g:hiddenField name="id" value="${projectTimeEntryInstance?.id}" />
		<g:hiddenField name="date" value="${date}" />
		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${projectTimeEntryInstance?.id}" onComplete="addTimeEntry()" />
		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</g:form>
</td>