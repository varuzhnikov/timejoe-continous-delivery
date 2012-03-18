	<div class="box fullsize">
		<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
		<form action="${(grailsApplication.metadata['app.name'] == '/') ? grailsApplication.metadata['app.name'] : '/' + grailsApplication.metadata['app.name'] + '/'}j_spring_security_check" method='POST' id='loginForm' class='cssform' autocomplete='off'>
                  <div class="field">
				<label for='username'>E-Mail</label>
				<input type='text' class='text_' name='j_username' id='username' />
			</div>
			<div class="field">
				<label for='password'>Password</label>
				<input type='password' class='text_' name='j_password' id='password' />
			</div>
			<div class="buttons">
               	<input type='submit' value='Login' />
            </div>
		</form>
	</div>
	<script type='text/javascript'>
	<!--
	(function(){
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
	</script>