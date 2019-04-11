var app1 = new Vue({
    el: '#app-1',
    data: {
        items: [],
        items2: [],
        items3: [],
        pageNo: '',
        total: '',
        dataIsNull: false,
        dataIsNull2: false,
        dataIsNull3: false
    },
    created: function () {
        //初始化显示页面,默认为第一页
        this.init();
        this.init2();
    },
    methods: {
        //更新页面
        init: function () {
            $.ajax({
                url: baseURL + '/home/findAllWarning',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                if(res.data.length === 0){
                    app1.dataIsNull = true
                }else{
                    app1.dataIsNull = false
                };
                app1.items = res.data;
            }).fail(function () {
                console.log('init初始化失败! 请刷新重试!')
            })
        },
        init2: function () {
            $.ajax({
                url: baseURL + '/home/findAllNoDrugId',
                type: 'GET',
                dataType: 'JSON'
            }).done(function (res) {
                if(res.data.length === 0){
                    app1.dataIsNull2 = true
                }else{
                    app1.dataIsNull2 = false
                };
                app1.items2 = res.data;
            }).fail(function () {
                console.log('init初始化失败! 请刷新重试!')
            })
        },
        editItemGo: function (catalogId,itemStoreId,storeName) {
            window.location.href=baseURL+'web/operation/drugStock?itemStoreId='+itemStoreId+"&itemStoreName="+storeName+"&catalogId="+catalogId;
        },
        editItemGo2: function (itemId,itemStoreName,storeName) {
            window.location.href=baseURL+'/web/operation/drugCode?itemId='+itemId+"&itemStoreName="+storeName;
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
    $("#tableScroll2").scroll(function() {
        var scrollHeight =$('#tableScroll2').scrollTop() + "px";
        $('#fixedHead2').css({
            "position": "relative",
            "transform":"translateY("+scrollHeight+")"
        });
    });
    $("#tableScroll3").scroll(function() {
        var scrollHeight =$('#tableScroll3').scrollTop() + "px";
        $('#fixedHead3').css({
            "position": "relative",
            "transform":"translateY("+scrollHeight+")"
        });
    });
});