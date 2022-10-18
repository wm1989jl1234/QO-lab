<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
   
<!doctype html>
<html lang="en">
  <head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Qo-Lab | Enter Pin</title>

    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
    <!-- Google Font -->
 	 <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
   	<!-- Theme style -->
  	<link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/dist/trainee.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script> 	
  </head>

 <body class="hold-transition login-page">
	 <div class="login-box">
	  <div class="login-logo">
	    <!-- <a href="#"><b>Rest</b>Bill</a> -->
	    <img alt="logo" src="/img/logo1.png" class=".img-responsive">
	  </div>
	  <!-- /.login-logo -->
	  <div class="login-box-body">
	    <form  method="post" action="show_enter_name">
	      <div class="form-group has-feedback">
	      <input id="roomKeyInput" name="roomKey" type="text" id="inputPin" class="form-control" placeholder="Room Pin" required autofocus>
	      </div>
	      <div class="row">
	        <!-- /.col -->
	        <div class="col-xs-12">
	          <button id="joinRoomButton" type="submit" class="btn btn-primary btn-block btn-flat">Join</button>
	        </div>
	        
	        <div class ="col-xs-12" style="padding-top:20px;">
	        	<a href="login" class="form-log-in-with-existing">Are you a Trainer? Login here</a>
	        </div>
	        
	        <!-- /.col -->
	      </div>
	    </form>
	    <p><%= request.getAttribute("message") %></p>
	  </div>
	  <!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="/js/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap/dist/js/bootstrap.min.js"></script>
</html>
