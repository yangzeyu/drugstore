<#import "layout/layout.ftl" as layout>
<@layout.layout title = "首页"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
    <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
            <h4 class="page-title">报警信息总览</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="#">零售药店</a></li>
                <li class="active">首页</li>
            </ol>
        </div>
    </div>
    <div id="app-1">
        <div class="row">
            <div class="col-sm-6">
                <div class="white-box" style="padding-top: 4px;">
                    <h3 class="box-title">报警信息</h3>
                    <div class="table-responsive fixed-table" id="tableScroll" style="border: 2px solid #98a6ad;
                        height: 250px;overflow: auto;">
                        <table class="table color-bordered-table muted-bordered-table font-size-sm" style="border: none;">
                            <thead id="fixedHead">
                            <tr style="font-size: 14px">
                                <th>#</th>
                                <th>名称</th>
                                <th>药品编码</th>
                                <th>药店名称</th>
                                <th>类型</th>
                                <th>更新时间</th>
                                <th><span class="pull-right">详情</span></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(item,index) in items" class="font-size-sm">
                                <td>{{ index+1 }}</td>
                                <td class="text-hide-100" :title="item.drugName">{{ item.drugName }}</td>
                                <td class="text-hide-100" :title="item.platformCode">{{ item.platformCode }}</td>
                                <td class="text-hide-100" :title="item.storeName">{{ item.storeName }}</td>
                                <td>
                                    <div v-if="item.type ==1">药品库存异常</div>
                                    <div v-else-if="item.type ==2">药品批次异常</div>
                                    <div v-else-if="item.type ==3">实时库存异常</div>
                                    <div v-else>{{ item.type }}</div>
                                </td>
                                <td class="text-hide-100" :title="item.updateAt">{{ item.updateAt }}</td>
                                <td class="text-nowrap" style="font-size: 1.25em;">
                                        <span class="pull-right m-r-5">
                                        <a class="yy_btn1" @click="editItemGo(item.catalogId,item.storeId,item.storeName)"
                                           style="cursor:pointer; "
                                           title="详情">
                                            <i class="ti-zoom-in"></i>
                                        </a>
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div v-show="dataIsNull" style="text-align: center;display: none;" class="m-t-20">
                            <span style="color: #f75b36;;">未检索到数据</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="white-box" style="padding-top: 4px;">
                    <h3 class="box-title">对码信息</h3>
                    <div class="table-responsive fixed-table" id="tableScroll2" style="border: 2px solid #98a6ad;
                        height: 250px;overflow: auto;">
                        <table class="table color-bordered-table muted-bordered-table font-size-sm" style="border: none;">
                            <thead id="fixedHead2">
                            <tr style="font-size: 14px">
                                <th>#</th>
                                <th>药店名称</th>
                                <th>未对码数量</th>
                                <th>紧急待处理数量</th>
                                <#--<th><span class="pull-right">详情</span></th>-->
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(item,index) in items2" class="font-size-sm">
                                <td>{{ index+1 }}</td>
                                <td class="text-hide-140" :title="item.storeName">{{ item.storeName }}</td>
                                <td class="text-hide-80" :title="item.drugNumber">{{ item.drugNumber }}</td>
                                <td class="text-hide-80" :title="item.emergentNumber" style="color: #f75b36">{{ item.emergentNumber }}</td>
                                <#--<td class="text-nowrap" style="font-size: 1.25em;">-->
                                        <#--<span class="pull-right">-->
                                        <#--<a class="yy_btn1 m-r-5" @click="editItemGo2(item.storeId,item.storeName,item.storeName)"-->
                                           <#--style="cursor:pointer; "-->
                                           <#--title="详情">-->
                                            <#--<i class="ti-zoom-in"></i>-->
                                        <#--</a>-->
                                    <#--</span>-->
                                <#--</td>-->
                            </tr>
                            </tbody>
                        </table>
                        <div v-show="dataIsNull2" style="text-align: center;display: none;" class="m-t-20">
                            <span style="color: #f75b36;;">未检索到数据</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </#if>
    <#if section = "js">
        <script src="${ctx.contextPath}/js/operation/waring-massage.js"></script>
    </#if>
</@layout.layout>
