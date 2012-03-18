<td colspan="3" style="padding-right: 0;">
	<g:hasErrors bean="${customerInstance}">
	<div class="errors">
	<g:renderErrors bean="${customerInstance}" as="list" />
	</div>
	</g:hasErrors>
	<g:formRemote name="edit" url="[controller: 'customer', action: 'update']" update="entry${customerInstance?.id}">
		<g:hiddenField name="id" value="${customerInstance?.id}" />
		<g:hiddenField name="version" value="${customerInstance?.version}" />
		<table><tr>
			<td style="width: 220px;" class="field"><g:textField name="name" value="${customerInstance?.name}" style="margin: 0;" /></td>
			<td><g:formatBoolean boolean="${customerInstance.bookable}" /></td>
			<td class="last right">
				<g:submitButton name="update" class="button" value="${message(code: 'default.button.update.label', default: 'Update')}" />
			</td>
		</tr></table>
	</g:formRemote>
</td>
