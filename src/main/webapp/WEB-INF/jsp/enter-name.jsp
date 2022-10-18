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
    <title>Qo-Lab | Enter Name</title>

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
  </head>

 <body class="hold-transition login-page">
	 <div class="login-box">
	  <div class="login-logo">
	    <!-- <a href="#"><b>Rest</b>Bill</a> -->
	    <img alt="logo" src="/img/logo1.png" class=".img-responsive">
	  </div>
	  <!-- /.login-logo -->
	  <div class="login-box-body">
	    <form  method="post" action="show_trainee_main">
	      <div class="form-group has-feedback">
	      <input type="text" name="traineeName" id="inputName" class="form-control" placeholder="Name" required="required" autofocus>
	      </div>
	      <div class="row">
	        <!-- /.col -->
	        <div class="col-xs-12">
	          <button type="submit" class="btn btn-primary btn-block btn-flat">Enter</button>
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
