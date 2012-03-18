<%@ page import="de.eleon.timejoe.tracking.Customer" %>
<%@ page import="de.eleon.timejoe.tracking.ProjectTimeEntry" %>
<h2>My Latest Project Time Entries</h2>
<div class="box fullsize">
	<div class="list">
		<table>
			<thead>
				<tr>
					<th style="width: 150px;"><g:message code="timeentry.customer" default="customer" /></th>
					<th><g:message code="timeentry.comment" default="comment" /></th>
					<th style="width: 105px;"><g:message code="timeentry.date.label" default="date" /></th>
					<th style="width: 70px;"><g:message code="timeentry.start.label" default="start" /></th>
					<th style="width: 70px;"><g:message code="timeentry.end.label" default = "end" /></th>
					<th style="width: 70px;" class="last right"><g:message code="timeentry.duration.label" default="duration" /></th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${latestProjectTimeList}" status="i" var="projectTimeEntryInstance">
					<tr id="entry${projectTimeEntryInstance?.id}">
						<td><span class="short" title="${projectTimeEntryInstance?.customer?.name}">${projectTimeEntryInstance?.customer?.name}</span></td>
						<td><span class="shortComment" title="${projectTimeEntryInstance?.comment}">${projectTimeEntryInstance?.comment}</span></td>
						<td><nobr><g:formatDate format="yyyy-MM-dd" date="${projectTimeEntryInstance?.start}"/></nobr></td>
						<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.start}"/></td>
						<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.end}"/></td>
						<td class="last right"><timejoe:duration duration="${projectTimeEntryInstance?.duration}" /></td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</div>