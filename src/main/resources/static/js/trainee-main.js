/*!
    * Start Bootstrap - SB Admin v6.0.1 (https://startbootstrap.com/templates/sb-admin)
    * Copyright 2013-2020 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
(function($) {
	"use strict";


	// Add active state to sidbar nav links
	var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
	$("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
		if (this.href === path) {
			$(this).addClass("active");
		}
	});

	// Toggle the side navigation
	$("#sidebarToggle").on("click", function(e) {
		e.preventDefault();
		$("body").toggleClass("sb-sidenav-toggled");
	});

	$('.close-button').click(function(e){
		e.stopPropagation();
		let value=$(this).attr("value");
		$("#removeSourceFile"+value).submit();
	});

})(jQuery);


$('#console').resizable({
	handles: "n"
});


/**
	function addClass(){
		if($('#newClassForm').hasClass('form-valid')){
			let newClassName= $('#className').val();
			let contentID= "nav-"+ newClassName;
			tabID = contentID+'-tab';
		  	let tabLinkHTMLString= '<a id="'+tabID+'" class="nav-item nav-link" data-toggle="tab" href="#'+contentID+'" role="tab" aria-controls="'+contentID+'">'+newClassName+'.java'+'</a>';
			$(tabLinkHTMLString).insertBefore('#nav-tab a:last');
			let tabContentHTMLString='<div class="tab-pane fade show active" id="'+contentID+'" role="tabpanel" aria-labelledby="'+tabID+'"><textarea rows=15 class="form-control text-left" name="'+newClassName+'"></textarea></div>';
			$('#code').append(tabContentHTMLString);
			$('#'+tabID).tab('show');
			$('#addClassModal').modal('hide');
		}
	}
**/
$('textarea').keydown(function(e) {
	var keyCode = e.keyCode || e.which;

	if (keyCode === 9) {
		e.preventDefault();
		var start = this.selectionStart;
		var end = this.selectionEnd;

		// set textarea value to: text before caret + tab + text after caret
		spaces = "    ";
		this.value = this.value.substring(0, start) + spaces + this.value.substring(end);

		// put caret at right position again
		this.selectionStart = this.selectionEnd = start + spaces.length;
	}


});

window.addEventListener('load', function() {
	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	var forms = document.getElementsByClassName('needs-validation');
	// Loop over them and prevent submission
	var validation = Array.prototype.filter.call(forms, function(form) {
		form.addEventListener('submit', function(event) {
			if (form.checkValidity() === false) {
				event.preventDefault();
				event.stopPropagation();
			}
			else {
				form.classList.add('form-valid');
			}
			form.classList.add('was-validated');
		}, false);
	});
}, false);

