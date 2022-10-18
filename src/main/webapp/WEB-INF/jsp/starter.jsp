<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="com.fdmgroup.qolab.model.Trainer" %>
    
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Qo-Lab | Home</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
 <link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
  <!-- Toggle style -->
  <link rel="stylesheet" href="/css/dist/toggle.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect. -->
  <link rel="stylesheet" href="/css/dist/skins/skin-blue.min.css">
  <!-- Google Font -->
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
	<!-- Javascript files for jQuery & WebSockets -->
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/dist/trainer-main.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
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
        <!-- <li class="header"></li> -->
        <!-- Optionally, you can add icons to the links -->
        <li class="active"><a href="#"><i class="fa fa-plus-circle"></i> <span>Create Challenge</span></a></li>
        <li><a href="load-all-challenge"><i class="fa fa-link"></i> <span>Load Challenge</span></a></li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
	    <!-- Content Header (Page header) -->
	    <section class="content-header">
		      <h1>
		        Create Challenge
		        <small>Optional description</small>
		      </h1>
		 
	    </section>
<!-- Main content -->
	    <section class="content container-fluid">
		    <form action="load-room" method="post">
		            <div class="form-group has-feedback">
		              <label>Name</label>
		              <input type="text" name="challengeName" class="form-control" placeholder="Enter challenge name" required>
		            </div>
		            <div class="form-group has-feedback">
		                <label>Enter Description</label>
		                <textarea class="form-control" name="problemStatement" rows="10" cols="100" placeholder="Enter problem statement" required></textarea>
		            </div>
		            <div class="row">
		                <div class="col-xs-9">
		                    <label class="switch">
		                          <input type="checkbox">
		                          <span class="slider round" ></span>
		                    </label>
		                    <label class="form-check-label">Allow trainee to see each others screen</label>
		                </div>
		                <div class="col-xs-3">
		                    <button style="background-color: #009FDF; color: white;" type="submit" class="btn btn-block btn-flat">Create Challenge</button>
		                </div>
	            	</div>
	        </form>
	    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

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

<script>
	var JSON = ${sessionScope.trainerJson};
	getJSON(JSON);
</script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>