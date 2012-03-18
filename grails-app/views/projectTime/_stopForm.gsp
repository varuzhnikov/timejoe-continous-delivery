<g:form action="stop">
	<g:hiddenField name="id" value="${runningProjectTimeEntry?.id}" />
    <div class="field">
    	<label style="width: auto; margin-right: 10px;"><g:message code="timeentry.startetat.label" default="started at" />: <g:formatDate format="yyyy-MM-dd HH:mm" date="${runningProjectTimeEntry?.start}"/></label>
        <g:select name="customer"
          from="${customerList}"
          value="${runningProjectTimeEntry?.customer?.id}"
          optionKey="id" 
          noSelection="['':'']" 
          style="margin-left: 10px; width: 164px;" 
          class="disabled" />
        <g:textField name="comment" value="${runningProjectTimeEntry?.comment}" style="margin-left: 10px; width: 350px;" disabled="disabled" />
        <g:submitButton name="stop" class="button" value="${message(code: 'timeentry.stopbutton.label', default: 'stop')}" />
	</div>
	<div class="clearer"></div>
</g:form>