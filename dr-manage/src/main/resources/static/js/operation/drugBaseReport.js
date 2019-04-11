var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData: {
            searchGoodName: '',
            searchPlatFormCode: '',
            searchDrugStoreId: ''
        },
        showItems: [],
        storeInPriceSum: 0, //入库金额汇总
        storeOutNumberSum: 0, //出库数量汇总
        storeInNumberSum: 0, //入库数量汇总
        pageNo: '',
        total: '',
        dataIsNull: false,
        showCoverPage: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.init(1, 1);
    },
    methods: {
        //更新页面
        init: function (pageNo, showPage) {
            this.storeInPriceSum = 0;
            this.storeOutNumberSum = 0;
            this.storeInNumberSum = 0;

            var goodName = this.searchData.searchGoodName;
            var platFormCode = this.searchData.searchPlatFormCode;
            var manufactureId = $("#searchItemId1").val();
            var startTime = $("#yy_search_startTime").val();
            var endTime = $("#yy_search_endTime").val();
            $("#loadingPage").show();
            $.ajax({
                url: baseURL + '/report/drugBase/findDrugBaseReport',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'goodName': goodName,
                    'manufactureId': manufactureId,
                    'platFormCode': platFormCode,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if (res.code == 1) {
                    if (res.data.list.length === 0) {
                        app1.dataIsNull = true
                    } else {
                        app1.dataIsNull = false
                    }
                    app1.showItems = res.data.list;

                    //汇总
                    var dataSum = res.data.list;
                    for(var i=0; i<dataSum.length; i++){
                        if(Number(dataSum[i].storeInPrice)){
                            app1.storeInPriceSum += Number(dataSum[i].storeInPrice)
                        }
                        if(Number(dataSum[i].storeOutNumber)){
                            app1.storeOutNumberSum += Number(dataSum[i].storeOutNumber)
                        }
                        if(Number(dataSum[i].storeInNumber)){
                            app1.storeInNumberSum += Number(dataSum[i].storeInNumber)
                        }
                    }
                    app1.pageNo = res.data.pageNo;
                    app1.total = res.data.total;
                } else {
                    swal(res.msg)
                }
                if (showPage === 1) {
                    app1.yy_pageing(showPage, res.data.total, res.data.count, res.data.pageSize)
                }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败!')
            })
        },
        //点击搜索按钮
        searchItem: function () {
            this.init(1, 1)
        },
        //点击重置按钮
        resetSearch: function () {
            this.searchData.searchGoodName = '';
            this.searchData.searchPlatFormCode = '';
            $("#dropSearch1").val("")
            $("#searchItemId1").val("")
            $("#yy_search_startTime").val("")
            $("#yy_search_endTime").val("")
        },
        //分页
        yy_pageing: function (initPageNo, total, setTotalCount, pageSize) {
            new Page({
                id: 'pagination',
                curPage: initPageNo, //当前页数,初始页码
                pageTotal: total, //必填,总页数
                pageSize: 5, //可选,分页个数
                dataTotal: setTotalCount, //总共多少条数据
                pageAmount: pageSize,  //每页多少条
                showPageTotalFlag: true, //是否显示数据统计
                showSkipInputFlag: true, //是否支持跳转
                getPage: function (page) {
                    //获取当前页数
                    app1.init(page);
                }
            })
        }
    }
});
