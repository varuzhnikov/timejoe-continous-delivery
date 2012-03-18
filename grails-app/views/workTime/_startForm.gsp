<g:formRemote name="workTimeStartForm" url="[controller: 'workTime', action: 'start']" update="workTimeTracker">
    <div class="field">
        <g:textField name="date" value="${date}" id="datepicker" />
        <g:submitButton name="start" class="button" value="${message(code: 'timeentry.startbutton.label', default: 'start')}" />
	</div>
	<div class="clearer"></div>
</g:formRemote>