$(".password-field").focus(function(){
	console.log("on focus")
	$("#hint").show();
	$("#pass-strenght").show(150);
});

$(".password-field").focusout(function(){
	$("#hint").hide(150);
	$("#pass-strenght").hide(150);
});

$(".password-field").keyup(function(){
	let pass = $(".password-field").val();
	let factor = verifyPassword(pass);
	
	let indicator  = $("#pass-strenght-indicator");
	let valuenow = indicator.attr("aria-valuenow");
	
	if(pass != "") {
		if(factor === 1 && valuenow != 25){
			indicator.animate({width:"25%"});
			indicator.removeClass("bg-primary");
			indicator.addClass("bg-danger");
			indicator.text("Fraca");
			indicator.attr("aria-valuenow", 25);
		}
		if(factor === 2 && valuenow != 50){
			indicator.animate({width:"50%"});
			indicator.removeClass("bg-danger");
			indicator.addClass("bg-warning");
			indicator.text("Moderada");
			indicator.attr("aria-valuenow", 50);
		}
		if(factor === 3 && valuenow != 75){
			indicator.animate({width:"75%"});
			indicator.removeClass("bg-warning");
			indicator.addClass("bg-primary");
			indicator.text("Forte");
			indicator.attr("aria-valuenow", 75);
		}
		if(factor === 4 && valuenow != 100){
			indicator.animate({width:"100%"});
			indicator.removeClass("bg-primary");
			indicator.addClass("bg-success");
			indicator.text("Inquebrável");
			indicator.attr("aria-valuenow", 100);
		}
	} else {
		indicator.animate({width:"0%"});
	}
	
});

function verifyPassword(password) {
	
	let strenghtFactor = 0;
	
	let factor1 = "^[a-z]{1,}$";
	let factor2 = "^(?=.*?[A-Z])[a-zA-Z]{1,}$";
	let factor3 = "^(?=.*?[A-Z])[a-zA-Z]{8,}$";
	let factor4 = "^(?=(?:[^A-Z]*[A-Z]){1,}(?![^A-Z]*[A-Z]))(?=(?:[^a-z]*[a-z]){1,}(?![^a-z]*[a-z]))(?=(?:[^0-9]*[0-9]){1,}(?![^0-9]*[0-9]))(?=(?:[^!\"#\$%&'\(\)\*\+,-\.\/:;<=>\?@[\]\^_`\{\|}~]*[!\"#\$%&'\(\)\*\+,-\.\/:;<=>\?@[\]\^_`\{\|}~]){1,}(?![^!\"#\$%&'\(\)\*\+,-\.\/:;<=>\?@[\]\^_`\{\|}~]*[!\"#\$%&'\(\)\*\+,-\.\/:;<=>\?@[\]\^_`\{\|}~]))[A-Za-z0-9!\"#\$%&\'\(\)\*\+,-\.\/:;<=>\?@[\]\^_`\{\|}~]{8,}$";
;
	
	if(password.match(factor1)){
		strenghtFactor = 1;
	}
	if(password.match(factor2)){
		strenghtFactor = 2;
	}
	if(password.match(factor3)){
		strenghtFactor = 3;	
	}
	if(password.match(factor4)){
		strenghtFactor = 4;
	}
	return strenghtFactor;
}