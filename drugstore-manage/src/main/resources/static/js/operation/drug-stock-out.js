var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchDrugName: '',
            searchOutNo: '',
            searchType: '',
            searchPayType: '',
            searchDrugStore: ''
        },
        detailData: {
            detailId: '',
            detailDrugName: '',    //药品名称
            detailDosageForm: '',  //剂型
            detailExpire: '', //有效期
            detailMfr: '',  //生产厂家
            detailOutNo: '', //出库单号
            detailPlatformCode: '',  //平台编码
            detailQuantity: '',    //数量
            detailSpec: '',  //规格
            detailUnit: ''  //单位
        },
        showItems: [],
        detailItems: [],
        drugStores: [],
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        coverLayer: false,  //遮罩层
        showDetailPage: false, // 详情窗口
        dataIsNull: false,
        detailDataIsNull: false
    },
    created: function () {
        //初始化显示页面
        this.init(1,1);
    },
    methods: {
        //搜索按钮
        searchItemGo: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchData.searchDrugName = '';
            this.searchData.searchOutNo = '';
            this.searchData.searchType = '';
            this.searchData.searchPayType = '';
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //修改按钮
        editItemGo: function (itemId) {
            this.coverLayer = true;
            this.showDetailPage = true;
            //根据点击的id拿到数据反填到修改框里
            $.ajax({
                url: baseURL + '/DrugStockOut/findDrugStockOutItem',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': yyStoreId,
                    'storeOutId': itemId
                }
            }).done(function (res) {
                if(res.data.length === 0){
                    app1.detailDataIsNull = true
                }else{
                    app1.detailDataIsNull = false
                }
                app1.detailItems = res.data;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //取消修改
        editCancelGo: function () {
            this.showDetailPage = false;
            this.coverLayer = false;
        },
        //更新页面
        init: function (pageNo,showPage) {
            var drugName = this.searchData.searchDrugName;
            var outNo = this.searchData.searchOutNo;
            var type = this.searchData.searchType;
            var searchPayType = this.searchData.searchPayType;
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/DrugStockOut/findDrugStockOut',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'drugName': drugName,
                    'outNo': outNo,
                    'drugStoreId': yyStoreId,
                    'type': type,
                    'payType': searchPayType,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                app1.showItems = res.data.list;
                if(showPage ===1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
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
        }
    }
});
