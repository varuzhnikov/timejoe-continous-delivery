
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
							window.location.href = "${createLink(action: 'date')}" + "/" + this.value;
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
    	<div id="workTimeTracker" class="box fullsize">
    		<g:if test="${!runningWorkTimeEntry}">
    			<g:render template="/workTime/startForm" />
    		</g:if>
    		<g:else>
	        	<g:render template="/workTime/stopForm" />
        	</g:else>
    	</div>
    	
    	<g:if test="${trackingDayList}">
	    	<g:render template="/workTime/list" />
    	</g:if>
    	
    </body>
</html>
