<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>登陆页面</title>
    <#include "layout/head-meta.ftl" >
</head>
<style>
    .title{
        color:white;
        font-size: 50px;
        text-align: center;
        margin-top: 100px;
    }
    .login-box{
        margin: 4% auto 0;
    }
    h3{
        font-size: 15px;
        text-align: center;
    }
    .white-box .form-horizontal .box-title{
        font-size: 30px;
        font-family: 微软雅黑;
    }
</style>
<body>
<!-- Preloader -->
<div class="preloader">
    <div class="cssload-speeding-wheel"></div>
</div>
<section id="wrapper" class="login-register">
    <div class="title">
运营端
    </div>
    <div class="login-box">
        <div class="white-box">
            <form class="form-horizontal form-material" id="loginform" method="post" action="${ctx.contextPath}/login">
                <h3 class="box-title m-b-20">登录</h3>
                <div class="form-group ">
                    <div class="col-xs-12">
                        <input class="form-control" type="text" required="" name="username" placeholder="用户名">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-12">
                        <input class="form-control" type="password" required="" name="password" placeholder="密码">
                    </div>
                </div>
                <div class="form-group text-center m-t-20">
                    <div class="col-xs-12">
                        <button class="btn btn-info btn-lg btn-block text-uppercase waves-effect waves-light" type="submit">登录</button>
                    </div>
                </div>

            </form>

        </div>
    </div>
</section>

<#include "layout/js-meta.ftl" >
</body>
</html>
