<#macro layout title="管理后台"  >
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <#--<link rel="icon" type="image/png" sizes="16x16" href="${ctx.contextPath}/plugins/images/favicon.png">-->
    <title>${title}</title>
    <#include "head-meta.ftl" >

    <#nested "css" >

</head>

<body class="fix-sidebar">
<!--加载图标-->
<div class="preloader2" id="loadingPage">
    <div class="cssload-speeding-wheel"></div>
</div>
<div id="wrapper">
    <!-- Top Navigation -->
    <#include "header.ftl" >
    <!-- End Top Navigation -->
    <!-- Left navbar-header -->
    <#include "menu.ftl" >
    <!-- Left navbar-header end -->
    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <#nested "content" >
        </div>
        <!-- /.container-fluid -->
        <#include "footer.ftl" >

    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->
<#include "js-meta.ftl" >

<!--Style Switcher -->
<#--<script src="${ctx.contextPath}/plugins/bower_components/styleswitcher/jQuery.style.switcher.js"></script>-->
<#nested "js" >

</body>

</html>

</#macro>
