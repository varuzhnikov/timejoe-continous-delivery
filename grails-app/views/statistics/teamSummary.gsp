<html>
    <head>
        <title>Timejoe Team Summary</title>
        <meta name="layout" content="main" />
        <!--[if lt IE 9]><script type="text/javascript" src="${resource(dir:'js',file:'excanvas.min.js')}"></script><![endif]-->
    	<script type="text/javascript" src="${resource(dir:'js',file:'jquery.flot.min.js')}"></script>
    	<script type="text/javascript" src="${resource(dir:'js',file:'jquery.flot.pie.min.js')}"></script>
    	<script type="text/javascript">
	    	$(document).ready(function() {
	    		$(function () {
	    		    var worktime = [<% latestTeamWorktimeList.eachWithIndex() { obj, i -> out << "${(i>0) ? ',' : ''}[${i*2}, ${obj.value}]" } %>];
	    		    var projecttime = [<% latestTeamProjecttimeList.eachWithIndex() { obj, i -> out << "${(i>0) ? ',' : ''}[${i*2}, ${obj.value}]" } %>];
	    		    $.plot($("#teamLatest"), [
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
			    		    	ticks: [<% latestTeamWorktimeList.eachWithIndex() { obj, i -> out << """${(i>0) ? "," : ""}[${i*2+0.5}, "${obj.key.format('yyyy-MM-dd')}"]""" } %>]
				    	    }
	    		    });
	    		    var teamCustomer = [<% latestTeamCustomerProjecttimeList.eachWithIndex() { obj, i -> out << """${(i>0) ? "," : ""}{ label: "${obj.key}", data: ${obj.value} }""" } %>];
	    		    $.plot($("#teamCustomer"), teamCustomer, 
   		    		{
   		    			series: {
   		    				pie: { 
   		    					show: true,
   		    	                combine: {
   		    	                    color: '#999',
   		    	                    threshold: 0.1
   		    	                }
   		    				}
   		    			},
	    				legend: {
	    					show: false
	    				}
   		    		});
	    		});
	        });
	    </script>
    </head>
    <body>
    	<h1>Team Summary: Last 10 days</h1>
    	<div style="width: 900px;">
	    	<div id="teamLatest" style="width:620px; height:260px; float: left;" class="chart"></div>
	    	<div id="teamCustomer" style="width:260px; height:260px; float: right;" class="chart"></div>
	    	<div class="clearer"></div>
    	</div>
    </body>
</html>
