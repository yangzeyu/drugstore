<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
<@layout.layout title = "药品基础信息管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-base.css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/dropsearch.css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/jquery.searchableSelect.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="7">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <h4 class="page-title inline-block">药品基础信息列表</h4>
                    <a class="m-l-40" href="${ctx.contextPath}/template/drugBaseTemplate.xlsx" download="药品信息模板.xlsx">模板下载</a>
                    <button @click="updateFileGo()" class="btn btn-success btn-sm m-l-10">上传 </button>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">零售药店</a></li>
                        <li class="active">药品信息</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <span class="m-r-5">关键字:</span>
                        <input v-model.trim="searchItems.searchKeyword" v-on:keyup.enter="searchHospitalGo()"
                               type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-120"
                               placeholder="药品名称/商品名">
                        <span class="m-r-5 m-l-20">批准文号:</span>
                        <input v-model.trim="searchItems.searchPermissionNum" v-on:keyup.enter="searchHospitalGo()"
                               type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-100"
                               placeholder="批准文号">
                        <span class="m-r-5 m-l-20">类型: </span>
                        <select v-model="searchItems.searchType"
                                class="form-control input-sm inline-block" style="width: 80px;">
                            <option value="">全部</option>
                            <option value="1">中药</option>
                            <option value="2">西药</option>
                            <option value="3">其他</option>
                        </select>
                        <span class="m-r-5 m-l-20">医保目录: </span>
                        <input type="text" id="dropSearch4"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 160px">
                        <input type="hidden" id="searchItemId4" class="dropSearch">
                        <span class="m-r-5 m-l-20">厂商:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 180px">
                        <input type="hidden" id="searchItemId1" class="dropSearch">
                    </div>
                    <div class="col-md-12 m-t-10">
                         <@security.authorize access="hasRole('PRESCRIPTION:ADD')">
                        <button @click="addHospitalGo()"
                                class="btn btn-success pull-right m-l-10 font-size-big">添加
                        </button>
                         </@security.authorize>

                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-10 font-size-big">清空
                        </button>
                        <button @click="searchHospitalGo()"
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
                                    <th>药品名称</th>
                                    <th>药品编码</th>
                                    <th>批准文号</th>
                                    <th>剂型</th>
                                    <th>类型</th>
                                    <th>规格</th>
                                    <th>单位</th>
                                    <th>转换比</th>
                                    <th>单价</th>
                                    <th>零售价</th>
                                    <th>医保目录</th>
                                    <th>厂商</th>
                                    <th>状态</th>
                                    <@security.authorize access="hasRole('PRESCRIPTION:EDIT')">
                                    <th>
                                        <span><span class="pull-right">操&nbsp;作</span></span>
                                    </th>
                                    </@security.authorize>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-10">{{ index+1 }}</td>
                                    <td class="text-hide-140" :title="showItem.name">{{ showItem.name }}</td>
                                    <td class="text-hide-140" :title="showItem.platformCode">{{ showItem.platformCode }}</td>
                                    <td class="text-hide-100" :title="showItem.permissionNumber">{{ showItem.permissionNumber }}</td>
                                    <td class="text-hide-60" :title="showItem.dosageForm">{{ showItem.dosageForm }}</td>
                                    <td class="text-hide-60" :title="showItem.type">
                                        <sapn v-if="showItem.type ==1">中药</sapn>
                                        <sapn v-else-if="showItem.type ==2">西药</sapn>
                                        <sapn v-else-if="showItem.type ==3">其他</sapn>
                                        <sapn v-else> {{ showItem.type }} </sapn>
                                    </td>
                                    <td class="text-hide-80" :title="showItem.spec">{{ showItem.spec }}</td>
                                    <td class="text-hide-60" :title="showItem.unit">{{ showItem.unit }}</td>
                                    <td class="text-hide-60" :title="showItem.standSpecRate">{{ showItem.standSpecRate }}</td>
                                    <td class="text-hide-60" :title="showItem.unitPrice">{{ showItem.unitPrice }}</td>
                                    <td class="text-hide-60" :title="showItem.retailPrice">{{ showItem.retailPrice }}</td>
                                    <td class="text-hide-160" :title="showItem.medicalInsuranceCode">{{ showItem.medicalInsuranceCode }}</td>
                                    <td class="text-hide-100" :title="showItem.manufactureName">{{ showItem.manufactureName }}</td>
                                    <td>
                                        <div v-if="showItem.status == 1" style="margin-left: 6px;color: #00c292;">
                                            <i class="fa fa-check-circle-o" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else-if="showItem.status == 0" style="margin-left: 6px;color: #f75b36">
                                            <i class="fa fa-times-circle" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else>{{ showItem.status }}</div>
                                    </td>

                                    <@security.authorize access="hasRole('DRUGSTORE:ADD')">
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right">
                                        <a class="m-r-10" @click="editHospitalGo(showItem.id)"
                                           style="cursor:pointer; "
                                           title="编辑">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        </span>
                                    </td>
                                    </@security.authorize>
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
                    <div class="col-md-8">
                        <div id="pagination" class="pagination"></div>
                    </div>
                </div>
            </div>

            <!--新增窗口-->
            <div v-show="showAddPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog" style="width: 750px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增药品
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
                                    <label class="col-sm-2 control-label">药品名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="药品名称">
                                    </div>
                                    <label class="col-sm-2 control-label">
                                        商品名<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addGoodName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="商品名">
                                    </div>
                                    <div class="col-sm-2 control-label">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">剂型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addDosageForm" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="剂型">
                                    </div>
                                    <label class="col-sm-2 control-label">
                                        类型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addItems.addType"
                                                class="form-control input-sm inline-block">
                                            <option disabled="disabled" value="">请选择</option>
                                            <option value="1">中药</option>
                                            <option value="2">西药</option>
                                            <option value="3">其他</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 control-label">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">规格<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addSpec" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="规格">
                                    </div>
                                    <label class="col-sm-2 control-label">
                                        是否医保<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addItems.isMedicalInsurance"
                                                class="form-control input-sm inline-block">
                                            <option disabled="disabled" value="">请选择</option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 control-label">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">转换比<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addStandSpecRate" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="转换比">
                                    </div>
                                    <label class="col-sm-2 control-label">生产厂家:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" id="dropSearch2"  class="form-control input-sm inline-block dropsearch" placeholder="请输入关键字然后选择" autocomplete="off">
                                        <input type="hidden" id="searchItemId2" class="dropsearch">
                                    </div>
                                    <div class="col-sm-2 control-label">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">
                                        单位<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addUnit" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="单位">
                                    </div>
                                    <label class="col-sm-2 control-label">零售价:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addRetailPrice" type="number"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="零售价" autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">批准文号:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addPermissionNum" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="批准文号" autocomplete="off">
                                    </div>
                                    <label class="col-sm-2 control-label">单价:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addItems.addUnitPrice" type="number"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="单价" autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否启用:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addItems.status"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">医保信息:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" id="dropSearch5"  class="form-control input-sm inline-block dropsearch" placeholder="请输入关键字然后选择" autocomplete="off">
                                        <input type="hidden" id="searchItemId5" class="dropsearch">
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
                            <button v-show="!addItems.addName || !addItems.addGoodName|| !addItems.addDosageForm || !addItems.addType ||
                                        !addItems.addSpec || !addItems.isMedicalInsurance || !addItems.addStandSpecRate || !addItems.addUnit"
                                    type="button" disabled="disabled"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="addItems.addName && addItems.addGoodName && addItems.addDosageForm && addItems.addType &&
                            addItems.addSpec && addItems.isMedicalInsurance && addItems.addStandSpecRate && addItems.addUnit"
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
                            <button @click="editCancelGo()" type="button" class="close">×</button>
                            <h4 class="modal-title">
                                编辑药品信息
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
                                    <label class="col-sm-2 control-label">药品名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药品名称" autocomplete="off">
                                    </div>
                                    <label class="col-sm-2 control-label">商品名<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editGoodName" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入商品名" autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">剂型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editDosageForm" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入药品名称" autocomplete="off">
                                    </div>
                                    <label class="col-sm-2 control-label">类型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editItems.editType"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">中药</option>
                                            <option value="2">西药</option>
                                            <option value="3">其他</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">规格<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editSpec" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入规格" autocomplete="off">
                                    </div>
                                    <label class="col-sm-2 control-label">单位<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editUnit" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入单位" autocomplete="off">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">转换比<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editStandSpecRate" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入" autocomplete="off">
                                    </div>
                                    <label class="col-sm-2 control-label">厂商:
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" id="dropSearch3"  class="form-control input-sm inline-block" placeholder="请输入关键字" autocomplete="off">
                                        <input type="hidden" id="searchItemId3" class="dropSearch">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">零售价:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editRetailPrice" type="number"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入">
                                    </div>
                                    <label class="col-sm-2 control-label">批准文号:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editPermissionNum" type="text"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">单价:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editUnitPrice" type="number"
                                               maxlength="30" class="form-control input-sm"
                                               placeholder="请输入">
                                    </div>
                                    <label class="col-sm-2 control-label">是否启用:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editItems.status"
                                                class="form-control input-sm inline-block">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">医保信息:
                                    </label>
                                    <div class="col-sm-3">
                                            <input type="text" id="dropSearch6"  class="form-control input-sm inline-block dropsearch" placeholder="请输入关键字然后选择" autocomplete="off">
                                            <input type="hidden" id="searchItemId6" class="dropsearch">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancelGo()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!editItems.editName || !editItems.editGoodName || !editItems.editDosageForm || !editItems.editType ||
                                    !editItems.editSpec || !editItems.editUnit || !editItems.editStandSpecRate"
                                    type="button" class="btn btn-info btn-sm waves-effect waves-light"
                                    disabled="disabled">
                                确认
                            </button>
                            <button v-show="editItems.editName && editItems.editGoodName && editItems.editDosageForm && editItems.editType &&
                                    editItems.editSpec && editItems.editUnit && editItems.editStandSpecRate"
                                    @click="editConfirmGo()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--上传窗口-->
            <div v-show="showUpdateFilePage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog" style="width: 400px;top:34%;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="updateFileCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">请选择需要上传的Excel文件 :</h4>
                        </div>
                        <div class="modal-body">
                                <div class="form-group">
                                    <div class="col-sm-11">
                                        <form id="updateForm" enctype="multipart/form-data">
                                            <input id="updateFile" type="file" name="file" class="form-control input-sm">
                                        </form>
                                    </div>
                                    <div class="col-sm-1">
                                    </div>
                                </div>
                        </div>
                        <div class="modal-footer">
                            <button @click="updateFileConfirm" type="button" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--上传窗口结束后的信息展示-->
            <div v-show="showUpdateFileMessagePage" class="modal-3" style="display: block;">
                <div class="modal-dialog" style="width: 500px; top: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="updateFileMessageCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title" style="text-align: center;">提示信息</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-12">
                                        <ul>
                                            <li v-for="(showItem,index) in updateFileMessage" style="text-align: center;">{{ showItem }}</li>
                                            <li v-for="(showItem,index) in updateFileMessage1" style="text-align: center;">{{ showItem }}</li>
                                        </ul>
                                    </div>
                                    <div class="col-md-2"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="showCoverPage" class="divBG" style="display: none;"></div>
        </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/drug-base.js"></script>
        <script src="${ctx.contextPath}/js/dropsearch.js"></script>
        <script src="${ctx.contextPath}/js/dropsearch4.js"></script>
        <script>

            $(function(){
                //表头固定
                $("#tableScroll").scroll(function () {
                    var scrollHeight = $('#tableScroll').scrollTop() + "px";
                    $('#fixedHead').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
                    });
                });

                //模糊搜索
                DropSearch.init({
                    selector: "#dropSearch1",
                    url: baseURL + '/common/findManufacture',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 1,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });

                DropSearch.init({
                    selector: "#dropSearch2",
                    url: baseURL + '/common/findManufacture',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 2,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });

                DropSearch.init({
                    selector: "#dropSearch3",
                    url: baseURL + '/common/findManufacture',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 3,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });

                //医保目录的模糊搜索
                DropSearch4.init({
                    selector: "#dropSearch4",
                    url: baseURL + '/common/findAllMedicalInsuranceCode',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 4,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });
                DropSearch4.init({
                    selector: "#dropSearch5",
                    url: baseURL + '/common/findAllMedicalInsuranceCode',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 5,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });
                DropSearch4.init({
                    selector: "#dropSearch6",
                    url: baseURL + '/common/findAllMedicalInsuranceCode',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 6,
                    clickHandler: function(data) {
                        console.log(data);
                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });


            })

        </script>
    </#if>
</@layout.layout>
