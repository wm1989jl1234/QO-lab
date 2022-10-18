<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.fdmgroup.qolab.model.Trainee"%>
<%@ page import="com.fdmgroup.qolab.model.Room"%>
<%@ page import="java.util.List"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Qo-Lab | Trainer View</title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
		<!-- Font Awesome -->
		<link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
		<!-- Ionicons -->
 		<link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
  		<link rel="stylesheet" href="/css/dist/skins/skin-blue.min.css">
		<!-- Theme style -->
  		<link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
  		<link rel="stylesheet"
        	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        <style>
        	div.challenge-description {
			  white-space: nowrap;
			  overflow: hidden;
			  width: 60%;
			  text-overflow: ellipsis;
			  font-size: 15px;
			  display: inline-block;
		   	  padding-left: 4px;
			  font-weight: 300
			}
		
		    .example-modal .modal {
		      position: relative;
		      top: auto;
		      bottom: auto;
		      right: auto;
		      left: auto;
		      display: block;
		      z-index: 1;
		    }
		
		    .example-modal .modal {
		      background: transparent !important;
		    }
		    
		    @font-face {
  				font-family: consoleFont;
  				src: url(/font/c64_pro_mono-style.woff);
			}

			.consoleBackground {
				background-color: #231F20;
				color:white;
			}
			
			.codingFont {
				font-family:'Lucida Console' ,Courier, monospace;
			}
			
		    .tab-pane:not(.active) {
				display: none;
			}
			.textarea-noborder {
				border:none;
			}
			.codingAreaHeight {
				height: auto;
				max-height: 40vh;
			}
			.consoleAreaHeight {
				height: auto;
				max-height: 15vh;
			}
			
			#console-nav-tabs  {
			    color: white;
			    background-color: black;
			    border-color: black;
			    border-right-color: grey;
			    border-left-color: grey;
			}

			#console-nav-tabContent,
			#console-nav-tabContent>.tab-pane>textarea {
				background-color: black;
				color: white;
				border-style: none;
			}
			
			#console-nav-tabs>li.disabled>a {
				color: #9e9e9e
			}
			
			#console-nav-tabs>li>a {
				color: #757575;
				border-radius: 0
			}
			
			#console-nav-tabs>li>a:hover {
				color: #e0e0e0
			}
			
			#console-nav-tabs>li.active {
				border-top-color: white;
				
			}
			
			#console-nav-tabs>li.active>a,
			#console-nav-tabs>li.active:hover>a {
				background-color: #000;
				color: #fff
			}
			
			#console-nav-tabs>li.active>a {
				border-top-color: transparent;
				border-left-color: black;
				border-right-color: grey;
				border-bottom-color: grey;
				
			}
			.modal-body {
			    max-height: 80vh;
			    overflow-y: auto;
			}
			#console-nav-tabs>li{
				border-top: 0px;
			}
        </style>
       	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script type="text/javascript" src="js/dist/trainerRoom.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>   	
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
		    <!-- Logo -->
		    <a href="starter" class="logo">
		      <!-- mini logo for sidebar mini 50x50 pixels -->
		      <img class="..img-responsive logo-mini" src="/img/logo-mini.png" alt="logo" />
		      <!-- logo for regular state and mobile devices -->
		      <img alt="logo" src="/img/logo-nav1.png" class=".img-responsive ">
		    </a>
		
		    <!-- Header Navbar -->
		    <nav class="navbar navbar-static-top" role="navigation">
		      <!-- Sidebar toggle button-->
		      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
		        <span class="sr-only">Toggle navigation</span>
		      </a>
		      <!-- Navbar Right Menu -->
		      <div class="navbar-custom-menu">
		        <ul class="nav navbar-nav">
		          <!-- User Account Menu -->
		          <li class="dropdown user user-menu">
		            <!-- Menu Toggle Button -->
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		              <!-- The user image in the navbar-->
		              <img src="/img/user-img.png" class="user-image" alt="User Image">
		              <!-- hidden-xs hides the username on small devices so only the image appears. -->
		              <span class="hidden-xs">${sessionScope.trainerLoggedIn.firstName}</span>
		            </a>
		            <ul class="dropdown-menu">
		              <!-- The user image in the menu -->
		              <li class="user-header">
		                <img src="/img/user-img.png" class="img-circle" alt="User Image">
		
		                <p>
		                  ${sessionScope.trainerLoggedIn.firstName} ${sessionScope.trainerLoggedIn.lastName}
		                  <small> Trainer</small>
		                </p>
		              </li>
		              <!-- Menu Footer-->
		              <li class="user-footer">
		                <div class="pull-left">
		                  <a href="#" class="btn btn-default btn-flat">Profile</a>
		                </div>
		                <div class="pull-right">
		                  <a href="logout" class="btn btn-default btn-flat">Sign out</a>
		                </div>
		              </li>
		            </ul>
		          </li>
		        </ul>
		      </div>
		    </nav>
	  </header>
		<!-- Left side column. contains the logo and sidebar -->
  		<aside class="main-sidebar">
		    <!-- sidebar: style can be found in sidebar.less -->
		    <section class="sidebar">
		
		      <!-- Sidebar user panel (optional) -->
		      <div class="user-panel">
		        <div class="pull-left image">
		          <img src="/img/user-img.png" class="img-circle" alt="User Image">
		        </div>
		        <div class="pull-left info">
		          <p>${sessionScope.trainerLoggedIn.firstName} ${sessionScope.trainerLoggedIn.lastName}</p>
		        </div>
		      </div>
		
		      <!-- Sidebar Menu -->
		      <ul class="sidebar-menu" data-widget="tree">
		       
		        <!-- Optionally, you can add icons to the links -->
		        <li class="active"><a href="#"><i class="fa fa-plus-circle"></i> <span>Create Challenge</span></a></li>
		        <li><a href="load-all-challenge"><i class="fa fa-link"></i> <span>Load Challenge</span></a></li>
		        <li class="treeview">
				  <a href="#">
		            <i class="fa fa-files-o"></i>
		            <span>Active Challenge</span>
		            <span class="pull-right-container">
		              <span id="traineeNumber" class="label label-primary pull-right"></span>
		            </span>
		          </a>
		          <ul class="treeview-menu" id="traineeList">
		          </ul>
		        </li>
		      </ul>
		      <!-- /.sidebar-menu -->
		    </section>
		    <!-- /.sidebar -->
		</aside>
	
		<div class="content-wrapper">
	    <!-- Content Header (Page header) -->
	    <section class="content-header">
		      <h1>${room.getChallenge().getChallengeName()}
		      </h1>
		      <div class="challenge-description" data-toggle="modal" data-target="#modal-default">${room.getProblemStatement()}</div>
		      <ol class="breadcrumb" style="margin-right: 10px">
		        <li>
			        <span style="font-size: 1.10em;">Room Key - </span>

			        <span id="room_pin" style="font-size: 1.25em; margin-right: 5px "><strong >${roomPin}</strong></span>
			        <span class="glyphicon glyphicon-copy"  data-toggle="modal" data-target="#modal-clipboard" onclick="copyToClipboard('#room_pin')"></span>
		        </li>
		      </ol>
		      
		      <div class="modal fade" id="modal-default">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Problem Statement</h4>
              </div>
              <div class="modal-body">
                <p>${room.getProblemStatement()}</p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default " data-dismiss="modal">OK</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>

        <div class="modal fade" id="modal-clipboard">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Room Key</h4>
              </div>
              <div class="modal-body">
                <p><strong>${roomPin}</strong> is copied to clipboard</p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default " data-dismiss="modal">OK</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
	    </section>
		<!-- Main content -->
		    <section class="content container-fluid" style="margin-top:10px">
		    	<div id="userList" class="row"></div>		    	
		    </section>
         
		   <!-- /.content -->
		 </div>
		 
		 <!-- /.content-wrapper -->
		 
		<!-- Modal -->
	    <div class="modal fade" id="traineePeekModal">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h4 id="traineeName" class="modal-title pull-left">Trainee name</h4>
                <div class="pull-right">
                    <button type="button" class="btn btn-box-tool" id="helpButton" disabled><i class="glyphicon glyphicon-question-sign fa-lg"></i></button>
				    <button type="button" class="btn btn-box-tool" id="doneButton" disabled><i class="glyphicon glyphicon-ok fa-lg"></i></button>
                    <button type="button" data-dismiss="modal" class="btn close" style="font-size: 31px; margin-left: 6px;"><span aria-hidden="true">&times;</span></button>
                    <!--
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  		<span aria-hidden="true">&times;</span>
                  	</button>
                  	  -->
              	</div>
              </div>
              <div class="modal-body">
				  
				<!-- Main content -->
						<div class="nav-tabs-custom">
				            <ul class="nav nav-tabs" id="sourceFile-nav-tabs">
				            	<li class="active">
				              		<a href="#nav-main" id="nav-main-tab" data-toggle="tab">Main.java</a>
				            	</li>
				            </ul> 
				            <div style="font-family:'Lucida Console' ,Courier, monospace" class="tab-content"" class="tab-content" id="sourceFile-tab-content">
						  		<div class="tab-pane active" id="nav-main" aria-labelledby=nav-main-tab">	
							  		<textarea readonly spellcheck="false" rows=10 class="form-control textarea-noborder text-left codingAreaHeight"></textarea>	
						  		</div>
				              <!-- /.tab-pane -->
				            </div>
				            <!-- /.tab-content -->
				          </div>
				         
				         <div class="nav-tabs-custom" id="console">
				            <ul class="nav nav-tabs" id="console-nav-tabs">
				              <li class="active"><a href="#nav-console" id="nav-console-tab" data-toggle="tab">Console</a></li>
				            </ul>
				            <div class="tab-content" style="font-family:'Lucida Console' ,Courier, monospace" id="console-nav-tabContent">
				              <div class="tab-pane active" id="nav-console" aria-labelledby="nav-console">
				                <textarea readonly spellcheck="false" rows=5 class="form-control textarea-noborder text-left consoleAreaHeight"></textarea>	
				              </div>
				            </div>
				            <!-- /.tab-content -->
				          </div>
              
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
		 
			<!-- Main Footer -->
		  <footer class="main-footer">
		    <!-- Default to the left -->
		    <strong>Copyright &copy; 2020 <a href="#">FDM Group</a>.</strong> All rights reserved.
		  </footer>
	</div>
<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 3 -->
<script src="/js/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="js/dist/adminlte.min.js"></script>
<script type="text/javascript">
	function copyToClipboard(element) {
		var $temp = $("<input>");
		$("body").append($temp);
		$temp.val($(element).text()).select();
		document.execCommand("copy");
		$('#code_copied').show();
		$temp.remove();
	}
	
	var sessionData= {"trainerUsername": "${sessionScope.trainerUsername}","roomId": "${sessionScope.roomId}"}
	loadSessionData(sessionData);
</script>
</body>
</html>