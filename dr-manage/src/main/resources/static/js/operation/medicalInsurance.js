var app1 = new Vue({
    el: '#app-1',
    data: {
        showItems: [],
        updateFileMessage: [],  //上传之后的信息展示列表
        updateFileMessage1: [],
        allGrades: [],  //全部收费项目等级
        searchData:{
            searchKeyword: '',
            searchCollectType: '',
            searchCollectLvl: ''
        },
        addData: {
            addMedicalInsuranceCode: '',
            addThreeDirectoryName: '',
            addThreeDirectoryType: '',
            addCollectType: '',
            addCollectLvl: '',
            addDosageForm: '',
            addSpec: '',
            addStatus: ''
        },
        editData: {
            editMedicalInsuranceCode: '',
            editThreeDirectoryName: '',
            editThreeDirectoryType: '',
            editCollectType: '',
            editCollectLvl: '',
            editDosageForm: '',
            editSpec: '',
            editStatus: ''
        },
        page: {
            pageNo: 1, //当前页数
            total: 1  //总页数
        },
        showCoverPage: false,
        showAddPage: false,
        showEditPage: false,
        dataIsNull: false,
        showUpdateFilePage: false,
        showUpdateFileMessagePage: false  //文件上传后的信息展示
    },
    created: function () {
        //初始化页面
        this.init(1,1);
        //全部收费项目等级
        this.findAllGrade()
    },
    methods: {
        //更新页面
        init: function (pageNo,initType) {
            $("#loadingPage").show();
            var keyword = this.searchData.searchKeyword;
            var collectType = this.searchData.searchCollectType;
            var collectLvl = this.searchData.searchCollectLvl;
            $.ajax({
                url: baseURL + '/medicalInsurance/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    keyword: keyword,
                    collectType: collectType,
                    collectLvl: collectLvl,
                    pageNo: pageNo

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
                if(initType === 1){
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                $("#loadingPage").hide();
                console.log('请求发送失败')
            })
        },
        //搜索按钮
        searchItemsGo: function () {
            this.init(1,1);
        },
        //重置按钮
        resetSearchGo: function () {
            this.searchData.searchKeyword = '';
            this.searchData.searchCollectType = '';
            this.searchData.searchCollectLvl = '';
        },
        //增加
        addItemGo: function () {
            this.showCoverPage = true;
            this.showAddPage = true;
            //全部收费项目等级
            this.findAllGrade()
        },
        //增加重置
        resetAddData: function () {
          this.addData.addMedicalInsuranceCode = '';
          this.addData.addThreeDirectoryName = '';
          this.addData.addThreeDirectoryType = '';
          this.addData.addCollectType = '';
          this.addData.addCollectLvl = '';
          this.addData.addDosageForm = '';
          this.addData.addSpec = '';
          this.addData.addStatus = ''
        },
        //取消增加
        addCancel: function () {
            this.showCoverPage = false;
            this.showAddPage = false;
        },
        //确认增加
        addConfirm: function () {
            var medicalInsuranceCode = this.addData.addMedicalInsuranceCode;
            var addThreeDirectoryName = this.addData.addThreeDirectoryName;
            var addThreeDirectoryType = this.addData.addThreeDirectoryType;
            var addCollectType = this.addData.addCollectType;
            var addCollectLvl = this.addData.addCollectLvl;
            var addDosageForm = this.addData.addDosageForm;
            var addSpec = this.addData.addSpec;
            var addStatus = this.addData.addStatus;
            //必填项不能为空
            if (!addThreeDirectoryName || !addThreeDirectoryType || !addCollectType || !addCollectLvl || !addDosageForm ||
                !addSpec || !medicalInsuranceCode || !addStatus
            ) {
                swal('*必填项不能为空,请填写后重试！');
            }else {
                $.ajax({
                    url: baseURL + '/medicalInsurance/add',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "medicalInsuranceCode": medicalInsuranceCode,
                        "threeDirectoryName": addThreeDirectoryName,
                        "threeDirectoryType": addThreeDirectoryType,
                        "collectType": addCollectType,
                        "collectLvl": addCollectLvl,
                        "dosageForm": addDosageForm,
                        "spec": addSpec,
                        "status": addStatus
                    }
                }).done(function (res) {
                    if (res.code === 1) {
                        //查当前分页总数是否有变化
                        app1.checkingPageChange();
                        //关闭增加页面
                        app1.addCancel();
                        //清除增加框的数据
                        app1.resetAddData();
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
            //全部收费项目等级
            this.findAllGrade()
            this.showCoverPage = true;
            this.showEditPage = true;
            this.editData.editId = Item_id;
            //根据点击的id拿到数据反填到修改框里
            $.ajax({
                url: baseURL + '/medicalInsurance/findOne',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'id': Item_id
                }
            }).done(function (res) {
                app1.editData.editId = res.data.id;
                app1.editData.editMedicalInsuranceCode = res.data.medicalInsuranceCode;
                app1.editData.editThreeDirectoryName = res.data.threeDirectoryName;
                app1.editData.editThreeDirectoryType = res.data.threeDirectoryType;
                app1.editData.editCollectType = res.data.collectType;
                app1.editData.editCollectLvl = res.data.collectLvl;
                app1.editData.editDosageForm = res.data.dosageForm;
                app1.editData.editSpec = res.data.spec;
                app1.editData.editStatus = res.data.status;
            }).fail(function () {
                console.log('请求数据失败! 请刷新重试!')
            })
        },
        //启用或停用
        changeStatus: function (editId,status) {
            $.ajax({
                url: baseURL + '/medicalInsurance/changeStatus',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    "id": editId,
                    "status": status
                }
            }).done(function (res) {
                if (res.code == 1) {
                    app1.init(app1.page.pageNo);
                } else {
                    swal(res.msg);
                }
            }).fail(function () {
                console.log('发送编辑请求失败!')
            })
        },
        //取消修改
        editCancel: function () {
            this.showCoverPage = false;
            this.showEditPage = false;
        },
        //确认修改
        editConfirm: function () {
            var editId = this.editData.editId;
            var medicalInsuranceCode = this.editData.editMedicalInsuranceCode;
            var threeDirectoryName = this.editData.editThreeDirectoryName;
            var threeDirectoryType = this.editData.editThreeDirectoryType;
            var collectType = this.editData.editCollectType;
            var collectLvl = this.editData.editCollectLvl;
            var dosageForm = this.editData.editDosageForm;
            var spec = this.editData.editSpec;
            //必填项不能为空
            if (!medicalInsuranceCode || !threeDirectoryName || !threeDirectoryType || !collectType || !collectLvl ||
                !dosageForm || !spec
            ) {
                swal(' * 必填项不能为空,请填写后重试！');
            }
            else {
                $.ajax({
                    url: baseURL + '/medicalInsurance/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        "id": editId,
                        "medicalInsuranceCode": medicalInsuranceCode,
                        "threeDirectoryName": threeDirectoryName,
                        "threeDirectoryType": threeDirectoryType,
                        "collectType": collectType,
                        "collectLvl": collectLvl,
                        "dosageForm": dosageForm,
                        "spec": spec
                    }
                }).done(function (res) {
                    if (res.code == 1) {
                        app1.init(app1.page.pageNo);
                        app1.editCancel();
                    } else {
                        swal(res.msg);
                    }
                }).fail(function () {
                    console.log('发送编辑请求失败!')
                })
            }
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
                url: baseURL + '/medicalInsurance/exportInsuranceModel',
                type: 'POST',
                data: formdata,
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false   // 告诉jQuery不要去设置Content-Type请求头
            }).done(function (res) {
                if(res.code == 1){
                    app1.updateFileCancel();
                    app1.init(1,1);
                    //提示信息
                    if(res.data != [] || res.data != ''){
                        app1.showCoverPage = true;
                        app1.showUpdateFileMessagePage = true;
                        app1.updateFileMessage = res.data;
                    }
                }else if(res.code ==0){
                    swal(res.msg)
                }
            }).fail(function () {
                alert("上传失败")
            })
        },
        //上传文件提示框关闭
        updateFileMessageCancel: function () {
            app1.showCoverPage = false;
            app1.showUpdateFileMessagePage = false;
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
                    console.log(page)
                    app1.init(page);
                }
            })
        },
        findAllGrade: function () {
            this.allGrades = [];
            $.ajax({
                url: baseURL + '/common/findAllGrade',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                app1.allGrades = res.data
            }).fail(function () {

            })
        },
        //查询总页数是否有变化
        checkingPageChange: function () {
            $.ajax({
                url: baseURL + '/medicalInsurance/list',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    keyword: '',
                    collectType: '',
                    collectLvl: '',
                    pageNo: 1
                }
            }).done(function (res) {
                app1.showItems = res.data.list;
                //总页数不变
                if (app1.page.total === res.data.total) {
                    if (app1.page.pageNo === 1) {
                        app1.showItems = res.data.list;
                    }else {
                        app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                    }
                //总页数变化
                }else {
                    app1.yy_pageing(1, res.data.total, res.data.count, res.data.pageSize);
                }
            }).fail(function () {
                console.log('请求发送失败');
                app1.init(app1.page.pageNo);
            });
        }
    }
});