var app1 = new Vue({
    el: '#app-1',
    data: {
        users: [],
        data: [],
        pageNo: '',
        total: '',
        dataIsNull: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.searchRole(1);
    },
    methods: {
        Vue_edit: function (user_id) {
            $('#yy_edit_1').css('display', 'block');
            $('.white-box').css('opacity', '0.3');
            $('#yy_edit_roleId').val(user_id);
            //拿到所有菜单
            app1.allMenu();
            //根据点击的id拿到数据加载到修改框里
            app1.searchMenu(user_id);
            //勾选已有角色
        },
        Vue_delete: function (user_id, pageNo) {
            var total = this.total;
            swal({
                title: "是否要删除?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
            }, function () {
                //确认删除
                app1.deleteDrug(user_id, pageNo, total)
            });
        },
        vue_nav: function () {
            // console.log(event.currentTarget.href)
            var id = event.currentTarget.id;
            $("#menuSon" + id + "").toggle(); //点击切换隐藏显示
        },
        vue_selected: function (id, parentId) {
            if ($("input:checkbox[name=menuIds][value='" + id + "']").prop("checked")) {
                this.selectParentMenu(parentId);
                this.selectChildMenu(id);
            } else {
                var qwe = $("input:checkbox[name=menuIds][parent= '" + parentId + "']:checked");
                if (qwe.length === 0) {
                    this.cancelParentMenu(parentId);
                }
                this.cancelChildMenu(id);
            }
        },
        operation_select: function (id, menuId) {
            var qwe;
            if ($("input:checkbox[name=operationIds][value='" + id + "']").prop("checked")) {
                app1.selectParentMenu(menuId);
            } else {
                qwe = $("input:checkbox[name=operationIds][menu= '" + menuId + "']:checked");
                if (qwe.length === 0) {
                    //可以不选子选项
                    // app1.cancelParentMenu(menuId);
                }
            }
        },
        vue_selected_all: function (id) {
            if ($("input:checkbox[name=menuIds][value='" + id + "']").prop("checked")) {
                this.selectChildMenu(id);
            } else {
                this.cancelChildMenu(id);
            }
        },
        cancelChildMenu: function(id) {
            this.cancelChildOperation(id);
            var nodes = $("input:checkbox[name=menuIds][parent='" + id + "']");
            if (nodes.length === 0) {
                return;
            }
            nodes.each(function () {
                $(this).prop("checked", false);
                var oid = $(this).val();
                app1.cancelChildMenu(oid);
            });
        },
        selectChildMenu: function(id) {
            this.selectChildOperation(id);
            var nodes = $("input:checkbox[name=menuIds][parent='" + id + "']");
            if (nodes.length === 0) {
                return;
            }
            nodes.each(function () {
                $(this).prop("checked", true);
                var oid = $(this).val();
                app1.selectChildMenu(oid);
            });
        },
        selectChildOperation: function(id) {
            $("input:checkbox[name=operationIds][menu='" + id + "']").prop("checked", true);
        },
        cancelChildOperation: function(id) {
            $("input:checkbox[name=operationIds][menu='" + id + "']").prop("checked", false);
        },
        selectParentMenu: function(parentId) {
            var qwe = $("input:checkbox[name=menuIds][value='" + parentId + "']");
            qwe.prop("checked", true);
            var pid=qwe.attr("parent");
            if (pid) {
                this.selectParentMenu(pid);
            }
        },
        cancelParentMenu: function(parentId) {
            var qwe = $("input:checkbox[name=menuIds][value= '" + parentId + "']");
            qwe.prop("checked", false);
            var pid = qwe.attr("parent");
            if (!pid) {
                return;
            }
            qwe = $("input:checkbox[name=menuIds][parent='" + pid + "']:checked");
            if (qwe.length === 0) {
                this.cancelParentMenu(pid);
            }
        },
        addRole: function () {
            $('#yy_add_2').css('display', 'block');
            $('.white-box').css('opacity', '0.3');
        },
        addCancel: function () {
            $('#yy_add_2').css('display', 'none');
            $('.white-box').animate({opacity: '1'},600);
        },
        addConfirm: function (pageNo) {
            var total = this.total;
            if ($.trim($('#yy_add_name').val()) === '') {
                swal('必填项不能为空！');
            } else {
                app1.addDrug(pageNo, total)
            }
        },
        editCancel: function () {
            $("[name=menuIds]:checkbox").prop("checked", false);
            $('#yy_edit_1').css('display', 'none');
            $('.white-box').animate({opacity: '1'},600);
        },
        //确定修改
        editConfirm: function (pageNo) {
            var roleId = $('#yy_edit_roleId').val();
            var id_array = new Array();
            var o_array = new Array();
            var name = $('#yy_edit_name').val();
            var code = $('#yy_edit_code').val();
            //拿到多个选项的数据并拼接成需要的格式
            $('input[name="menuIds"]:checked').each(function () {
                id_array.push($(this).val()); //向数组中添加元素
            });
            $('input[name="operationIds"]:checked').each(function () {
                o_array.push($(this).val()); //向数组中添加元素
            });
            var menuIds = id_array.join(',');//将数组元素连接起来以构建一个字符串
            var operations = o_array.join(',');
            //修改角色菜单
            $.ajax({
                url: baseURL + '/api/sysRole/editRoleMenu',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "menuIds": menuIds,
                    "operationIds" : operations,
                    "roleId": roleId
                },
            }).done(function (res) {
                if (res.code === 1) {
                } else if (res.code === 0) {
                    swal(res.msg);
                }
            }).fail(function () {
                console.log('编辑失败了!')
            })
            //修改角色信息
            $.ajax({
                url: baseURL + '/api/sysRole/edit',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "roleId": roleId,
                    "roleName": name,
                    "roleCode": code
                },
            }).done(function (res) {
                if (res.code === 1) {
                    app1.init(app1.pageNo)
                    $('#yy_edit_1').css('display', 'none');
                    $('.white-box').animate({opacity: '1'},600);
                } else if (res.code === 0) {
                    swal(res.msg);
                }
            }).fail(function () {
                console.log('编辑失败了!')
            })
        },
        //拿到所有菜单
        allMenu: function () {
            $.ajax({
                url: baseURL + '/api/common/findAllMenu',
                type: 'GET',
                dataType: 'JSON',
                async: false
            }).done(function (res) {
                console.log(res)
                app1.data = res.data
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //根据id查询角色菜单
        searchMenu: function (user_id) {
            $.ajax({
                url: baseURL + '/api/sysRole/findMenuByRole',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'roleId': user_id
                }
            }).done(function (res) {
                for (var i = 0; i < res.data.length; i++) {
                    var tem = res.data[i].id;
                    $("input:checkbox[name=menuIds][value='" + tem + "']").prop("checked", true);
                }
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            });
            $.ajax({
                url: baseURL + '/api/sysRole/findOperationByRole',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'roleId': user_id
                }
            }).done(function (res) {
                for (var i = 0; i < res.data.length; i++) {
                    var tem = res.data[i].id;
                    $("input:checkbox[name=operationIds][value='" + tem + "']").prop("checked", true);
                }
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            });
            $.ajax({
                url: baseURL + '/api/sysRole/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'roleId': user_id
                }
            }).done(function (res) {
                $("#yy_edit_name").val(res.data.name)
                $("#yy_edit_code").val(res.data.code)
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            });
        },
        //初始化页面,查询用户角色
        searchRole: function (pageNo) {
            var key = $('#yy_search_key').val();
            $.ajax({
                url: baseURL + '/api/sysRole/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'key': key,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.pageNo = res.data.pageNo;
                app1.users = res.data.list;
                app1.total = res.data.total;
                app1.yy_pageing(1,res.data.total,res.data.count, res.data.pageSize)
            }).fail(function () {
                console.log('初始化失败! 请刷新重试!')
            })
        },
        //更新页面
        init: function (pageNo) {
            var key = $('#yy_search_key').val();
            $.ajax({
                url: baseURL + '/api/sysRole/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'key': key,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                //拿到数据传到vue里渲染出页面
                app1.pageNo = res.data.pageNo;
                app1.users = res.data.list;
                app1.total = res.data.total;
            }).fail(function () {
                console.log('init初始化失败! 请刷新重试!')
            })
        },
        //增加药品
        addDrug: function (pageNo, total) {
            var roleName = $('#yy_add_name').val();
            var code = $('#yy_add_code').val();
            $.ajax({
                url: baseURL + '/api/sysRole/add',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "roleName": roleName,
                    "code": code
                }
            }).done(function (res) {
                if (res.code === 1) {
                    $.ajax({
                        url: baseURL + '/api/sysRole/list',
                        type: 'GET',
                        dataType: 'JSON',
                        data: {
                            'key': '',
                            'pageNo': total
                        }
                    }).done(function (res) {
                        if (total === res.data.total) {
                            if (pageNo === res.data.total) {
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
                    $('#yy_add_2').css('display', 'none');
                    $('.white-box').animate({opacity: '1'},600);
                    $('#yy_add_name').val('');
                    $('#yy_add_code').val('');
                } else{
                    swal(res.msg);
                }
            }).fail(function () {
                console.log('增加失败了!')
            })
        },//删除
        deleteDrug: function (user_id, pageNo, total) {
            $.ajax({
                url: baseURL + '/api/sysRole/delete',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "roleId": user_id
                },
            }).done(function (res) {
                if (res.code == 1) {
                    $.ajax({
                        url: baseURL + '/api/sysRole/list',
                        type: 'GET',
                        dataType: 'JSON',
                        data: {
                            'key': '',
                            'pageNo': pageNo
                        }
                    }).done(function (res) {
                        if (total > res.data.total && res.data.total !== 1) {
                            app1.yy_pageing(res.data.total, res.data.total,res.data.count, res.data.pageSize)
                        } else if (total > res.data.total && res.data.total === 1) {
                            app1.yy_pageing(res.data.total, res.data.total,res.data.count, res.data.pageSize); //更新了页码但是页面不会更新
                            app1.init(1) // 更新页面
                        } else {
                            app1.users = res.data.list;
                        }

                    }).fail(function () {
                        console.log('初始化失败! 请刷新重试!')
                        app1.init(app1.pageNo)
                    });
                } else{
                    alert(res.msg)
                }
            }).fail(function () {
                console.log('删除失败了!')
            })
        },
        //分页查询
        yy_pageing: function (initPageNo, total, setTotalCount, pageSize) {
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
        }

    }
});