var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchDeliverCode: '',
            searchStoreId: '',
            searchType: '',
            searchDrugName: ''
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
        coverLayer: false,
        detailWindow: false,
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
            this.searchData.searchDeliverCode = '';
            this.searchData.searchStoreId = '';
            this.searchData.searchDrugName = '';
            this.searchData.searchType = '';
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //查看详情
        editItemGo: function (itemId) {
            this.coverLayer = true;
            this.detailWindow = true;
            $.ajax({
                url: baseURL + '/drugDeliver/findDrugDeliverItem',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': yyStoreId,
                    'deliverId': itemId
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
        //关闭查看
        editCancelGo: function () {
            this.detailWindow = false;
            this.coverLayer = false;
        },
        //更新页面
        init: function (pageNo,showPage) {
            var deliverCode = this.searchData.searchDeliverCode;
            var drugName = this.searchData.searchDrugName;
            var searchType = this.searchData.searchType;
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/drugDeliver/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'deliverCode': deliverCode,
                    'drugName': drugName,
                    'type': searchType,
                    'storeId': yyStoreId,
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
                if(showPage === 1){
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