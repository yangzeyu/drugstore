var app1 = new Vue({
    el: '#app-1',
    data: {
        showItems: [],
        XXX: [],
        searchData:{
            searchXXX: '',
            searchXXX: '',
            searchXXX: ''
        },
        addData: {
            addXXX: '',
            addXXX: '',
            addXXX: '',
            addXXX: ''
        },
        editData: {
            editXXX: '',
            editXXX: '',
            editXXX: ''
        },
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        showCoverPage: false,
        showAddPage: false,
        showEditPage: false
    },
    created: function () {
        //初始化页面
        this.searchXXX(1);
    },
    methods: {
        //更新页面
        init: function (pageNo,initType) {
            var keyword = this.searchData.searchXXX;
            $.ajax({
                url: baseURL + 'XXX',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': keyword,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                app1.showItems = res.data.list;
                if(initType === 1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //搜索按钮
        searchItemsGo: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchData.searchXXX = '';
            this.searchData.searchXXX = '';
            this.searchData.searchXXX = '';
            this.init(1,1);
        },
        //增加
        addItemGo: function () {
            this.showCoverPage = true;
            this.showAddPage - true;
        },
        //取消增加
        addCancel: function () {
            this.showCoverPage = false;
            this.showAddPage - false;
        },
        //确认增加
        addConfirm: function () {
            var addXXX = app1.addData.addXXX;
            var addXXX = app1.addData.addXXX;
            //必填项不能为空
            if (addXXX === '' || addXXX === ''
            ) {
                swal('*必填项不能为空,请填写后重试！');
            }else {
                $.ajax({
                    url: baseURL + 'XXX',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "name": addXXX

                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        //查当前分页总数是否有变化
                        app1.checkingPageChange();
                        //关闭增加页面
                        app1.addCancel();
                    } else {
                        swal(res.msg);
                    }
                }).fail(function (res) {
                    swal(res.msg);
                });
            }
        },
        //修改按钮
        editItemGo: function (Item_id) {
            this.showCoverPage = true;
            this.showEditPage - true;
            //根据点击的id拿到数据反填到修改框里
            $.ajax({
                url: baseURL + 'XXX',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'XXX': Item_id
                }
            }).done(function (res) {
                app1.editData.editId = res.data.id;
                app1.editData.editXXX = res.data.XXX;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //取消修改
        editCancel: function () {
            this.showCoverPage = false;
            this.showEditPage - false;
        },
        //确认修改
        editConfirm: function () {
            var editId = app1.editData.editId;
            var editXXX = app1.editData.XXX;
            //必填项不能为空
            if (editXXX === '' || editXXX === ''
            ) {
                swal(' * 必填项不能为空,请填写后重试！');
            }
            else {
                $.ajax({
                    url: baseURL + 'XXX',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "XXX": editId,
                        "XXX": editXXX
                    }
                }).done(function (res) {
                    if (res.code == 1) {
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
        //查当前分页总数是否有变化
        checkingPageChange: function () {
            $.ajax({
                url: baseURL + 'XXX',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'XXX': '',
                    'XXX': '',
                    'pageNo': 1
                }
            }).done(function (res) {
                if (app1.page.total === res.data.total) {
                    if (app1.page.pageNo === res.data.total) {
                        app1.showItems = res.data.list;
                    } else {
                        app1.yy_pageing(res.data.total, res.data.total, res.data.count, res.data.pageSize);
                    }
                } else {
                    app1.yy_pageing(res.data.total, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                console.log('请求发送失败');
                app1.init(app1.page.pageNo);
            });
        }
    }
});



$(function () {
    //表头固定
    $("#tableScroll").scroll(function() {
        var scrollHeight =$('#tableScroll').scrollTop() + "px";
        $('#fixedHead').css({
            "position": "relative",
            "transform":"translateY("+scrollHeight+")"
        });
    });
});