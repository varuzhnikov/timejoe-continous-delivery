<g:hasErrors bean="${runningProjectTimeEntry}">
	<div class="errors">
		<g:renderErrors bean="${runningProjectTimeEntry}" as="list" />
	</div>
</g:hasErrors>
<g:formRemote name="projectTimeStartForm" url="[controller: 'projectTime', action: 'start']" update="projectTimeTracker" onComplete="disableInput()">
    <div class="field">
        <g:textField name="date" value="${date}" id="datepicker" />
        <g:select name="customer"
          from="${customerList}"
          value="${runningProjectTimeEntry?.customer?.id}"
          optionKey="id" 
          noSelection="['':'-Select Customer-']" 
          style="margin-left: 10px; width: 164px;"
          onchange="activateAutocomplete(this.value)"
          />
        <g:textField name="comment" value="${runningProjectTimeEntry?.comment}" style="margin-left: 10px; width: 350px;" id="autocomplete" />
        <g:submitButton name="start" class="button" value="${message(code: 'timeentry.startbutton.label', default: 'start')}" />
	</div>
	<div class="clearer"></div>
</g:formRemote>