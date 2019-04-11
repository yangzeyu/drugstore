<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
<@layout.layout title = "药品目录管理"; section>
    <#if section = "css">
    <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-list.css">
    <link rel="stylesheet" href="${ctx.contextPath}/css/dropsearch.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="11">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">药品目录列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">零售药店</a></li>
                        <li class="active">药品目录</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <span class="m-r-5">所属药店:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 160px;">
                        <input type="text" id="searchItemId1" class="dropSearch" style="display: none;">
                        <span class="m-r-5  m-l-10">关键字:</span>
                        <input v-model.trim="searchItems.searchKeyword" @keyup.enter="searchHospitalGo()" type="text"
                               maxlength="30" class="form-control input-sm inline-block "
                               placeholder="药品名称/商品名称/药品编码" style="width: 200px;">
                        <span class="m-r-5 m-l-10">状态: </span>
                        <select v-model="searchItems.searchStatus"
                                class="form-control input-sm inline-block" style="width: 100px;">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
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
                                    <th>药店</th>
                                    <th>通用名</th>
                                    <th>使用方法</th>
                                    <th>规格</th>
                                    <th>剂型</th>
                                    <th>单位</th>
                                    <th>厂家</th>
                                    <th>实时库存</th>
                                    <th>对码状态</th>
                                    <th>状态</th>
                                    <@security.authorize access="hasRole('DRUGLIST:EDIT')">
                                        <th>
                                            <span class="pull-right">操&nbsp;&nbsp;作</span>
                                        </th>
                                    </@security.authorize>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-10">{{ index+1 }}</td>
                                    <td>{{ showItem.storeName }}</td>
                                    <td class="text-hide-80" v-bind:title="showItem.drugName">
                                        {{ showItem.drugName }}
                                    </td>
                                    <td>{{ showItem.direction }}</td>
                                    <td class="text-hide-80" v-bind:title="showItem.spec">
                                        {{ showItem.spec }}
                                    </td>
                                    <td>{{ showItem.dosageForm }}</td>
                                    <td>{{ showItem.unit }}</td>
                                    <td class="text-hide-100" v-bind:title="showItem.manufacture">
                                        {{ showItem.manufacture }}
                                    </td>
                                    <td>
                                        <span v-if="showItem.stock ===null">0</span>
                                        <span v-else-if="showItem.stock ==='' ">0</span>
                                        <span v-else> {{ showItem.stock }}</span>
                                    </td>
                                    <td>
                                        <div v-if="showItem.drugId" style="margin-left: 6px;color: #00c292;">
                                            已对码
                                        </div>
                                        <div v-else-if="!showItem.drugId" style="margin-left: 6px;color: #f75b36">
                                            未对码
                                        </div>
                                    </td>
                                    <td>
                                        <div v-if="showItem.status == 1" style="margin-left: 6px;color: #00c292;">
                                            <i class="fa fa-check-circle-o" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else-if="showItem.status == 0" style="margin-left: 6px;color: #f75b36">
                                            <i class="fa fa-times-circle" style="font-size: 18px;"></i>
                                        </div>
                                        <div v-else>{{ showItem.status }}</div>
                                    </td>
                                    <@security.authorize access="hasRole('DRUGLIST:EDIT')">
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
            <!--编辑修改窗口-->
            <div v-show="showEditPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog" style="width: 750px">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancelGo()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑药品目录
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
                                    <label class="col-sm-3 control-label">单次最大处方用量<span style="color: #f75b36;">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editMaxOnceNumber" type="text"
                                               maxlength="30" class="form-control input-sm inline-block wid-150"
                                               placeholder="请输入单次最大处方用量">
                                    </div>
                                    <label class="col-sm-2 control-label">
                                        是否启用<span style="color: #f75b36;">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <select v-model="editItems.editStatus"
                                                class="form-control input-sm inline-block wid-150">
                                            <option value="">请选择</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">库存下限<span style="color: #f75b36;">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editLowerLimit" type="text"
                                               maxlength="30" class="form-control input-sm inline-block wid-150"
                                               placeholder="请输入库存下限">
                                    </div>
                                    <label class="col-sm-2 control-label">
                                        库存上限<span style="color: #f75b36;">*</span>:
                                    </label>
                                    <div class="col-sm-3">
                                        <input v-model.trim="editItems.editUpperLimit" type="text"
                                               maxlength="30" class="form-control input-sm inline-block wid-150"
                                               placeholder="请输入库存上限">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancelGo()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button v-show="!editItems.editMaxOnceNumber || !editItems.editStatus ||
                                            !editItems.editLowerLimit || !editItems.editUpperLimit"
                                    type="button" disabled="disabled"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                            <button v-show="editItems.editMaxOnceNumber && editItems.editStatus &&
                            editItems.editLowerLimit && editItems.editUpperLimit"
                            @click="editConfirmGo()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="showCoverPage" class="divBG" style="display: none;"></div>
        </div>
    </#if>
    <#if section = "js">
        <script>
            <#--var storeId = ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId!'xxx'}-->
            <#--var storeId = ${session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId}-->
        </script>
        <script src="${ctx.contextPath}/js/operation/drug-list.js"></script>
        <script src="${ctx.contextPath}/js/dropsearch.js"></script>
        <script>
            $(function () {
                //表头固定
                $("#tableScroll").scroll(function() {
                    var scrollHeight =$('#tableScroll').scrollTop() + "px";
                    $('#fixedHead').css({
                        "position": "relative",
                        "transform":"translateY("+scrollHeight+")"
                    });
                });

                //搜索框-药店模糊搜索
                DropSearch.init({
                    selector: "#dropSearch1",
                    url: baseURL + '/common/findAllDrugStore',
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

            });



        </script>
    </#if>
</@layout.layout>
