var stompClient = null;
var trainerUsername = null;
var room = null;
let trainees = {};

function loadSessionData(data) {
	roomId = data.roomId;
	trainerUsername = data.trainerUsername;
}

function connect(event) {

	var socket = new SockJS('/qolab');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, onConnected, onError);
	
}

function onConnected() {
	
	console.log('/topic/' + roomId);
	stompClient.subscribe('/topic/' + roomId, onRoomUpdate); 
   	stompClient.send("/app/trainer/createRoom/" + roomId, {}, JSON.stringify(trainerUsername));

}

const set = (obj, path, val) => { 
    const keys = path.split('.');
    const lastKey = keys.pop();
    const lastObj = keys.reduce((obj, key) => 
        obj[key] = obj[key] || {}, 
        obj); 
    lastObj[lastKey] = val;
};

function onRoomUpdate(payload) {
	roomData = JSON.parse(payload.body);
	console.log(roomData);
	$("#userList").empty();
	$("#traineeList").empty();
	$("#traineeNumber").text(roomData.trainees.length);
	for (currentTrainee of roomData.trainees) {
		var isNeedsHelp = "";
		var isDone = "";
		if (currentTrainee.isNeedHelp) {isNeedsHelp = 'style="color: rgb(255, 255, 0)"'};
		if (currentTrainee.isDone) {isDone = 'style="color: rgb(0, 166, 90)"'};
		set(trainees, currentTrainee.name + '.isNeedHelp', currentTrainee.isNeedHelp);
		set(trainees, currentTrainee.name + '.isDone', currentTrainee.isDone);
		set(trainees, currentTrainee.name + '.sourceFiles', currentTrainee.sourceFiles);
		set(trainees, currentTrainee.name + '.consoleOutput', currentTrainee.consoleOutput);
		$("#userList").append('       <div class="col-md-4">           <!-- DIRECT CHAT PRIMARY -->           <div class="box box-primary direct-chat direct-chat-primary">             <div class="box-header with-border">               <h3 class="box-title"><a id="selectedTrainee'+currentTrainee.name+'">' + currentTrainee.name + '</a></h3>                <div class="box-tools pull-right">                 <button type="button" class="btn btn-box-tool"><i class="glyphicon glyphicon-question-sign fa-lg"' + isNeedsHelp + '></i></button>                 <button type="button" class="btn btn-box-tool"><i class="fa fa-comments fa-lg"></i></button>                 <button type="button" class="btn btn-box-tool"><i class="glyphicon glyphicon-ok fa-lg"' + isDone + '></i></button>               </div>             </div>             <!-- /.box-header -->             <div class="box-body" style="margin:10px;">  		<label for="codeBlock">Code Block</label> 		<textarea id="codeBlock" spellcheck="false" class="direct-chat-messages codingFont" style="width:100%; resize: none;" readonly>' + currentTrainee.sourceFiles[0].bodyText + '</textarea>                 </div>                    <!-- /.box-body -->             <div class="box-footer">       <label for="consoleOutput">Console Output</label>     <textarea id="consoleOutput" spellcheck="false" class="direct-chat-messages codingFont consoleBackground" style="width:100%; resize: none;" readonly>' + currentTrainee.consoleOutput + '</textarea>                </div>                  <!-- /.box-footer-->           </div>           <!--/.direct-chat -->         </div>');
		if (currentTrainee.sessionId == null) {
			$("#userList").last().css({ 'opacity' : 0.7 , 'border-color': 'red'});
			$("#userList").last().css("border-color", "red");
			console.log(currentTrainee.name + " disconnected.");
		}
	}
	
	
	function sortByTimestamp(traineesDone) {
		var fastestTrainee = traineesDone[0];
		for (currentTrainee of traineesDone) {
			if (currentTrainee.isDoneTimestamp < fastestTrainee.isDoneTimestamp) {
				fastestTrainee = currentTrainee;
			}
		}
		console.log(fastestTrainee);
		var isNeedHelpString = "";
		if (fastestTrainee.isNeedHelp) {isNeedHelpString = '<i class="fa fa-question fa-lg"></i>'}
		$("#traineeList").append('<li><a href="#"><i class="fa fa-user"></i> <span>' + fastestTrainee.name + '</span><span class="pull-right-container"><small class="label pull-right bg-green"><i class="glyphicon glyphicon-ok"></i></small><small class="label pull-right bg-yellow">' + isNeedHelpString + '</small></span></a></li>')
		traineesDone.splice(traineesDone.indexOf(fastestTrainee), 1);
		if (traineesDone.length > 0 ) {sortByTimestamp(traineesDone);}
	}
	
	var traineesDone = [];
	var traineesNotDone = [];
	
	//Split trainee array into two - done and not done
	for (trainee of roomData.trainees) {
		if (trainee.isDoneTimestamp) {traineesDone.push(trainee)}
		else {traineesNotDone.push(trainee)}
	}
	
	//Display trainees that are done (if there are any)
	if (traineesDone.length > 0) {sortByTimestamp(traineesDone)};
	
	//Display trainees that are not yet done
	for (trainee of traineesNotDone) {
		var isNeedHelpString = "";
		if (trainee.isNeedHelp) {isNeedHelpString = '<i class="fa fa-question fa-lg"></i>'}
		$("#traineeList").append('<li><a href="#"><i class="fa fa-user"></i> <span>' + trainee.name + '</span><span class="pull-right-container"><small class="label pull-right"></i></small><small class="label pull-right bg-yellow">' + isNeedHelpString + '</small></span></a></li>')
	}
	
}

function onError(error) {
	console.log("Error connecting to websocket");
}

$(document).ready(function() {
	connect();
});

$(document).on('click', '[id^=selectedTrainee]', function(e){ 
		e.preventDefault();
		$('#sourceFile-nav-tabs').children().not(':first').remove();
		$('#sourceFile-tab-content').children().not(':first').remove();
		let traineeName = $(e.target).text();
		$('#traineeName').text(traineeName);
		let currentTrainee = trainees[traineeName]
		if (currentTrainee.isNeedHelp) {
			$('#helpButton').css("color", "rgb(255, 255, 0)");
		}
		if (currentTrainee.isDone) {$('#doneButton').css("color", "rgb(0, 166, 90)")};
		$('#nav-main textarea').text(currentTrainee.sourceFiles[0].bodyText);
		for(let i = 1; i < currentTrainee.sourceFiles.length; i++){
			let className= currentTrainee.sourceFiles[i].title;
			let contentID= "nav-"+ className;
			let tabID = contentID+'-tab';
		  	let tabLinkHTMLString= '<li><a id="'+tabID+'" data-toggle="tab" href="#'+contentID+'"	>'+className+'.java'+'</a><li>';
			$('#sourceFile-nav-tabs').append(tabLinkHTMLString);
			let tabContentHTMLString='<div id="'+contentID+'" class="tab-pane" aria-labelledby="'+tabID+'"><textarea readonly spellcheck="false" rows=10 class="form-control textarea-noborder text-left codingAreaHeight" name="sourceFileContents" fileindex="'+ i +'">'+currentTrainee.sourceFiles[i].bodyText+'</textarea></div>';
			$('#sourceFile-tab-content').append(tabContentHTMLString);
		}
		$('#nav-console textarea').text(currentTrainee.consoleOutput);
		$('#traineePeekModal').modal('show');
});