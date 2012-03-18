<td colspan="3" style="padding-right: 0;">
	<g:hasErrors bean="${autoCompleteElemInstance}">
		<div class="errors">
			<g:renderErrors bean="${autoCompleteElemInstance}" as="list" />
		</div>
	</g:hasErrors>
	<g:formRemote name="edit" url="[controller: 'autoComplete', action: 'update']" update="entry${tableCounter}">
		<g:hiddenField name="tableCounter" value="${tableCounter}" />
		<g:hiddenField name="customer" value="${autoCompleteElemInstance?.customer?.id}" />
        <g:hiddenField name="oldText" value="${autoCompleteElemInstance?.text}" />
		<table><tr>
			<td style="width: 150px;">${autoCompleteElemInstance?.customer?.name}</td>
			<td class="field">
				<g:textField name="text" value="${autoCompleteElemInstance?.text}" style="margin: 0;" />
				<div class="checkboxField" style="clear: left; float: none;">
					<g:checkBox name="refactorTimeEntries" class="checkbox" /> 
					<label for="refactorTimeEntries" style="width: auto; margin-left: 10px;"><g:message code="autoCompleteElemen.refactorTimeEntries" default="Refactor all Time Entries?" /></label>
				</div>	
			</td>
			<td class="last right">
				<g:submitButton name="update" class="button" value="${message(code: 'default.button.update.label', default: 'Update')}" />
			</td>
		</tr></table>
	</g:formRemote>
</td>
