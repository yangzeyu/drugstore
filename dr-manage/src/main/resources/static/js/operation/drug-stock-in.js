var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchDeliverCode: '',
            searchStoreId: '',
            searchType: '',
            searchStoreName: '',
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
    beforeCreate: function () {

    },
    created: function () {
        //初始化显示页面
        this.searchItem(1);
    },
    methods: {
        //搜索按钮
        searchItemGo: function () {
            this.searchItem(1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchData.searchDeliverCode = '';
            $("#searchItemId1").val("");
            $("#dropSearch1").val("");
            this.searchData.searchDrugName = '';
            this.searchData.searchType = '';
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //修改按钮
        editItemGo: function (itemId,storeId) {
            $('#yy_edit_1').css('display', 'block');
            this.coverLayer = true;
            //根据点击的id拿到数据反填到修改框里
            $.ajax({
                url: baseURL + '/drugDeliver/findDrugDeliverItem',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'deliverId': itemId,
                    'storeId': storeId
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
            $('#yy_edit_1').css('display', 'none');
            this.coverLayer = false;
        },
        //初始化搜索
        searchItem: function (pageNo) {
            $("#loadingPage").show();
            var deliverCode = this.searchData.searchDeliverCode;
            var drugName = this.searchData.searchDrugName;
            var searchType = this.searchData.searchType;
            var storeId = $("#searchItemId1").val();
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
                    'storeId': storeId,
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
            var deliverCode = this.searchData.searchDeliverCode;
            var drugName = this.searchData.searchDrugName;
            var type = this.searchData.searchType;
            var storeId = $("#searchItemId1").val();
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/drugDeliver/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'deliverCode': deliverCode,
                    'drugName': drugName,
                    'type': type,
                    'storeId': storeId,
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
        //下载
        downLoadExcel: function () {
            var deliverCode = this.searchData.searchDeliverCode;
            var drugName = this.searchData.searchDrugName;
            var type = this.searchData.searchType;
            var storeId = $("#searchItemId1").val();
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            window.location.href = baseURL + "/drugDeliver/export?deliverCode="+ deliverCode +"&storeId="+ storeId +"&drugName="
                + drugName +"&type="+ type +"&startTime="+ startTime +"&endTime=" + endTime
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
