
<%@ page import="de.eleon.timejoe.tracking.Customer" %>
<%@ page import="de.eleon.timejoe.tracking.ProjectTimeEntry" %>
<html>
    <head>
        <title>Timejoe Time Tracking</title>
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery.timeentry.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery.toolong.min.js')}"></script>
        <script type="text/javascript">
        	$(document).ready(function() {
				$( "#datepicker" ).datepicker({ 
					dateFormat: 'yy-mm-dd',
					maxDate: '${today}',
					onClose: function() {
						if (this.value != "${date}") {
							window.location.href = "${createLink(action: 'date')}" + "/" + this.value;
						}
					}
				});
				shortText();
				addTimeEntry();
				disableInput();
				disableAutocomplete();
			});
        	function refreshInputFormatting() {
				shortText();
				addTimeEntry();
			}
			function shortText() {
				$('.customer').TooLong({ 'len' : 20 });
				$('.comment').TooLong({ 'len' : 35 });
			}
			function addTimeEntry() {
				$('.timeInput').timeEntry({show24Hours: true});
			}
			function disableInput() {
				$('.disabled').attr("disabled", "disabled")
			}
			function activateAutocomplete(c) {
				$("#autocomplete").val('');
				if (c != "") {
					$("#autocomplete").removeAttr("disabled");
					$("#autocomplete").autocomplete({
	 					source: "/projectTime/autoCompleteList/" + c + ".json",
	 					minLength: 0
	 				});
				} else {
					disableAutocomplete();
				}
			}
			function disableAutocomplete() {
				$("#autocomplete").attr("disabled", "disabled");
			}
			
		</script>
		<style type="text/css">
			input.timeInput { width: 38px; }
			.ui-autocomplete-loading { background: url('/images/spinner_transp.gif') center center no-repeat; }
			.customer, .comment { cursor: default; }
		</style>
    </head>
    <body>
    	<g:if test="${!customerList}">
    		<div class="errors">
    			<ul>
    				<li><g:message code="timeentry.error.nocustomers" /></li>
    			</ul>
    		</div>
    	</g:if>
    	<div id="projectTimeTracker" class="box fullsize">
    		<g:if test="${!runningProjectTimeEntry}">
    			<g:render template="/projectTime/startForm" />
    		</g:if>
    		<g:else>
	        	<g:render template="/projectTime/stopForm" />
        	</g:else>
    	</div>
    	
    	<g:if test="${trackingDayList}">
	    	<g:render template="/projectTime/list" />
    	</g:if>
    	
    </body>
</html>
