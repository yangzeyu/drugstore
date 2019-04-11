<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "接口日志"; section>
    <#if section = "css">

    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="22">
        <div id="app-1" v-cloak>
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">接口日志列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">系统管理</a></li>
                        <li class="active">接口日志</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box" style="padding: 15px 18px 16px 18px;">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">操作人: </span>
                        <select id="yy_search_operator" class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option v-for="operator in operators" :value="operator.appCode">{{ operator.name }}</option>
                        </select>
                        <span class="m-r-5 m-l-20">调用地址: </span>
                        <select id="yy_search_url"  class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="/api/registerDrug">药品首营登记接口</option>
                            <option value="/api/queryDrug">药品查询接口</option>
                            <option value="/api/uploadInvoice">药品进货发票信息上传接口</option>
                            <option value="/api/uploadDeliver">药品进货同行单上传接口</option>
                            <option value="/api/relationInvoiceDeliver">发票和随货同行单关联接口</option>
                            <option value="/api/uploadStoreOut">药品出库上传接口</option>
                            <option value="/api/uploadFile">发票和随货同行单图片上传接口</option>
                        </select>
                        <span class="m-r-5 m-l-20">状态: </span>
                        <select id="yy_search_status"  class="form-control input-sm inline-block wid-100">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                        <span class="m-r-5 m-l-20">时间: </span>
                        <div class="input-daterange input-group" id="date-range" style="width: 250px;position: absolute;top: 1px;left: 580px;">
                            <input type="text" id="yy_search_startTime" class="form-control" name="start" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="起始时间"/>
                            <span class="input-group-addon bg-info b-0 text-white">到</span>
                            <input type="text" id="yy_search_endTime" class="form-control" name="end" autocomplete="off"
                                   maxlength="30" style="height: 2em;" placeholder="结束时间"/>
                        </div>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-20 font-size-big">清空
                        </button>
                        <button @click="searchItemsGo()" class="btn btn-info pull-right" style="font-size: 1.1em;">搜索
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
                                <tr style="font-size: 1.11em;">
                                    <th>序号</th>
                                    <th>药店名称</th>
                                    <th>操作时间</th>
                                    <th>地址</th>
                                    <th>返回内容</th>
                                    <th>参数</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in items" style="font-size: 0.9em;">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td style="width: 10%">{{ item.storeName }}</td>
                                    <td style="width: 15%">{{ item.operateAt }}</td>
                                    <td style="width: 15%">{{ item.url }}</td>
                                    <td @click="detailItems(item.respContent)" v-bind:title="item.respContent" style="max-width:200px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;cursor:pointer">{{ item.respContent }}</td>
                                    <td @click="detailItems(item.reqParam)" v-bind:title="item.reqParam" style="max-width:200px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;cursor:pointer">{{ item.reqParam }}</td>
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
        </div>
    </#if>
    <#if section = "js">
        <!--时间选择器-->
        <script src="${ctx.contextPath}/js/laydate.js"></script>
        <script src="${ctx.contextPath}/js/operation/operate-log.js"></script>
        <script>
            $(function () {
                //时间选择器
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
            });
        </script>
    </#if>
</@layout.layout>
