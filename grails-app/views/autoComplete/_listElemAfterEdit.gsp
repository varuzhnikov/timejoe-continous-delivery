<td style="width: 150px;">${newAutoCompleteElem?.customer?.name}</td>
<td>${fieldValue(bean: newAutoCompleteElem, field: "text")}</td>
<td class="last right">
	<g:form method="post">
		<g:hiddenField name="tableCounter" value="${tableCounter}" />
		<g:hiddenField name="customer" value="${newAutoCompleteElem?.customer?.id}" />
		<g:hiddenField name="text" value="${newAutoCompleteElem?.text}" />
		<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${tableCounter}" />
		<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</g:form>
</td>