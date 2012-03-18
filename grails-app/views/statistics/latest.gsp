<h2>Yesterday Team Summary</h2>
<div class="box fullsize">
	<div class="list">
		<table>
			<thead>
				<tr>
					<th>Time Entry</th>
					<th style="width: 120px;" class="last right"><g:message code="timeentry.duration.label" default="duration" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Work Time</td>
					<td class="last right">${workTimePreviousDay?.getDays() ?: '0'} days ${workTimePreviousDay?.getHours() ?: '0'} h ${workTimePreviousDay?.getMinutes() ?: '0'} min</td>
				</tr>
				<tr>
					<td>Project Time</td>
					<td class="last right">${projectTimePreviousDay?.getDays() ?: '0'} days ${projectTimePreviousDay?.getHours() ?: '0'} h ${projectTimePreviousDay?.getMinutes() ?: '0'} min</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>