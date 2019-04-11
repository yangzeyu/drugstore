<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "药店进货与发票核算"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="35">

        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">药店进货与发票核算列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">报表管理</a></li>
                        <li class="active">药店进货与发票核算</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">药店名称:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 160px;">
                        <input type="hidden" id="searchItemId1" class="dropSearch" style="display: none !important;">
                        <span class="m-r-5 m-l-10">发票号:</span>
                        <input v-model="searchData.searchInvoiceCode" type="text" class="form-control input-sm inline-block wid-120"
                               placeholder="请输入发票号" autocomplete="off">
                        <span class="m-r-5 m-l-10">发票类型:</span>
                        <input v-model="searchData.searchInvoiceType" type="text" class="form-control input-sm inline-block wid-120"
                               placeholder="请输入发票号" autocomplete="off">
                        <button @click="resetSearch()"
                                class="btn btn-primary m-l-10 pull-right font-size-big">重置
                        </button>
                        <button @click="searchItem()"
                                class="btn btn-info pull-right font-size-big">搜索
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
                                    <th>药店名称</th>
                                    <th>发票号</th>
                                    <th>发票金额</th>
                                    <th>入库单号</th>
                                    <th>入库金额</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-100" :title="item.storeName">{{ item.storeName }}</td>
                                    <td class="text-hide-100" :title="item.invoiceCode">{{ item.invoiceCode }}</td>
                                    <td class="text-hide-100" :title="item.invoicePrice">{{ item.invoicePrice }}</td>
                                    <td class="text-hide-100" :title="item.deliverCode">{{ item.deliverCode }}</td>
                                    <td class="text-hide-100" :title="item.deliverPrice">{{ item.deliverPrice }}</td>
                                    <td>
                                        <div v-if="item.isCorrect ==1" style="margin-left: 6px;color: #00c292;">
                                            正常
                                        </div>
                                        <div v-else-if="item.isCorrect == 0" style="margin-left: 6px;color: #f75b36">
                                            异常
                                        </div>
                                        <div v-else>
                                            {{ item.isCorrect }}
                                        </div>
                                    </td>
                                </tr>
                                <tr v-show="!dataIsNull" class="font-size-sm">
                                    <td>汇总</td>
                                    <td></td>
                                    <td></td>
                                    <td>{{ invoicePriceSum }}</td>
                                    <td></td>
                                    <td>{{ deliverPriceSum }}</td>
                                    <td></td>
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
        </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/invoiceDeliver.js"></script>
        <script>
            $(function () {
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
