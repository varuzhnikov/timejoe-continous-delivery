
<%@ page import="de.eleon.timejoe.tracking.WorkTimeEntry" %>
<html>
    <head>
        <title>Timejoe Track Working Hours</title>
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery.timeentry.js')}"></script>
        <script>
	        $(function() {
				$( "#datepicker" ).datepicker({ 
					dateFormat: 'yy-mm-dd',
					maxDate: '${today}',
					onClose: function() {
						if (this.value != "${date}") {
							if (this.value == "${today}") {
								window.location.href = "${createLink(action: 'today')}";
							} else {
								window.location.href = "${createLink(action: 'date')}" + "/" + this.value;
							}
						}
					}
				});
				addTimeEntry();
			});

			function addTimeEntry() {
				$('.timeInput').timeEntry({show24Hours: true});
			}
		</script>
		<style type="text/css">
			input.timeInput { width: 38px; }
		</style>
    </head>
    <body>
    	<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
	        <g:hasErrors bean="${workTimeEntryInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${workTimeEntryInstance}" as="list" />
	            </div>
            </g:hasErrors>
    		<g:form action="save">
	    		<div class="field">
		        	<g:textField name="date" value="${date}" id="datepicker" />
		        	<g:textField class="timeInput" name="start" value="${workTimeEntryInstance?.start ?: '00:00'}" style="margin-left: 10px;" />
		        	<g:textField class="timeInput" name="stop" value="${workTimeEntryInstance?.end ?: '00:00'}" style="margin-left: 10px;" />
		        	<g:submitButton name="save" class="button" value="${message(code: 'default.button.create.label', default: 'create')}" />
	        	</div>
	        	<div class="clearer"></div>
        	</g:form>
    	</div>
    	
    	<g:if test="${trackingDayList}">
	    	<g:render template="/workTime/list" />
    	</g:if>
    	
    </body>
</html>
