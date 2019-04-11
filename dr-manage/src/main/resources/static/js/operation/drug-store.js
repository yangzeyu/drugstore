var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData:{
            searchKeyword: '',
            searchStatus: ''
        },
        addData: {
            addStoreName: '',
            addCode: '',
            addAddress: '',
            addStatus: 1,
            addContractPerson: '',
            addContractPhone: '',
            addContractEmail: ''
        },
        editData: {
            editId: '',
            editStoreName: '',
            editCode: '',
            editAddress: '',
            editStatus: '',
            editContractPerson: '',
            editContractPhone: '',
            editContractEmail: ''
        },
        addUser: {
            storeId: '',
            userName: '',
            nickName: '',
            userPassword: '',
            userStatus: 1
        },
        editUser: {
            userId: '',
            userName: '',
            nickName: '',
            oldPassword: '',
            newPassword: '',
            userStatus: ''
        },
        showItems: [],
        drugStoreUsers: [],
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        dataIsNull: false,
        dataIsNull2: false,
        showCoverPage: false,
        showCoverPage2: false,
        showAddPage: false,  //新增页面
        showEditPage: false,  //修改药店页面
        showUserPage: false,
        showEditUserPage: false,
        showAddUserPage: false

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
            this.searchData.searchKeyword = '';
            this.searchData.searchStatus = '';
            $('#yy_search_startTime').val('');
            $('#yy_search_endTime').val('');
        },
        //添加按钮
        addItemGo: function () {
            this.addData.addStoreName = '';
            this.addData.addCode = '';
            this.addData.addAddress = '';
            this.addData.addStatus = 1;
            this.addData.addContractPerson = '';
            this.addData.addContractPhone = '';
            this.addData.addContractEmail = '';
            this.showAddPage = true;
            this.showCoverPage = true;
        },
        //用户管理
        editUserList: function (itemId) {
            this.addUser.storeId = itemId;
            $.ajax({
                url: baseURL + '/drugStore/findStoreUser',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'storeId': itemId
                }
            }).done(function (res) {
                if(res.data.length === 0){
                    app1.dataIsNull2 = true
                }else{
                    app1.dataIsNull2 = false
                }
                app1.drugStoreUsers = res.data
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
            this.showCoverPage = true;
            this.showUserPage = true;
        },
        //修改用户
        editUserGo: function (userId) {
            this.showCoverPage2=true;
            this.showEditUserPage=true;
            //将之前的数据清空
            var addArray = this.editUser;
            for(var key in addArray){
                addArray[key] = '';
            }
            //根据id加载修改框
            $.ajax({
                url: baseURL + '/drugStore/findUserById',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'userId': userId
                }
            }).done(function (res) {
               app1.editUser.userId = res.data.id;
               app1.editUser.userName = res.data.userName;
               app1.editUser.nickName = res.data.nickName;
               app1.editUser.oldPassword = '';
               app1.editUser.newPassword = '';
               app1.editUser.userStatus = res.data.status;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //用户修改取消
        editUserCancel: function () {
            this.showCoverPage2=false;
            this.showEditUserPage=false;
        },
        //用户修改确认
        editUserConfirm: function () {
            var storeId = this.addUser.storeId;
            var userId = this.editUser.userId;
            var userName = this.editUser.userName;
            var nickName = this.editUser.nickName;
            var oldPassword = this.editUser.oldPassword;
            var newPassword = this.editUser.newPassword;
            var status = this.editUser.userStatus;
            $.ajax({
                url: baseURL + '/drugStore/editStoreUser',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    'userId': userId,
                    'userName': userName,
                    'nickName': nickName,
                    'oldPassword': oldPassword,
                    'newPassword': newPassword,
                    'status': status
                }
            }).done(function (res) {
               if(res.code ===1){
                   $.ajax({
                       url: baseURL + '/drugStore/findStoreUser',
                       type: 'GET',
                       dataType: 'JSON',
                       data: {
                           'storeId': storeId
                       }
                   }).done(function (res) {
                       app1.drugStoreUsers = res.data
                   }).fail(function () {
                       console.log('请求数据失败! 请刷新重试!')
                   });
                   app1.editUserCancel()
               }else{
                   swal(res.msg)
               }
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //添加用户
        addUserGo: function () {
            this.showCoverPage2=true;
            this.showAddUserPage = true;
        },
        //添加用户关闭
        addUserClose: function () {
            this.showCoverPage2=false;
            this.showAddUserPage = false;
        },
        //确定添加用户
        addUserConfirm: function () {
            var storeId = this.addUser.storeId;
            var userName = this.addUser.userName;
            var nickName = this.addUser.nickName;
            var userPassword = this.addUser.userPassword;
            var userStatus = this.addUser.userStatus;
            if(userName ==='' || nickName ==='' || userPassword==='' || userStatus ===''){
                swal("*必填项不能为空!")
            }else{
                $.ajax({
                    url: baseURL + '/drugStore/addStoreUser',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        'storeId': storeId,
                        'userName': userName,
                        'nickName': nickName,
                        'password': userPassword,
                        'status': userStatus
                    }
                }).done(function (res) {
                    if(res.code ==1){
                        $.ajax({
                            url: baseURL + '/drugStore/findStoreUser',
                            type: 'GET',
                            dataType: 'JSON',
                            data: {
                                'storeId': storeId
                            }
                        }).done(function (res) {
                            if(res.data.length === 0){
                                app1.dataIsNull2 = true
                            }else{
                                app1.dataIsNull2 = false
                            }
                            app1.drugStoreUsers = res.data
                        }).fail(function () {
                            console.log('请求数据失败! 请刷新重试!')
                        })
                        app1.addUserClose()
                        app1.addUser.userName = '';
                        app1.addUser.nickName = '';
                        app1.addUser.userPassword = '';
                        app1.addUser.userStatus = 1
                    }else{
                        swal(res.msg)
                    }
                }).fail(function (res) {
                    console.log("添加用户请求发送失败!")
                })
            }
        },
        //用户管理页面关闭
        userClose: function () {
            this.showCoverPage = false;
            this.showUserPage = false;
        },
        //修改按钮
        editItemGo: function (itemId) {
            this.showEditPage = true;
            this.showCoverPage = true;
            //将之前的数据清空
            var addArray = this.editData;
            for(var key in addArray){
                addArray[key] = '';
            }
            //根据点击的id拿到数据反填到修改框里
            $.ajax({
                url: baseURL + '/drugStore/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'drugStoreId': itemId
                }
            }).done(function (res) {
                    app1.editData.editId = res.data.id;
                    app1.editData.editStoreName = res.data.name;
                    app1.editData.editCode = res.data.code;
                    app1.editData.editAddress = res.data.address;
                    app1.editData.editStatus = res.data.status;
                    app1.editData.editContractPerson = res.data.contractPerson;
                    app1.editData.editContractPhone = res.data.contractPhone;
                    app1.editData.editContractEmail = res.data.contractEmail;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //发送邮件
        sendEmailGo: function (itemId) {
            swal({
                title: "是否要发送邮件?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是"
            }, function () {
                //发送邮件
                $.ajax({
                    url: baseURL + '/drugStore/sendMessage',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'drugStoreId': itemId
                    }
                }).done(function (res) {
                    if(res.code === 1){
                        swal("邮件发送成功！")
                    }else{
                        swal("邮件发送失败！")
                    }
                }).fail(function () {
                    console.log('请求数据失败! 请刷新重试!')
                })
            });
        },
        //取消增加
        addCancel: function () {
            this.showCoverPage = false;
            this.showAddPage = false;
        },
        //确认增加
        addConfirmGo: function () {
            var storeName = app1.addData.addStoreName;
            var code = app1.addData.addCode;
            var address = app1.addData.addAddress;
            var status = app1.addData.addStatus;
            var contractPerson = app1.addData.addContractPerson;
            var contractPhone = app1.addData.addContractPhone;
            var contractEmail = app1.addData.addContractEmail;
            //必填项不能为空
            if (storeName === '' || code === '' ||
                status === '' || contractEmail === ''
            ) {
                swal('*必填项不能为空,请填写后重试！');
            }
            else {
                $.ajax({
                    url: baseURL + '/drugStore/add',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "storeName": storeName,
                        "code": code,
                        "address": address,
                        "status": status,
                        "contractPerson": contractPerson,
                        "contractPhone": contractPhone,
                        "contractEmail": contractEmail
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
        //取消修改
        editCancel: function () {
            this.showCoverPage = false;
            this.showEditPage = false;
        },
        //确认修改
        editConfirmGo: function () {
            var editId = app1.editData.editId;
            var storeName = app1.editData.editStoreName;
            var code = app1.editData.editCode;
            var address = app1.editData.editAddress;
            var status = app1.editData.editStatus;
            var contractPerson = app1.editData.editContractPerson;
            var contractPhone = app1.editData.editContractPhone;
            var contractEmail = app1.editData.editContractEmail;
            //必填项不能为空
            if (storeName === '' || code === '' ||
                status === '' || contractEmail === ''
            ) {
                swal('*必填项不能为空,请填写后重试！');
            }
            else {
                $.ajax({
                    url: baseURL + '/drugStore/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        'storeId': editId,
                        "storeName": storeName,
                        "code": code,
                        "address": address,
                        "status": status,
                        "contractPerson": contractPerson,
                        "contractPhone": contractPhone,
                        "contractEmail": contractEmail
                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        app1.init(app1.page.pageNo);
                        //关闭增加页面
                        app1.editCancel();
                    } else {
                        swal(res.msg);
                    }
                }).fail(function (res) {
                    swal(res.msg);
                });
            }
        },
        //更新页面
        init: function (pageNo,showPagination) {
            $("#loadingPage").show();
            var keyword = this.searchData.searchKeyword;
            var status = this.searchData.searchStatus;
            if($('#yy_search_startTime').val() === ''){
                var startTime = $('#yy_search_startTime').val();
            }else{
                var startTime = $('#yy_search_startTime').val()+' 00:00:00';
            }
            if($('#yy_search_endTime').val() === ''){
                var endTime = $('#yy_search_endTime').val();
            }else{
                var endTime = $('#yy_search_endTime').val()+' 23:59:59';
            }
            $.ajax({
                url: baseURL + '/drugStore/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': keyword,
                    'status': status,
                    'startTime': startTime,
                    'endTime': endTime,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide()
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
                app1.page.pageNo = res.data.pageNo;
                app1.page.total = res.data.total;
                app1.showItems = res.data.list;
                if(showPagination ==1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                $("#loadingPage").hide();
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
        },
        //查当前分页总数是否有变化,有变化时处理
        checkingPageChange: function () {
            $.ajax({
                url: baseURL + '/drugStore/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': '',
                    'status': '',
                    'startTime': '',
                    'endTime': '',
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
})
