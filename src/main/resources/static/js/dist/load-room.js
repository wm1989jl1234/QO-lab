var stompClient = null;
var username = null;
var roomId = null;
var trainer = null;
var room = null;

function getJSON(data) {
	trainer = data;
}

function connect(event) {
	
	event.preventDefault();

	var socket = new SockJS('/qolab');
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnected, onError);
	
}


function onConnected() {

    stompClient.send("/app/trainer/createRoom", {}, JSON.stringify(trainer));

    stompClient.subscribe('/topic/trainer/' + trainer.trainerId, onMessageReceived);    
	
}

function onMessageReceived(payload) {
	
	room = JSON.parse(payload.body);
	
	stompClient.subscribe('/topic/' + room.roomId + '/code', onRoomUpdate);
	
}

function onRoomUpdate(payload) {
	console.log("Room object has been udpated.")
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


$(document).ready(function() {
});