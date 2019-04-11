var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData: {
            searchStoreId: '',
            searchInvoiceCode: ''
        },
        showItems: [],
        showItems2: [],
        showInvoices: [],
        pageNo: '',
        total: '',
        showSelectedDrugStorm: true,
        dataIsNull: false,
        showCoverPage: false,
        showInvoicesPage: false
    },
    created: function () {
    },
    methods: {
        //更新页面
        init: function (pageNo, showPage) {
            this.showItems2 = [];
            this.showInvoices = [];
            this.showInvoicesPage = false;
            this.showSelectedDrugStorm = true;
            var storeId = $("#searchItemId1").val();
            var searchInvoiceCode = this.searchData.searchInvoiceCode;
            if(storeId){
                this.showSelectedDrugStorm = false;
                $("#loadingPage").show();
                $.ajax({
                    url: baseURL + '/report/invoiceCompare/invoiceCompare',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'storeId': storeId,
                        'invoiceCode': searchInvoiceCode,
                        'pageNo': pageNo
                    }
                }).done(function (res) {
                    $("#loadingPage").hide();
                    if (res.code == 1) {
                        this.showItems = [];
                        if (res.data.list.length === 0) {
                            app1.dataIsNull = true
                        } else {
                            app1.dataIsNull = false
                        }
                        app1.pageNo = res.data.pageNo;
                        app1.total = res.data.total;
                        app1.showItems = res.data.list;

                        if(searchInvoiceCode){
                            app1.showInvoicesPage = true;
                            app1.showInvoices = res.data.invoice.goodsData;
                            app1.showItems2 = res.data.list;
                        }
                        if (showPage === 1) {
                            app1.yy_pageing(showPage, res.data.total, res.data.count, res.data.pageSize)
                        }
                    } else {
                        swal(res.msg)
                    }
                }).fail(function () {
                    $("#loadingPage").hide();
                    console.log('请求发送失败!')
                })
            }else{
                swal('请先选择药店！')
            }
        },
        //点击搜索按钮
        searchItem: function () {
            this.init(1, 1)
        },
        //点击重置按钮
        resetSearch: function () {
            $("#dropSearch1").val("")
            $("#searchItemId1").val("")
            this.searchData.searchInvoiceCode = '';
        },
        //查看API接口信息
        lookAPI: function (invoiceCode) {
            this.searchData.searchInvoiceCode = invoiceCode;
            this.init(1, 1)
        },
        lookInvoiceImg: function (deliveryId) {
            /*丰城市弘源大药房*/
            console.log(deliveryId)
            $.ajax({
                url: baseURL + '/DeliverInvoice/findInvoiceImg',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'deliveryId': deliveryId
                }
            }).done(function (res) {

            }).fail(function () {
                console.error('请求发送失败!')
            })
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
