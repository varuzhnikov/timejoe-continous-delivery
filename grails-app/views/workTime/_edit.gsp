<td colspan="4" style="padding-right: 0;">
	<g:hasErrors bean="${workTimeEntryInstance}">
		<div class="errors">
			<g:renderErrors bean="${workTimeEntryInstance}" as="list" />
		</div>
	</g:hasErrors>
	<g:formRemote name="edit" url="[controller: 'workTime', action: 'update']" update="entry${workTimeEntryInstance?.id}">
		<g:hiddenField name="id" value="${workTimeEntryInstance?.id}" />
		<g:hiddenField name="date" value="${date}" />
		<table><tr>
			<td style="width: 120px;" class="field"><g:textField class="timeInput" name="start" value="${workTimeEntryInstance?.start?.format('HH:mm')}" style="margin: 0;" /></td>
			<td style="width: 120px;" class="field"><g:textField class="timeInput" name="stop" value="${workTimeEntryInstance?.end?.format('HH:mm')}" style="margin: 0;" /></td>
			<td style="width: 150px;"></td>
			<td class="last right">
				<g:actionSubmit class="button" action="save" value="${message(code: 'default.button.save.label', default: 'Save')}"  />
			</td>
		</tr></table>
	</g:formRemote>
</td>
