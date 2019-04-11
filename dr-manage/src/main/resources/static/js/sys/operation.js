var app1 = new Vue({
    el: '#app-1',
    data: {
        operations: [],
        pageNo: '',
        total: '',
        dataIsNull: false
    },
    //初始化
    mounted: function () {
        search(1);
    },
    methods: {
        //点击修改
        Vue_edit: function (operationId) {
            $('#yy_edit_1').css('display', 'block');
            $('.white-box1').css('opacity', '0.3');
            //根据点击的id拿到数据加载到修改框里
            searchOperationMsg(operationId)
        },
        //确认修改
        editConfirm: function () {
            var menuId=$("#menuId").val();
            var pageNo = this.pageNo;
            var key = this.searchKey;
            if ($.trim($('#yy_edit_name').val()) === '' || $.trim($('#yy_edit_code').val()) === '') {
                swal('*必填项不能为空！');
            } else {
                var id = $('#yy_edit_id').val();
                var code = $('#yy_edit_code').val();
                var name = $('#yy_edit_name').val();
                $.ajax({
                    url: baseURL + '/api/sysMenu/'+menuId+'/operation/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "id": id,
                        "name": name,
                        "code": code
                    },
                }).done(function (res) {
                    if (res.code === 1) {
                        init(menuId, pageNo);
                        $('#yy_edit_1').css('display', 'none');
                        $('.white-box1').animate({
                            opacity: 1,
                        }, 600);
                    } else if (res.code === 0) {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('编辑失败了!')
                })
            }
        },
        //删除
        Vue_delete: function (operationId) {
            var menuId=$("#menuId").val();
            var pageNo = this.pageNo;
            var total = this.total;
            swal({
                title: "是否要删除?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是"
            }, function () {
                //确认删除
                $.ajax({
                    url: baseURL + '/api/sysMenu/'+menuId+'/operation/delete',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "operationId": operationId
                    },
                }).done(function (res) {
                    if (res.code === 1) {
                        $.ajax({
                            url: baseURL + '/api/sysMenu/'+menuId+'/operation/list',
                            type: 'GET',
                            dataType: 'JSON',
                            data: {
                                'pageNo': pageNo
                            }
                        }).done(function (res) {
                            if (res.code==1){
                                app1.pageNo = res.data.pageNo;
                                app1.operations = res.data.list;
                                app1.total = res.data.total;
                                yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize,menuId)
                            }
                        }).fail(function () {
                            console.log('初始化失败了!')
                        });

                    } else if (res.code === 0) {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('删除失败了!')
                })
            });
        },
        //增加按钮
        addOperation: function () {
            $('#yy_add_2').css('display', 'block');
            $('.white-box1').css('opacity', '0.3');
        },
        //确认增加
        addConfirm: function () {
            var menuId=$("#menuId").val();
            var pageNo = this.pageNo;
            var total = this.total;
            if ($.trim($('#yy_add_name').val()) === '' || $.trim($('#yy_add_code').val()) === '') {
                swal('必填项不能为空！');
            } else {
                var name = $('#yy_add_name').val();
                var code = $('#yy_add_code').val();
                $.ajax({
                    url: baseURL + '/api/sysMenu/'+menuId+'/operation/add',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "name": name,
                        "code": code
                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        $.ajax({
                            url: baseURL + '/api/sysMenu/'+menuId+'/operation/list',
                            type: 'GET',
                            dataType: 'JSON',
                            data: {
                                'pageNo': pageNo
                            }
                        }).done(function (res) {
                            if (res.code==1){
                                app1.pageNo = res.data.pageNo;
                                app1.operations = res.data.list;
                                app1.total = res.data.total;
                                yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize,menuId)
                            }
                        }).fail(function () {
                            console.log('初始化失败了!')
                        });

                        $('#yy_add_2').css('display', 'none');
                        $('.white-box1').animate({
                            opacity: 1,
                        }, 600);
                        $('#yy_add_name').val('');
                        $('#yy_add_code').val('');
                    } else if (res.code === 0) {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('增加失败了!')
                })
            }
        },
        //增加取消
        addCancel: function () {
            $('#yy_add_2').css('display', 'none');
            $('.white-box1').animate({
                opacity: 1,
            }, 600);
        },
        //取消修改
        editCancel: function () {
            $('#yy_edit_1').css('display', 'none');
            $('.white-box1').animate({
                opacity: 1,
            }, 600);
        },//选择图标
        changeIcon: function () {
            $('#selectIcon').css('display', 'block');
            $("#qwer input").css('cursor','pointer')

        },
        selectIconCancel: function () {
            $('#selectIcon').css('display', 'none');
        }
    }
});


//初始化搜索,默认为第一页
function search(pageNo) {
    var menuId=$("#menuId").val();
    $.ajax({
        url: baseURL + '/api/sysMenu/'+menuId+'/operation/list',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'pageNo': pageNo
        }
    }).done(function (res) {
        if(res.data.list.length ===0){
           app1.dataIsNull = true
        }else{
            app1.dataIsNull = false
        }
        app1.operations = res.data.list;
        app1.pageNo = res.data.pageNo;
        app1.total = res.data.total;
        var total = res.data.total;
        yy_pageing(1,total,res.data.count, res.data.pageSize,menuId)
    }).fail(function () {
        console.log('初始化失败了!')
    });
}

//更新页面
function init(menuId, page) {
    var menuId=$("#menuId").val();
    $.ajax({
        url: baseURL + '/api/sysMenu/'+menuId+'/operation/list',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'pageNo': page
        }
    }).done(function (res) {
        if(res.data.list.length ===0){
            app1.dataIsNull = true
        }else{
            app1.dataIsNull = false
        }
        app1.pageNo = res.data.pageNo;
        app1.operations = res.data.list;
        app1.total = res.data.total;
    }).fail(function () {
        console.log('初始化失败! 请刷新重试!')
    })
}

//根据点击的id拿到数据加载到修改框里
function searchOperationMsg(operationId) {
    var menuId=$("#menuId").val();
    $.ajax({
        url: baseURL + '/api/sysMenu/'+menuId+'/operation/findOne',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'operationId': operationId,
        }
    }).done(function (res) {
        $('#yy_edit_id').val(res.data.id);
        $('#yy_edit_name').val(res.data.name);
        $('#yy_edit_code').val(res.data.code);
    }).fail(function () {
        console.log('请求数据失败!')
    })
}

//分页查询
function yy_pageing(initPageNo,total,setTotalCount, pageSize,menuId) {
    new Page({
        id: 'pagination',
        curPage:initPageNo, //当前页数,初始页码
        pageTotal: total, //必填,总页数
        pageSize: 5, //可选,分页个数
        dataTotal: setTotalCount, //总共多少条数据
        pageAmount: pageSize,  //每页多少条
        showPageTotalFlag:true, //是否显示数据统计
        showSkipInputFlag:false, //是否支持跳转
        getPage: function (page) {
            //获取当前页数
            init(menuId,page);
        }
    })
}
