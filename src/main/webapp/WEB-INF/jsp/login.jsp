<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
 
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Qo-Lab | Log In</title>
    <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="/css/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="css/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="/css/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/css/dist/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="/css/iCheck/blue.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <!-- <a href="#"><b>Rest</b>Bill</a> -->
    <img alt="logo" src="/img/logo1.png" class=".img-responsive">
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>

    <sf:form action="loginTrainer" modelAttribute="trainer" method="post">
      <div class="form-group has-feedback">
        <sf:input type="email" class="form-control" placeholder="FDM Email" path="username" name="loginEmailForm" required="required" />
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <sf:input type="password" class="form-control" placeholder="Password" path="password" name="loginPasswordForm" required="required" />
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> Remember Me
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </sf:form>
    
    <p class="text-red"  >${errorMsg}</p>

    <a href="#">Forgot Password?</a><br>
    <a href="register" class="text-center">New user! Sign up here</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="/js/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/js/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
