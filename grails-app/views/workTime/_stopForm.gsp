<g:form action="stop">
	<g:hiddenField name="id" value="${runningWorkTimeEntry?.id}" />
	<div class="field">
		<label style="width: auto; margin-right: 10px;"><g:message code="timeentry.startetat.label" default="started at" />: <g:formatDate format="yyyy-MM-dd HH:mm" date="${runningWorkTimeEntry?.start}"/></label>
        <g:submitButton name="stop" class="button" value="${message(code: 'timeentry.stopbutton.label', default: 'stop')}" />
	</div>
	<div class="clearer"></div>
</g:form>