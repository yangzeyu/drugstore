var app1 = new Vue({
    el: '#app-1',
    data: {
        items: [],
        operators: [],
        pageNo: '',
        total: '',
        dataIsNull: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.init(1,1);
        this.allurl();
    },
    methods: {
        //搜索
        searchItemsGo: function () {
            this.init(1,1)
        },
        //重置
        resetSearchGo: function () {
            $('#yy_search_url').val('');
            $('#yy_search_operator').val('');
            $('#yy_search_status').val('');
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //更新页面
        init: function (page,showPagination) {
            $("#loadingPage").show();
            var url = $('#yy_search_url').val();
            var operator = $('#yy_search_operator').val();
            var status = $('#yy_search_status').val();
            var startTime = $('#yy_search_startTime').val();
            var endTime = $('#yy_search_endTime').val();
            $.ajax({
                url: baseURL + '/api/apiOperateLog/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'url': url,
                    'operator': operator,
                    'status': status,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': page
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.items = res.data.list;
                app1.pageNo = res.data.pageNo;
                app1.total = res.data.total;
                if(showPagination == 1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize)
                }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('init初始化失败! 请刷新重试!')
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
        //查看参数详情信息
        detailItems: function (item_reqParam) {
            alert(item_reqParam)
        },
        //查到所有操作人
        allurl: function () {
            $.ajax({
                url: baseURL + '/common/findAllDrugStore',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                var arr1 = res.data;
                var arr2 = [];
                for(var i=0,leg = arr1.length; i<leg; i++){
                    if(arr1[i]){
                        arr2.push(arr1[i])
                    }
                }
                app1.operators = arr2;
            }).fail(function () {
                console.log("查询所有操作人失败，请重试！")
            })
        }
    }
});