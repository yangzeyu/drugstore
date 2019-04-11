var app1 = new Vue({
    el: '#app-1',
    data: {
        menus: [],
        upperLevelMenus: [],
        searchKey: '',
        searchParentId: '',
        pageNo: '',
        total: '',
        dataIsNull: false
    },
    //初始化
    mounted: function () {
        LevelOneMenu();
        searchMenu('', '', 1);
        //点击选择图标
        $("#qwer input").on("click", function () {
            $("#yy_edit_icon").val($(this).val());
            $('#selectIcon').css('display', 'none');
        })
    },
    methods: {
        //点击修改
        Vue_edit: function (menu_id) {
            $('#yy_edit_1').css('display', 'block');
            $('.white-box1').css('opacity', '0.3');
            //拿到一级菜单
            LevelOneMenu();
            //根据点击的id拿到数据加载到修改框里
            searchMenuMsg(menu_id)
        },
        //确认修改
        editConfirm: function () {
            var pageNo = this.pageNo;
            var key = this.searchKey;
            if ($.trim($('#yy_edit_name').val()) === '' || $.trim($('#yy_edit_code').val()) === ''
                || $.trim($('#yy_edit_num').val()) === '') {
                swal('*必填项不能为空！');
            } else {
                var parentId = $('#yy_edit_parentId option:selected').val();
                var id = $('#yy_edit_id').val();
                var url = $('#yy_edit_url').val();
                var num = $('#yy_edit_num').val();
                var icon = $('#yy_edit_icon').val();
                var code = $('#yy_edit_code').val();
                var name = $('#yy_edit_name').val();
                $.ajax({
                    url: baseURL + '/api/sysMenu/editMenu',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "id": id,
                        "name": name,
                        "icon": icon,
                        "code": code,
                        "url": url,
                        "num": num,
                        "parentId": parentId
                    },
                }).done(function (res) {
                    if (res.code === 1) {
                        init(key, pageNo)
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
        Vue_delete: function (menu_id) {
            var pageNo = this.pageNo;
            var total = this.total;
            var key = this.searchKey;
            var parentId = this.searchParentId;
            swal({
                title: "是否要删除?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是"
            }, function () {
                //确认删除
                $.ajax({
                    url: baseURL + '/api/sysMenu/delete',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "menuId": menu_id
                    },
                }).done(function (res) {
                    if (res.code === 1) {
                        $.ajax({
                            url: baseURL + '/api/sysMenu/list',
                            type: 'GET',
                            dataType: 'JSON',
                            data: {
                                'key': key,
                                'parentId': parentId,
                                'pageNo': pageNo
                            }
                        }).done(function (res) {
                            if (total > res.data.total && res.data.total !== 1) {
                                yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize,key)
                            } else if (total > res.data.total && res.data.total === 1) {
                                yy_pageing(res.data.total, res.data.total,res.data.count, res.data.pageSize,key)
                                init(key, 1)
                            } else {
                                app1.menus = res.data.list;
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
        //搜索
        searchMenus: function () {
            var key = this.searchKey;
            var parentId = this.searchParentId;
            searchMenu(key, parentId, 1)
        },
        //增加按钮
        addMenu: function () {
            $('#yy_add_2').css('display', 'block');
            $('.white-box1').css('opacity', '0.3');
            LevelOneMenu()
        },
        //确认增加
        addConfirm: function () {
            var pageNo = this.pageNo;
            var total = this.total;
            if ($.trim($('#yy_add_name').val()) === '' || $.trim($('#yy_add_code').val()) === ''
                || $.trim($('#yy_add_num').val()) === '') {
                swal('必填项不能为空！');
            } else {
                var name = $('#yy_add_name').val();
                var icon = $('#yy_add_icon').val();
                var code = $('#yy_add_code').val();
                var url = $('#yy_add_url').val();
                var num = $('#yy_add_num').val();
                var parentId = $('#yy_add_parentId option:selected').val();
                $.ajax({
                    url: baseURL + '/api/sysMenu/addMenu',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "name": name,
                        "icon": icon,
                        "code": code,
                        "url": url,
                        "num": num,
                        "parentId": parentId
                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        $.ajax({
                            url: baseURL + '/api/sysMenu/list',
                            type: 'GET',
                            dataType: 'JSON',
                            data: {
                                'key': '',
                                'parentId': '',
                                'pageNo': pageNo
                            }
                        }).done(function (res) {
                            if (total === res.data.total) {
                                if (pageNo === res.data.total) {
                                    app1.menus = res.data.list;
                                } else {
                                    yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize,'')
                                }
                            } else {
                                yy_pageing(res.data.total,res.data.totalres.data.count, res.data.pageSize,'')
                            }
                        }).fail(function () {
                            console.log('初始化失败了!')
                        });

                        $('#yy_add_2').css('display', 'none');
                        $('.white-box1').animate({
                            opacity: 1,
                        }, 600);
                        $('#yy_add_name').val('');
                        $('#yy_add_icon').val('');
                        $('#yy_add_code').val('');
                        $('#yy_add_url').val('');
                        $('#yy_add_parentId').val('');
                        $('#yy_add_num').val('');
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
function searchMenu(key, parentId, pageNo) {
    $.ajax({
        url: baseURL + '/api/sysMenu/list',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'key': key,
            'parentId': parentId,
            'pageNo': pageNo
        }
    }).done(function (res) {
        if(res.data.list.length === 0){
            app1.dataIsNull = true
        }else{
            app1.dataIsNull = false
        }
        app1.menus = res.data.list;
        app1.pageNo = res.data.pageNo;
        app1.total = res.data.total;
        var total = res.data.total;
        yy_pageing(1,total,res.data.count, res.data.pageSize,key)
    }).fail(function () {
        console.log('初始化失败了!')
    });
}

//更新页面
function init(key, page) {
    $.ajax({
        url: baseURL + '/api/sysMenu/list',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'key': key,
            'pageNo': page
        }
    }).done(function (res) {
        if(res.data.list.length === 0){
            app1.dataIsNull = true
        }else{
            app1.dataIsNull = false
        }
        app1.pageNo = res.data.pageNo;
        app1.menus = res.data.list;
        app1.total = res.data.total;
    }).fail(function () {
        console.log('初始化失败! 请刷新重试!')
    })
}

//拿到一级菜单
function LevelOneMenu() {
    $.ajax({
        url: baseURL + '/api/sysMenu/findLevelOneMenu',
        type: 'GET',
        dataType: 'JSON'
    }).done(function (res) {
        app1.upperLevelMenus = res.data
    }).fail(function () {
        console.log('初始化失败!')
    })
}

//根据点击的id拿到数据加载到修改框里
function searchMenuMsg(menu_id) {
    $.ajax({
        url: baseURL + '/api/sysMenu/findOne',
        type: 'GET',
        dataType: 'JSON',
        data: {
            'menuId': menu_id,
        }
    }).done(function (res) {
        $('#yy_edit_parentId').val(res.data.parentId);
        $('#yy_edit_id').val(res.data.id);
        $('#yy_edit_url').val(res.data.url);
        $('#yy_edit_num').val(res.data.num);
        $('#yy_edit_icon').val(res.data.icon);
        $('#yy_edit_name').val(res.data.name);
        $('#yy_edit_code').val(res.data.code);
    }).fail(function () {
        console.log('请求数据失败!')
    })
}

//分页查询
function yy_pageing(initPageNo,total,setTotalCount, pageSize,key) {
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
            app1.init(key,page);
        }
    })
}
