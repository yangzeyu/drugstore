<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "库存管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-stock.css">
    </#if>
    <#if section = "content">
    <#--菜单的-->
        <input type="hidden" id="g_menuId" value="25">
    <#--药店的-->
        <input type="hidden" id="searchItemId1" class="dropSearch">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-md-2">
                    <h4 class="page-title">库存列表</h4>
                </div>
                <div class="col-md-6" style="text-align: center">
                    <span v-show="drugStockMSGPage">
                        <h5 style="display: inline-block;color: rgba(0, 0, 0, .5);font-size: 16px;" class="m-r-20">当前库存: <span
                                style="font-weight: bold;color: black;">{{ drugStockMSG.stock }}</span></h5>
                        <h5 style="display: inline-block;color: rgba(0, 0, 0, .5);font-size: 16px;" class="m-r-20">入库合计: <span>{{ drugStockMSG.inStock }}</span></h5>
                        <h5 style="display: inline-block;color: rgba(0, 0, 0, .5);font-size: 16px;" class="m-r-20">出库合计: <span>{{drugStockMSG.outStock }}</span></h5>
                    </span>
                </div>
                <div class="col-md-4">
                    <ol class="breadcrumb">
                        <li><a href="#">零售药店</a></li>
                        <li class="active">库存管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <span class="m-r-5">所属药店:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip"
                               data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1" class="form-control input-sm inline-block wid-140"
                               placeholder="请输入关键字" autocomplete="off">
                        <span class="m-r-5 m-l-10">药品名称:</span>
                        <input v-on:keyup.enter="searchItemGo()" v-model.trim="searchData.searchDrugName" type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-100"
                               placeholder="药品名称">
                        <input type="hide" v-model.trim="searchData.searchCatalogId" style="display: none !important;">
                        <button @click="goBackPage()" v-if="searchData.searchCatalogId!==''"
                                class="btn btn-default pull-right m-l-10 font-size-big">返回
                        </button>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-10 font-size-big">清空
                        </button>
                        <button @click="searchItemGo()"
                                class="btn btn-info pull-right font-size-big">搜索
                        </button>
                    </div>
                </div>
            </div>
            <!--数据展示区-->
            <div v-show="showStockPage" class="white-box">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        max-height: 540px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>#</th>
                                    <th>商品名</th>
                                    <th>药品编码</th>
                                    <th>剂型</th>
                                    <th>规格</th>
                                    <th>厂家</th>
                                    <th>单位</th>
                                    <th>首营库存</th>
                                    <th>校验库存</th>
                                    <th>药店库存</th>
                                    <th>
                                        <span class="pull-right">详&nbsp;&nbsp;情</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-10">{{ index+1 }}</td>
                                    <td class="text-hide-150" :title="showItem.goodName">{{ showItem.goodName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugCode">{{ showItem.drugCode }}</td>
                                    <td class="text-hide-100" :title="showItem.dosageForm">{{ showItem.dosageForm }}
                                    </td>
                                    <td class="text-hide-100" :title="showItem.spec">{{ showItem.spec }}</td>
                                    <td class="text-hide-100" :title="showItem.manufacture">{{ showItem.manufacture }}
                                    </td>
                                    <td class="text-hide-100" :title="showItem.unit">{{ showItem.unit }}</td>
                                    <td class="text-hide-100" :title="showItem.initStock">{{ showItem.initStock }}</td>
                                    <td class="text-hide-100" :title="showItem.stock">
                                        <span v-if="showItem.stock ===null">0</span>
                                        <span v-else-if="showItem.stock ===''">0</span>
                                        <span v-else>{{ showItem.stock }}</span>
                                    </td>
                                    <td class="text-hide-80" :title="showItem.storeStock">{{ showItem.storeStock }}</td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-10">
                                        <a class="yy_btn1" @click="detailItem(showItem.id)"
                                           style="cursor:pointer; "
                                           title="库存详情">
                                            <i class="ti-zoom-in"></i>
                                        </a>
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="drugStoreIsNull" style="text-align: center;display: none;"
                                 class="m-t-10 m-b-10">
                                <span style="color: #f75b36;;">请先选择药店</span>
                            </div>
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
                        <span class="pull-right">
                            <span class="m-l-10">所属药店:
                                <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip"
                                   data-placement="top"
                                   title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                    <i class="ti-help-alt"></i>
                                </a>
                            </span>
                            <input type="text" id="dropSearch2" class="form-control input-sm inline-block wid-140 m-l-10"
                                   placeholder="请输入关键字" autocomplete="off">
                            <input type="hidden" id="searchItemId2" class="dropSearch" autocomplete="off">
                            <button @click="compareStock()"
                                    class="btn btn-success m-l-10">库存重算
                            </button>
                        </span>
                    </div>
                </div>

            </div>
            <!--出入库信息-->
            <div v-show="showOutInPage" class="row" style="display: none;">
                <!--入库信息-->
                <div class="col-md-6">
                    <div class="white-box">
                        <h3 class="box-title">入库信息</h3>
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        max-height: 540px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>#</th>
                                    <th>药店</th>
                                    <th>药品名称</th>
                                    <th>药品编码</th>
                                    <th>批号</th>
                                    <th>进货单号</th>
                                    <th>数量</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems2">
                                    <td class="p-l-5">{{ index+1 }}</td>
                                    <td class="text-hide-100" :title="showItem.storeName">{{ showItem.storeName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugName">{{ showItem.drugName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugCode">{{ showItem.drugCode }}</td>
                                    <td class="text-hide-100" :title="showItem.batchNo">{{ showItem.batchNo }}</td>
                                    <td class="text-hide-100"><a href="javascript:void(0)"
                                                                 @click="deliverCodeList(showItem.deliverCode)">{{
                                        showItem.deliverCode }}</a></td>
                                    <td>{{ showItem.quantity }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNullInPage">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!--出库信息-->
                <div class="col-md-6">
                    <div class="white-box">
                        <h3 class="box-title">出库信息</h3>
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        max-height: 540px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>#</th>
                                    <th>药店</th>
                                    <th>药品名称</th>
                                    <th>药品编码</th>
                                    <th>批号</th>
                                    <th>出货单号</th>
                                    <th>类型</th>
                                    <th>付款方式</th>
                                    <th>数量</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems3">
                                    <td class="p-l-5">{{ index+1 }}</td>
                                    <td class="text-hide-100" :title="showItem.storeName">{{ showItem.storeName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugName">{{ showItem.drugName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugCode">{{ showItem.drugCode }}</td>
                                    <td class="text-hide-100" :title="showItem.batchNo">{{ showItem.batchNo }}</td>
                                    <td class="text-hide-120"><a href="javascript:void(0)"
                                                                 @click="outNoList(showItem.outNo)">{{ showItem.outNo
                                        }}</a></td>
                                    <td>
                                        <span v-if="showItem.type ==1">销售</span>
                                        <span v-else-if="showItem.type ==2">退货</span>
                                        <span v-else-if="showItem.type ==3">调拨出</span>
                                        <span v-else>{{ showItem.type }}</span>
                                    </td>
                                    <td>
                                        <span v-if="showItem.payType ==1">现金</span>
                                        <span v-else-if="showItem.payType ==2">社保卡</span>
                                        <span v-else>{{ showItem.payType }}</span>
                                    </td>
                                    <td class="text-hide-60" :title="showItem.quantity">{{ showItem.quantity }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin: 10px; display: none;" v-show="dataIsNullOutPage">
                                <span style="color: #f75b36;">未检索到数据</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--进货单详情窗口-->
            <div v-show="showDetailPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog" style="width: 80%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" v-show="showInNo">进货单详情</h4>
                            <h4 class="modal-title" v-show="showOutNo">出库单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="white-box">
                                <div class="table-responsive fixed-table" id="tableScroll3"
                                     style="border: 2px solid #98a6ad;max-height: 540px;overflow: auto;">
                                    <table class="table color-bordered-table muted-bordered-table"
                                           style="border: none;">
                                        <thead id="fixedHead-1">
                                        <tr class="font-size-big">
                                            <th>序号</th>
                                            <th>药店</th>
                                            <th>批号</th>
                                            <th>药品名称</th>
                                            <th>剂型</th>
                                            <th>数量</th>
                                            <th>规格</th>
                                            <th>单位</th>
                                            <th>实时库存</th>
                                            <th>平台编码</th>
                                            <th>生产厂家</th>
                                            <th>有效期</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr v-for="(detailItem,index) in detailItems">
                                            <td class="p-l-20">{{ index+1 }}</td>
                                            <td class="text-hide-80" :title="detailItem.storeName">{{
                                                detailItem.storeName }}
                                            </td>
                                            <td class="text-hide-120" :title="detailItem.batchNo">{{ detailItem.batchNo
                                                }}
                                            </td>
                                            <td class="text-hide-100" :title="detailItem.drugName">{{
                                                detailItem.drugName }}
                                            </td>
                                            <td class="text-hide-100" :title="detailItem.dosageForm">{{
                                                detailItem.dosageForm }}
                                            </td>
                                            <td class="text-hide-60" :title="detailItem.quantity">{{ detailItem.quantity
                                                }}
                                            </td>
                                            <td class="text-hide-80" :title="detailItem.spec">{{ detailItem.spec }}</td>
                                            <td class="text-hide-60" :title="detailItem.unit">{{ detailItem.unit }}</td>
                                            <td class="text-hide-60" :title="detailItem.storeStock">{{
                                                detailItem.storeStock }}
                                            </td>
                                            <td class="text-hide-100" :title="detailItem.platformCode">{{
                                                detailItem.platformCode }}
                                            </td>
                                            <td class="text-hide-80" :title="detailItem.mfr">{{ detailItem.mfr }}</td>
                                            <td class="text-hide-80" :title="detailItem.expire">{{ detailItem.expire
                                                }}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div style="text-align: center;margin: 10px;display: none;"
                                         v-show="dataIsNullDetail">
                                        <span style="color: #f75b36;">未检索到数据</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button @click="closeDetailPage()" type="button" class="btn btn-default waves-effect"
                                    data-dismiss="modal">
                                关闭
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
        <!--时间选择器-->
        <script src="${ctx.contextPath}/js/laydate.js"></script>
        <script>
            //时间选择器
            laydate.render({
                elem: '#yy_search_startTime',
                trigger: 'click'
            });
            laydate.render({
                elem: '#yy_search_endTime',
                trigger: 'click'
            });
            //预警过来的时候，反选
            addressParameter()

            function addressParameter() {
                //药店id
                var itemStoreId = GetQueryString("itemStoreId");
                if (itemStoreId) {
                    $("#searchItemId1").val(itemStoreId)
                } else {
                    $("#searchItemId1").val("")
                }
            }

            function GetQueryString(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return decodeURI(r[2]);
                return null;
            }
        </script>
        <script src="${ctx.contextPath}/js/operation/drug-stock.js"></script>
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
                $("#tableScroll-1").scroll(function () {
                    var scrollHeight = $('#tableScroll-1').scrollTop() + "px";
                    $('#fixedHead-1').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
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
                    clickHandler: function (data) {

                    },
                    ajaxHandler: function (res, list) {
                        $.each(res.data, function (i, item) {
                            list.push(item);
                        });
                    }
                });

                DropSearch.init({
                    selector: "#dropSearch2",
                    url: baseURL + '/common/findAllDrugStore',
                    inputParam: "keyword",
                    ajaxType: "get",
                    debounceTime: 500,
                    searchType: 2,
                    clickHandler: function (data) {

                    },
                    ajaxHandler: function (res, list) {
                        $.each(res.data, function (i, item) {
                            list.push(item);
                        });
                    }
                });

                //预警过来的时候，反选药店名称
                addressParameterStoreName();

                function addressParameterStoreName() {
                    //药店id
                    var itemStoreName = GetQueryString("itemStoreName");
                    if (itemStoreName) {
                        $("#dropSearch1").val(itemStoreName)
                    } else {
                        $("#dropSearch1").val("")
                    }
                }
            });
        </script>
    </#if>
</@layout.layout>
