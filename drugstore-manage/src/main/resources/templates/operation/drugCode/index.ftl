<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "手工对码"; section>
    <#if section = "css">
        <#--<link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-code.css">-->
        <link rel="stylesheet" href="${ctx.contextPath}/css/dropsearch.css">
    </#if>
    <#if section = "content">
        <#--菜单-->
        <input type="hidden" id="g_menuId" value="4">
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
                        <li class="active">手工对码</li>
                    </ol>
                </div>
            </div>
            <!--数据展示区-->
            <div class="row">
                <#--未对码的药品-->
                <div class="col-md-6">
                    <div class="white-box" style="padding-top: 4px;">
                        <h3 class="box-title">未对码的药品</h3>
                        <div class="m-b-10">
                            <span class="m-r-5">商品名 : </span>
                            <input type="text" v-model.trim="searchData.searchGoodName" class="form-control input-sm inline-block wid-140"
                                   placeholder="请输入商品名" autocomplete="off" maxlength="20">
                            <button @click="searchStoreDrugCode()"
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
                                    <th>商品名</th>
                                    <th>药品编码</th>
                                    <th>单位</th>
                                    <th>规格</th>
                                    <th>剂型</th>
                                    <th>厂家</th>
                                    <th>状态</th>
                                    <th><span class="pull-right">选择</span>
                                    </th>
                                </tr class="font-size-sm">
                                </thead>
                                <tbody>
                                <tr class="font-size-sm" v-for="(showItem,index) in showItems">
                                    <td>
                                        <span>{{ index+1 }}</span>
                                    </td>
                                    <td class="text-hide-140" :title="showItem.goodName">{{ showItem.goodName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugCode">{{ showItem.drugCode }}</td>
                                    <td class="text-hide-60" :title="showItem.unit">{{ showItem.unit }}</td>
                                    <td class="text-hide-60" :title="showItem.spec">{{ showItem.spec }}</td>
                                    <td class="text-hide-80" :title="showItem.dosageForm">{{ showItem.dosageForm }}</td>
                                    <td class="text-hide-80" :title="showItem.manufacture">{{ showItem.manufacture }}</td>
                                    <td>
                                        <span v-if="showItem.drugId" style="color: #00c292;">
                                            已对码
                                        </span>
                                        <span v-else-if="!showItem.drugId" style="color: #f75b36">
                                           未对码
                                        </span>
                                    </td>
                                    <td>
                                        <div class="pull-right m-r-10">
                                            <input type="radio" name="drugCode1" v-model="selectDrugCode" @change="selectName(showItem.id,showItem.drugId)"
                                                   v-bind:value="showItem.drugCode"
                                                   style="cursor:pointer;">
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNull">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <div style="height: 30px"></div>
                    </div>
                </div>
            <#--平台药品信息-->
                <div class="col-md-6">
                    <div class="white-box" style="padding-top: 4px;">
                        <h3 class="box-title">平台药品信息</h3>
                        <div class="m-b-10">
                            <span class="m-r-5">药品名称:</span>
                            <input v-model.trim="searchData2.searchKeyword" @keyup.enter="searchPlatformDrugName(1,1)"
                                   type="text"
                                   class="form-control input-sm inline-block"
                                   placeholder="请输入药品名称" autocomplete="off" maxlength="20" style="width: 100px">
                            <span class="m-l-10">
                                <span class="m-r-5">厂家:
                                    <a href="javascript:void(0)" class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                                       title="" data-original-title="输入关键字然后选择" autocomplete="off" maxlength="20" style="color: #f75b36; font-size: 14px;">
                                        <i class="ti-help-alt"></i>
                                    </a>
                                </span>
                                <input @input="changeName()" type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                                        placeholder="请输入关键字" autocomplete="off" style="width: 150px">
                                <input type="hidden" id="searchItemId1" class="dropSearch">
                            </span>
                            <button @click="searchPlatformDrugName(1,1)"
                                    class="btn btn-info btn-sm m-l-10">搜索
                            </button>
                        </div>
                        <div class="table-responsive fixed-table" id="tableScroll2" style="border: 2px solid #98a6ad;
                                        height: 400px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead2">
                                    <tr style="font-size: 14px">
                                        <th><span class="pull-left">选择</span></th>
                                        <th><span>#</span></th>
                                        <th>通用名</th>
                                        <th>平台编码</th>
                                        <th>收费类别</th>
                                        <th>收费项目等级</th>
                                        <th>剂型</th>
                                        <th>转换比</th>
                                        <th>批准文号</th>
                                        <th>目录类别</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="font-size-sm" v-for="(showItem,index) in showItems2">
                                        <td class="text-nowrap">
                                            <div class="pull-left m-l-10">
                                                <input type="radio" name="drugCode2" type="radio" v-model="selectDrugId"
                                                       v-bind:value="showItem.id">
                                            </div>
                                        </td>
                                        <td><span>{{ index+1 }}</span></td>
                                        <td class="text-hide-140" :title="showItem.name">{{ showItem.name }}</td>
                                        <td class="text-hide-80" :title="showItem.platformCode">{{ showItem.platformCode }}</td>
                                        <td class="text-hide-80" :title="showItem.collectType">
                                            <div v-if="showItem.collectType ==1">中成药</div>
                                            <div v-else-if="showItem.collectType ==2">中药饮片</div>
                                            <div v-else-if="showItem.collectType ==3">西药</div>
                                            <div v-else-if="showItem.collectType ==4">床位费</div>
                                            <div v-else-if="showItem.collectType ==5">挂号费</div>
                                            <div v-else-if="showItem.collectType ==6">护理费</div>
                                            <div v-else-if="showItem.collectType ==7">检查费</div>
                                            <div v-else-if="showItem.collectType ==8">检验费</div>
                                            <div v-else-if="showItem.collectType ==9">其他</div>
                                            <div v-else-if="showItem.collectType ==10">手术费</div>
                                            <div v-else-if="showItem.collectType ==11">血液</div>
                                            <div v-else-if="showItem.collectType ==12">医用特殊材料</div>
                                            <div v-else-if="showItem.collectType ==13">治疗费</div>
                                            <div v-else>{{ showItem.collectType }}</div>
                                        </td>
                                        <td class="text-hide-80" :title="showItem.collectLvl">{{ showItem.collectLvl }}</td>
                                        <td class="text-hide-80" :title="showItem.dosageForm">{{ showItem.dosageForm }}</td>
                                        <td class="text-hide-80" :title="showItem.standSpecRate">{{ showItem.standSpecRate }}</td>
                                        <td class="text-hide-80" :title="showItem.permissionNumber">{{ showItem.permissionNumber }}</td>
                                        <td class="text-hide-80" :title="showItem.threeDirectorytype">{{ showItem.threeDirectorytype }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px;" v-show="dataIsNull2">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <!--开始对码-->
                            <button @click="startComparedCode()" v-if="!showLinkedDrugCodePage"
                                    class="btn btn-success m-t-10 pull-right">匹配
                            </button>
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
                                    <th>药品编码/平台编码</th>
                                    <th>通用名</th>
                                    <th>规格</th>
                                    <th>单位</th>
                                    <th>转换比</th>
                                    <th>生产企业</th>

                                </tr class="font-size-sm">
                                </thead>
                                <tbody>
                                <tr class="font-size-sm">
                                    <td>药品信息：</td>
                                    <td>{{ showItems3.drugBase.platformCode }}</td>
                                    <td>{{ showItems3.drugBase.goodName }}</td>
                                    <td>{{ showItems3.drugBase.spec }}</td>
                                    <td>{{ showItems3.drugBase.unit }}</td>
                                    <td>{{ showItems3.drugBase.standSpecRate }}</td>
                                    <td>{{ showItems3.drugBase.manufactureName }}</td>
                                </tr>
                                <tr class="font-size-sm">
                                    <td>平台信息：</td>
                                    <td>{{ showItems3.drugCatalog.drugCode }}</td>
                                    <td>{{ showItems3.drugCatalog.goodName }}</td>
                                    <td>{{ showItems3.drugCatalog.spec }}</td>
                                    <td>{{ showItems3.drugCatalog.unit }}</td>
                                    <td>{{ showItems3.drugCatalog.standSpecRate }}</td>
                                    <td>{{ showItems3.drugCatalog.manufactureName }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNull3">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                        <div class="row">
                        <button @click="delCompare()" class="btn btn-success m-t-10 pull-right">解除匹配</button>
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
                            <h4 class="modal-title">请输入换算比 :</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <div class="col-sm-10">
                                        <input v-model.trim="conversionRatio" type="number" maxlength="30" class="form-control input-sm"
                                               placeholder="请输入换算比" autocomplete="off">
                                    </div>
                                    <div class="col-sm-2">
                                        <button v-show="!conversionRatio" type="button" disabled="disabled"
                                                class="btn btn-info btn-sm waves-effect waves-light">
                                            确认
                                        </button>
                                        <button @click="comparedCodeConfirm()" v-show="conversionRatio" type="button"
                                                class="btn btn-info btn-sm waves-effect waves-light">
                                            确认
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">

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
            var baseURL = '${ctx.contextPath}';
            var yyStoreId = ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId!'xxx'}
        </script>
        <script src="${ctx.contextPath}/js/dropsearch.js"></script>
        <script src="${ctx.contextPath}/js/operation/drug-code.js"></script>
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


