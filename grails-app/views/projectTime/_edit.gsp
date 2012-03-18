<td colspan="6" style="padding-right: 0;">
	<g:hasErrors bean="${projectTimeEntryInstance}">
		<div class="errors">
			<g:renderErrors bean="${projectTimeEntryInstance}" as="list" />
		</div>
	</g:hasErrors>
	<g:formRemote name="edit" url="[controller: 'projectTime', action: 'update']" update="entry${projectTimeEntryInstance?.id}" onComplete="refreshInputFormatting()">
		<g:hiddenField name="id" value="${projectTimeEntryInstance?.id}" />
		<g:hiddenField name="date" value="${date}" />
		<table><tr>
			<td style="width: 150px;"><span class="customer" title="${projectTimeEntryInstance?.customer?.name}">${projectTimeEntryInstance?.customer?.name}</span></td>
			<td style="width: 260px;"><span class="comment" title="${projectTimeEntryInstance?.comment}">${projectTimeEntryInstance?.comment}</span></td>
			<td style="width: 90px; padding-right: 0;" class="field"><g:textField class="timeInput" name="start" value="${projectTimeEntryInstance?.start?.format('HH:mm')}" style="margin: 0;" /></td>
			<td style="width: 90px; padding-right: 0;" class="field"><g:textField class="timeInput" name="stop" value="${projectTimeEntryInstance?.end?.format('HH:mm')}" style="margin: 0;" /></td>
			<td style="width: 100px;"><timejoe:duration duration="${projectTimeEntryInstance?.duration}" /></td>
			<td class="last right">
				<g:actionSubmit class="button" action="save" value="${message(code: 'default.button.save.label', default: 'Save')}"  />
		    </td>
		</tr></table>
	</g:formRemote>
</td>
