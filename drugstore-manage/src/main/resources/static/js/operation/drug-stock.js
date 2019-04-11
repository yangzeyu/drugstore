var app1 = new Vue({
    el: '#app-1',
    data: {
        searchItems:{
            searchKeyword: ''
        },
        showItems: [],
        allStores: [],
        page: {
            pageNo: '',
            total: ''
        },
        dataIsNull: false,
        showCoverPage: false
    },
    created: function () {
        //初始化显示页面
        this.init(1,1);
    },
    methods: {
        //搜索按钮
        searchItem: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchItems.searchKeyword = '';
        },
        //更新页面
        init: function (pageNo,showPage) {
            var searchKeyword = this.searchItems.searchKeyword;
            var storeId = yyStoreId;
            $.ajax({
                url: baseURL + '/DrugStock/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': searchKeyword,
                    'storeId': storeId,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.showItems = res.data.list;
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                if(showPage === 1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //下载
        downLoadExcel: function () {
            var keyword = this.searchItems.searchKeyword;
            window.location.href = baseURL + "/DrugStock/list/export?keyword="+ keyword

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