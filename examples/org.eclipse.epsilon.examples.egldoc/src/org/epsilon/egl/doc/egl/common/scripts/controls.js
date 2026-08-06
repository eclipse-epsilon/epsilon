function createCookie(name, value) {
	var date = new Date();
	date.setTime(date.getTime()+(365*24*60*60*1000));
	
	var expires = "; expires="+date.toGMTString();
  
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
  
  return null;
}

function getElementsByClass(className) {
	var all = document.all ? document.all : document.getElementsByTagName('*');
	var elements = new Array();
	
	for (var e = 0; e < all.length; e++)
		if (all[e].className == className)
			elements[elements.length] = all[e];
			
	return elements;
}


function saveShowInherited() {
	if (document.getElementById('inherited').checked) {
		createCookie('EglDoc.Controls.ShowInheritedFeatures', 'true');
		displayInherited(true);
	} else {
		createCookie('EglDoc.Controls.ShowInheritedFeatures', 'false');
		displayInherited(false);
	}
}

function setupControls() {
	var showInherited = readCookie('EglDoc.Controls.ShowInheritedFeatures');
	
	if (showInherited) {
		if (showInherited == 'true') {
			document.getElementById('inherited').checked = 'true';
			return;
		}
	}
	
	document.getElementById('inherited').checked = '';
	displayInherited(false);
}

function displayInherited(show) {
	var displayValue = show ? '' : 'none';

	var inheritedFeatures = getElementsByClass('inheritedFeature');
	
	for (var index = 0; index < inheritedFeatures.length; index++) {
		inheritedFeatures[index].style.display = displayValue;
	}
}
