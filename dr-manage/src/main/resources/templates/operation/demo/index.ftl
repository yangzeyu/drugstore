<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "XXX管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/XXX.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="7">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">XXX列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">XXX</a></li>
                        <li class="active">XXX</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-2">
                        <span class="m-r-5">XXX:</span>
                        <input v-model.trim="searchData.searchXXX" v-on:keyup.enter="searchItems(1)" type="text"
                               class="form-control input-sm inline-block wid-100"
                               placeholder="XXX">
                    </div>
                    <div class="col-md-2">
                        <span class="m-r-5">XXX: </span>
                        <select v-model="searchData.searchXXX"
                                class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option :value="XXX.id" v-for="XXX in XXXs">{{ XXX.XXX }}</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <button @click="addItemGo()"
                                class="btn btn-success pull-right m-l-20 font-size-big">添加
                        </button>
                        <button @click="resetItemGo()"
                                class="btn btn-primary pull-right m-l-20 font-size-big">重置
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
                                    <th>XXX</th>
                                    <th>
                                        <span class="pull-right m-r-20">操&nbsp;&nbsp;作</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="showItem in showItems">
                                    <td>{{ showItem.name }}</td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-20">
                                        <a class="yy_btn1 m-r-15" @click="editItemGo(showItem.id)"
                                           style="cursor:pointer; "
                                           title="编辑">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                    </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
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
            <div v-show="showAddPage"  class="modal fade in">
                <div class="modal-dialog top-20" style="width: 40%;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">新增XXX</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">XXX<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addXXX" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入剂型">
                                    </div>
                                    <label class="col-sm-2 control-label">XXX<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addXXX"
                                                class="form-control input-sm inline-block">
                                            <option value="2">XXX</option>
                                            <option value="1">XXX</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">XXX:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addXXX" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入">
                                    </div>
                                    <label class="col-sm-2 control-label">XXX:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addXXX"
                                                class="form-control input-sm inline-block">
                                            <option :value="XXX.id" v-for="XXX in XXXs">{{ XXX.name }}</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addCancel()" type="button"
                                    class="btn btn-default waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="addConfirm()" type="button"
                                    class="btn btn-info waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--修改窗口-->
            <div v-show="showEditPage"  class="modal fade in">
                <div class="modal-dialog top-20" style="width: 40%;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">修改XXX</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">XXX<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editXXX" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入剂型">
                                    </div>
                                    <label class="col-sm-2 control-label">XXX<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editXXX"
                                                class="form-control input-sm inline-block">
                                            <option value="2">XXX</option>
                                            <option value="1">XXX</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">XXX:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editXXX" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入">
                                    </div>
                                    <label class="col-sm-2 control-label">XXX:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editXXX"
                                                class="form-control input-sm inline-block">
                                            <option :value="XXX.id" v-for="XXX in XXXs">{{ XXX.name }}</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button"
                                    class="btn btn-default waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="editConfirm()" type="button"
                                    class="btn btn-info waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="showCoverPage" class="coverPage"></div>
        </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/drug-store.js"></script>
    </#if>
</@layout.layout>
