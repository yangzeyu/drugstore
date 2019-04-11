var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData: {
            searchGoodName: '',
            searchPlatFormCode: '',
            searchDrugStoreId: ''
        },
        showItems: [],
        initStockSum: 0,  //首营登记库存总数
        inQuantitySum: 0, //进货总数
        outQuantitySum: 0, //出货总数
        storeStockSum: 0, //效验库存总数
        stockSum: 0,  //当前库存总数
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
            this.initStockSum = 0;
            this.inQuantitySum = 0;
            this.outQuantitySum = 0;
            this.stockSum = 0;
            this.storeStockSum = 0;

            var storeId = $("#searchItemId1").val();
            var startTime = $("#yy_search_startTime").val();
            var endTime = $("#yy_search_endTime").val();
            var searchGoodName = this.searchData.searchGoodName;
            $("#loadingPage").show();
            $.ajax({
                url: baseURL + '/report/storeOutIn/findStoreOutIn',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId,
                    'goodName': searchGoodName,
                    'startTime': startTime,
                    'endTIme': endTime,
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
                        if(Number(dataSum[i].initStock)){
                            app1.initStockSum += Number(dataSum[i].initStock)
                        }
                        if(Number(dataSum[i].inQuantity)){
                            app1.inQuantitySum += Number(dataSum[i].inQuantity)
                        }
                        if(Number(dataSum[i].outQuantity)){
                            app1.outQuantitySum += Number(dataSum[i].outQuantity)
                        }
                        if(Number(dataSum[i].stock)){
                            app1.stockSum += Number(dataSum[i].stock)
                        }
                        if(Number(dataSum[i].storeStock)){
                            app1.storeStockSum += Number(dataSum[i].storeStock)
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
        //下载
        downLoadExcel: function () {
            var storeId = $("#searchItemId1").val();
            var startTime = $("#yy_search_startTime").val();
            var endTime = $("#yy_search_endTime").val();
            var goodName = this.searchData.searchGoodName;

            window.location.href = baseURL + "/report/storeOutIn/export?storeId="+ storeId +"&goodName="+ goodName +"&startTime="+ startTime +"&endTime=" + endTime
        },
        //点击搜索按钮
        searchItem: function () {
            this.init(1, 1)
        },
        //点击重置按钮
        resetSearch: function () {
            $("#dropSearch1").val("");
            $("#searchItemId1").val("");
            $("#yy_search_startTime").val("");
            $("#yy_search_endTime").val("");
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
