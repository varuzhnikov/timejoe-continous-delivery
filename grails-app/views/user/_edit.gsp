<td colspan="3" style="padding-right: 0;">
	<g:hasErrors bean="${userInstance}">
	<div class="errors">
	<g:renderErrors bean="${userInstance}" as="list" />
	</div>
	</g:hasErrors>
	<g:formRemote name="edit" url="[controller: 'user', action: 'update']" update="entry${userInstance?.id}" autocomplete="off">
		<g:hiddenField name="id" value="${userInstance?.id}" />
		<g:hiddenField name="version" value="${userInstance?.version}" />
		<table>
			<tr>
				<td style="width: 250px;" class="field"><g:textField name="username" value="${userInstance?.username}" style="margin: 0;" /></td>
				<td colspan="2" class="field">
					<div class="checkboxField">
						<g:checkBox name="setPassword" id="setPassword" class="checkbox" /> 
						<label for="setPassword"><g:message code="user.set.new.password" default="Set new password?" /></label>
					</div>
					<input type="password" name="newPassword" class="password" value="" style="margin: 0;" />
				</td>
			</tr>
			<tr>
				<td colspan="3" class="last">
					<div class="field" style="width: 700px; float: left;">
						<g:each var="auth" in="${roleList}">
	                    	<div class="checkboxField">
		                    	<input type="checkbox" name="auth[${auth.id}]" value="1" <g:if test="${userInstance?.authorities?.find { it == auth }}">checked="checked"</g:if> class="checkbox" />
		                    	<label for="auth[${auth.id}]"><g:message code="role.${auth.authority}.label" default="${auth.authority}" /></label>
		                    </div>
	                   	</g:each>
	                   	<div class="clearer"></div>
					</div>
					<g:submitButton name="update" class="button" value="${message(code: 'default.button.update.label', default: 'Update')}" style="float: right;" />
				</td>
			</tr>
		</table>
	</g:formRemote>
</td>
