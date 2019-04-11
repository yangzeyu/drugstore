<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <#include "layout/head-meta.ftl" >

</head>
<body>
<!-- Preloader -->
<div class="preloader">
    <div class="cssload-speeding-wheel"></div>
</div>
<section id="wrapper" class="error-page">
    <div class="error-box">
        <div class="error-body text-center">
            <h3 class="text-uppercase">出错了</h3>
            <h3 class="text-uppercase">${url!''}</h3>
            <p class="text-muted m-t-30 m-b-30">${(exception.message)!""}</p>
            <a href="${ctx.contextPath}/home" class="btn btn-info btn-rounded waves-effect waves-light m-b-40">返回首页</a> </div>
        <footer class="footer text-center">2016 © Pixel Admin.</footer>
    </div>
</section>
<!-- jQuery -->
    <#include "layout/js-meta.ftl" >
</body>
</html>
