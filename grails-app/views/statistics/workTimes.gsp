
<%@ page import="de.eleon.timejoe.user.User" %>
<%@ page import="de.eleon.timejoe.tracking.WorkTimeEntry" %>
<html>
    <head>
        <title>Timejoe Work Times</title>
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery.toolong.min.js')}"></script>
    	<script type="text/javascript">
	    	$(document).ready(function() {
	    		$( ".datepicker" ).datepicker({ 
					dateFormat: 'yy-mm-dd',
					maxDate: '${today}'
				});
	    		$('.short').TooLong({ 'len' : 60 });
			});
	    </script>
    </head>
    <body>
    	<h1>Work Times: Data</h1>
    	<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
    		<g:form action="workTimesData">
	    		<div class="field">
	    			<sec:ifAllGranted roles="ROLE_STATISTICS">
			        	<g:select name="user"
				          from="${userList}"
				          value="${user?.id}"
				          optionKey="id" 
				          noSelection="['':'-Select User-']" 
				          style="margin-right: 10px; width: 164px;"
				          />
			        </sec:ifAllGranted>
	    			<label for="from" style="width: auto; margin: 0 10px 0 0;">From:</label>
		        	<g:textField name="from" value="${from}" class="datepicker" />
		        	<label for="to" style="width: auto; margin: 0 10px;">To:</label>
		        	<g:textField name="to" value="${to}" class="datepicker" />
		        	<g:submitButton name="workTimesData" class="button" value="${message(code: 'default.button.ok.label', default: 'ok')}" />
	        	</div>
	        	<div class="clearer"></div>
        	</g:form>
    	</div>
    	<g:if test="${timeEntryList}">
    		<div class="list">
				<table>
					<thead>
						<tr>
							<th><g:message code="timeentry.user" default="user" /></th>
							<th style="width: 100px;"><g:message code="timeentry.date.label" default="date" /></th>
							<th style="width: 70px;"><g:message code="timeentry.start.label" default="start" /></th>
							<th style="width: 70px;"><g:message code="timeentry.end.label" default = "end" /></th>
							<th style="width: 100px;" class="last right"><g:message code="timeentry.duration.label" default="duration" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="3"></td>
							<td colspan="2" class="last right">
								${durationSum?.getDays()} days ${durationSum?.getHours()} h ${durationSum?.getMinutes()} min
							</td>
						</tr>
					</tfoot>
					<tbody>
						<g:each in="${timeEntryList}" status="i" var="workTimeEntryInstance">
							<tr id="entry${workTimeEntryInstance?.id}">
								<td><span class="short" title="${workTimeEntryInstance?.user?.username}">${workTimeEntryInstance?.user?.username}</span></td>
								<td><nobr><g:formatDate format="yyyy-MM-dd" date="${workTimeEntryInstance?.start}"/></nobr></td>
								<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.start}"/></td>
								<td><g:formatDate format="HH:mm" date="${workTimeEntryInstance?.end}"/></td>
								<td class="last right"><timejoe:duration duration="${workTimeEntryInstance?.duration}" /></td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<p style="text-align: right; margin: 20px 0 0 0;">
					<g:link action="workTimesData" params="[format: 'xls', from: from, to: to]">Export to Excel</g:link>
				</p>
			</div>
   		</g:if>
    </body>
</html>
