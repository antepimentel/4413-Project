
/**
 * This is placeholder function
 * 
 * @returns
 */
function validate() {
	var ok = true;
	var p = document.getElementById("minCreditTaken").value;
	if (isNaN(p) || p < 0) {
		alert("Minimum Credits Taken must be at least 0");
		document.getElementById("credit-error").innerHTML = "*";
		ok = false;
	} else {
		document.getElementById("credit-error").innerHTML = "";
	}
	
	if (!ok)
		return false;

	p = document.getElementById("namePrefix").value;
	if (p = "") {
		alert("Name must not be empty");
		document.getElementById("name-error").innerHTML = "*";
		ok = false;
	} else {
		document.getElementById("name-error").innerHTML = "";
	}
		
	return ok;
}

function doAjaxQuery(address) {
	
//	var check = validate();
//	
//	if(!check){
//		return;
//	}
	
	var request = new XMLHttpRequest();
	var data = 'comm=ajax';
	
	var title = "title="+document.getElementById("title").value;
	var category = "category="+document.getElementById("category").value;
	
	
	data += "&"+title+"&"+category;
	
	var newAddress = address + "?" + data;
	request.open("GET", newAddress, true);
	request.onreadystatechange = function() {
		handler(request);
	};
	
	console.log(newAddress);
	
	request.send();
}

function handler(request) {
	if ((request.readyState == 4) && (request.status == 200)) {
		var target = document.querySelector("#ajaxTarget");
		target.innerHTML = request.responseText;
	}
}