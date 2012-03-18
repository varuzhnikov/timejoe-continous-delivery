
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
							if (this.value == "${today}") {
								window.location.href = "${createLink(action: 'today')}";
							} else {
								window.location.href = "${createLink(action: 'date')}" + "/" + this.value;
							}
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
    	<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
	        <g:hasErrors bean="${projectTimeEntryInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${projectTimeEntryInstance}" as="list" />
	            </div>
            </g:hasErrors>
    		<g:form action="save">
	    		<div class="field">
		        	<g:textField name="date" value="${date}" id="datepicker" />
		        	<g:textField class="timeInput" name="start" value="${projectTimeEntryInstance?.start ?: '00:00'}" style="margin-left: 10px;" />
		        	<g:textField class="timeInput" name="stop" value="${projectTimeEntryInstance?.end ?: '00:00'}" style="margin-left: 10px;" />
		        	<g:select name="customer"
			          from="${customerList}"
			          value="${projectTimeEntryInstance?.customer?.id}"
			          optionKey="id" 
			          noSelection="['':'']" 
			          style="margin-left: 10px; width: 164px;"
			          onchange="activateAutocomplete(this.value)"
			          />
			        <g:textField name="comment" value="${projectTimeEntryInstance?.comment}" style="margin-left: 10px; width: 210px;" id="autocomplete" />
		        	
		        	<g:submitButton name="save" class="button" value="${message(code: 'default.button.create.label', default: 'create')}" />
	        	</div>
	        	<div class="clearer"></div>
        	</g:form>
    	</div>
    	
    	<g:if test="${trackingDayList}">
	    	<g:render template="/projectTime/list" />
    	</g:if>
    	
    </body>
</html>
