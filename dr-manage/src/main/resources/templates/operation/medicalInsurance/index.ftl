<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "医保管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/medicalInsurance.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="29">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <h4 class="page-title inline-block">医保列表</h4>
                    <a class="m-l-40" href="${ctx.contextPath}/template/medicalInsuranceTemplate.xlsx" download="医保目录模板.xlsx">模板下载</a>
                    <button @click="updateFileGo()" class="btn btn-success btn-sm m-l-10">上传
                    </button>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">医保管理</a></li>
                        <li class="active">医保列表</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <span class="m-r-5">关键字:</span>
                        <input v-on:keyup.enter="searchItemsGo()" v-model.trim="searchData.searchKeyword" type="text"
                               class="form-control input-sm inline-block wid-160"
                               placeholder="医保编号/三大目录名称">


                        <span class="m-r-5 m-l-10">收费项目等级:</span>
                        <select v-model.trim="searchData.searchCollectLvl"
                                class="form-control input-sm inline-block wid-80">
                            <option value="">全部</option>
                            <option v-for="item in allGrades" :value="item.collectLvl">{{ item.collectLvl }}</option>
                        </select>
                       <#-- <input v-on:keyup.enter="searchItemsGo()" v-model.trim="searchData.searchCollectLvl" type="text"
                               class="form-control input-sm inline-block wid-100"
                               placeholder="请输入">-->

                        <span class="m-r-5 m-l-10">收费类别:</span>
                        <select v-model.trim="searchData.searchCollectType"
                                class="form-control input-sm inline-block wid-80">
                            <option value="">全部</option>
                            <option value="1">中成药</option>
                            <option value="2">中药饮片</option>
                            <option value="3">西药</option>
                            <option value="4">床位费</option>
                            <option value="5">挂号费</option>
                            <option value="6">护理费</option>
                            <option value="7">检查费</option>
                            <option value="8">检验费</option>
                            <option value="9">其他</option>
                            <option value="10">手术费</option>
                            <option value="11">血液</option>
                            <option value="12">医用特殊材料</option>
                            <option value="13">治疗费</option>
                        </select>
                        <button @click="addItemGo()"
                                class="btn btn-success pull-right m-l-20 font-size-big">添加
                        </button>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-20 font-size-big">清空
                        </button>
                        <button @click="searchItemsGo()"
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
                                    <th>医保编号</th>
                                    <th>三大目录名称</th>
                                    <th>三大目录类别</th>
                                    <th>收费类别</th>
                                    <th>收费项目等级</th>
                                    <th>剂型</th>
                                    <th>规格</th>
                                    <th>是否启用</th>
                                    <th>
                                        <span class="pull-right">操&nbsp;&nbsp;作</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td>{{ index+1 }}</td>
                                    <td>{{ showItem.medicalInsuranceCode }}</td>
                                    <td>{{ showItem.threeDirectoryName }}</td>
                                    <td>{{ showItem.threeDirectoryType }}</td>
                                    <td>
                                        <span v-if="showItem.collectType == 1">
                                            中成药
                                        </span>
                                        <span v-else-if="showItem.collectType == 2">
                                            中药饮片
                                        </span>
                                        <span v-else-if="showItem.collectType == 3">
                                            西药
                                        </span>
                                            <span v-else-if="showItem.collectType == 4">
                                            床位费
                                        </span>
                                            <span v-else-if="showItem.collectType == 5">
                                            挂号费
                                        </span>
                                            <span v-else-if="showItem.collectType == 6">
                                            护理费
                                        </span>
                                            <span v-else-if="showItem.collectType == 7">
                                            检查费
                                        </span>
                                            <span v-else-if="showItem.collectType == 8">
                                            检验费
                                        </span>
                                            <span v-else-if="showItem.collectType == 9">
                                            其他
                                        </span>
                                            <span v-else-if="showItem.collectType == 10">
                                            手术费
                                        </span>
                                            <span v-else-if="showItem.collectType == 11">
                                            血液
                                        </span>
                                            <span v-else-if="showItem.collectType == 12">
                                            医用特殊材料
                                        </span>
                                            <span v-else-if="showItem.collectType == 13">
                                            治疗费
                                        </span>

                                        <div v-else>{{ showItem.collectType }}</div>
                                    </td>
                                    <td>
                                        {{ showItem.collectLvl }}
                                    </td>
                                    <td>{{ showItem.dosageForm }}</td>
                                    <td>{{ showItem.spec }}</td>
                                    <td>
                                        <div v-if="showItem.status == 1" style="margin-left: 6px;color: #00c292;">
                                            <i @click="changeStatus(showItem.id,0)" class="fa fa-check-circle-o" style="font-size: 18px; cursor:pointer"></i>
                                        </div>
                                        <div v-else-if="showItem.status == 0" style="margin-left: 6px;color: #f75b36">
                                            <i @click="changeStatus(showItem.id,1)" class="fa fa-times-circle" style="font-size: 18px; cursor:pointer"></i>
                                        </div>
                                        <div v-else>{{ showItem.status }}</div>
                                    </td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-10">
                                        <a class="yy_btn1" @click="editItemGo(showItem.id)"
                                           style="cursor:pointer; "
                                           title="编辑">
                                            <i class="fa fa-edit"></i>
                                        </a>
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

            <!--新增窗口-->
            <div v-show="showAddPage" class="modal-1 fade in">
                <div class="modal-dialog" style="width: 720px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增医保信息
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
                                    <label class="col-sm-2 control-label">医保编码<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addMedicalInsuranceCode" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录名称">
                                    </div>
                                    <label class="col-sm-2 control-label">收费类别<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addCollectType"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="1">中成药</option>
                                            <option value="2">中药饮片</option>
                                            <option value="3">西药</option>
                                            <option value="4">床位费</option>
                                            <option value="5">挂号费</option>
                                            <option value="6">护理费</option>
                                            <option value="7">检查费</option>
                                            <option value="8">检验费</option>
                                            <option value="9">其他</option>
                                            <option value="10">手术费</option>
                                            <option value="11">血液</option>
                                            <option value="12">医用特殊材料</option>
                                            <option value="13">治疗费</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">三大目录名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addThreeDirectoryName" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录名称">
                                    </div>
                                    <label class="col-sm-2 control-label">三大目录类别<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addThreeDirectoryType" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录类别">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">规格<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addSpec" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入剂型">
                                    </div>
                                    <label class="col-sm-2 control-label">收费项目等级<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model.trim="addData.addCollectLvl"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option v-for="item in allGrades" :value="item.collectLvl">{{ item.collectLvl }}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">剂型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="addData.addDosageForm" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入剂型">
                                    </div>
                                    <label class="col-sm-2 control-label">状态<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="addData.addStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
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
                            <button v-show="!addData.addMedicalInsuranceCode || !addData.addCollectType ||
                                            !addData.addThreeDirectoryName || !addData.addThreeDirectoryType ||
                                            !addData.addSpec || !addData.addCollectLvl || !addData.addDosageForm ||
                                            !addData.addStatus" disabled="disabled"
                                    type="button" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button @click="addConfirm()" v-show="addData.addMedicalInsuranceCode && addData.addCollectType &&
                                            addData.addThreeDirectoryName && addData.addThreeDirectoryType &&
                                            addData.addSpec && addData.addCollectLvl && addData.addDosageForm
                                            && addData.addStatus" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--修改窗口-->
            <div v-show="showEditPage" class="modal-1 fade in">
                <div class="modal-dialog top-20" style="width: 40%;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">修改医保信息
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
                                    <label class="col-sm-2 control-label">医保编码<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editMedicalInsuranceCode" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录名称">
                                    </div>
                                    <label class="col-sm-2 control-label">收费项目等级<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model.trim="editData.editCollectLvl"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option v-for="item in allGrades" :value="item.collectLvl">{{ item.collectLvl }}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">三大目录名称<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editThreeDirectoryName" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录名称">
                                    </div>
                                    <label class="col-sm-2 control-label">三大目录类别<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editThreeDirectoryType" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入三大目录类别">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">规格<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editSpec" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入规格">
                                    </div>
                                    <label class="col-sm-2 control-label">剂型<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editData.editDosageForm" type="text"
                                               class="form-control input-sm"
                                               placeholder="请输入剂型">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">收费类别<span style="color: #f75b36;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editCollectType"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="1">中成药</option>
                                            <option value="2">中药饮片</option>
                                            <option value="3">西药</option>
                                            <option value="4">床位费</option>
                                            <option value="5">挂号费</option>
                                            <option value="6">护理费</option>
                                            <option value="7">检查费</option>
                                            <option value="8">检验费</option>
                                            <option value="9">其他</option>
                                            <option value="10">手术费</option>
                                            <option value="11">血液</option>
                                            <option value="12">医用特殊材料</option>
                                            <option value="13">治疗费</option>
                                        </select>
                                    </div>
                                    <#--<label class="col-sm-2 control-label">状态<span style="color: red;">*</span> :
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editData.editStatus"
                                                class="form-control input-sm inline-block">
                                            <option value="" disabled="disabled">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>-->
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button"
                                    class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!editData.editMedicalInsuranceCode || !editData.editCollectLvl ||
                                            !editData.editThreeDirectoryName || !editData.editThreeDirectoryType ||
                                            !editData.editSpec || !editData.editDosageForm || !editData.editCollectType"
                                   disabled="disabled" type="button" class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="editData.editMedicalInsuranceCode && editData.editCollectLvl &&
                                            editData.editThreeDirectoryName && editData.editThreeDirectoryType &&
                                            editData.editSpec && editData.editDosageForm && editData.editCollectType"
                                    @click="editConfirm()" type="button"
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
            <div v-show="showCoverPage" class="coverPage"></div>
        </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/medicalInsurance.js"></script>
        <script>
            $(function () {
                //表头固定
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
