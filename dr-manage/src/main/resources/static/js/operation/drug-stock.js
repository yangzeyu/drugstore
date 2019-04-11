var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchDrugName: '',
            searchCatalogId: '',
            searchBatchNo: '',
            searchDrugCode: ''
        },
        //库存统计
        drugStockMSG: {
            stock: '',
            outStock: '',
            inStock: ''
        },
        showItems: [], //库存列表
        showItems2: [], //出库列表
        showItems3: [], //入库列表
        detailItems: [], //出入库列表
        deliverCode: '', //进货单号
        outNo: '', //出货单号
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        dataIsNull: false,
        drugStoreIsNull: false,
        dataIsNullInPage: false,
        dataIsNullOutPage: false,
        dataIsNullDetail: false,
        showCoverPage: false,
        showDetailPage: false,  //出入库详情
        showOutInPage: false,  //出入库列表
        showStockPage: true,  //库存列表
        drugStockMSGPage: false,
        showInNo: false,
        showOutNo: false
    },
    created: function () {
        var searchCatalogId = this.GetQueryString("catalogId"); //药品id
        if(searchCatalogId){
            this.searchData.searchCatalogId= searchCatalogId
        }else{
            this.searchData.searchCatalogId= ''
        }
        if(this.searchData.searchCatalogId){
            this.showStockPage = false;
            this.init2();
        }else{
            this.searchItemGo();
        }
    },
    methods: {
        //搜索按钮
        searchItemGo: function () {
            this.showItems=[];

            var searchStoreId = $("#searchItemId1").val(); //药店id
            var searchCatalogId = this.searchData.searchCatalogId;  //药品id

            if(!searchStoreId){
                //没有药店
                this.drugStoreIsNull = true; //请先选择药店
                this.showStockPage = true;  //库存页面
                this.showOutInPage = false; //出入库页面
                this.drugStockMSGPage = false; //库存统计
                this.yy_pageing(1, 0, 0, 1);
            }else{
                this.drugStoreIsNull = false;
                if(!searchCatalogId){
                    //只有药店
                   this.init(1,1)
                }else if(searchCatalogId){
                    //有药店和药品id
                    this.init2()
                }
            }
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchData.searchDrugName = '';
            $("#searchItemId1").val("");
            $("#dropSearch1").val("");
            this.searchData.searchBatchNo = '';
        },
        //库存重算
        compareStock: function () {
            var storeId = $("#searchItemId2").val();
            $.ajax({
                url: baseURL + '/common/compareStock',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId
                }
            }).done(function (res) {
                if(res.code == 1){

                }else{

                }
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //返回
        goBackPage: function () {
            this.searchData.searchCatalogId = '';
            this.init(app1.page.pageNo,1)
        },
        //库存列表
        init: function (pageNo,showPagination) {
            $("#loadingPage").show();
            this.showStockPage = true;
            this.showOutInPage = false;
            this.drugStockMSGPage = false

            this.showItems = [];

            var searchStoreId = $("#searchItemId1").val(); //药店id
            var searchDrugName = this.searchData.searchDrugName; //药品名称

            $.ajax({
                url: baseURL + '/drugStock/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': searchStoreId,
                    'drugName': searchDrugName,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.code == 1){
                    if(!res.data.list.length){
                        app1.dataIsNull = true
                    }else{
                        app1.dataIsNull = false
                    }
                    app1.showItems = res.data.list;
                    app1.page.pageNo = res.data.pageNo;
                    app1.page.total = res.data.total;
                    if(showPagination ===1){
                        app1.yy_pageing(pageNo, res.data.total, res.data.count, res.data.pageSize);
                    }
                }else{
                    alert(res.msg)
                }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败')
            })
        },
        //库存列表--出入库列表
        init2: function () {

            this.showStockPage = false;
            this.showOutInPage = true;
            this.drugStockMSGPage = true

            this.showItems2 = [];

            this.drugStockMSG.stock = ''
            this.drugStockMSG.outStock = ''
            this.drugStockMSG.inStock = ''

            var searchStoreId = $("#searchItemId1").val();
            var searchCatalogId = this.searchData.searchCatalogId;

            // 入库
            $.ajax({
                url: baseURL + '/drugStock/catalogInList',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': searchStoreId,
                    'catalogId': searchCatalogId
                }
            }).done(function (res) {
                if(res.code == 1){
                    if(!res.data.stockInList.length){
                        app1.dataIsNullInPage = true
                    }else{
                        app1.dataIsNullInPage = false
                    }
                    //入库信息
                    app1.showItems2 = res.data.stockInList;

                    app1.drugStockMSG.stock = res.data.sumAll
                    app1.drugStockMSG.outStock = res.data.sumOut
                    app1.drugStockMSG.inStock = res.data.sumIn

                }else{
                    alert(res.msg)
                }
            }).fail(function () {
                console.log('请求发送失败')
            });

            // 出库
            $.ajax({
                url: baseURL + '/drugStock/catalogOutList',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': searchStoreId,
                    'catalogId': searchCatalogId
                }
            }).done(function (res) {

                if(res.code == 1){
                    if(!res.data.stockOutList.length){
                        app1.dataIsNullOutPage = true
                    }else{
                        app1.dataIsNullOutPage = false
                    }
                    app1.showItems3 = res.data.stockOutList;

                }else{
                    alert(res.msg)
                }
            }).fail(function () {
                console.log('请求发送失败')
            });


        },
        //库存详情
        detailItem: function (catalogId) {
            this.searchData.searchCatalogId = catalogId;
            this.showOutInPage = true;
            this.drugStockMSGPage = true
            this.showStockPage = false;
            this.init2()
        },
        //关闭出入库详情页面
        closeDetailPage: function () {
            this.showDetailPage = false;
            this.showCoverPage = false;
            this.searchData.searchBatchNo = '';
        },
        //进货详情
        deliverCodeList: function (deliverCode) {
            this.showInNo = true;
            this.showOutNo = false;

            this.detailItems = [];
            this.showCoverPage=true;
            this.showDetailPage=true;
            var storeId = $("#searchItemId1").val();
            $.ajax({
                url: baseURL + '/drugStock/findByDeliverCode',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId,
                    'deliverCode': deliverCode
                }
            }).done(function (res) {
                app1.detailItems = res.data
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //出库详情
        outNoList: function (outNo) {
            this.showInNo = false;
            this.showOutNo = true;
            this.detailItems = [];
            this.showCoverPage=true;
            this.showDetailPage=true;

            var storeId = $("#searchItemId1").val();
            $.ajax({
                url: baseURL + '/drugStock/findByOutNo',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': storeId,
                    'outNo': outNo
                }
            }).done(function (res) {
                app1.detailItems = res.data
            }).fail(function () {
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
        //获取页面参数
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  decodeURI(r[2]); return null;
        }
    }
});