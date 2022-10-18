var room = null;
var trainee = null;

function getTrainee(traineeData) {
	trainee = traineeData;
}

function connect(event) {
	var socket = new SockJS('/qolab');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, onConnected, onError);
}

function onConnected() {
	stompClient.subscribe('/topic/' + trainee.roomId, onMessageReceived);
	stompClient.send("/app/trainee/register/" + trainee.roomId, {}, JSON.stringify(trainee));
}

function onError(error) {
	console.log("Websocket connection error.");
}

function onMessageReceived(payload) {	
	$("#userList").empty();
	room = JSON.parse(payload.body);
	console.log(room)
	
	//trainee list
	for (currentTrainee of room.trainees) {
		if (currentTrainee.name == trainee.name) {
			trainee = currentTrainee;
			$("#consoleOutput").val(currentTrainee.consoleOutput);
		}
		
		//populate left-hand list
		$("#userList").append('<a href="#"><i class="fa fa-user"></i> <span style="margin-left:5px;">' + currentTrainee.name + '</span></a>');
			//console output
	}
}

function updateCode() {
	if (trainee.isDone) {
		trainee.isDone = false;
		$('#doneButton').children().css("color", "white");
		stompClient.send("/app/trainee/status/" + trainee.roomTag, {}, JSON.stringify(trainee));
	}
	var newCode = $(this).val();
	var index = $(this).attr("fileindex");
	console.log(index);
	trainee.sourceFiles[index].bodyText = newCode;

	stompClient.send("/app/trainee/send/" + trainee.roomTag, {}, JSON.stringify(trainee));
}

function runCode() {
	stompClient.send("/app/trainee/run/" + trainee.roomTag, {}, JSON.stringify(trainee));
}

function createClass(){
	var className = $("#newClassName").val();

	for (sourceFile of trainee.sourceFiles){
		if(sourceFile.title == className){
			$('#addClassModal').modal("hide");
			return;
		}
	}
	
	stompClient.send("/app/trainee/createClass/" + trainee.roomTag + "/" + trainee.name, {}, className)
	$('#code').append('<div class="tab-pane" id="nav-'+className+'" aria-labelledby="nav-'+className+'-tab">	<textarea id="file-'+trainee.sourceFiles.length+'" spellcheck="false" rows=20 class="form-control textarea-noborder text-left" name="sourceFileContents" fileindex="'+trainee.sourceFiles.length+'"> public class '+className+' {\r\n\t\r\n} </textarea>	</div><input type="hidden" name="sourceFileNames" value="'+className+'">')
	$('<li>              	<a href="#nav-'+className+'" id="nav-'+className+'-tab" data-toggle="tab">'+className+'.java<button type="button" value="'+className+'" class="close ml-1 close-button" aria-label="Close" >					  <span class="d-flex justify-content-center align-items-center" aria-hidden="true" style="margin-left:5px">&times;</span>					</button>				</a>				<form id="removeSourceFile'+className+'" action="remove_source_file">	    			<input type="hidden" name="sourceFileName" value="'+className+'">	    		</form>              </li>').insertBefore('#nav-add-tab-item')
	$('#file-'+trainee.sourceFiles.length).on('keyup', updateCode);
	$('#addClassModal').modal("hide");
}

function helpUpdate() {
	if (trainee.isNeedHelp) {
		trainee.isNeedHelp = false;
		$('#helpButton').children().css("color", "white");
	}
	else {
		$('#helpButton').children().css("color", "yellow");
		trainee.isNeedHelp = true;
	};
	
	stompClient.send("/app/trainee/status/" + trainee.roomTag, {}, JSON.stringify(trainee));
}

function doneUpdate() {
	if (trainee.isDone) {
		trainee.isDone = false;
		$('#doneButton').children().css("color", "white");
	}
	else {
		$('#doneButton').children().css("color", "rgb(0,166,90)");
		trainee.isDone = true;
	};
	
	stompClient.send("/app/trainee/status/" + trainee.roomTag, {}, JSON.stringify(trainee));
}

$(document).ready(function() {
	connect();
	$('textarea').on('keyup', updateCode);
	$('#helpButton').on('click', helpUpdate);
	$('#doneButton').on('click', doneUpdate);
});