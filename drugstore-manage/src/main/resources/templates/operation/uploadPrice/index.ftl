<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "上传金额统计表"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="5">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">上传金额统计表</h4>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="selectedTime">
                            <span class="m-r-5 m-l-10">时间: </span>
                            <input id="yy_search_startTime" type="text" name="start" class="form-control input-sm
                                inline-block wid-130" autocomplete="off" placeholder="起始时间"><span class="selectedTimeTo">到</span><input
                                id="yy_search_endTime" type="text" name="end" class="form-control input-sm
                                inline-block wid-130" autocomplete="off" placeholder="结束时间">
                        </span>
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
                                    <th>入库金额</th>
                                    <th>销售金额</th>
                                    <th>医保金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td>{{ showItem.deliveryPrice }}</td>
                                    <td>{{ showItem.stockOutPrice }}</td>
                                    <td>{{ showItem.insurancePrice }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="showItems.length === 0" style="text-align: center;display: none;" class="m-t-10 m-b-10">
                                <span style="color: #f75b36;;">未检索到数据</span>
                            </div>
                        </div>
                    </div>
                </div>
                <#--<!--分页&ndash;&gt;
                <div class="row m-t-20">
                    <div class="col-md-12">
                        <div id="pagination" class="pagination"></div>
                    </div>
                </div>-->
            </div>
        </div>
    </#if>
    <#if section = "js">
        <script>
            var baseURL = '${ctx.contextPath}';
            var yyStoreId = ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId!'xxx'}
        </script>
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
        </script>
        <script src="${ctx.contextPath}/js/operation/uploadPrice.js"></script>
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
