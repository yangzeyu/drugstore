<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@layout.layout title = "用户管理"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="2">
        <div id="app-1">
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">用户列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">系统管理</a></li>
                        <li class="active">用户管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box" style="padding: 15px 18px 16px 18px;">
                <div class="row">
                    <div class="col-md-3">
                        <span class="m-r-5">关键字: </span>
                        <input @keyup.enter="searchUser(1)" v-model="searchKey" type="text" maxlength="30"
                               class="form-control input-sm"
                               style="width: 50%;display: inline-block" placeholder="请输入用户名称">
                    </div>
                    <div class="col-md-3">
                        <span class="m-r-5">角色: </span>
                        <select v-model="searchRoleId" class="form-control input-sm"
                                style=" display: inline-block; width: auto;">
                            <option value="">请选择</option>
                            <option v-for="role in roles" v-bind:value="role.id">{{ role.name }}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <span class="m-r-5">是否启用: </span>
                        <select id="yy_search_status" v-model="searchStatus" class="form-control input-sm"
                                style=" display: inline-block; width: auto;">
                            <option value="">请选择</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                    <div class="col-md-3">
        <@security.authorize access="hasRole('USER:ADDSYSUSER')">
                        <button @click="addUserGo()" class="btn btn-success pull-right m-l-20" style="font-size: 1.1em;">添加
                        </button>
        </@security.authorize>
                        <button id="searchUser" @click="searchUser(1)" class="btn btn-info pull-right"
                                style="font-size: 1.1em;">搜索
                        </button>
                    </div>
                </div>
            </div>
            <!--数据展示区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        max-height: 540px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead">
                                <tr style="font-size: 1.11em;">
                                    <th style="width: 20%;">用户名称</th>
                                    <th style="width: 20%;">用户昵称</th>
                                    <th>状态</th>
                                    <th><span class="pull-right">操&nbsp;&nbsp;作</span></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="user in users" style="font-size: 0.9em;">
                                    <td>{{ user.userName }}</td>
                                    <td>{{ user.nickName }}</td>
                                    <td>
                                        <div v-if="user.status === 1" style="margin-left: 6px;color: #00c292;">
                                            <i class="fa fa-check-circle-o" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else-if="user.status === 0" style="margin-left: 6px;color: #f75b36">
                                            <i class="fa fa-times-circle" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else>{{ user.status }}</div>
                                    </td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                    <span class="pull-right">
        <@security.authorize access="hasRole('USER:EDITSYSUSER')">
                                    <a class="yy_btn1 m-r-5"
                                       @click="editUserGo(user.id,pageNo)"
                                       style="cursor:pointer; " title="编辑"> <i
                                            class="fa fa-edit"></i></a>
        </@security.authorize>

            <@security.authorize access="hasRole('USER:DELETESYSUSER')">
                                    <a class="yy_btn2" @click="deleteUserGo(user.id,pageNo)" href="#"
                                       data-toggle="tooltip"
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
                <div class="modal-dialog" style="top: 20%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑用户信息</h4>
                        </div>
                        <div class="modal-body">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称: </label>
                                    <div class="col-sm-8">
                                        <input type="text" maxlength="30" class="form-control input-sm" v-model="editUserName" autocomplete="off" value=""
                                               placeholder="请输入用户名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户昵称: </label>
                                    <div class="col-sm-8">
                                        <input type="text" maxlength="30" class="form-control input-sm" v-model="editNickName" autocomplete="off" value=""
                                               placeholder="请输入用户昵称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">密码: </label>
                                    <div class="col-sm-8">
                                        <input type="text" style="display: none !important;">
                                        <input type="password" style="display: none !important;">
                                        <input type="password" class="form-control input-sm" v-model="editPassword"
                                               value=""
                                               placeholder="请输入密码">
                                        <input type="text" style="display: none !important;">
                                        <input type="password" style="display: none !important;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色: </label>
                                    <div class="col-sm-8">
                                        <select id="yy_edit_roleId" v-model="editRoleId" value=""
                                                class="form-control input-sm"
                                                style="width: 25%;">
                                            <option value="">请选择</option>
                                            <option v-for="role in roles" v-bind:value="role.id">{{ role.name }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="modal-footer">
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
                <div class="modal-dialog" style="top: 20%;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增用户</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_userName" v-model="addUserName" autocomplete="off" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入用户名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户昵称: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_nickName" v-model="addNickName" autocomplete="off" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入用户昵称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户密码<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" style="display: none !important;">
                                        <input type="password" style="display: none !important;">
                                        <input id="yy_add_password" v-model="addPassword" autocomplete="off" type="password"
                                               class="form-control input-sm"
                                               placeholder="请输入密码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <select id="yy_add_roleId" v-model="addRoleId" value=""
                                                class="form-control input-sm"
                                                style="width: 25%; display: inline-block;">
                                            <option value=""> 请选择</option>
                                            <option v-for="role in roles" v-bind:value="role.id">{{ role.name }}
                                            </option>
                                        </select>
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
        <script>
            var baseURL = '${ctx.contextPath}';
        </script>
        <script src="${ctx.contextPath}/js/sys/user.js"></script>
    </#if>
</@layout.layout>
