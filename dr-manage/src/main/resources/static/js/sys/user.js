var app1 = new Vue({
        el: '#app-1',
        data: {
            users: [],
            roles: [],
            searchKey: '',
            searchRoleId: '',
            searchStatus: '',
            addUserName: '',
            addNickName: '',
            addPassword: '',
            addRoleId: '',
            editUserName: '',
            editNickName: '',
            editPassword: '',
            editRoleId: '',
            id: '',
            pageNo: '',
            total: '',
            dataIsNull: false
        },
        created: function () {
            //初始化显示页面,默认为第一页
            this.searchUser(1);
            //全部角色
            $.ajax({
                url: baseURL + '/api/common/findAllRole',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                app1.roles = res.data
            }).fail(function () {
                console.log('初始化失败! 请刷新重试!')
            })
        },
        methods: {
            //初始化搜索
            searchUser: function (pageNo) {
                var key = this.searchKey;
                var roleId = this.searchRoleId;
                var status = this.searchStatus;
                $.ajax({
                    url: baseURL + '/api/sysUser/list',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'key': key,
                        'roleId': roleId,
                        'status': status,
                        'pageNo': pageNo
                    }
                }).done(function (res) {
                    if(res.data.list.length === 0){
                        app1.dataIsNull = true
                    }else{
                        app1.dataIsNull = false
                    }
                    app1.users = res.data.list;
                    app1.pageNo = res.data.pageNo;
                    app1.total = res.data.total;
                    app1.yy_pageing(1, res.data.total,res.data.count, res.data.pageSize)
                }).fail(function () {
                    console.log('初始化失败! 请刷新重试!')
                })
            },
            //更新页面
            init: function (pageNo) {
                var key = this.searchKey;
                var roleId = this.searchRoleId;
                var status = this.searchStatus;
                $.ajax({
                    url: baseURL + '/api/sysUser/list',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'key': key,
                        'roleId': roleId,
                        'status': status,
                        'pageNo': pageNo
                    }
                }).done(function (res) {
                    if(res.data.list.length === 0){
                        app1.dataIsNull = true
                    }else{
                        app1.dataIsNull = false
                    }
                    app1.users = res.data.list;
                    app1.pageNo = res.data.pageNo;
                    app1.total = res.data.total;
                }).fail(function () {
                    console.log('初始化失败! 请刷新重试!')
                })
            },
            //分页
            yy_pageing: function (initPageNo, total,setTotalCount, pageSize) {
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
                        app1.init(page);
                    }
                })
            },
            //添加用户
            addUserGo: function () {
                $('#yy_add_2').css('display', 'block');
                $('.white-box').css('opacity', '0.3');
                this.addUserName = '';
                this.addNickName = '';
                this.addPassword = '';


            },
            addCancel: function () {
                $('#yy_add_2').css('display', 'none');
                $('.white-box').animate({
                    opacity: 1,
                }, 600);
            },
            //确认添加
            addConfirm: function () {
                if ($.trim(app1.addUserName) === '' || $.trim(app1.addPassword) === ''
                    || $.trim(app1.addNickName) === ''
                // || $.trim(app1.addRoleId) === ''
                ) {
                    swal('必填项不能为空！');
                }
                else {
                    var userName = app1.addUserName;
                    var nickName = app1.addNickName;
                    var password = app1.addPassword;
                    var roleId = app1.addRoleId;
                    $.ajax({
                        url: baseURL + '/api/sysUser/add',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            "userName": userName,
                            "nickName": nickName,
                            "password": password,
                            "roleId": roleId
                        }
                    }).done(function (res) {
                        if (res.code === 1) {
                            //查当前分页总数是否有变化
                            $.ajax({
                                url: baseURL + '/api/sysUser/list',
                                type: 'GET',
                                dataType: 'JSON',
                                data: {
                                    'key': '',
                                    'roleId': '',
                                    'status': '',
                                    'pageNo': 1,
                                }
                            }).done(function (res) {
                                if (app1.total === res.data.total) {
                                    if (app1.pageNo === res.data.total) {
                                        app1.users = res.data.list;
                                    } else {
                                        app1.yy_pageing(res.data.total, res.data.total,res.data.count, res.data.pageSize)
                                    }
                                } else {
                                    app1.yy_pageing(res.data.total, res.data.total,res.data.count, res.data.pageSize)
                                }
                            }).fail(function () {
                                console.log('初始化失败! 请刷新重试!')
                                app1.init(app1.pageNo)
                            });
                            app1.addUserName = '';
                            app1.addNickName = '';
                            app1.addPassword = '';
                            app1.addRoleId = '';
                        } else if (res.code === 0) {
                            swal(res.msg);
                        }
                    }).fail(function (res) {
                        console.log('增加失败!')
                    });
                    $('#yy_add_2').css('display', 'none');
                    $('.white-box').animate({
                        opacity: 1,
                    }, 600);
                }
            },
            //修改用户
            editUserGo: function (user_id) {
                $('#yy_edit_1').css('display', 'block');
                $('.white-box').css('opacity', '0.3');
                //根据点击的id拿到数据加载到修改框里
                $.ajax({
                    url: baseURL + '/api/sysUser/findOne',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'userId': user_id
                    }
                }).done(function (res) {
                    app1.id = res.data.id;
                    app1.editUserName = res.data.userName
                    app1.editNickName = res.data.nickName
                    app1.editPassword = ''
                    app1.editRoleId = res.data.roleId
                }).fail(function () {
                    console.log('请求数据失败! 请刷新重试!')
                })
            },
            //取消修改
            editCancel: function () {
                $('#yy_edit_1').css('display', 'none');
                $('.white-box').animate({
                    opacity: 1,
                }, 600);
            },
            //确认修改
            editConfirm: function () {
                var pageNo= this.pageNo;
                var id = this.id;
                var userName = this.editUserName
                var nickName = this.editNickName
                var password = this.editPassword
                var roleId = this.editRoleId
                $.ajax({
                    url: baseURL + '/api/sysUser/update',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "userId": id,
                        "userName": userName,
                        "nickName": nickName,
                        "password": password,
                        "roleId": roleId
                    },
                }).done(function (res) {
                    if (res.code === 1) {
                        app1.init(pageNo)
                    } else if (res.code === 0) {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('编辑失败!')
                })
                $('#yy_edit_1').css('display', 'none');
                $('.white-box').animate({
                    opacity: 1,
                }, 600);
            },
            //删除
            deleteUserGo: function (user_id) {
                var total = this.total;
                swal({
                    title: "是否要删除?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "是",
                }, function () {
                    //确认删除
                    $.ajax({
                        url: baseURL + '/api/sysUser/delete',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            "userId": user_id
                        },
                    }).done(function (res) {
                        if (res.code === 1) {
                            $.ajax({
                                url: baseURL + '/api/sysUser/list',
                                type: 'GET',
                                dataType: 'JSON',
                                data: {
                                    'pageNo': app1.pageNo,
                                }
                            }).done(function (res) {
                                if(total > res.data.total && res.data.total !== 1){
                                    app1.yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize)
                                }else if(total > res.data.total && res.data.total === 1){
                                    app1.yy_pageing(res.data.total,res.data.total,res.data.count, res.data.pageSize); //更新了页码但是页面不会更新
                                    app1.init(1) // 更新页面
                                }else{
                                    app1.users = res.data.list;
                                }
                            }).fail(function () {
                                console.log('初始化失败! 请刷新重试!')
                                app1.init(pageNo)
                            });
                        } else if (res.code === 0) {
                            swal(res.msg);
                        }
                    }).fail(function () {
                        console.log('删除失败了!')
                    })
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