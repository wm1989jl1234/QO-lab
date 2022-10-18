var roomId = null;

function connect(event) {
	
	//event.preventDefault();

	var socket = new SockJS('/qolab');
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnected, onError);
	
}


function onConnected() {
	
	roomId = $("#roomKeyInput").val();
	
	console.log(roomId);
	
	stompClient.subscribe('/topic/' + roomId, onMessageReceived);    
	
    stompClient.send("/app/trainee/register/" + roomId, {}, JSON.stringify({username: "user1"})); 
   
}


function onError(error) {
	console.log("Websocket connection error.");
}


function onMessageReceived(payload) {
	
	console.log(JSON.parse(payload));
	
}


$(document).ready(function() {
	$('#joinRoomButton').click(connect);
	console.log("JS file started")
});