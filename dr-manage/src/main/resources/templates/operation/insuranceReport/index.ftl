<#import "../../layout/layout.ftl" as layout>
<@layout.layout title = "医保进销存统计分析"; section>
    <#if section = "css">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="34">

        <div id="app-1" v-cloak>
            <!--顶部标题-->
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">医保进销存统计分析列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">报表管理</a></li>
                        <li class="active">医保进销存统计分析</li>
                    </ol>
                </div>
            </div>
            <!--搜索区-->
            <div class="white-box">
                <div class="row">
                    <div class="col-md-12" style="position: relative;">
                        <span class="m-r-5">医保目录名称:</span>
                        <input v-model.trim="searchData.searchThreeDirectoryName" @keyup.enter="searchItem()" type="text"
                               autocomplete="off"
                               maxlength="30" class="form-control input-sm inline-block wid-120"
                               placeholder="医保目录名称">
                        <span class="m-r-5 m-l-10">医保编码:</span>
                        <input v-model.trim="searchData.searchMedicalInsuranceCode" @keyup.enter="searchItem()" type="text"
                               autocomplete="off"
                               maxlength="30" class="form-control input-sm inline-block wid-120"
                               placeholder="医保编码">
                        <span class="m-r-5 m-l-10">收费项目等级:</span>
                        <select v-model.trim="searchData.searchCollectLvl"
                                class="form-control input-sm inline-block wid-80">
                            <option value="">全部</option>
                            <option v-for="item in allGrades" :value="item.collectLvl">{{ item.collectLvl }}</option>
                        </select>

                        <button @click="downLoadExcel" class="btn btn-success font-size-big pull-right m-l-10">导出
                        </button>
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
                                    <th>医保编码</th>
                                    <th>医保目录名称</th>
                                    <th>药店</th>
                                    <th>药品编码</th>
                                    <th>通用名</th>
                                    <th>剂型</th>
                                    <th>厂家</th>
                                    <th>规格</th>
                                    <th>出库数量</th>
                                    <th>收费项目等级</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in showItems" class="font-size-sm">
                                    <td class="p-l-20">{{ index+1 }}</td>
                                    <td class="text-hide-100" :title="item.medicalInsuranceCode">{{ item.medicalInsuranceCode }}</td>
                                    <td class="text-hide-100" :title="item.threeDirectoryName">{{ item.threeDirectoryName }}</td>
                                    <td class="text-hide-80" :title="item.storeName">{{ item.storeName }}</td>
                                    <td class="text-hide-100" :title="item.platformCode">{{ item.platformCode }}</td>
                                    <td class="text-hide-100" :title="item.goodName">{{ item.goodName }}</td>
                                    <td class="text-hide-60" :title="item.dosageForm">{{ item.dosageForm }}</td>
                                    <td class="text-hide-80" :title="item.manufactureName">{{ item.manufactureName }}</td>
                                    <td class="text-hide-60" :title="item.spec">{{ item.spec }}</td>
                                    <td class="text-hide-60" :title="item.storeOutNumber">{{ item.storeOutNumber }}</td>
                                    <td class="text-hide-60">{{ item.collectLvl }}</td>

                                </tr>
                                <tr v-show="!dataIsNull" class="font-size-sm">
                                    <td>汇总</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>{{ storeOutNumberSum }}</td>
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
        <script src="${ctx.contextPath}/js/operation/insuranceReport.js"></script>
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
