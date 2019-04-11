<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "药店进货与发票核算"; section>
    <#if section = "css">
    <link href="${ctx.contextPath}/plugins/bower_components/Magnific-Popup-master/dist/magnific-popup.css" rel="stylesheet">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="36">
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
                        <span class="m-r-5">所属药店:
                            <a href="javascript:void(0) " class="tooltip-danger" data-toggle="tooltip" data-placement="top"
                               title="" data-original-title="输入关键字然后选择" style="color: #f75b36; font-size: 14px;">
                                <i class="ti-help-alt"></i>
                            </a>
                        </span>
                        <input type="text" id="dropSearch1"  class="form-control input-sm inline-block dropSearch"
                               placeholder="请输入关键字" autocomplete="off" style="width: 200px;">
                        <input type="hidden" id="searchItemId1" class="dropSearch" style="display: none !important;">
                        <span class="m-r-5">发票号:</span>
                        <input v-model.trim="searchData.searchInvoiceCode" @keyup.enter="searchItem()" type="text" class="form-control input-sm inline-block wid-160"
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
                <div class="row" v-if="!showInvoicesPage">
                    <div class="col-md-12">
                        <div class="table-responsive fixed-table tableScroll" id="tableScroll">
                            <table class="table color-bordered-table muted-bordered-table">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>序号</th>
                                    <th>药店名称</th>
                                    <th>发票号</th>
                                    <th>API接口信息</th>
                                    <th>是否上传图片</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-200" :title="item.storeName">{{ item.storeName }}</td>
                                    <td class="text-hide-100" :title="item.invoiceCode">
                                        <a @click="lookAPI(item.invoiceCode)" href="javascript:void(0)">{{ item.invoiceCode }}</a>
                                    </td>
                                    <td class="text-hide-100">
                                            <a @click="lookAPI(item.invoiceCode)" class="m-l-10" href="javascript:void(0)">查看</a>
                                    </td>
                                    <td class="text-hide-100">
                                        <div v-if="item.isUploadImage ==1" class="m-l-10">
                                            <a @click="lookInvoiceImg(item.deliveryId)" class="m-l-10" href="javascript:void(0)">查看</a>
                                        </div>
                                        <div v-else-if="item.isUploadImage ==0" class="m-l-10">
                                            否
                                            <a @click="lookInvoiceImg(item.deliveryId)" class="m-l-10" href="javascript:void(0)">查看</a>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="dataIsNull" class="t-a-center m-t-10 m-b-10">
                                <span class="c-warning">未检索到数据</span>
                            </div>
                            <div v-show="showSelectedDrugStorm" class="t-a-center m-t-10 m-b-10">
                                <span class="c-warning">请先选择药店</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" v-if="showInvoicesPage">
                    <#--核查窗口-->
                    <div class="col-md-6">
                        <div class="table-responsive fixed-table tableScroll" id="tableScroll2">
                            <table class="table color-bordered-table muted-bordered-table">
                                <thead id="fixedHead2">
                                <tr class="font-size-big">
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>剂型</th>
                                    <th>规格</th>
                                    <th>数量</th>
                                    <th>厂商</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems2" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-200" :title="item.goodName">{{ item.goodName }}</td>
                                    <td class="text-hide-100" :title="item.dosageForm">{{ item.dosageForm }}</td>
                                    <td class="text-hide-100" :title="item.spec">{{ item.spec }}</td>
                                    <td class="text-hide-100" :title="item.quantity">{{ item.quantity }}</td>
                                    <td class="text-hide-200" :title="item.manufacture">{{ item.manufacture }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="showItems.length ==0" class="t-a-center m-t-10 m-b-10">
                                <span class="c-warning">未检索到数据</span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="table-responsive fixed-table tableScroll" id="tableScroll3">
                            <table class="table color-bordered-table muted-bordered-table">
                                <thead id="fixedHead3">
                                <tr class="font-size-big">
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>规格</th>
                                    <th>数量</th>
                                    <th>单位</th>
                                    <th>单价</th>
                                    <th>总价</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showInvoices" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-200" :title="item.name">{{ item.name }}</td>
                                    <td class="text-hide-100" :title="item.spec">{{ item.spec }}</td>
                                    <td class="text-hide-100" :title="item.quantity">{{ item.quantity }}</td>
                                    <td class="text-hide-100" :title="item.unit">{{ item.unit }}</td>
                                    <td class="text-hide-100" :title="item.priceUnit">{{ item.priceUnit }}</td>
                                    <td class="text-hide-100" :title="item.priceSum">{{ item.priceSum }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="showInvoices.length ==0" class="t-a-center m-t-10 m-b-10">
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

            <!--查看图片窗口-->
            <div class="modal-page-800">
                <div>
                    <div class="modal-content">
                        <div class="modal-header" style="padding-top: 2px; padding-bottom: 2px;">
                            <h3 class="box-title m-b-0">发票图片
                                <button type="button" class="close">×</button>
                            </h3>
                        </div>
                        <div class="modal-body">
                                <div class="popup-gallery" style="text-align: center">
                                    <a href="${ctx.contextPath}/plugins/images/big/img5.jpg" title="第一张">
                                        <img src="${ctx.contextPath}/plugins/images/big/img5.jpg" width="30%" />
                                    </a>
                                    <a href="${ctx.contextPath}/plugins/images/big/img6.jpg">
                                        <img src="${ctx.contextPath}/plugins/images/big/img6.jpg" width="30%" />
                                    </a>
                                    <a href="${ctx.contextPath}/plugins/images/big/img4.jpg">
                                        <img src="${ctx.contextPath}/plugins/images/big/img4.jpg" width="30%" />
                                    </a>
                                    <a href="${ctx.contextPath}/plugins/images/big/img4.jpg">
                                        <img src="${ctx.contextPath}/plugins/images/big/img4.jpg" width="30%" />
                                    </a>
                                    <a href="${ctx.contextPath}/plugins/images/big/img4.jpg">
                                        <img src="${ctx.contextPath}/plugins/images/big/img4.jpg" width="30%" />
                                    </a>
                                </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--遮罩层-->
            <div class="coverPage"></div>

        </div>
    </#if>
    <#if section = "js">
        <!-- Magnific popup JavaScript -->
        <script src="${ctx.contextPath}/plugins/bower_components/Magnific-Popup-master/dist/jquery.magnific-popup.min.js"></script>
        <script src="${ctx.contextPath}/plugins/bower_components/Magnific-Popup-master/dist/jquery.magnific-popup-init.js"></script>
        <script src="${ctx.contextPath}/js/operation/invoiceCompare.js"></script>
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
                })
                $("#tableScroll2").scroll(function () {
                    var scrollHeight = $('#tableScroll2').scrollTop() + "px";
                    $('#fixedHead2').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
                    });
                })
                $("#tableScroll3").scroll(function () {
                    var scrollHeight = $('#tableScroll3').scrollTop() + "px";
                    $('#fixedHead3').css({
                        "position": "relative",
                        "transform": "translateY(" + scrollHeight + ")"
                    });
                });
            });
        </script>
    </#if>
</@layout.layout>
