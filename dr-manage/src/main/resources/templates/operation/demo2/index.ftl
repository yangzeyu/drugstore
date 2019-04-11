<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "demo管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/community.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="7">

        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">demo列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">demo管理</a></li>
                        <li class="active">demo管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <span class="m-r-5">关键字:</span>
                        <input v-model.trim="searchData.searchKey" @keyup.enter="searchInit()" type="text" maxlength="20"
                               class="form-control input-sm inline-block wid-140"
                               placeholder="名称/编码/简称">
                        <span class="m-r-5 m-l-10">是否启用: </span>
                        <select v-model="searchData.searchStatus"
                                class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                        <button @click="searchItem()"
                                class="btn btn-info m-l-40 font-size-big">搜索
                        </button>
                        <button @click="resetSearch()"
                                class="btn btn-primary m-l-10 font-size-big">重置
                        </button>
                        <button @click="addItemGo()"
                                class="btn btn-success pull-right font-size-big">添加
                        </button>
                    </div>
                </div>
            </div>
            <!--数据展示区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive fixed-table tableScroll" id="tableScroll">
                            <table class="table color-bordered-table muted-bordered-table">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>序号</th>
                                    <th>社区名称</th>
                                    <th>编码</th>
                                    <th>appCode</th>
                                    <th>联系人</th>
                                    <th>联系方式</th>
                                    <th>状态</th>
                                    <th>等级</th>
                                    <th>地址</th>
                                    <th><span class="pull-right">操&nbsp;&nbsp;作</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-120" :title="item.name">{{ item.name }}</td>
                                    <td class="text-hide-140" :title="item.code">{{ item.code }}</td>
                                    <td class="text-hide-120" :title="item.appCode">{{ item.appCode }}</td>
                                    <td class="text-hide-80" :title="item.contactPerson">{{ item.contactPerson }}</td>
                                    <td class="text-hide-100" :title="item.contactPhone">{{ item.contactPhone }}</td>
                                    <td>
                                        <div v-if="item.status == 1" style="margin-left: 6px;color: #00c292;">
                                            <i class="fa fa-check-circle-o" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else-if="item.status == 0" style="margin-left: 6px;color: #f75b36">
                                            <i class="fa fa-times-circle" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else>{{ item.status }}</div>
                                    </td>
                                    <td>
                                        <div v-if="item.lvl ==1">一级</div>
                                        <div v-else-if="item.lvl ==2">二级</div>
                                        <div v-else-if="item.lvl ==3">三级</div>
                                        <div v-else> {{ item.lvl }}</div>
                                    </td>
                                    <td class="text-hide-120" :title="item.address">{{ item.address }}</td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-10">
                                        <a class="yy_btn1" @click="editItemGo(item.id)"
                                           style="cursor:pointer; "
                                           title="编辑">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                    </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="dataIsNull" class="t-a-center m-t-10 m-b-10">
                                <span class="c-warning">未检索到数据</span>
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
            <div v-show="showAddPage" class="modal-1 fade in">
                <div class="modal-dialog wid-700">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增社区
                                <a href="javascript:void(0) " class="tooltip-danger"
                                   data-toggle="tooltip" data-placement="top" title=""
                                   data-original-title="*必填项不能为空">
                                    <i class="ti-help-alt sayWhat"></i>
                                </a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 col-md-2 control-label">社区名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3 col-md-3">
                                        <input v-model.trim="addData.addName" type="text" maxlength="20"
                                               class="form-control input-sm"
                                               placeholder="请输入社区名称">
                                    </div>
                                    <label class="col-sm-2 control-label">上级社区:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addParentId"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="0">无上级</option>
                                            <option v-for="lvlOneHospital in lvlOneHospitals"
                                                    v-bind:value="lvlOneHospital.id">{{ lvlOneHospital.name }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">社区简称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addShortName" type="text" maxlength="20"
                                               class="form-control input-sm"
                                               placeholder="请输入社区简称">
                                    </div>
                                    <label class="col-sm-2 control-label">社区地址:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addAddress" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入社区地址">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">社区编码<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addCode" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入社区编码">
                                    </div>
                                    <label class="col-sm-2 control-label">联系人:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addContactPerson" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入联系人姓名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否启用<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled>请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">联系电话:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addContactPhone" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入联系电话">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">appCode :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addAppCode" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入appCode">
                                    </div>
                                    <label class="col-sm-2 control-label">appToken:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-if="!addData.addAppToken" @click="createAppToken(1)"  type="text" maxlength="50"
                                               class="form-control input-sm"
                                               placeholder="点击生成appToken">
                                        <input v-if="addData.addAppToken"  v-model.trim="addData.addAppToken" type="text" disabled="disabled" maxlength="100"
                                               class="form-control input-sm b-c-white">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addCancel()" type="button" class="btn btn-sm btn-default waves-effect">
                                取消
                            </button>
                            <button v-show="!addData.addName || !addData.addShortName || !addData.addCode || !addData.addStatus"
                                    type="button" disabled="disabled"
                                    class="btn btn-sm btn-info waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="addData.addName && addData.addShortName && addData.addCode && addData.addStatus"
                                    @click="addConfirm()" type="button"
                                    class="btn btn-sm btn-info waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!--编辑修改窗口-->
            <div v-show="showEditPage" class="modal-1 fade in">
                <div class="modal-dialog wid-700">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑社区信息
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
                                    <label class="col-sm-2 control-label">社区名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model="editData.editName" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入社区名称" maxlength="30">
                                    </div>
                                    <label class="col-sm-2 control-label">上级社区:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editParentId"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="0">无上级</option>
                                            <option v-for="lvlOneHospital in lvlOneHospitals"
                                                    v-bind:value="lvlOneHospital.id">{{ lvlOneHospital.name }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">社区简称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model="editData.editShortName" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入社区简称" maxlength="30">
                                    </div>
                                    <label class="col-sm-2 control-label">社区地址:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editAddress" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入社区地址" maxlength="30">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">社区编码<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editCode" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入社区编码" maxlength="30">
                                    </div>
                                    <label class="col-sm-2 control-label">联系人:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model="editData.editContactPerson" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入联系人姓名" maxlength="30">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否启用:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model.trim="editData.editStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled>请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">联系电话:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editContactPhone" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入联系电话" maxlength="30">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">appCode :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editAppCode" type="text" maxlength="30"
                                               class="form-control input-sm"
                                               placeholder="请输入appCode" maxlength="30">
                                    </div>
                                    <label class="col-sm-2 control-label">appToken:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-if="!editData.editAppToken" @click="createAppToken(2)"  type="text" maxlength="100"
                                               class="form-control input-sm"
                                               placeholder="点击生成appToken" maxlength="30">
                                        <input v-if="editData.editAppToken"  v-model.trim="editData.editAppToken" type="text" disabled="disabled" maxlength="100"
                                               class="form-control input-sm b-c-white">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <span v-show="!editData.editName || !editData.editShortName || !editData.editCode">
                                <button type="button" disabled="disabled"
                                        class="btn btn-info btn-sm waves-effect waves-light">
                                    确认
                                </button>
                            </span>
                            <span v-show="editData.editName && editData.editShortName && editData.editCode">
                                <button @click="editConfirmGo(pageNo)" type="button"
                                        class="btn btn-info btn-sm waves-effect waves-light">
                                    确认
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="showCoverPage" class="coverPage"></div>
        </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/community.js"></script>
        <script>
            $(function () {
                //表头固定--数据展示区
                $("#tableScroll").scroll(function () {
                    var scrollHeight = $('#tableScroll').scrollTop() + "px";
                    $('#fixedHead').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
                    });
                });
            });
        </script>
    </#if>
</@layout.layout>
