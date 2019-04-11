<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
<@layout.layout title = "医保对码"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/medical-insurance-code.css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/dropsearch.css">
    </#if>
    <#if section = "content">
    <#--菜单-->
        <input type="hidden" id="g_menuId" value="30">
    <#--药店-->
        <input type="hidden" id="searchItemId2" class="dropSearch">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">比对管理列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">运营管理</a></li>
                        <li class="active">医保对码</li>
                    </ol>
                </div>
            </div>
            <!--数据展示区-->
            <div class="row">
            <#--平台药品信息-->
                <div class="col-md-6">
                    <div class="white-box" style="padding-top: 4px;">
                        <h3 class="box-title">药品信息
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </h3>
                        <div class="m-b-10">
                            <span class="m-r-5">关键字:</span>
                            <input @keyup.enter="searchPlatformDrugName(1,1)"
                                   type="text" id="dropSearch6"
                                   class="form-control input-sm inline-block wid-120"
                                   placeholder="请输入药品名称" autocomplete="off">
                            <input type="hidden" id="searchItemId6" style="display: none;">
                            <span class="m-l-10">
                                <span class="m-r-5">厂家:
                                </span>
                                <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch wid-120"
                                       placeholder="请输入关键字" autocomplete="off" style="width: 160px;">
                                <input type="hidden" id="searchItemId1" class="dropSearch" style="display: none;">
                            </span>
                            <button @click="searchInit2()" class="btn btn-info btn-sm m-l-10">搜索</button>
                        </div>
                        <div class="table-responsive fixed-table" id="tableScroll2" style="border: 2px solid #98a6ad;
                                        height: 400px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead2">
                                <tr style="font-size: 14px">
                                    <th><span>#</span></th>
                                    <th>通用名</th>
                                    <th>平台编码</th>
                                    <th>规格</th>
                                    <th>单位</th>
                                    <th>剂型</th>
                                    <th>转换比</th>
                                    <th>厂商</th>
                                    <th>状态</th>
                                    <th><span class="pull-left">选&nbsp;&nbsp;择</span></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr class="font-size-sm" v-for="(showItem,index) in showItems2">
                                    <td><span>{{ index+1 }}</span></td>
                                    <td class="text-hide-120" :title="showItem.name">{{ showItem.name }}</td>
                                    <td>{{ showItem.platformCode }}</td>
                                    <td class="text-hide-80" :title="showItem.spec">{{ showItem.spec }}</td>
                                    <td>{{ showItem.unit }}</td>
                                    <td>{{ showItem.dosageForm }}</td>
                                    <td>{{ showItem.standSpecRate }}</td>
                                    <td class="text-hide-60" :title="showItem.manufactureName">{{ showItem.manufactureName }}</td>
                                    <td>
                                        <span v-if="showItem.insuranceId" style="color: #00c292;">
                                            已对码
                                        </span>
                                        <span v-else-if="!showItem.insuranceId" style="color: #f75b36">
                                           未对码
                                        </span>
                                    </td>
                                    <td class="text-nowrap">
                                        <div class="pull-left m-l-10">
                                            <input type="radio" name="drugCode2" type="radio" v-model="selectDrugId" @change="selectName(showItem.id,showItem.insuranceId)"
                                                   v-bind:value="showItem.id">
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px;" v-show="dataIsNull2">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <div style="height: 30px"></div>
                    </div>
                </div>

                <#--医保信息-->
                <div class="col-md-6">
                    <div class="white-box" style="padding-top: 4px;">
                        <h3 class="box-title">医保信息
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </h3>
                        <div class="m-b-10">
                            <span class="m-r-5">关键字 :
                            </span>
                            <input type="text" id="dropSearch5" class="form-control input-sm inline-block wid-120"
                                   placeholder="请输入药品名称" autocomplete="off" maxlength="30">
                            <input type="hidden" id="searchItemId5" style="display: none;">
                            <span class="m-r-5">类别: </span>
                            <select v-model="searchData.searchCollectType"
                                    class="form-control input-sm inline-block wid-70">
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
                            <span class="m-r-5">等级 : </span>
                            <select v-model.trim="searchData.searchCollectLvl"
                                    class="form-control input-sm inline-block wid-70">
                                <option value="">全部</option>
                                <option v-for="item in allGrades" :value="item.collectLvl">{{ item.collectLvl }}</option>
                            </select>
                            <button @click="searchInit()"
                                    class="btn btn-info btn-sm m-l-10">搜索
                            </button>
                        </div>
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                                height: 400px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table"
                                   style="border: none">
                                <thead id="fixedHead">
                                <tr style="font-size: 14px">
                                    <th>#</th>
                                    <th><span>选&nbsp;&nbsp;择</span>
                                    <th>三大目录名称</th>
                                    <th>三大目录类别</th>
                                    <th>医保编码</th>
                                    <th>规格</th>
                                    <th>剂型</th>
                                    <th>类别</th>
                                    <th>状态</th>
                                    </th>
                                </tr class="font-size-sm">
                                </thead>
                                <tbody>
                                <tr class="font-size-sm" v-for="(showItem,index) in showItems">
                                    <td>
                                        <span>{{ index+1 }}</span>
                                    </td>
                                    <td>
                                        <div class="">
                                            <input type="radio" name="drugCode1" v-model="selectMedicalInsuranceId" @change="selectName2(showItem.id)"
                                                   v-bind:value="showItem.id"
                                                   style="cursor:pointer;">
                                        </div>
                                    </td>
                                    <td class="text-hide-100" :title="showItem.goodName">{{ showItem.threeDirectoryName }}</td>
                                    <td>{{ showItem.threeDirectoryType }}</td>
                                    <td>{{ showItem.medicalInsuranceCode }}</td>
                                    <td>{{ showItem.spec }}</td>
                                    <td>{{ showItem.dosageForm }}</td>
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
                                        <span v-else>{{ showItem.collectType }}</span>
                                    </td>
                                    <td>
                                        <span v-if="showItem.status ==1" style="color: #00c292;">
                                            启用
                                        </span>
                                        <span v-else-if="!showItem.status ==1" style="color: #f75b36">
                                           停用
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNull">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <button @click="startComparedCode()" v-if="!showLinkedDrugCodePage" class="btn btn-success m-t-10 pull-right">匹配</button>
                        <div style="height: 30px"></div>
                    </div>
                </div>
            </div>

            <!--已对码窗口-->
            <div v-if="showLinkedDrugCodePage" class="row">
                <div class="col-md-12">
                    <div class="white-box" style="padding-top: 4px;">
                        <h3 class="box-title" style="margin: 0px">对码结果列表
                        </h3>
                        <div class="table-responsive fixed-table" id="tableScroll3" style="border: 2px solid #98a6ad;
                                height: 132px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table"
                                   style="border: none">
                                <thead id="fixedHead3">
                                <tr style="font-size: 14px">
                                    <th>#</th>
                                    <th>平台编码/医保编码</th>
                                    <th>通用名/三大目录名称</th>
                                    <th>规格</th>
                                    <th>单位/剂型</th>
                                    <th>转换比/收费类别</th>
                                    <th>生产企业/状态</th>

                                </tr class="font-size-sm">
                                </thead>
                                <tbody>
                                <tr class="font-size-sm" v-if="showItems3.drugBase">
                                    <td>药品信息：</td>
                                    <td>{{ showItems3.drugBase.platformCode }}</td>
                                    <td>{{ showItems3.drugBase.goodName }}</td>
                                    <td>{{ showItems3.drugBase.spec }}</td>
                                    <td>{{ showItems3.drugBase.unit }}</td>
                                    <td>{{ showItems3.drugBase.standSpecRate }}</td>
                                    <td>{{ showItems3.drugBase.manufactureName }}</td>
                                </tr>
                                <tr class="font-size-sm" v-if="showItems3.medicalInsurance">
                                    <td>医保信息：</td>
                                    <td>{{ showItems3.medicalInsurance.medicalInsuranceCode }}</td>
                                    <td>{{ showItems3.medicalInsurance.threeDirectoryName }}</td>
                                    <td>{{ showItems3.medicalInsurance.spec }}</td>
                                    <td>{{ showItems3.medicalInsurance.dosageForm }}</td>
                                    <td>
                                        <span v-if="showItems3.medicalInsurance.collectType ==1">中药</span>
                                        <span v-else-if="showItems3.medicalInsurance.collectType ==2">西药</span>
                                        <span v-else>{{ showItems3.medicalInsurance.collectType }}</span>
                                    </td>
                                    <td>
                                         <span v-if="showItems3.medicalInsurance.status ==1" style="color: #00c292;">
                                            启用
                                        </span>
                                        <span v-else-if="showItems3.medicalInsurance.status ==0" style="color: #f75b36">
                                           停用
                                        </span>

                                        <span v-else>
                                            {{ showItems3.medicalInsurance.status }}
                                        </span>

                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNull3">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <div class="row">
                            <button @click="delCompare()" class="btn btn-success m-l-10 m-t-10 pull-right">解除匹配</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--对码窗口-->
            <div v-show="showComparedCodePage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog top-36 wid-400">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="comparedCodeCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">是否确认匹配 :</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <div class="col-sm-10">

                                    </div>
                                    <div class="col-sm-2">

                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="comparedCodeConfirm()" type="button"
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
        <script src="${ctx.contextPath}/js/dropsearch.js"></script>
        <script src="${ctx.contextPath}/js/dropsearch5.js"></script>
        <script src="${ctx.contextPath}/js/dropsearch6.js"></script>
        <script src="${ctx.contextPath}/js/operation/medical-insurance-code.js"></script>
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

                $("#tableScroll2").scroll(function () {
                    var scrollHeight = $('#tableScroll2').scrollTop() + "px";
                    $('#fixedHead2').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
                    });
                });

                $("#tableScroll3").scroll(function () {
                    var scrollHeight = $('#tableScroll3').scrollTop() + "px";
                    $('#fixedHead3').css({
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

                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });

                DropSearch6.init({
                    selector: "#dropSearch6",
                    url: baseURL + '/compareDrugCode/findAllDrugByKey',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    clickHandler: function(data) {

                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                    }
                });

                DropSearch5.init({
                    selector: "#dropSearch5",
                    url: baseURL + '/compareMedicalInsurance/findAllInsuranceByKey',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    clickHandler: function(data) {

                    },
                    ajaxHandler: function(res, list) {
                        $.each(res.data, function(i, item) {
                            list.push(item);
                        });
                        console.log(list)
                    }
                });

            });
        </script>
    </#if>
</@layout.layout>


