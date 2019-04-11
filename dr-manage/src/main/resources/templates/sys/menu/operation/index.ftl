<#import "../../../layout/layout.ftl" as layout>
<@layout.layout title = "功能管理"; section>
    <#if section = "css">
        <!--分页的CSS-->
        <link rel="stylesheet" href="${ctx.contextPath}/css/pagination.css">
        <!--alerts CSS -->
        <link href="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.css" rel="stylesheet">
    </#if>
    <#if section = "content">
        <input type="hidden" id="menuId" value="${menuId}">
        <input type="hidden" id="g_menuId" value="3">
        <div id="app-1">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">功能列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">系统管理</a></li>
                        <li><a href="/web/menu/index">菜单管理</a></li>
                        <li class="active">功能管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索添加-->
            <div class="white-box white-box1" style="padding: 15px 18px 16px 18px;">
                <div class="row">
                    <div class="col-md-9">
                        <h4 class="pull-left" style="color: rgba(0, 0, 0, .5)">页面按钮菜单</h4>
                    </div>
                    <div class="col-md-3 ">
                        <a href="/web/menu/index" class="pull-right"><button class="btn btn-default pull-right m-l-20 font-size-big">返回
                        </button></a>
                        <button @click="addOperation()" class="btn btn-success pull-right m-l-20" style="font-size: 1.1em;">
                            添加
                        </button>
                    </div>
                </div>
            </div>
            <!--展示-->
            <div class="white-box white-box1">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table color-bordered-table muted-bordered-table table-hover">
                                <thead>
                                <tr style="font-size: 1.11em;">
                                    <th style="width: 20%;">功能名称</th>
                                    <th>功能编码</th>
                                    <th><span class="pull-right">操&nbsp;&nbsp;作</span></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="operation in operations" :key="operation.id" style="font-size: 0.9em;">
                                    <td>{{ operation.name }}</td>
                                    <td>{{ operation.code }}</td>
                                    <td class="text-nowrap">
                                     <span class="pull-right">
                                         <a class="yy_btn1 m-r-10" v-on:click="Vue_edit(operation.id)" style="cursor:pointer;
                                            font-size: 1.25em;" title="编辑">
                                             <i class="fa fa-edit"></i>
                                         </a>
                                         <#--<a class="yy_btn2" v-on:click="Vue_delete(operation.id)" href="#"
                                            data-toggle="tooltip"
                                            data-original-title="删除" style="font-size: 1.25em;" title="删除">
                                         <i class="fa fa-close text-danger"></i>
                                        </a>-->
                                     </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="dataIsNull" style="text-align: center;display: none;" class="m-b-10 m-t-10">
                                <span style="color: #f75b36;;">未检索到数据</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!--分页-->
                <div class="row m-t-20">
                    <div class="col-md-12">
                        <div id="pagination" class="pagination"></div>
                    </div>
                </div>
            </div>
            <!--编辑修改窗口-->
            <div id="yy_edit_1" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true" style="padding-right: 17px; ">
                <div class="modal-dialog" style="top: 20%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">编辑菜单功能</h4>
                        </div>
                        <div class="modal-body">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <input type="text" class="form-control input-sm" id="yy_edit_id" value=""
                                       style="display: none">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">功能名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_name" value="" type="text" class="form-control input-sm"
                                               placeholder="功能名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">功能编号<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_code" value="" disabled="disabled" type="text" class="form-control input-sm"
                                               placeholder="功能编号">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="editConfirm()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--新增窗口-->
            <div id="yy_add_2" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true" style="padding-right: 17px; ">
                <div class="modal-dialog" style="top: 10%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">新增菜单功能</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">功能名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_name" value="" type="text" class="form-control input-sm"
                                               placeholder="功能名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">功能编号<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_code" value="" type="text" class="form-control input-sm"
                                               placeholder="功能编号">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="addConfirm()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </#if>

    <#if section = "js">
        <!--Vue-->
        <script src="${ctx.contextPath}/js/vue.js"></script>
        <!--分页-->
        <script src="${ctx.contextPath}/js/pagination.js"></script>
        <!-- Sweet-Alert  -->
        <script src="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.min.js"></script>
        <script>
            var baseURL = '${ctx.contextPath}';
        </script>
        <script src="${ctx.contextPath}/js/sys/operation.js"></script>
    </#if>
</@layout.layout>
