var app1 = new Vue({
    el: '#app-1',
    data: {
        searchItems:{
            searchKeyword: '',
            searchStatus: ''
        },
        editItems: {
            editDrugCatalogId: '',
            editMaxOnceNumber: '',
            editStatus: '',
            editLowerLimit: '',
            editUpperLimit: ''
        },
        showItems: [],
        allDrugs: [],
        allStores: [],
        page: {
            pageNo: '',
            total: ''
        },
        dataIsNull: false,
        showCoverPage: false,
        showEditPage: false,
        showAddPage: false
    },
    created: function () {
        //初始化显示页面
        this.init(1,1);
    },
    methods: {
        //搜索按钮
        searchHospitalGo: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchItems.searchKeyword = '';
            this.searchItems.searchStatus = '';
            $("#searchItemId1").val("");
            $("#dropSearch1").val("");
        },
        //修改按钮
        editHospitalGo: function (itemId) {
            var editData = this.editItems;
            for(var key in editData){
                editData[key] = '';
            }
            this.showCoverPage = true;
            this.showEditPage = true;
            //根据点击的id拿到数据加载到修改框里
            $.ajax({
                url: baseURL + '/drugCatalog/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'drugCatalogId': itemId
                }
            }).done(function (res) {
                app1.editItems.editDrugCatalogId = res.data.id;
                app1.editItems.editMaxOnceNumber = res.data.maxOnceNumber;
                app1.editItems.editStatus = res.data.status;
                app1.editItems.editLowerLimit = res.data.lowerLimit;
                app1.editItems.editUpperLimit = res.data.upperLimit;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //取消修改
        editCancelGo: function () {
            this.showEditPage = false;
            this.showCoverPage = false;
        },
        //取消添加
        addCancelGo: function () {
            this.showAddPage = false;
            this.showCoverPage = false;
        },
        //确认修改
        editConfirmGo: function () {
            var editDrugCatalogId = app1.editItems.editDrugCatalogId;
            var editMaxOnceNumber = app1.editItems.editMaxOnceNumber;
            var editStatus = app1.editItems.editStatus;
            var editLowerLimit = app1.editItems.editLowerLimit;
            var editUpperLimit = app1.editItems.editUpperLimit;
            if(editMaxOnceNumber ==='' || editStatus ==='' || editLowerLimit ==='' || editUpperLimit ===''){
            }else{
                $.ajax({
                    url: baseURL + '/drugCatalog/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "drugCatalogId": editDrugCatalogId,
                        "maxOnceNumber": editMaxOnceNumber,
                        "lowerLimit": editLowerLimit,
                        "upperLimit": editUpperLimit,
                        "status": editStatus
                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        app1.init(app1.page.pageNo);
                        app1.editCancelGo();
                    } else {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('发送编辑请求失败!')
                })
            }
        },
        //更新页面
        init: function (pageNo,showPagination) {
            var searchKeyword = this.searchItems.searchKeyword;
            var searchStoreId = $("#searchItemId1").val();
            var searchStatus = this.searchItems.searchStatus;
            $("#loadingPage").show();
            $.ajax({
                url: baseURL + '/drugCatalog/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': searchKeyword,
                    'storeId': searchStoreId,
                    'status': searchStatus,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.showItems = res.data.list;
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                if(showPagination ==1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
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
        //全部药品
        findAllDrug: function () {
            $.ajax({
                url: baseURL + '/common/findAllDrug',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                app1.allDrugs = res.data;
            }).fail(function () {
                console.log('请求数据失败!')
            })
        }
    }
});