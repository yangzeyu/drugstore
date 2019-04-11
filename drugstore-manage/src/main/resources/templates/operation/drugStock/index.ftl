<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "库存管理"; section>
    <#if section = "css">
    <link rel="stylesheet" href="${ctx.contextPath}/css/operation/drug-list.css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="1">
        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">库存列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
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
                        <span class="m-r-5">关键字:</span>
                        <input v-model.trim="searchItems.searchKeyword" @keyup.enter="searchItem()" type="text"
                               maxlength="30" class="form-control input-sm inline-block"
                               placeholder="请输入药品名称/商品名称/药品编码" style="width: 240px;">
                        <button @click="downLoadExcel()" class="btn btn-success font-size-big pull-right m-l-10">导出
                        </button>
                        <button @click="resetSearchGo()"
                                class="btn btn-primary pull-right m-l-10 font-size-big">重置
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
                        <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        max-height: 540px;overflow: auto;">
                            <table class="table color-bordered-table muted-bordered-table" style="border: none;">
                                <thead id="fixedHead">
                                <tr class="font-size-big">
                                    <th>序号</th>
                                    <th>通用名</th>
                                    <th>药品编码</th>
                                    <th>剂型</th>
                                    <th>规格</th>
                                    <th>厂家</th>
                                    <th>单位</th>
                                    <th>首营库存</th>
                                    <th>校验库存</th>
                                    <th>药店库存</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(showItem,index) in showItems">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-140" :title="showItem.drugName">{{ showItem.drugName }}</td>
                                    <td class="text-hide-100" :title="showItem.drugCode">{{ showItem.drugCode }}</td>
                                    <td class="text-hide-100" :title="showItem.dosageForm">{{ showItem.dosageForm }}</td>
                                    <td class="text-hide-100" :title="showItem.spec">{{ showItem.spec }}</td>
                                    <td class="text-hide-140" :title="showItem.manufacture">{{ showItem.manufacture }}</td>
                                    <td class="text-hide-100" :title="showItem.unit">{{ showItem.unit }}</td>
                                    <td class="text-hide-100" :title="showItem.initStock">{{ showItem.initStock }}</td>
                                    <td class="text-hide-100" :title="showItem.stock">
                                        <span v-if="showItem.stock ===null">0</span>
                                        <span v-else-if="showItem.stock ===''">0</span>
                                        <span v-else>{{ showItem.stock }}</span>
                                    </td>
                                    <td class="text-hide-80" :title="showItem.storeStock">{{ showItem.storeStock }}</td>
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
            <!--遮罩层-->
            <div v-show="showCoverPage" class="divBG" style="display: none;"></div>
        </div>
    </#if>
    <#if section = "js">
        <script>
            var baseURL = '${ctx.contextPath}';
            var yyStoreId = ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.storeId!'xxx'}
        </script>
        <script src="${ctx.contextPath}/js/operation/drug-stock.js"></script>
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
            });
        </script>
    </#if>
</@layout.layout>
