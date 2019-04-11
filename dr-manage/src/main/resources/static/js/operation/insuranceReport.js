var app1 = new Vue({
    el: '#app-1',
    data: {
        searchData: {
            searchThreeDirectoryName: '',
            searchMedicalInsuranceCode: '',
            searchCollectLvl: ''
        },
        showItems: [],
        allGrades: [], //全部项目等级
        storeOutNumberSum: 0,
        pageNo: '',
        total: '',
        dataIsNull: false,
        showCoverPage: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.init(1, 1);
        this.findAllGrade()
    },
    methods: {
        //更新页面
        init: function (pageNo, showPage) {
            this.storeOutNumberSum = 0;

            var searchThreeDirectoryName = this.searchData.searchThreeDirectoryName;
            var searchMedicalInsuranceCode = this.searchData.searchMedicalInsuranceCode;
            var searchCollectLvl = this.searchData.searchCollectLvl;

            $("#loadingPage").show();
            $.ajax({
                url: baseURL + '/report/medicalInsurance/findAllInsuranceReport',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    'threeDirectoryName': searchThreeDirectoryName,
                    'medicalInsuranceCode': searchMedicalInsuranceCode,
                    'collectLvl': searchCollectLvl,
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
                    //汇总
                    var dataSum = res.data.list;
                    for(var i=0; i<dataSum.length; i++){
                        if(Number(dataSum[i].storeOutNumber)){
                            app1.storeOutNumberSum +=  Number(dataSum[i].storeOutNumber)
                        }
                    }
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
        //下载
        downLoadExcel: function () {
            var threeDirectoryName = this.searchData.searchThreeDirectoryName;
            var medicalInsuranceCode = this.searchData.searchMedicalInsuranceCode;
            var collectLvl = this.searchData.searchCollectLvl;
            window.location.href = baseURL + "/report/medicalInsurance/export?threeDirectoryName="+ threeDirectoryName +"&medicalInsuranceCode="+ medicalInsuranceCode +"&collectLvl="+ collectLvl
        },
        //点击搜索按钮
        searchItem: function () {
            this.init(1, 1)
        },
        //点击重置按钮
        resetSearch: function () {
           this.searchData.searchThreeDirectoryName = '';
           this.searchData.searchMedicalInsuranceCode = '';
           this.searchData.searchCollectLvl = '';
        },
        //全部项目等级
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
        }
    }
});
