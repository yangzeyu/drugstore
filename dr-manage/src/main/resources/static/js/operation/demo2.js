var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData: {
            searchKey: '',
            searchStatus: ''
        },
        addData: {
            addType: '',
            addName: '',
            addShortName: '',
            addCode: '',
            addStatus: 1,
            addParentId: 0,
            addAddress: '',
            addContactPerson: '',
            addContactPhone: '',
            addAppCode: '',
            addAppToken: ''
        },
        editData: {
            editPharmacyPartyId: '',
            editType: '',
            editName: '',
            editShortName: '',
            editCode: '',
            editStatus: '',
            editParentId: '',
            editAddress: '',
            editContactPerson: '',
            editContactPhone: '',
            editIsFactory: '',
            editIsCommunity: '',
            editIsStore: '',
            editAppCode: '',
            editAppToken: ''
        },
        showItems: [],   //社区列表
        lvlOneHospitals: [],  //一级社区
        pageNo: '',
        total: '',
        dataIsNull: false,
        showCoverPage: false,
        showAddPage: false,
        showEditPage: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.init(1, 1);
    },
    methods: {
        //更新页面
        init: function (pageNo, showPage) {
            var key = this.searchData.searchKey;
            var status = this.searchData.searchStatus;
            $("#loadingPage").show();
            $.ajax({
                url: baseURL + '/api/pharmacyParty/findPharmacyParty',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'kind': 3, // 1- 药店 2-药厂 3-社区医院
                    'key': key,
                    'level': '',
                    'parentId': '',
                    'status': status,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if (res.code == 1) {
                    if (res.data.list.length === 0) {
                        app1.dataIsNull = true
                    } else {
                        app1.dataIsNull = false
                    }
                    app1.showItems = res.data.list;
                    app1.pageNo = res.data.pageNo;
                    app1.total = res.data.total;
                } else {
                    swal(res.msg)
                }
                if (showPage === 1) {
                    app1.yy_pageing(showPage, res.data.total, res.data.count, res.data.pageSize)
                }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败!')
            })
        },
        //点击搜索按钮
        searchItem: function () {
            this.init(1, 1)
        },
        //点击重置按钮
        resetSearch: function () {
            var searchData = this.searchData;
            for (var j in searchData) {
                searchData[j] = ""
            }
        },
        //点击添加按钮
        addItemGo: function () {
            this.showAddPage = true;
            this.showCoverPage = true;
            this.oneLevelPharmacy();
            //还原增加框的数据
            var addData = this.addData;
            for (var j in addData) {
                addData[j] = ""
            }
            this.addData.addStatus = 1;
            this.addData.addParentId = 0;
        },
        //取消增加
        addCancel: function () {
            this.showAddPage = false;
            this.showCoverPage = false;
        },
        //修改用户
        editItemGo: function (partyId) {
            //还原修改框的数据
            var editData = this.editData;
            for (var j in editData) {
                editData[j] = ""
            }
            this.showEditPage = true;
            this.showCoverPage = true;
            this.oneLevelPharmacy();
            this.editData.editPharmacyPartyId = partyId;
            //根据点击的id拿到数据加载到修改框里
            $.ajax({
                url: baseURL + '/api/pharmacyParty/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'kind': 3,
                    'pharmacyPartyId': partyId
                }
            }).done(function (res) {
                app1.editData.editName = res.data.name;
                app1.editData.editShortName = res.data.shortName;
                app1.editData.editCode = res.data.code;
                app1.editData.editStatus = res.data.status;
                app1.editData.editParentId = res.data.parentId;
                app1.editData.editAddress = res.data.address;
                app1.editData.editContactPerson = res.data.contactPerson;
                app1.editData.editContactPhone = res.data.contactPhone;
                app1.editData.editIsFactory = res.data.isFactory;
                app1.editData.editIsCommunity = res.data.isCommunity;
                app1.editData.editIsStore = res.data.isStore;
                app1.editData.editAppCode = res.data.appCode;
                app1.editData.editAppToken = res.data.appToken;

            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //取消修改
        editCancel: function () {
            this.showEditPage = false;
            this.showCoverPage = false;
        },
        //确认修改
        editConfirmGo: function () {
            var pharmacyPartyId = this.editData.editPharmacyPartyId;
            var name = this.editData.editName;
            var shortName = this.editData.editShortName;
            var code = this.editData.editCode;
            var status = this.editData.editStatus;
            var parentId = this.editData.editParentId;
            var address = this.editData.editAddress;
            var contactPerson = this.editData.editContactPerson;
            var contactPhone = this.editData.editContactPhone;
            var appCode = this.editData.editAppCode;
            var appToken = this.editData.editAppToken;
            $.ajax({
                url: baseURL + '/api/pharmacyParty/edit',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "pharmacyPartyId": pharmacyPartyId,
                    "kind": 3, //(1- 药店    2-药厂   3-社区医院)
                    "type": 0, //暂时没用到.先传0
                    "name": name,
                    "shortName": shortName,
                    'code': code,
                    'status': status,
                    'parentId': parentId,
                    'address': address,
                    'contactPerson': contactPerson,
                    'contactPhone': contactPhone,
                    'appCode': appCode,
                    'appToken': appToken
                }
            }).done(function (res) {
                if (res.code === 1) {
                    app1.init(app1.pageNo);
                    app1.editCancel()
                } else {
                    swal(res.msg);
                }
            }).fail(function () {
                console.log('编辑失败!')
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
        },
        //确认增加
        addConfirm: function () {
            var name = this.addData.addName;
            var shortName = this.addData.addShortName;
            var code = this.addData.addCode;
            var status = this.addData.addStatus;
            var parentId = this.addData.addParentId;
            var address = this.addData.addAddress;
            var contactPerson = this.addData.addContactPerson;
            var contactPhone = this.addData.addContactPhone;
            var appCode = this.addData.addAppCode;
            var appToken = this.addData.addAppToken;

            $.ajax({
                url: baseURL + '/api/pharmacyParty/add',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "type": 0, //暂时没用到,先传0
                    "kind": 3, //(1- 药店 2-药厂 3-社区医院)
                    "name": name,
                    "shortName": shortName,
                    'code': code,
                    'status': status,
                    'parentId': parentId,
                    'address': address,
                    'contactPerson': contactPerson,
                    'contactPhone': contactPhone,
                    'appCode': appCode,
                    'appToken': appToken
                }
            }).done(function (res) {
                if (res.code == 1) {
                    app1.whetherChange();
                    app1.addCancel()
                } else {
                    swal(res.msg);
                }
            }).fail(function (res) {
                console.log('增加失败!')
            })
        },
        //一级社区
        oneLevelPharmacy: function () {
            $.ajax({
                url: baseURL + '/api/common/findOneLevelPharmacy',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    kind: 3
                }
            }).done(function (res) {
                app1.lvlOneHospitals = res.data
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //查分页是否有变化
        whetherChange: function () {
            $.ajax({
                url: baseURL + '/api/pharmacyParty/findPharmacyParty',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'kind': 3, //(1- 药店  2-药厂   3-社区医院
                    'key': '',
                    'level': '',
                    'parentId': '',
                    'status': '',
                    'pageNo': 1
                }
            }).done(function (res) {
                if (app1.total === res.data.total) {
                    if (app1.pageNo === res.data.total) {
                        app1.showItems = res.data.list;
                    } else {
                        app1.yy_pageing(res.data.total, res.data.total, res.data.count, res.data.pageSize)
                    }
                } else {
                    app1.yy_pageing(res.data.total, res.data.total, res.data.count, res.data.pageSize)
                }
            }).fail(function () {
                console.log('初始化失败! 请刷新重试!')
                app1.init(app1.pageNo)
            })
        },
        //随机生成appToken
        createAppToken: function (type) {
            console.log(type)
            $.ajax({
                url: baseURL + '/api/common/createAppToken',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                if (type === 1) {
                    app1.addData.addAppToken = res.data.appToken
                }
                if (type === 2) {
                    app1.editData.editAppToken = res.data.appToken
                }
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        }
    }
});
