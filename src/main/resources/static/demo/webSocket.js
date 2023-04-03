$(document).ready(function(e){
	$("#sendBtn").on("click", function(e){
		var msg = $("#msg").val().trim();
		sendMessage(msg);
	});
	
	initWebSocket();
});

var webSocket = null;

var initWebSocket = function(){
	var url = "ws://" + location.host + "/webSocket";
	
	webSocket = new WebSocket(url);
	webSocket.onopen = onOpen;
	webSocket.onmessage = onMessage;
	webSocket.onclose = onClose;
	webSocket.onerror = onError;
};

var onOpen = function(e){
	console.log(e);
}

var onMessage = function(e){
	var data = e.data;
	console.log("from server: " + data);
	
	webSocket.close();
};

var onClose = function(e){
	console.log(e);
	console.log("webSocket is disconnected.");
	
	initWebSocket();
};

var onError = function(e){
	console.log(e);
}

var sendMessage = function(msg){
	if(webSocket === undefined || webSocket === null){
		console.log("webSocket is undefined or null.");
		return false;
	} else if(msg === undefined || msg === null || msg === ""){
		console.log("msg is undefined or null or empty.");
		return false;
	}
	
	webSocket.send(msg);
}