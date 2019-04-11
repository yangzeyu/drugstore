var app1 = new Vue({
    el: '#app-1',
    data: {
        searchItems:{
            searchKeyword: '',
            searchType: '',
            searchPermissionNum: '',
            searchMedicalInsuranceCode: ''
        },
        addItems: {
            addName: '',
            addGoodName: '',
            addDosageForm: '',
            addType: '', //1中药 ,2西药
            addSpec: '',
            addUnit: '',
            addStandSpecRate: '',
            addUnitPrice: '',
            addRetailPrice: '',
            addPermissionNum: '',
            isMedicalInsurance: '',  //是否医保
            status: ''
        },
        editItems: {
            editItemId: '',
            editName: '',
            editGoodName: '',
            editDosageForm: '',
            editType: '', //1中药 ,2西药
            editSpec: '',
            editUnit: '',
            editStandSpecRate: '',
            editUnitPrice: '',
            editRetailPrice: '',
            editPermissionNum: '',
            status: ''
        },
        showItems: [],
        manufactures: [],
        manufacturesAdd: [],
        manufacturesEdit: [],
        updateFileMessage: [],  //上传之后的信息展示列表
        updateFileMessage1: [],
        medicalInsuranceCodes: [],
        page: {
            pageNo: '',
            total: ''
        },
        dataIsNull: false,
        showCoverPage: false,
        showAddPage: false,
        showEditPage: false,
        showChangeSearch: true,
        showUpdateFilePage: false,  //文件上传弹窗
        showUpdateFileMessagePage: false  //文件上传后的信息展示
    },
    created: function () {
        //初始化显示页面
        this.init(1,1);
    },
    methods: {
        //搜索按钮
        searchHospitalGo: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchItems.searchKeyword = '';
            this.searchItems.searchType = '';
            this.searchItems.searchPermissionNum = '';
            this.searchItems.searchMedicalInsuranceCode = '';
            $("#searchItemId1").val("");
            $("#dropSearch1").val("");
            $("#searchItemId4").val("");
            $("#dropSearch4").val("");
        },
        //添加按钮
        addHospitalGo: function () {
            var addArray = this.addItems;
            for(var key in addArray){
                addArray[key] = '';
            }
            this.showAddPage = true;
            this.showCoverPage = true;
            $("#searchItemId2").val("");
            $("#dropSearch2").val("");
        },
        //修改按钮
        editHospitalGo: function (id) {
            var editArray = this.editItems;
            for(var key in editArray){
                editArray[key] = '';
            }
            $("#searchItemId3").val("");
            $("#dropSearch3").val("");
            this.showCoverPage = true;
            this.showEditPage = true;
            //根据点击的id拿到数据加载到修改框里
            $.ajax({
                url: baseURL + '/drugBase/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'drugBaseId': id
                }
            }).done(function (res) {
                app1.editItems.editItemId = res.data.id;
                app1.editItems.editName = res.data.name;
                app1.editItems.editGoodName = res.data.goodName;
                app1.editItems.editDosageForm = res.data.dosageForm;
                app1.editItems.editType = res.data.type;
                app1.editItems.editSpec = res.data.spec;
                app1.editItems.editUnit = res.data.unit;
                app1.editItems.editStandSpecRate = res.data.standSpecRate;
                app1.editItems.editUnitPrice = res.data.unitPrice;
                app1.editItems.editRetailPrice = res.data.retailPrice;
                app1.editItems.editPermissionNum = res.data.permissionNumber;
                app1.editItems.status = res.data.status;
                $("#dropSearch3").val(res.data.manufactureName);
                $("#searchItemId3").val(res.data.manufactureId)
                $("#dropSearch6").val(res.data.medicalInsuranceCode)
                $("#searchItemId6").val(res.data.medicalInsuranceId)
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //文件上传
        updateFileGo: function () {
            this.showUpdateFilePage = true;
            this.showCoverPage = true;
            // $("#updateFile").click()
        },
        //文件上传取消
        updateFileCancel: function () {
            this.showUpdateFilePage = false;
            this.showCoverPage = false;
            $('#updateForm').empty();
            $("#updateForm").append("<input id=\"updateFile\" type=\"file\" name=\"file\" class=\"form-control input-sm\">");
        },
        //文件上传确认
        updateFileConfirm: function () {
            this.updateFileMessage = [];
            this.updateFileMessage1 = [];
            var formdata = new FormData($("#updateForm")[0]);
            $.ajax({
                url: baseURL + '/drugBase/exportDrugBaseModel',
                type: 'POST',
                data: formdata,
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false   // 告诉jQuery不要去设置Content-Type请求头
            }).done(function (res) {
                if(res.code == 1){
                    app1.updateFileCancel();
                    app1.init(1,1);
                    //提示信息
                    if(res.data.msg || res.data.msgList){
                        app1.showCoverPage = true;
                        app1.showUpdateFileMessagePage = true;
                        app1.updateFileMessage = res.data.msg;
                        app1.updateFileMessage1 = res.data.msgList;
                    }
                }else if(res.code ==0){
                    swal(res.msg)
                }
            }).fail(function () {
                alert("请求失败")
            })
        },
        //上传文件提示框关闭
        updateFileMessageCancel: function () {
            app1.showCoverPage = false;
            app1.showUpdateFileMessagePage = false;
        },
        //取消增加
        addCancel: function () {
            this.showAddPage = false;
            this.showCoverPage = false;
        },
        //确认增加
        addConfirmGo: function () {
            this.addConfirm()
        },
        //取消修改
        editCancelGo: function () {
            this.showCoverPage = false;
            this.showEditPage = false;
        },
        //确认修改
        editConfirmGo: function () {
            var editItemId = app1.editItems.editItemId;
            var editName = app1.editItems.editName;
            var editGoodName = app1.editItems.editGoodName;
            var editDosageForm = app1.editItems.editDosageForm;
            var editType = app1.editItems.editType;
            var editSpec = app1.editItems.editSpec;
            var editUnit = app1.editItems.editUnit;
            var editStandSpecRate = app1.editItems.editStandSpecRate;
            var editUnitPrice = app1.editItems.editUnitPrice;
            var editRetailPrice = app1.editItems.editRetailPrice;
            var editPermissionNum = app1.editItems.editPermissionNum;
            if(!$("#dropSearch3").val()){
                $("#searchItemId3").val("")
            }
            var editManufactureId = $("#searchItemId3").val();
            var medicalInsuranceId = $.trim($("#searchItemId6").val());
            var status = app1.editItems.status;
            if (editName=='' || editGoodName=='' ||
                editDosageForm=='' || editUnit=='' ||
                editType=='' || editSpec=='' ||
                editStandSpecRate=='' || editName==null || editGoodName==null ||
                editDosageForm==null || editUnit==null ||
                editType==null || editSpec==null ||
                editStandSpecRate==null
            ) {
                alert("*必填项不能为空")
            }
            else {
                $.ajax({
                    url: baseURL + '/drugBase/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "drugBaseId": editItemId,
                        "name": editName,
                        "goodName": editGoodName,
                        "dosageForm": editDosageForm,
                        "type": editType,
                        "spec": editSpec,
                        "unit": editUnit,
                        "standSpecRate": editStandSpecRate,
                        "unitPrice": editUnitPrice,
                        "retailPrice": editRetailPrice,
                        "permissionNum": editPermissionNum,
                        "manufactureId": editManufactureId,
                        "status": status,
                        "medicalInsuranceId": medicalInsuranceId
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
        //更新页面
        init: function (pageNo,showPagination) {
            $("#loadingPage").show();
            var keyword = this.searchItems.searchKeyword;
            var type = this.searchItems.searchType;
            var permissionNum = this.searchItems.searchPermissionNum;
            if(!$("#dropSearch1").val()){
                $("#searchItemId1").val("")
            }
            var manufactureId = $("#searchItemId1").val();
            var medicalInsuranceId = $("#searchItemId4").val();
            $.ajax({
                url: baseURL + '/drugBase/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': keyword,
                    'type': type,
                    'permissionNum': permissionNum,
                    'manufactureId': manufactureId,
                    'medicalInsuranceId': medicalInsuranceId,
                    'pageNo': pageNo
                }
            }).done(function (res) {
                $("#loadingPage").hide();
                if(res.data.list.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                }
               app1.page.pageNo = res.data.pageNo;
               app1.page.total = res.data.total;
               app1.showItems = res.data.list;
               if(showPagination ==1){
                   var initPageNo = 1;
                   var pageTotal = res.data.total;
                   var dataTotal= res.data.count;
                   var pageSize = res.data.pageSize;
                   app1.yy_pageing(initPageNo, pageTotal, dataTotal, pageSize);
               }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败')
            })
        },
        //确认增加
        addConfirm: function () {
            var addName = app1.addItems.addName;
            var addGoodName = app1.addItems.addGoodName;
            var addDosageForm = app1.addItems.addDosageForm;
            var addType = app1.addItems.addType;
            var addSpec = app1.addItems.addSpec;
            var addUnit = app1.addItems.addUnit;
            var addStandSpecRate = app1.addItems.addStandSpecRate;
            var isMedicalInsurance = app1.addItems.isMedicalInsurance;
            var addUnitPrice = app1.addItems.addUnitPrice;
            var addRetailPrice = app1.addItems.addRetailPrice;
            var addPermissionNum = app1.addItems.addPermissionNum;
            if(!$("#dropSearch2").val()){
                $("#searchItemId2").val("")
            }
            var addManufactureId = $.trim($("#searchItemId2").val());
            var medicalInsuranceId = $.trim($("#searchItemId5").val());
            var status = app1.addItems.status;
            if (addName=='' || addGoodName=='' || addDosageForm=='' || addType=='' ||
                addSpec=='' || addUnit=='' || addStandSpecRate=='' || isMedicalInsurance==''||
                addName==null || addGoodName==null || addDosageForm==null || addType==null ||
                addSpec==null || addUnit==null || addStandSpecRate==null || isMedicalInsurance==null
            ) {
                alert("*必填项不能为空")
            }else{
                $.ajax({
                    url: baseURL + '/drugBase/add',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "name": addName,
                        "goodName": addGoodName,
                        "dosageForm": addDosageForm,
                        "type": addType,
                        "spec": addSpec,
                        "unit": addUnit,
                        "standSpecRate": addStandSpecRate,
                        "unitPrice": addUnitPrice,
                        "retailPrice": addRetailPrice,
                        "permissionNum": addPermissionNum,
                        "manufactureId": addManufactureId,
                        "isMedicalInsurance": isMedicalInsurance,
                        "status": status,
                        "medicalInsuranceId": medicalInsuranceId

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
                    swal("增加失败,请刷新后重试!");
                    console.log(res.msg);
                });
            }
        },
        //分页
        yy_pageing: function (initPageNo, pageTotal, dataTotal, pageSize) {
            new Page({
                id: 'pagination',
                curPage:initPageNo, //初始页码
                pageTotal: pageTotal, //必填,总页数
                pageSize: 5, //可选,分页个数
                dataTotal: dataTotal, //总共多少条数据
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
                url: baseURL + '/drugBase/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'keyword': '',
                    'type': '',
                    'permissionNum': '',
                    'manufactureId': '',
                    'medicalInsuranceId': '',
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
        },
    }
});