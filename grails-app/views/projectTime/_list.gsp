<div class="list">
	<table>
		<thead>
			<tr>
				<th style="width: 150px;"><g:message code="timeentry.customer" default="customer" /></th>
				<th style="width: 260px;"><g:message code="timeentry.comment" default="comment" /></th>
				<th style="width: 70px;"><g:message code="timeentry.start.label" default="start" /></th>
				<th style="width: 70px;"><g:message code="timeentry.end.label" default = "end" /></th>
				<th style="width: 100px;"><g:message code="timeentry.duration.label" default="duration" /></th>
				<th></th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="4"></td>
				<td colspan="2">
					<timejoe:duration duration="${durationSum}" />
				</td>
			</tr>
		</tfoot>
		<tbody>
			<g:each in="${trackingDayList}" status="i" var="projectTimeEntryInstance">
				<tr id="entry${projectTimeEntryInstance?.id}">
					<td><span class="customer" title="${projectTimeEntryInstance?.customer?.name}">${projectTimeEntryInstance?.customer?.name}</span></td>
					<td><span class="comment" title="${projectTimeEntryInstance?.comment}">${projectTimeEntryInstance?.comment}</span></td>
					<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.start}"/></td>
					<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.end}"/></td>
					<td><timejoe:duration duration="${projectTimeEntryInstance?.duration}" /></td>
					<td class="last right">
						<g:form method="post">
							<g:hiddenField name="id" value="${projectTimeEntryInstance?.id}" />
							<g:hiddenField name="date" value="${date}" />
							<g:submitToRemote class="textButton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" update="entry${projectTimeEntryInstance?.id}" onComplete="refreshInputFormatting()" />
							<g:actionSubmit class="textButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</g:form>
				    </td>
				</tr>
			</g:each>
		</tbody>
	</table>
</div>