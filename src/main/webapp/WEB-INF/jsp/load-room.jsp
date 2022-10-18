<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page import ="com.fdmgroup.qolab.model.Challenge" %>
<%@ page import ="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Qo-Lab | Load Room</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
 <link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
 <!-- DataTables -->
  <link rel="stylesheet" href="/css/datatables.net-bs/css/dataTables.bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect. -->
  <link rel="stylesheet" href="/css/dist/skins/skin-blue.min.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/dist/load-room.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
   <style>
	    .example-modal .modal {
	      position: relative;
	      top: auto;
	      bottom: auto;
	      right: auto;
	      left: auto;
	      display: block;
	      z-index: 1;
	    }
	    
	    .dataTableTextWrap {
			overflow: hidden;
			display: -webkit-box;
			-webkit-line-clamp: 1;
			-webkit-box-orient: vertical;
		}
	
	    .example-modal .modal {
	      background: transparent !important;
	    }
	    
	    .text {
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
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
          <p> ${sessionScope.trainerLoggedIn.firstName} ${sessionScope.trainerLoggedIn.lastName}</p>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu" data-widget="tree">
        
        <!-- Optionally, you can add icons to the links -->
        <li ><a href="starter"><i class="fa fa-plus-circle"></i> <span>Create Challenge</span></a></li>
        <li class="active"><a href="load-all-challenge"><i class="fa fa-link"></i> <span>Load Challenge</span></a></li>
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
       Load Challenge
        <small>Optional description</small>
      </h1>
     
    </section>

    <!-- Main content -->
    <section class="content container-fluid">
		
      <!--------------------------
        | Your Page Content Here |
        -------------------------->
		<div class="row">
       		<div class="col-xs-12">
				<div class="box">
            <div class="box-header">
              <h3 class="box-title">Challenge Data</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                  <th class="text">Challenge Name</th>
                  <th class="text">Challenge Description</th>
                  <th class="text">Action</th>
                </tr>
                </thead>
                <tbody>
                	 <%
	                	if((List<Challenge>)request.getAttribute("challengeList") != null) {
	                    	for (Challenge challenge : (List<Challenge>)request.getAttribute("challengeList")) {
               		 %>
			                <tr>
			                  <td><div class ="dataTableTextWrap"><%= challenge.getChallengeName() %></div></td>
			                  <td><div class="dataTableTextWrap"><%= challenge.getProblemStatement() %></div></td>
			                  <td class="text"><a href="/launch-load-room/<%=challenge.getChallengeId() %>">Launch </a><span>|</span><a href="delete-load-room/<%=challenge.getChallengeId() %>" > Delete</a></td>
			                </tr>
			            <%               
                    		}
                		}
                		%>
                </tbody>
                <!--  <tfoot>
	                <tr>
	                  	<th>Challenge Name</th>
                  		<th>Challenge Description</th>
                  		<th>Action</th>
	                </tr> 
                </tfoot>  -->
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
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
<!-- DataTables -->
<script src="/js/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="/js/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="/js/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="/js/fastclick/lib/fastclick.js"></script>
<script>
  $(function () {
    $('#example1').DataTable({
    	"language": {
            "info": "Showing from _START_ till _END_ from the total of _TOTAL_ records",
           	 "aria": {
            	        "sortAscending":  ": activate to sort column ascending",
            	        "sortDescending": ": activate to sort column descending"
            	    }
        },
      'paging'      : true,
      'lengthChange': true,
      'searching'   : true,
      'ordering'    : true,
      'autoWidth'   : true,
      'fixedHeader' : true
    })
  })
</script>
<script type="text/javascript">
	function copyToClipboard(element) {
		var $temp = $("<input>");
		$("body").append($temp);
		$temp.val($(element).text()).select();
		document.execCommand("copy");
		$('#code_copied').show();
		$temp.remove();
	}
</script>
<script type="text/javascript">
	$(document).ready(function () {
			$('#modal-default').on('hidden.bs.modal', function (e) {
			$('#code_copied').hide();
		});
	});
</script>
<script>
	var trainerObject = ${sessionScope.trainerJson};
	getJSON(trainerObject);
</script>
</body>
</html>