$(function () {
	$('.list table tr').bind('mouseenter mouseleave', function() {
		$(this).toggleClass('hover');
	});
});