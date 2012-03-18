<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Timejoe" /></title>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css/custom-theme',file:'jquery-ui-1.8.13.custom.css')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.6.1.min.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery-ui-1.8.13.custom.min.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js',file:'main.js')}"></script>
        <g:layoutHead />
    </head>
    <body>
    	<div id="wrapper">
	    	<div id="head">
	    		<img src="${resource(dir:'images',file:'timejoe-logo.gif')}" alt="Timejoe" id="logo" />
	    		<sec:ifLoggedIn>
    				<g:render template="/layouts/nav" />
   				</sec:ifLoggedIn>
	    	</div>
	    	<div id="content">
	    		<g:layoutBody />
	    	</div>
   	    	<div id="footer">
	   			<g:render template="/layouts/footer" />
	    	</div>
    	</div>
    </body>
</html>