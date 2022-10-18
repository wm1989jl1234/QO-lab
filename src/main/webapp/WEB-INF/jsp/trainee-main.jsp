<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ page import="com.fdmgroup.qolab.model.Trainee"%>
<%@ page import="com.fdmgroup.qolab.model.Room"%>
<%@ page import="java.util.List"%>
<%@ page import="com.fdmgroup.qolab.model.SourceFile"%>

<!DOCTYPE html>
<html lang="en">
<head>
       <meta charset="ISO-8859-1" />
       <meta http-equiv="X-UA-Compatible" content="IE=edge" />
       <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
       <title>Qo-Lab | Challenge</title>
       <link href="jquery-ui/jquery-ui.min.css" rel="stylesheet">
       
       <link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
	 <!-- Theme style -->
	<link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
	<link rel="stylesheet" href="/css/dist/trainee-main.css">
 	<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
	      page. However, you can choose any other skin. Make sure you
	      apply the skin class to the body tag so the changes take effect. -->
	<link rel="stylesheet" href="/css/dist/skins/skin-blue.min.css">
	<!-- Google Font -->
	<link rel="stylesheet"
	      href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
	<style>
		.glyphicon {
    		font-size: 25px;
    		color: #fff;
		}
		.textarea-noborder {
			border:none;
		}
		.codingAreaHeight {
			height: auto;
			min-height: 65vh;
		}
		.codingFont {
			font-family:'Lucida Console' ,Courier, monospace;
		}
	</style>

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
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
      	<button type="button" class="btn btn-box-tool" id="helpButton"><i class="glyphicon glyphicon-question-sign"></i></button>
        <button type="button" class="btn btn-box-tool" id="doneButton"><i class="glyphicon glyphicon-ok"></i></button>
      	<button type="submit" class="btn btn-success btn-lg" style="margin-right:10px" onclick="runCode()">Run</button>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header" style="font-size: 1.3em;color:#fff">Challengers</li>
        <li id="userList"></li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
<!-- Main content -->

		<div class="nav-tabs-custom" style="height:100%">
            <ul id="sourceTabs" class="nav nav-tabs">

            <% for(SourceFile s : (List<SourceFile>)((Trainee)request.getAttribute("traineeLoggedIn")).getSourceFiles()) { 
					    		if(s.getTitle() == "Main"){ %>
              	<li class="active"><a href="<%= "#nav-"+s.getTitle() %>" id="<%= "nav-"+s.getTitle()+"-tab" %>" data-toggle="tab"><%= s.getTitle() %>.java</a></li>
              <% } else { %>
              <li>
              	<a href="<%= "#nav-"+s.getTitle() %>" id="<%= "nav-"+s.getTitle()+"-tab" %>" data-toggle="tab"><%= s.getTitle() %>.java
	    			<button type="button" value="<%=s.getTitle()%>" class="close ml-1 close-button" aria-label="Close" >
					  <span class="d-flex justify-content-center align-items-center" aria-hidden="true" style="margin-left:5px">&times;</span>
					</button>
				</a>
				<form id="<%= "removeSourceFile"+s.getTitle() %>" action="remove_source_file">
	    			<input type="hidden" name="sourceFileName" value="<%=s.getTitle()%>">
	    		</form>
              </li>
              <% } %>
			<% } %>
              <li id="nav-add-tab-item"><a id="nav-add-tab" data-toggle="modal" data-target="#addClassModal" class="text-muted"><i class="fa fa-plus"></i></a></li>
            </ul> 
            <div class="tab-content codingFont" id="nav-tabContent">
            <form id="code" action="compile_code">
            			<% int i = 0; %>
						<% for(SourceFile s : (List<SourceFile>)((Trainee)request.getAttribute("traineeLoggedIn")).getSourceFiles()) {
							if(s.getTitle().equals("Main")){ 
							%>
						  		<div class="tab-pane active" id="<%= "nav-"+s.getTitle() %>" aria-labelledby="<%= "nav-"+s.getTitle()+"-tab" %>">	

<textarea id="file-0" spellcheck="false" rows=20 class="form-control textarea-noborder text-left codingAreaHeight codingFont" name="sourceFileContents" fileindex="<%= i%>"><%= s.getBodyText() %></textarea>	
</div>
			    			<% } else{ %>
					    		<div class="tab-pane" id="<%= "nav-"+s.getTitle() %>" aria-labelledby="<%= "nav-"+s.getTitle()+"-tab" %>">	
<textarea  spellcheck="false" rows=20 class="form-control textarea-noborder text-left codingAreaHeight codingFont" name="sourceFileContents" fileindex="<%= i%>"><%= s.getBodyText() %></textarea>	
</div>

			  				<% } %>
			  				<input type="hidden" name="sourceFileNames" value="<%=s.getTitle()%>">
					  		<% 
					  			i++;
							} 
					  		%>
			</form>

              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
         
         <div class="nav-tabs-custom" id="console" style="margin-bottom:0px">
            <ul class="nav nav-tabs" id="console-nav-tab">
              <li class="active"><a href="#nav-console" id="nav-console-tab" data-toggle="tab">Console</a></li>
              <li><a href="#nav-challenge-desc" id="nav-challenge-desc-tab" data-toggle="tab">Challenge Description</a></li>
              
            </ul>
            <div class="tab-content" id="console-nav-tabContent">
              <div class="tab-pane active" id="nav-console" aria-labelledby="nav-console" style="font-family:'Lucida Console' ,Courier, monospace">
                <textarea id="consoleOutput" spellcheck="false" rows=10 class="form-control textarea-noborder text-left"></textarea>	
              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane" id="nav-challenge-desc">
               	<textarea spellcheck="false" rows=10 class="form-control textarea-noborder text-left"><%= ((Trainee)request.getAttribute("traineeLoggedIn")).getRoom().getProblemStatement() %></textarea>	
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
         
	    <!-- Modal -->
	    <div class="modal fade" id="addClassModal">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Class</h4>
              </div>
              <div class="modal-body">
                <form id="newClassForm" action="add_source_file">
			      	<div class="form-group">
						<label>Class Name</label>
						<div class="input-group">
                			<input id="newClassName" type="text" class="form-control" name="sourceFileName" type="text" autofocus required>
                			<span class="input-group-addon">.java</span>
              			</div>
			        </div>
		        </form>
              </div>
              <div class="modal-footer" >
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="createClassButton"  class="btn btn-primary" onclick="createClass()" >Create</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
  
</div>

<!-- jQuery 3 -->
<script src="/js/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="js/dist/adminlte.min.js"></script>

	<script src="jquery-ui/jquery-ui.min.js"></script>
	<script src="js/trainee-main.js"></script>
	<script src="fontawesome/js/all.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script type="text/javascript" src="js/dist/trainee-main.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	    
	<script>
		var room = ${requestScope.roomKeyJson};
		getRoom(room);
		var trainee = ${requestScope.traineeJson};
		getTrainee(trainee);
	</script>
	<script>
       	var trainee = {"name": "${sessionScope.traineeName}", "roomId": "${sessionScope.traineeRoomId}"}
       	getTrainee(trainee);
    </script>
</body>
</html>
