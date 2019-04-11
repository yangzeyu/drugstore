<#import "../../layout/layout.ftl" as layout>

<@layout.layout title = "发票管理"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="17">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">发票列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">零售药店</a></li>
                        <li class="active">两票查询</li>
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
                               placeholder="请输入关键字" autocomplete="off" style="width: 160px;">
                        <input type="text" id="searchItemId1" class="dropSearch" style="display: none !important;">
                        <span class="m-r-5 m-l-10">关键字:</span>
                        <input v-model.trim="searchItems.searchKeyword" @keyup.enter="searchHospitalGo()" type="text"
                               autocomplete="off"
                               maxlength="30" class="form-control input-sm inline-block"
                               placeholder="发票/同行单编码" style="width: 140px;">
                        <span class="m-r-5 m-l-10">票据时间: </span>
                        <div class="input-daterange input-group" id="date-range"
                             style="width: 250px;position: absolute;top: 1px;left: 544px;">
                            <input type="text" id="yy_search_startTime" class="form-control" name="start"
                                   autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="起始时间"/>
                            <span class="input-group-addon bg-info b-0 text-white">到</span>
                            <input type="text" id="yy_search_endTime" class="form-control" name="end" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="结束时间"/>
                        </div>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-20 font-size-big">清空
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
                                    <th>药店名称</th>
                                    <th>同行单编码</th>
                                    <th>同行单金额</th>
                                    <th>同行单时间</th>
                                    <th>发票编码</th>
                                    <th>发票号码</th>
                                    <th>发票金额</th>
                                    <th>开票日期</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-10">{{ index+1 }}</td>
                                    <td>{{ showItem.storeName }}</td>
                                    <td>
                                        <a href="javascript:void(0)" id="invoiceImg" @click="findInvoiceImg(showItem.deliveryId)"> {{ showItem.deliverCode }}</a>
                                    </td>
                                    <td>{{ showItem.deliverPrice }}</td>
                                    <td>{{ showItem.deliverDate }}</td>
                                    <td>
                                        <a href="javascript:void(0)" id="invoiceImg" @click="findInvoiceImg(showItem.deliveryId)">{{ showItem.invoiceCode }}</a>
                                    </td>
                                    <td>{{ showItem.invoiceNumber }}</td>
                                    <td>{{ showItem.invoicePrice }}</td>
                                    <td>{{ showItem.invoiceDate }}</td>
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
            <!--图片查看-->
            <div v-show="showImagePage" class="show-image-page">
                <ul>
                    <li style="text-align: center">
                        <div style="position: relative">
                            <img v-for="item in invoiceImg.deliverList" :src="item" alt="" style="height: 400px;border: 1px solid grey">
                            <img @click="imagePageClose()"
                                 src="${ctx.contextPath}/plugins/images/close-circle-1.png"
                                 alt="点击关闭发票图片"  class="image-page" title="关闭图片">
                            <img v-for="item in invoiceImg.invoiceList" :src="item" alt="" style="height: 400px;border: 1px solid grey">

                        </div>
                    </li>
                </ul>
            </div>
            <!--查看图片窗口-->
            <div v-show="showImagePage" class="modal-page-800">
                <div>
                    <div class="modal-content">
                        <div class="modal-header" style="padding-top: 2px; padding-bottom: 2px;">
                            <h3 class="box-title m-b-0">发票图片
                                <button @click="imagePageClose()" type="button" class="close">×</button>
                            </h3>
                        </div>
                        <div class="modal-body">
                            <div class="popup-gallery" style="text-align: center">
                                <a v-for="item in invoiceImg.deliverList" :href="item" title="第一张">
                                    <img :src="item" width="30%" />
                                </a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--遮罩层-->
            <div v-bind:style="activeDisplay" id="divBG" class="divBG" style="display: none;"></div>
            <div v-show="showImagePage" class="coverPage2" style="display: none;"></div>
        </div>
    </#if>
    <#if section = "js">
        <!--时间选择器-->
        <script src="${ctx.contextPath}/js/laydate.js"></script>
        <script src="${ctx.contextPath}/js/operation/invoice.js"></script>
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
