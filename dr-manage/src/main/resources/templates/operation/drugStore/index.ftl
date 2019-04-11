<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@layout.layout title = "药店管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-store.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="19">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">药店列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">零售药店</a></li>
                        <li class="active">药店管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">关键字:</span>
                        <input v-model.trim="searchData.searchKeyword" v-on:keyup.enter="searchItemGo()" type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-120"
                               placeholder="药店名称">
                        <span class="m-r-5 m-l-10">状态: </span>
                        <select v-model="searchData.searchStatus"
                                class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                        <span class="m-r-5 m-l-10">时间: </span>
                        <div class="input-daterange input-group" id="date-range" style="width: 250px;position: absolute;top: 1px;left: 388px;">
                            <input type="text" id="yy_search_startTime" class="form-control wid-100" name="start"
                                   autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="起始时间"/>
                            <span class="input-group-addon bg-info b-0 text-white">到</span>
                            <input type="text" id="yy_search_endTime" class="form-control wid-100" name="end" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="结束时间"/>
                        </div>
                        <@security.authorize access="hasRole('DRUGSTORE:ADD')">
                            <button @click="addItemGo()"
                                    class="btn btn-success pull-right m-l-20 font-size-big">添加
                            </button>
                        </@security.authorize>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-20 font-size-big">清空
                        </button>
                        <button @click="searchItemGo()"
                                class="btn btn-info pull-right font-size-big">搜索
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
                                <tr class="font-size-big">
                                    <th>#</th>
                                    <th>药店名称</th>
                                    <th>药店编码</th>
                                    <th>地址</th>
                                    <th>联系人</th>
                                    <th>联系电话</th>
                                    <th>邮箱</th>
                                    <th>状态</th>
                                    <th><span class="pull-right">用户管理</span></th>

                                    <th>
                                        <span class="pull-right">操&nbsp;&nbsp;&nbsp;&nbsp;作</span>
                                    </th>

                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-10">{{ index+1 }}</td>
                                    <td>{{ showItem.name }}</td>
                                    <td>{{ showItem.code }}</td>
                                    <td>{{ showItem.address }}</td>
                                    <td>{{ showItem.contractPerson }}</td>
                                    <td>{{ showItem.contractPhone }}</td>
                                    <td>{{ showItem.contractEmail }}</td>
                                    <td style="font-size: 18px;">
                                        <div v-if="showItem.status == 1" style="margin-left: 6px;color: #00c292;">
                                            <i class="fa fa-check-circle-o"></i>
                                        </div>
                                        <div v-else-if="showItem.status == 0" style="margin-left: 6px;color: #f75b36">
                                            <i class="fa fa-times-circle"></i>
                                        </div>
                                        <div v-else>{{ showItem.status }}</div>
                                    </td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right">
                                            <@security.authorize access="hasRole('DRUGSTORE:EDITUSER')">
                                                <a class="yy_btn1 m-r-20" @click="editUserList(showItem.id)"
                                                   style="cursor:pointer; " title="药店用户管理">
                                                    <i class="icon-people"></i>
                                                </a>
                                            </@security.authorize>
                                            </a>
                                    </span>
                                    </td>

                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right">
                                        <@security.authorize access="hasRole('DRUGSTORE:EDIT')">
                                            <a class="yy_btn1 m-r-5" @click="editItemGo(showItem.id)"
                                               style="cursor:pointer; " title="编辑">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                        </@security.authorize>
                                            <@security.authorize access="hasRole('DRUGSTORE:SENDEMAIL')">
                                            <a class="yy_btn1" @click="sendEmailGo(showItem.id)"
                                               style="cursor:pointer; " title="发送邮件">
                                            <i class="ti-email"></i>
                                            </@security.authorize>
                                        </a>
                                    </span>
                                    </td>

                                </tr>
                                </tbody>
                            </table>
                            <div v-show="dataIsNull" style="text-align: center;display: none;" class="m-t-10 m-b-10">
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
            <!--新增窗口-->
            <div v-show="showAddPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增药店
                                <a href="javascript:void(0) " class="tooltip-danger"
                                   data-toggle="tooltip" data-placement="top" title=""
                                   data-original-title="* 为必填项不能为空" style="color: #f75b36; font-size: 14px;">
                                    <i class="ti-help-alt"></i>
                                </a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">药店名称<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addStoreName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店名称">
                                    </div>
                                    <label class="col-sm-2 control-label">药店编码<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addCode" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店编码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">联系邮箱<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addContractEmail" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系邮箱">
                                    </div>
                                    <label class="col-sm-2 control-label">状态<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">联系人 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addContractPerson" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系人">
                                    </div>
                                    <label class="col-sm-2 control-label">联系电话 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addContractPhone" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系电话">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">药店地址 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addAddress" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店地址">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addCancel()" type="button"
                                    class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!addData.addStoreName ||!addData.addCode ||
                                            !addData.addContractEmail ||!addData.addStatus"
                                    type="button" disabled="disabled"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>

                            <button v-show="addData.addStoreName && addData.addCode &&
                                            addData.addContractEmail && addData.addStatus"
                                    @click="addConfirmGo()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--编辑修改窗口-->
            <div v-show="showEditPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑药店
                                <a href="javascript:void(0) " class="tooltip-danger"
                                   data-toggle="tooltip" data-placement="top" title=""
                                   data-original-title="* 为必填项不能为空" style="color: #f75b36; font-size: 14px;">
                                    <i class="ti-help-alt"></i>
                                </a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">药店名称<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editStoreName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店名称">
                                    </div>
                                    <label class="col-sm-2 control-label">药店编码<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editCode" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店编码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">联系邮箱<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editContractEmail" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系邮箱">
                                    </div>

                                    <label class="col-sm-2 control-label">状态<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">联系人 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editContractPerson" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系人">
                                    </div>
                                    <label class="col-sm-2 control-label">联系电话 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editContractPhone" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入联系电话">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">药店地址 :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editAddress" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药店地址">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!editData.editStoreName || !editData.editCode ||
                                            !editData.editContractEmail || !editData.editStatus"
                                    type="button" disabled="disabled" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="editData.editStoreName && editData.editCode &&
                                            editData.editContractEmail && editData.editStatus"
                                    @click="editConfirmGo()" type="button" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!--用户管理页面-->
            <div v-show="showUserPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                药店用户管理
                                <button @click="addUserGo()"
                                        class="btn btn-success btn-sm pull-right inline-block">添加
                                </button>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive fixed-table" id="tableScroll2" style="border: 2px solid #98a6ad;
                        max-height: 400px;overflow: auto;">
                                        <table class="table color-bordered-table muted-bordered-table"
                                               style="border: none;">
                                            <thead id="fixedHead2">
                                            <tr style="font-size: 1.11em;">
                                                <th style="width: 20%;">序号</th>
                                                <th style="width: 20%;">用户名称</th>
                                                <th style="width: 20%;">用户昵称</th>
                                                <th>是否启用</th>
                                                <th><span class="pull-right"
                                                          style="margin-right: 28px">操&nbsp;&nbsp;作</span></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="(user,index) in drugStoreUsers" style="font-size: 0.9em;">
                                                <td>{{ index+1 }}</td>
                                                <td>{{ user.userName }}</td>
                                                <td>{{ user.nickName }}</td>
                                                <td>
                                                    <div v-if="user.status === 1"
                                                         class="checkbox checkbox-success checkbox-circle"
                                                         style="margin: 0; padding: 0 40px;">
                                                        <input type="checkbox" checked="true" disabled>
                                                        <label for="checkbox9"></label>
                                                    </div>
                                                    <div v-else class="checkbox checkbox-success checkbox-circle"
                                                         style="margin: 0; padding: 0 40px;">
                                                        <input type="checkbox" disabled>
                                                        <label for="checkbox9"></label>
                                                    </div>
                                                </td>
                                                <td class="text-nowrap" style="font-size: 1.25em;">
                                    <span class="pull-right m-r-20">
                                    <a class="yy_btn1 m-r-15"
                                       @click="editUserGo(user.id)"
                                       style="cursor:pointer; " title="编辑"> <i
                                            class="fa fa-edit"></i></a>
                                    </span>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div v-show="dataIsNull2" style="text-align: center;display: none;" class="m-b-10 m-t-10">
                                            <span style="color: #f75b36;;">未检索到数据</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button @click="userClose()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                关闭
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--用户修改页面-->
            <div v-show="showEditUserPage" class="modal-2 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editUserCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">修改药店用户
                                <a href="javascript:void(0) " class="tooltip-danger"
                                   data-toggle="tooltip" data-placement="top" title=""
                                   data-original-title="* 为必填项不能为空" style="color: #f75b36; font-size: 14px;">
                                    <i class="ti-help-alt"></i>
                                </a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称 <span style="color: #f75b36">*</span>: </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="editUser.userName" value=""
                                               placeholder="请输入用户名称">
                                    </div>
                                    <label class="col-sm-2 control-label">用户昵称 <span style="color: #f75b36">*</span>: </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="editUser.nickName" value=""
                                               placeholder="请输入用户昵称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">旧密码: </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="editUser.oldPassword" value=""
                                               placeholder="请输入旧密码">
                                    </div>
                                    <label class="col-sm-2 control-label">状态 <span style="color: #f75b36">*</span>: </label>
                                    <div class="col-sm-3">
                                        <select v-model="editUser.userStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">新密码: </label>
                                    <div class="col-sm-3">
                                        <input v-show="!editUser.oldPassword"  type="text" maxlength="30" class="form-control input-sm"
                                               placeholder="请先输入旧密码" disabled="disabled">

                                        <input v-show="editUser.oldPassword" type="text" maxlength="30" class="form-control input-sm"
                                               v-model="editUser.newPassword" value=""
                                               placeholder="请输入新密码">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editUserCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button  v-show="!editUser.userName || !editUser.nickName || !editUser.userStatus"
                                    type="button" disabled="disabled" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="editUser.userName && editUser.nickName && editUser.userStatus"
                                    @click="editUserConfirm()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--用户添加页面-->
            <div v-show="showAddUserPage" class="modal-2 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addUserClose()" type="button" class="close">×</button>
                            <h4 class="modal-title">添加药店用户
                                <a href="javascript:void(0) " class="tooltip-danger"
                                   data-toggle="tooltip" data-placement="top" title=""
                                   data-original-title="* 为必填项不能为空" style="color: #f75b36; font-size: 14px;">
                                    <i class="ti-help-alt"></i>
                                </a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称 <span style="color: #f75b36">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="addUser.userName" value=""
                                               placeholder="请输入用户名称">
                                    </div>
                                    <label class="col-sm-2 control-label">用户昵称 <span style="color: #f75b36">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="addUser.nickName" value=""
                                               placeholder="请输入用户昵称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">密码 <span style="color: #f75b36">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" maxlength="30" class="form-control input-sm"
                                               v-model="addUser.userPassword" value=""
                                               placeholder="请输入密码">
                                    </div>
                                    <label class="col-sm-2 control-label">状态 <span style="color: #f75b36">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addUser.userStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addUserClose()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!addUser.userName || !addUser.nickName || !addUser.userPassword || !addUser.userStatus"
                                    type="button" disabled="disabled" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="addUser.userName && addUser.nickName && addUser.userPassword && addUser.userStatus"
                                    @click="addUserConfirm()" type="button" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="showCoverPage" class="divBG" style="display: none;"></div>
            <div v-show="showCoverPage2" class="coverPage2" style="display: none;"></div>
        </div>
    </#if>
    <#if section = "js">
        <!--时间选择器-->
        <script src="${ctx.contextPath}/js/laydate.js"></script>
        <script src="${ctx.contextPath}/js/operation/drug-store.js"></script>
        <script>
            $(function () {
                //时间选择
                laydate.render({
                    elem: '#yy_search_startTime',
                    trigger: 'click'
                });
                laydate.render({
                    elem: '#yy_search_endTime',
                    trigger: 'click'
                });
                //表头固定
                $("#tableScroll").scroll(function() {
                    var scrollHeight =$('#tableScroll').scrollTop() + "px";
                    $('#fixedHead').css({
                        "position": "relative",
                        "transform":"translateY("+scrollHeight+")"
                    });
                });
                $("#tableScroll2").scroll(function() {
                    var scrollHeight =$('#tableScroll2').scrollTop() + "px";
                    $('#fixedHead2').css({
                        "position": "relative",
                        "transform":"translateY("+scrollHeight+")"
                    });
                });
            });
        </script>
    </#if>
</@layout.layout>
