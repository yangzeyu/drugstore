var app1 = new Vue({
    el: '#app-1',
    data: {
        showItems: [],  //未对码的药品
        showItems2: [], //平台药品信息
        showItems3: {
            drugBase: {},
            drugCatalog:{}
        }, //已对码
        drugStores: [], //全部药店
        manufactures: [], //全部厂商
        selectDrugCode: '',
        selectDrugId: '',
        selectCatalogId: '',
        searchData: {
            searchStoreId: '',
            searchGoodName: ''
        },
        conversionRatio: '',
        searchData2: {
            searchKeyword: '',
            searchManufactureId: '',
            pageNo: 1
        },
        showCoverPage: false,
        dataIsNull: false,
        dataIsNull2: false,
        dataIsNull3: false,
        showComparedCodePage: false,
        showLinkedDrugCodePage: false
    },
    created: function () {
        //初始化页面
        this.init(1);
    },
    create: function () {
    },
    methods: {
        //未对码的药品
        init: function () {
            this.selectDrugCode = '';
            this.selectDrugId = '';

            var searchGoodName = this.searchData.searchGoodName;

            $.ajax({
                url: baseURL + '/compareDrugCode/findByDrugId',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': yyStoreId,
                    'goodName': searchGoodName
                }
            }).done(function (res) {
                if (res.data.length === 0) {
                    app1.dataIsNull = true
                } else {
                    app1.dataIsNull = false
                }
                app1.showItems = res.data
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //搜药店未对码药品
        searchStoreDrugCode: function () {
            this.init(1, 1)
        },
        selectName: function (catalogId,drugId) {
            this.selectCatalogId = catalogId;
            if(!drugId){
                this.showLinkedDrugCodePage = false
            }else{
                this.showLinkedDrugCodePage = true;
                this.showItems3.drugBase = [];
                this.showItems3.drugCatalog = [];

                $.ajax({
                    url: baseURL + '/compareDrugCode/findCompared',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'catalogId': catalogId
                    }
                }).done(function (res) {
                    app1.showItems3.drugBase = res.data.drugBase;
                    app1.showItems3.drugCatalog = res.data.drugCatalog;
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }
        },
        //平台药品信息
        searchPlatformDrugName: function () {
            var searchKeyword = this.searchData2.searchKeyword;
            if(!$('#dropSearch1').val()){
                $('#searchItemId1').val("")
            }
            var searchManufactureId = $('#searchItemId1').val();
            if(!searchKeyword && !searchManufactureId){
                swal("请至少输入一个条件！")
            }else{
                $.ajax({
                    url: baseURL + '/compareDrugCode/list',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'keyword': searchKeyword,
                        'manufactureId': searchManufactureId
                    }
                }).done(function (res) {
                    if (res.data.length === 0) {
                        app1.dataIsNull2 = true
                    } else {
                        app1.dataIsNull2 = false
                    }
                    app1.showItems2 = res.data;
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }
        },
        //开始对码
        startComparedCode: function () {
            this.showCoverPage = true;
            this.showComparedCodePage = true;
        },
        //解除匹配/对码
        delCompare: function () {
            var drugCode = this.selectDrugCode;
            $.ajax({
                url: baseURL + '/compareDrugCode/delCompare',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    'drugCode': drugCode,
                    'storeId': yyStoreId
                }
            }).done(function (res) {
                if (res.code == 1) {
                    swal("解除匹配成功");
                    app1.init(1, 1);
                    app1.searchPlatformDrugName(1, 1);
                    app1.showLinkedDrugCodePage = false;
                    app1.showItems3.drugBase = {};
                    app1.showItems3.drugCatalog = {};
                } else {
                    swal(res.msg)
                }
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //填写转换比后确定对码
        comparedCodeConfirm: function () {
            var selectDrugCode = this.selectDrugCode;
            var selectDrugId = this.selectDrugId;
            var conversionRatio = this.conversionRatio
            var selectCatalogId = this.selectCatalogId
            if (!selectDrugCode || !selectDrugId || !conversionRatio) {
                swal('请选择数据后重试!')
            } else {
                $.ajax({
                    url: baseURL + '/compareDrugCode/compareDrug',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        'storeId': yyStoreId,
                        'drugCode': selectDrugCode,
                        'drugId': selectDrugId,
                        'conversionRatio': conversionRatio,
                        'catalogId': selectCatalogId
                    }
                }).done(function (res) {
                    if (res.code == 1) {
                        swal("对码成功")
                        app1.init(1, 1);
                        app1.searchPlatformDrugName(1, 1);
                        app1.comparedCodeCancel()
                    } else {
                        swal(res.msg)
                    }
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }
        },
        //取消对码
        comparedCodeCancel: function () {
            this.showCoverPage = false;
            this.showComparedCodePage = false;
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
                showPageTotalFlag: false, //是否显示数据统计
                showSkipInputFlag: true, //是否支持跳转
                getPage: function (page) {
                    //获取当前页数
                    app1.init(page);
                }
            })
        },
        //平台药品分页
        yy_pageing2: function (initPageNo, total, setTotalCount, pageSize) {
            new Page({
                id: 'pagination2',
                curPage: initPageNo, //当前页数,初始页码
                pageTotal: total, //必填,总页数
                pageSize: 5, //可选,分页个数
                dataTotal: setTotalCount, //总共多少条数据
                pageAmount: pageSize,  //每页多少条
                showPageTotalFlag: false, //是否显示数据统计
                showSkipInputFlag: true, //是否支持跳转
                getPage: function (page) {
                    //获取当前页数
                    app1.searchPlatformDrugName(page);
                }
            })
        },
        changeName: function () {
            if(!$("#dropSearch1").val()){
                $("#searchItemId1").val('')
            }
        }
    }
});
