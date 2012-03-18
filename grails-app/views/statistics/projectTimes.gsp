
<%@ page import="de.eleon.timejoe.tracking.Customer" %>
<%@ page import="de.eleon.timejoe.user.User" %>
<%@ page import="de.eleon.timejoe.tracking.ProjectTimeEntry" %>
<html>
    <head>
        <title>Timejoe Project Times</title>
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery.toolong.min.js')}"></script>
    	<script type="text/javascript">
	    	$(document).ready(function() {
	    		$( ".datepicker" ).datepicker({ 
					dateFormat: 'yy-mm-dd',
					maxDate: '${today}'
				});
	    		$('.short').TooLong({ 'len' : 20 });
	    		$('.shortComment').TooLong({ 'len' : 30 });
			});
	    </script>
	    <style type="text/css">
	    	tfoot td .error { color: #3C4246; }
	    </style>
    </head>
    <body>
    	<h1>Project Times: Data</h1>
    	<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
    		<g:form action="projectTimesData">
	    		<div class="field">
		        	<g:select name="customer"
			          from="${customerList}"
			          value="${customer?.id}"
			          optionKey="id" 
			          noSelection="['':'-Select Customer-']" 
			          style="width: 164px;"
			          />
				    <sec:ifAllGranted roles="ROLE_STATISTICS">
			        	<g:select name="user"
				          from="${userList}"
				          value="${user?.id}"
				          optionKey="id" 
				          noSelection="['':'-Select User-']" 
				          style="margin-left: 10px; width: 164px;"
				          />
			        </sec:ifAllGranted>
	    			<label for="from" style="width: auto; margin: 0 10px;">From:</label>
		        	<g:textField name="from" value="${from}" class="datepicker" />
		        	<label for="to" style="width: auto; margin: 0 10px;">To:</label>
		        	<g:textField name="to" value="${to}" class="datepicker" />
		        	<g:submitButton name="projectTimesData" class="button" value="${message(code: 'default.button.ok.label', default: 'ok')}" />
	        	</div>
	        	<div class="clearer"></div>
        	</g:form>
    	</div>
    	<g:if test="${timeEntryList}">
    		<div class="list">
				<table>
					<thead>
						<tr>
							<th style="width: 150px;"><g:message code="timeentry.customer" default="customer" /></th>
							<th><g:message code="timeentry.comment" default="comment" /></th>
							<th style="width: 150px;"><g:message code="timeentry.user" default="user" /></th>
							<th><g:message code="timeentry.date.label" default="date" /></th>
							<th><g:message code="timeentry.start.label" default="start" /></th>
							<th><g:message code="timeentry.end.label" default = "end" /></th>
							<th class="last right"><g:message code="timeentry.duration.label" default="duration" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="5"></td>
							<td colspan="2" class="last right">
								${durationSum?.getDays()} days ${durationSum?.getHours()} h ${durationSum?.getMinutes()} min
							</td>
						</tr>
					</tfoot>
					<tbody>
						<g:each in="${timeEntryList}" status="i" var="projectTimeEntryInstance">
							<tr id="entry${projectTimeEntryInstance?.id}">
								<td><span class="short" title="${projectTimeEntryInstance?.customer?.name}">${projectTimeEntryInstance?.customer?.name}</span></td>
								<td><span class="shortComment" title="${projectTimeEntryInstance?.comment}">${projectTimeEntryInstance?.comment}</span></td>
								<td><span class="short" title="${projectTimeEntryInstance?.user?.username}">${projectTimeEntryInstance?.user?.username}</span></td>
								<td><nobr><g:formatDate format="yyyy-MM-dd" date="${projectTimeEntryInstance?.start}"/></nobr></td>
								<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.start}"/></td>
								<td><g:formatDate format="HH:mm" date="${projectTimeEntryInstance?.end}"/></td>
								<td class="last right"><timejoe:duration duration="${projectTimeEntryInstance?.duration}" /></td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<p style="text-align: right; margin: 20px 0 0 0;">
					<g:link action="projectTimesData" params="[format: 'xls', customer: customer?.id, user: user?.id, from: from, to: to]">Export to Excel</g:link>
				</p>
			</div>
   		</g:if>
    </body>
</html>
