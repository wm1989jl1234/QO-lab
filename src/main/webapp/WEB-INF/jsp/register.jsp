<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Qo-Lab | Registration</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
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
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition register-page">
	<div class="register-box">
		<div class="register-logo">
			<img alt="logo" src="/img/logo1.png" class=".img-responsive">
		</div>
			
		<div class="register-box-body">
			<p class="login-box-msg">Register a new membership</p>

			<sf:form action="registerTrainer" modelAttribute="trainer"
				method="post">
				<div class="form-group has-feedback">
					<sf:input id="firstName" type="text" class="form-control" placeholder="First name"
						path="firstName" name="registerFirstNameForm" required="required" onkeyup='check();'></sf:input>
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<sf:input id="lastName" type="text" class="form-control" placeholder="Last name"
						path="lastName" name="registerLastNameForm" required="required" onkeyup='check();'></sf:input>
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<sf:input id="email" type="email" class="form-control" placeholder="FDM Email" pattern=".+@fdmgroup.com"
						path="username" name="registerEmailForm" required="required" onkeyup='check();'></sf:input>
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<sf:input id="password" type="password" class="form-control"
						placeholder="Password" path="password" name="registerPasswordForm"
						required="required" onkeyup='check();'></sf:input>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input id="confirmPassword" type="password" class="form-control"
						placeholder="Retype password" name="registerConfirmPasswordForm"
						required="required" onkeyup='check();'> <span
						class="glyphicon glyphicon-log-in form-control-feedback"></span>
				</div>
				<!-- /.col -->
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label> <input type="checkbox"> I agree to the <a
								href="#">terms</a>
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button id="submitButton" disabled type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
					</div>
					<!-- /.col -->
				</div>
			</sf:form>
			
			<a href="login" class="text-center">Already a user! Sign in here</a>
			
			<p class="text-red" id="message" style="margin-top:10px; margin-bottom:5px;" >${registerErrorMsg}</p>
		</div>
		<!-- /.form-box -->
	</div>
	<!-- /.register-box -->

	<!-- jQuery 3 -->
	<script src="/js/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="/js/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="/js/iCheck/icheck.min.js"></script>
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
		});
	</script>

	<script>
		var check = function() {
			if (document.getElementById('password').value == document.getElementById('confirmPassword').value) {
				document.getElementById('message').innerHTML = '';
				
				if (document.getElementById('firstName').value != "" && document.getElementById('lastName').value != "" &&
			 	     document.getElementById('email').value != "" && document.getElementById('password').value != "") {
					document.getElementById('submitButton').disabled = false;
				} else {
					document.getElementById('submitButton').disabled = true;
				}
			} else {
				document.getElementById('message').style.color = 'red';
				document.getElementById('message').innerHTML = 'Password doesn\'t match';
				document.getElementById('submitButton').disabled = true;
			}
		}
	</script>
</body>
</html>
