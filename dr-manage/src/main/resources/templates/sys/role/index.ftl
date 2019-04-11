<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@layout.layout title = "角色管理"; section>
    <#if section = "css">
        <!--分页的CSS-->
        <link rel="stylesheet" href="${ctx.contextPath}/css/pagination.css">
        <!--alerts CSS -->
        <link href="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.css" rel="stylesheet">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="4">
        <div id="app-1">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">角色列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">系统管理</a></li>
                        <li class="active">角色管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box" style="padding: 15px 18px 16px 18px;">
                <div class="row">
                    <div class="col-md-3">
                        <span class="m-r-5">关键字: </span>
                        <input type="text" id="yy_search_key" @keyup.enter="searchRole(1)" class="form-control input-sm"
                               style="width:50%;  display: inline-block" placeholder="请输入角色名称">
                    </div>
                    <div class="col-md-6"></div>
                    <div class="col-md-3">
        <@security.authorize access="hasRole('ROLE:ADDROLE')">
            <button @click="addRole(pageNo)" class="btn btn-success pull-right m-l-20"
                                style="font-size: 1.1em;">添加
                        </button>
        </@security.authorize>
                        <button @click="searchRole(1)" class="btn btn-info pull-right" style="font-size: 1.1em;">搜索
                        </button>
                    </div>
                </div>
            </div>
            <!--数据展示区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table color-bordered-table muted-bordered-table">
                                <thead>
                                <tr style="font-size: 1.11em;">
                                    <th style="width: 20%;">名称</th>
                                    <th>角色</th>
                                    <th><span class="pull-right">操&nbsp;&nbsp;作</span></th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr v-for="user in users" style="font-size: 0.9em;">
                                    <td>{{ user.name }}</td>
                                    <td>{{ user.code }}</td>
                                    <td class="text-nowrap" style="1.25em;">
                                    <span class="pull-right">
        <@security.authorize access="hasRole('ROLE:ADDROLE')">
                                    <a class="yy_btn1 m-r-5" v-on:click="Vue_edit(user.id,pageNo)"
                                       style="cursor:pointer; font-size: 1.25em;" title="编辑"> <i class="fa fa-edit"></i></a>
        </@security.authorize>
            <@security.authorize access="hasRole('ROLE:DELETEROLE')">
                                    <a class="yy_btn2" v-on:click="Vue_delete(user.id,pageNo)"
                                       style="cursor:pointer;font-size: 1.25em;" data-toggle="tooltip"
                                       data-original-title="删除" title="删除"> <i class="fa fa-close text-danger"></i></a>
            </@security.authorize>
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
                <div class="modal-dialog" style="top: 10%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑角色菜单和信息</h4>
                        </div>
                        <div class="modal-body max-height">
                            <input type="text" id="yy_edit_roleId" style="display: none">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <ul>
                                    <li v-for="d in data">
                                        <span class="checkbox checkbox-info">
                                            <input name="menuIds" type="checkbox" v-bind:value="d.id"
                                                   @click="vue_selected_all(d.id)">
                                            <label style="font-weight: 900;font-size: 15px;"><a
                                                    href="javascript:void(0)" @click="vue_nav($event)"
                                                    v-bind:id="d.id">{{ d.name }} </a></label>
                                        </span>
                                        <ul>
                                            <li v-for="s in d.subMenus">
                                                <span class="checkbox checkbox-info">
                                                    <input name="menuIds" v-bind:parent="d.id" type="checkbox"
                                                           v-bind:value="s.id" @click="vue_selected(s.id, d.id)">
                                                    <label> {{ s.name }} </label>
                                                </span>
                                                <div class="checkbox-list m-l-20">
                                                    <span v-for="o in s.operations" class="checkbox checkbox-inline checkbox-info">
                                                        <input type="checkbox" name="operationIds"
                                                               v-bind:value="o.id" v-bind:menu="s.id"
                                                               @click="operation_select(o.id,s.id)">
                                                        <label> {{ o.name }}</label>
                                                    </span>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </form>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">

                                    <div class="col-sm-10">
                                        <label class="control-label m-r-10">角色名称:
                                        </label>
                                        <input id="yy_edit_name" value="" type="text" class="form-control input-sm"
                                               placeholder="请输入角色名称" style="width: 80%;display: inline-block;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-10">
                                        <label class="control-label m-r-10">角色编码: </label>
                                        <input id="yy_edit_code" value="" type="text" class="form-control input-sm"
                                               placeholder="请输入角色编码" style="width: 80%;display: inline-block;">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                        <#-- <button @click="editSelectAll()" type="button"
                                 class="btn btn-default waves-effect pull-left"
                                 data-dismiss="modal">
                             全选
                         </button>
                         <button @click="editSelectNo()" type="button" class="btn btn-default waves-effect pull-left"
                                 data-dismiss="modal">
                             清除
                         </button>-->
                            <button @click="editCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="editConfirm(pageNo)" type="button"
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
                <div class="modal-dialog" style="top: 20%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增角色</h4>
                        </div>

                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_name" value="" type="text" class="form-control input-sm"
                                               placeholder="请输入角色名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色编码: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_code" value="" type="text" class="form-control input-sm"
                                               placeholder="请输入角色编码">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="modal-footer">
                            <button @click="addCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="addConfirm(pageNo)" type="button"
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
        <!--VUE-->
        <script src="${ctx.contextPath}/js/vue.js"></script>
        <!--分页-->
        <script src="${ctx.contextPath}/js/pagination.js"></script>
        <!-- Sweet-Alert  -->
        <script src="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.min.js"></script>
        <script>
            var baseURL = '${ctx.contextPath}';
        </script>
        <script src="${ctx.contextPath}/js/sys/role.js"></script>
    </#if>
</@layout.layout>
