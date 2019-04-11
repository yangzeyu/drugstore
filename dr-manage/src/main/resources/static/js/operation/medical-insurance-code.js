var app1 = new Vue({
    el: '#app-1',
    data: {
        showItems: [],  //未对码的医保
        showItems2: [], //平台医保信息
        showItems3: {   //已对码医保信息
            drugBase: {},
            medicalInsurance: {}
        },
        allGrades: [], //项目收费等级
        selectDrugId: '',
        selectMedicalInsuranceId: '',
        searchData: {
            searchKeyword: '',
            searchCollectType: '',
            searchCollectLvl: ''
        },
        conversionRatio: '',
        searchData2: {
            searchKeyword: '',
            searchManufactureId: '',
            pageNo: 1,
            searchItemId1: '' //厂商id
        },
        showCoverPage: false,
        dataIsNull: false,
        dataIsNull2: false,
        dataIsNull3: false,
        showComparedCodePage: false,
        showLinkedDrugCodePage: false
    },
    created: function () {
        //初始化页面
        // this.init();
        //项目收费等级
        this.findAllGrade()
    },
    create: function () {
    },
    methods: {
        //右侧医保信息
        init: function () {
            this.selectMedicalInsuranceId = ''

            var searchKeyword = $("#searchItemId5").val();
            var searchCollectType = this.searchData.searchCollectType;
            var searchCollectLvl = this.searchData.searchCollectLvl;

            if(searchKeyword || searchCollectType || searchCollectLvl){
                $.ajax({
                    url: baseURL + '/compareMedicalInsurance/findByMedicalInsurance',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'keyword': searchKeyword,
                        'collectType': searchCollectType,
                        'collectLvl': searchCollectLvl
                    }
                }).done(function (res) {
                    if (res.data.length === 0) {
                        app1.dataIsNull = true
                    } else {
                        app1.dataIsNull = false
                    }
                    app1.showItems = res.data


                }).fail(function () {
                    console.log('请求发送失败')
                })
            }else{
                swal("请输入条件后重试！")
            }

        },
        //平台药品信息
        init2: function (page, showPagination) {
            this.selectDrugId = ''

            var searchKeyword = $("#dropSearch6").val();
            if (!$('#dropSearch1').val()) {
                $('#searchItemId1').val("")
            }
            var searchManufactureId = $('#searchItemId1').val();
            if(searchKeyword || searchManufactureId){
                $.ajax({
                    url: baseURL + '/compareDrugCode/list',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'keyword': searchKeyword,
                        'manufactureId': searchManufactureId
                    }
                }).done(function (res) {
                    if (res.data.length === 0) {
                        app1.dataIsNull2 = true
                    } else {
                        app1.dataIsNull2 = false
                    }
                    app1.showItems2 = res.data;
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }else{
                swal("请输入条件后重试！")
            }
        },
        //搜索
        searchInit: function () {
            this.init()
        },
        searchInit2: function () {
            this.init2()
        },
        selectName: function (drugId,insuranceId) {
            this.selectDrugId = drugId;
            console.log(this.selectDrugId)
            if(!insuranceId){
               this.showLinkedDrugCodePage = false
            }else{
                this.showItems3.drugBase = []
                this.showItems3.medicalInsurance =[]
                this.showLinkedDrugCodePage = true;
                $.ajax({
                    url: baseURL + '/compareMedicalInsurance/findCompared',
                    type: 'GET',
                    dataType: 'JSON',
                    data: {
                        'drugId': drugId
                    }
                }).done(function (res) {
                    app1.showItems3.drugBase = res.data.drugBase;
                    app1.showItems3.medicalInsurance = res.data.medicalInsurance;
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }


        },
        selectName2: function (medicalInsuranceId) {
            this.selectMedicalInsuranceId = medicalInsuranceId;
            console.log(this.selectMedicalInsuranceId)
        },
        //开始对码
        startComparedCode: function () {

            var selectMedicalInsuranceId = this.selectMedicalInsuranceId;
            var selectDrugId = this.selectDrugId;

            console.log(selectMedicalInsuranceId)
            console.log(selectDrugId)

            if (!selectMedicalInsuranceId || !selectDrugId) {
                swal('请选择数据后重试!')
            } else {
                $.ajax({
                    url: baseURL + '/compareMedicalInsurance/compareMedicalInsurance',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        'medicalInsuranceId': selectMedicalInsuranceId,
                        'drugId': selectDrugId
                    }
                }).done(function (res) {
                    if (res.code == 1) {
                        swal("对码成功")
                        app1.init();
                        app1.init2();
                        app1.comparedCodeCancel()
                    } else {
                        swal(res.msg)
                    }
                }).fail(function () {
                    console.log('请求发送失败')
                })
            }

        },
        //解除匹配/对码
        delCompare: function () {
            var selectDrugId = this.selectDrugId;

            $.ajax({
                url: baseURL + '/compareMedicalInsurance/delCompare',
                type: 'POST',
                dataType: 'JSON',
                data: {
                    'drugId': selectDrugId
                }
            }).done(function (res) {
                if (res.code == 1) {
                    swal("解除匹配成功");
                    app1.init();
                    app1.init2();
                    app1.showLinkedDrugCodePage = false;
                    app1.showItems3.drugBase = [];
                    app1.showItems3.medicalInsurance = [];
                } else {
                    swal(res.msg)
                }
            }).fail(function () {
                console.log('请求发送失败')
            })
        },
        //确定对码
        comparedCodeConfirm: function () {

        },
        //取消对码
        comparedCodeCancel: function () {
            this.showCoverPage = false;
            this.showComparedCodePage = false;
        },
        //项目收费等级
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
    }
});
