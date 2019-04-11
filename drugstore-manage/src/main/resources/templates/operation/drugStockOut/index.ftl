<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "出库管理"; section>
    <#if section = "css">
        <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-stock-out.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="2">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">出库信息列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li class="active">出库管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">药品名称:</span>
                        <input v-model.trim="searchData.searchDrugName" v-on:keyup.enter="searchItemGo()" type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-100"
                               placeholder="药品名称">
                        <span class="m-r-5 m-l-10">出库单号:</span>
                        <input v-model.trim="searchData.searchOutNo" v-on:keyup.enter="searchItemGo()" type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-100"
                               placeholder="出库单号">
                        <span class="m-r-5 m-l-10">类型: </span>
                        <select v-model="searchData.searchType"
                                class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="1">销售出</option>
                            <option value="2">采购退货</option>
                            <option value="3">调拨出</option>
                            <option value="4">盘亏</option>
                        </select>
                        <span class="m-r-5 m-l-10">付款方式: </span>
                        <select v-model="searchData.searchPayType"
                                class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="1">现金</option>
                            <option value="2">医保卡</option>
                        </select>
                        <span class="m-r-5 m-l-10">时间: </span>
                        <div class="input-daterange input-group" id="date-range" style="width: 250px;position: absolute;top: 1px;left: 746px;">
                            <input type="text" id="yy_search_startTime" class="form-control" name="start" autocomplete="off"
                                   maxlength="30"  style="height: 2em;" placeholder="起始时间"/>
                            <span class="input-group-addon bg-info b-0 text-white">到</span>
                            <input type="text" id="yy_search_endTime" class="form-control" name="end" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="结束时间"/>
                        </div>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-10 font-size-big">重置
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
                                    <th>序号</th>
                                    <th>出库单号</th>
                                    <th>出库金额</th>
                                    <th>记账金额</th>
                                    <th>出库日期</th>
                                    <th>类型</th>
                                    <th>付款方式</th>
                                    <th>交易方名称</th>
                                    <th>药店</th>
                                    <th>
                                        <span class="pull-right">操&nbsp;&nbsp;作</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-180" :title="showItem.outNo">{{ showItem.outNo }}</td>
                                    <td class="text-hide-180" :title="showItem.price">{{ showItem.price }}</td>
                                    <td class="text-hide-180" :title="showItem.insurancePrice">{{ showItem.insurancePrice }}</td>
                                    <td class="text-hide-180" :title="showItem.outDate">{{ showItem.outDate }}</td>
                                    <td>
                                        <span v-if="showItem.type ==1">销售出</span>
                                        <span v-else-if="showItem.type ==2">采购退货</span>
                                        <span v-else-if="showItem.type ==3">调拨出</span>
                                        <span v-else-if="showItem.type ==4">盘亏</span>
                                        <span v-else>{{ showItem.type }}</span>
                                    </td>
                                    <td>
                                        <span v-if="showItem.payType ==1">现金</span>
                                        <span v-else-if="showItem.payType ==2">医保卡</span>
                                        <span v-else>{{ showItem.payType }}</span>
                                    </td>
                                    <td>
                                        <div v-if="showItem.idcard">{{ showItem.tradeName }}：{{ showItem.idcard }}</div>
                                        <div  v-else-if="!showItem.idcard">{{ showItem.tradeName }}</div>
                                    </td>
                                    <td class="text-hide-100" :title="showItem.storeName">{{ showItem.storeName }}</td>
                                    <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-10">
                                        <a class="yy_btn1" @click="editItemGo(showItem.id)"
                                           style="cursor:pointer; "
                                           title="详情">
                                            <i class="ti-zoom-in"></i>
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
            <!--查看详情窗口-->
            <div v-show="showDetailPage" class="modal-1 fade in" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancelGo()" type="button" class="close">×</button>
                            <h4 class="modal-title">出库单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="table-responsive fixed-table" id="tableScroll-1" style="border: 2px solid #98a6ad;
                        max-height: 400px;overflow: auto;">
                                <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                    <thead id="fixedHead-1">
                                    <tr class="font-size-big">
                                        <th>序号</th>
                                        <th>药品名称</th>
                                        <th>剂型</th>
                                        <th>数量</th>
                                        <th>规格</th>
                                        <th>单位</th>
                                        <th>药品单价</th>
                                        <th>平台编码</th>
                                        <th>生产厂家</th>
                                        <th>当前库存</th>
                                        <th>有效期</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="(detailItem,index) in detailItems" class="font-size-sm">
                                        <td class="p-l-20">{{ index+1 }}</td>
                                        <td class="text-hide-140" :title="detailItem.drugName">{{ detailItem.drugName }}</td>
                                        <td class="text-hide-80" :title="detailItem.dosageForm">{{ detailItem.dosageForm }}</td>
                                        <td class="text-hide-80" :title="detailItem.quantity">{{ detailItem.quantity }}</td>
                                        <td class="text-hide-80" :title="detailItem.spec">{{ detailItem.spec }}</td>
                                        <td class="text-hide-80" :title="detailItem.unit">{{ detailItem.unit }}</td>
                                        <td class="text-hide-80" :title="detailItem.unitPrice">{{ detailItem.unitPrice }}</td>
                                        <td class="text-hide-100" :title="detailItem.platformCode">{{ detailItem.platformCode }}</td>
                                        <td class="text-hide-100" :title="detailItem.mfr">{{ detailItem.mfr }}</td>
                                        <td class="text-hide-80" :title="detailItem.storeStock">{{ detailItem.storeStock }}</td>
                                        <td class="text-hide-100" :title="detailItem.expire">{{ detailItem.expire }}</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div v-show="detailDataIsNull" style="text-align: center;display: none;" class="m-t-10 m-b-10">
                                    <span style="color: #f75b36;;">未检索到数据</span>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancelGo()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                关闭
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--遮罩层-->
            <div v-show="coverLayer" id="divBG" class="divBG" style="display: none;"></div>
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
            var baseURL = '${ctx.contextPath}';
            var yyStoreId = ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId!'xxx'}
        </script>
        <script src="${ctx.contextPath}/js/operation/drug-stock-out.js"></script>
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
                $("#tableScroll-1").scroll(function() {
                    var scrollHeight =$('#tableScroll-1').scrollTop() + "px";
                    $('#fixedHead-1').css({
                        "position": "relative",
                        "transform":"translateY("+scrollHeight+")"
                    });
                });
            });
        </script>
    </#if>
</@layout.layout>
