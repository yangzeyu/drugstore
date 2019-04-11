<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "药店进销存统计分析"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="32">

        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">药店进销存统计分析列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">报表管理</a></li>
                        <li class="active">药店进销存统计分析</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">药店:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 120px;">
                        <input type="hidden" id="searchItemId1" class="dropSearch" style="display: none !important;">
                        <span class="m-r-5 m-l-10">药品名称:</span>
                        <input v-model.trim="searchData.searchGoodName" v-on:keyup.enter="searchItem()" type="text"
                               maxlength="30" class="form-control input-sm inline-block wid-100"
                               placeholder="药品名称">
                        <span class="m-r-5 m-l-10">时间: </span>
                        <div class="input-daterange input-group" id="date-range"
                             style="width: 250px;position: absolute;top: 1px;left: 420px;">
                            <input type="text" id="yy_search_startTime" class="form-control" name="start"
                                   autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="起始时间"/>
                            <span class="input-group-addon bg-info b-0 text-white">到</span>
                            <input type="text" id="yy_search_endTime" class="form-control" name="end" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="结束时间"/>
                        </div>
                        <button @click="downLoadExcel" class="btn btn-success font-size-big pull-right m-l-10">导出
                        </button>
                        <button @click="resetSearch()"
                                class="btn btn-primary pull-right m-l-10 font-size-big">清空
                        </button>
                        <button @click="searchItem()"
                                class="btn btn-info pull-right m-l-10 font-size-big">搜索
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
                                    <th>通用名</th>
                                    <th>首营登记</th>
                                    <th>规格</th>
                                    <th>剂型</th>
                                    <th>厂家</th>
                                    <th>入库数量</th>
                                    <th>出库数量</th>
                                    <th>校验库存</th>
                                    <th>当前库存</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-100" :title="item.storeName">{{ item.storeName }}</td>
                                    <td class="text-hide-100" :title="item.goodName">{{ item.goodName }}</td>
                                    <td class="text-hide-60" :title="item.initStock">{{ item.initStock }}</td>
                                    <td class="text-hide-100" :title="item.spec">{{ item.spec }}</td>
                                    <td class="text-hide-100" :title="item.dosageForm">{{ item.dosageForm }}</td>
                                    <td class="text-hide-100" :title="item.manufacture">{{ item.manufacture }}</td>
                                    <td class="text-hide-60" :title="item.inQuantity">{{ item.inQuantity }}</td>
                                    <td class="text-hide-60" :title="item.outQuantity">{{ item.outQuantity }}</td>
                                    <td class="text-hide-60" :title="item.stock">{{ item.stock }}</td>
                                    <td class="text-hide-60" :title="item.storeStock">{{ item.storeStock }}</td>
                                </tr>
                                <tr v-show="!dataIsNull" class="font-size-sm">
                                    <td>汇总</td>
                                    <td></td>
                                    <td></td>
                                    <td>{{ initStockSum }}</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>{{ inQuantitySum }}</td>
                                    <td>{{ outQuantitySum }}</td>
                                    <td>{{ stockSum }}</td>
                                    <td>{{ storeStockSum }}</td>
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
        <script src="${ctx.contextPath}/js/laydate.js"></script>
        <script src="${ctx.contextPath}/js/operation/storeOutIn.js"></script>
        <script>
            $(function () {
                //时间选择
                laydate.render({
                    elem: '#yy_search_startTime',
                    trigger: 'click'
                });
                laydate.render({
                    elem: '#yy_search_endTime',
                    trigger: 'click'
                });
                //表头固定--数据展示区
                $("#tableScroll").scroll(function () {
                    var scrollHeight = $('#tableScroll').scrollTop() + "px";
                    $('#fixedHead').css({
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
