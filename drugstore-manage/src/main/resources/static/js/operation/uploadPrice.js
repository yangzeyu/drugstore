var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchDeliverCode: '',
            searchStoreId: '',
            searchType: '',
            searchDrugName: ''
        },
        showItems: [],
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        coverLayer: false,
        dataIsNull: false
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
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //更新页面
        init: function (pageNo,showPage) {
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/uploadPrice/findUploadPrice',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': yyStoreId,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                if(res.code ===1){
                    app1.page.pageNo = res.data.pageNo;
                    app1.page.total = res.data.total;
                    app1.showItems = res.data;
                }else{
                    swal(res.msg)
                }
                /*if(showPage === 1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }*/
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