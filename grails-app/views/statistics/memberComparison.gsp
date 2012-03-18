<html>
    <head>
        <title>Timejoe Member Comparison</title>
        <meta name="layout" content="main" />
        <!--[if lt IE 9]><script type="text/javascript" src="${resource(dir:'js',file:'excanvas.min.js')}"></script><![endif]-->
    	<script type="text/javascript" src="${resource(dir:'js',file:'jquery.flot.min.js')}"></script>
    	<script type="text/javascript">
	    	$(document).ready(function() {
	    		$( ".datepicker" ).datepicker({ 
					dateFormat: 'yy-mm-dd',
					maxDate: '${today}'
				});
				<g:if test="${membersWorkHoursList}">
		    		var worktime = [<% membersWorkHoursList.eachWithIndex() { obj, i -> out << "${(i>0) ? ',' : ''}[${i*2}, ${obj.value}]" } %>];
				    var projecttime = [<% membersProjectHoursList.eachWithIndex() { obj, i -> out << "${(i>0) ? ',' : ''}[${i*2}, ${obj.value}]" } %>];
				    $.plot($("#memberComparison"), [
				        {
				        	color: "#ffa201",
				        	label: "Work Time in Hours",
				        	data: worktime,
				            bars: { show: true }
				        },
				        {
				        	color: "#63BFFC",
				        	label: "Project Time in Hours",
				            data: projecttime,
				            bars: { show: true }
				        },
				    ], {
			    		    xaxis: {
			    		    	ticks: [<% membersWorkHoursList.eachWithIndex() { obj, i -> out << """${(i>0) ? "," : ""}[${i*2+0.5}, "${obj.key}"]""" } %>]
				    	    }
				    });
			    </g:if>
			});
	    </script>
    </head>
    <body>
    	<h1>Member Comparison</h1>
    	<div class="box fullsize">
    		<g:if test="${flash.message}">
	       		<div class="message">${flash.message}</div>
	        </g:if>
    		<g:form action="memberComparisonData">
	    		<div class="field">
	    			<label for="from" style="width: auto; margin-right: 10px;">From:</label>
		        	<g:textField name="from" value="${from}" class="datepicker" />
		        	<label for="to" style="width: auto; margin: 0 10px;">To:</label>
		        	<g:textField name="to" value="${to}" class="datepicker" />
		        	<g:submitButton name="memberComparisonData" class="button" value="${message(code: 'default.button.ok.label', default: 'ok')}" />
	        	</div>
	        	<div class="clearer"></div>
        	</g:form>
    	</div>
    	<g:if test="${membersWorkHoursList}">
    		<div id="memberComparison" style="width:900px; height:260px; class="chart"></div>
   		</g:if>
    </body>
</html>
