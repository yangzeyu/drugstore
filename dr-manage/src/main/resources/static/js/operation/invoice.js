var app1 = new Vue({
    el: '#app-1',
    data: {
        searchItems:{
            searchKeyword: ''
        },
        showItems: [],
        allStores: [],
        invoiceImg: {
            deliverList: [],
            invoiceList: []
        },
        page: {
            pageNo: '',
            total: ''
        },
        activeDisplay: {
            display: 'none'
        },
        dataIsNull: false,
        showImagePage: false
    },
    created: function () {
        //初始化显示页面
        this.searchHospital(1);
    },
    methods: {
        //搜索按钮
        searchHospitalGo: function () {
            this.searchHospital(1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchItems.searchKeyword = '';
            $("#searchItemId1").val("");
            $("#dropSearch1").val("");
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //发票查询
        findInvoiceImg: function (itemId) {
            $.ajax({
                url: baseURL + '/DeliverInvoice/findInvoiceImg',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'deliveryId': 107499
                }
            }).done(function (res) {
                if(res.data.deliverList.length !=0 || res.data.invoiceList.length !=0){
                    app1.showImagePage = true
                }else{
                    swal("未找到该图片！")
                }
                app1.invoiceImg.deliverList = res.data.deliverList
                app1.invoiceImg.invoiceList = res.data.invoiceList
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        imagePageClose: function () {
            this.showImagePage = false
        },
        //初始化搜索
        searchHospital: function (pageNo) {
            $("#loadingPage").show();
            var keyword = this.searchItems.searchKeyword;
            var storeId = $("#searchItemId1").val();
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/DeliverInvoice/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId,
                    'keyword': keyword,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                app1.showItems = res.data.list;
                app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败')
            })
        },
        //更新页面
        init: function (pageNo) {
            $("#loadingPage").show();
            var keyword = this.searchItems.searchKeyword;
            var storeId = $("#searchItemId1").val();
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/DeliverInvoice/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId,
                    'keyword': keyword,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                app1.showItems = res.data.list;
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败')
            })
        },
        //分页
        yy_pageing: function (initPageNo, total, setTotalCount, pageSize) {
            new Page({
                id: 'pagination',
                curPage:initPageNo, //当前页数,初始页码
                pageTotal: total, //必填,总页数
                pageSize: 5, //可选,分页个数
                dataTotal: setTotalCount, //总共多少条数据
                pageAmount: pageSize,  //每页多少条
                showPageTotalFlag:true, //是否显示数据统计
                showSkipInputFlag:true, //是否支持跳转
                getPage: function (page) {
                    //获取当前页数
                    app1.init(page);
                }
            })
        },
        //所有药店
        findAllStore: function () {
            var keyword = $('#dropSearch').val()
            $.ajax({
                url: baseURL + '/common/findAllDrugStore',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    keyword: keyword
                }
            }).done(function (res) {
                app1.allStores = res.data;
            }).fail(function () {
                console.log('请求数据失败!')
            })
        }
    }
});