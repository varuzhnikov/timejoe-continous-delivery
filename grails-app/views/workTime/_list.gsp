<div class="list">
	<table>
		<thead>
			<tr>
				<th style="width: 120px;"><g:message code="timeentry.start.label" default="start" /></th>
				<th style="width: 120px;"><g:message code="timeentry.end.label" default = "end" /></th>
				<th style="width: 150px;"><g:message code="timeentry.duration.label" default="duration" /></th>
				<th></th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="2"></td>
				<td colspan="2">
					<timejoe:duration duration="${durationSum}" />
				</td>
			</tr>
		</tfoot>
		<tbody>
			<g:each in="${trackingDayList}" status="i" var="workTimeEntryInstance">
				<tr id="entry${workTimeEntryInstance?.id}">
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
				</tr>
			</g:each>
		</tbody>
	</table>
</div>