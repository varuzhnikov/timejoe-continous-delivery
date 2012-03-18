<%@ page import="de.eleon.timejoe.tracking.WorkTimeEntry" %>
<h2>My Latest Work Time Entries</h2>
<div class="box fullsize">
	<div class="list">
		<table>
			<thead>
				<tr>
					<th></th>
					<th style="width: 105px;"><g:message code="timeentry.date.label" default="date" /></th>
					<th style="width: 70px;"><g:message code="timeentry.start.label" default="start" /></th>
					<th style="width: 70px;"><g:message code="timeentry.end.label" default = "end" /></th>
					<th style="width: 70px;" class="last right"><g:message code="timeentry.duration.label" default="duration" /></th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${latestWorkTimeList}" status="i" var="workTimeEntryInstance">
					<tr id="entry${workTimeEntryInstance?.id}">
						<td></td>
						<td><nobr><g:formatDate format="yyyy-MM-dd" date="${workTimeEntryInstance?.start}"/></nobr></td>
						<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.start}"/></td>
						<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.end}"/></td>
						<td class="last right"><timejoe:duration duration="${workTimeEntryInstance?.duration}" /></td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</div>