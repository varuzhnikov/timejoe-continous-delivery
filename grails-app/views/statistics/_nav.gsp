		<ul id="subnav">
			<sec:ifAnyGranted roles="ROLE_TRACKER">
    			<li class="${(params.controller == "statistics" && params.action == "userSummary") ? "hit" : ""}"><g:link controller="statistics" action="userSummary">User Summary</g:link></li>
   			</sec:ifAnyGranted>
   			<sec:ifAnyGranted roles="ROLE_STATISTICS">
    			<li class="${(params.controller == "statistics" && params.action == "teamSummary") ? "hit" : ""}"><g:link controller="statistics" action="teamSummary">Team Summary</g:link></li>
    			<li class="${(params.controller == "statistics" && (params.action == "memberComparison" || params.action == "memberComparisonData")) ? "hit" : ""}"><g:link controller="statistics" action="memberComparison">Member Comparison</g:link></li>
   			</sec:ifAnyGranted>
   			<sec:ifAnyGranted roles="ROLE_TRACKER, ROLE_STATISTICS">
    			<li class="${(params.controller == "statistics" && (params.action == "projectTimes" || params.action == "projectTimesData")) ? "hit" : ""}"><g:link controller="statistics" action="projectTimes">Project Times</g:link></li>
    			<li class="${(params.controller == "statistics" && (params.action == "workTimes" || params.action == "workTimesData")) ? "hit" : ""}"><g:link controller="statistics" action="workTimes">Work Times</g:link></li>
   			</sec:ifAnyGranted>
    	</ul>
    	<div class="clearer"></div>