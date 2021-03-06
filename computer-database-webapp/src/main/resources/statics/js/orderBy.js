document.getElementById("tableHeadComputerName").onclick = function () {
	var url = new URL(location.href);
	var nbItem = url.searchParams.get("nbItem");
	var numPage = url.searchParams.get("numPage");
	var order = "name";
	if (nbItem == null) {
		nbItem = 10; 
	}
	if (numPage == null) {
		numPage = 0; 
	}
	location.href = '?nbItem=' + nbItem + '&numPage='+ numPage + '&order=' + order + '&change=true';
};

document.getElementById("tableHeadComputerIntroduced").onclick = function () {
	var url = new URL(location.href);
	var nbItem = url.searchParams.get("nbItem");
	var numPage = url.searchParams.get("numPage");
	var order = "introduced";
	if (nbItem == null) {
		nbItem = 10; 
	}
	if (numPage == null) {
		numPage = 0; 
	}
	location.href = '?nbItem=' + nbItem + '&numPage='+ numPage + '&order=' + order + '&change=true';
};

document.getElementById("tableHeadComputerDiscontinued").onclick = function () {
	var url = new URL(location.href);
	var nbItem = url.searchParams.get("nbItem");
	var numPage = url.searchParams.get("numPage");
	var order = "discontinued";
	if (nbItem == null) {
		nbItem = 10; 
	}
	if (numPage == null) {
		numPage = 0; 
	}
	location.href = '?nbItem=' + nbItem + '&numPage='+ numPage + '&order=' + order + '&change=true';
};

document.getElementById("tableHeadComputerCompany").onclick = function () {
	var url = new URL(location.href);
	var nbItem = url.searchParams.get("nbItem");
	var numPage = url.searchParams.get("numPage");
	var order = "company";
	if (nbItem == null) {
		nbItem = 10; 
	}
	if (numPage == null) {
		numPage = 0; 
	}
	location.href = '?nbItem=' + nbItem + '&numPage='+ numPage + '&order=' + order + '&change=true';
};