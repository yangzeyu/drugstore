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
<!-- Preloader -->
<#--
<div class="preloader">
    <div class="cssload-speeding-wheel"></div>
</div>
-->
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

<div id="userMessageCard" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="padding-right: 17px; ">
    <div class="modal-dialog" style="top: 10%; width: 40%;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">编辑用户信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用量 :
                        </label>
                        <div class="col-sm-3">
                            <input type="text"
                                   class="form-control input-sm"
                                   placeholder="请输入">
                        </div>
                        <label class="col-sm-2 control-label"> 单次剂量:
                        </label>
                        <div class="col-sm-3">
                            <input type="text"
                                   class="form-control input-sm"
                                   placeholder="请输入">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="editCancelGo" type="button" class="btn btn-default waves-effect"
                        data-dismiss="modal">
                    取消
                </button>
                <button id="editConfirm" type="button"
                        class="btn btn-info waves-effect waves-light">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--遮罩层-->
<div class="divBG" style="display: none;"></div>
<#include "js-meta.ftl" >

<!--Style Switcher -->
<#--<script src="${ctx.contextPath}/plugins/bower_components/styleswitcher/jQuery.style.switcher.js"></script>-->
<#nested "js" >

</body>

</html>

</#macro>
