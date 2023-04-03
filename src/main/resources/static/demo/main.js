$(document).ready(function(e){
	$("#webSocketBtn").on("click", function(e){
		var url = location.origin + "/webSocket.do";
		window.open(url);
	});
});
