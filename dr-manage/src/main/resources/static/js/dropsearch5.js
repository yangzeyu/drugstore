(function($) {
    var DropSearch5 = function(options) {
        this.searchType = options.searchType || 0;
        var self = this;
        this.inputField = $(options.selector);
        this.searchUrl = options.url;
        this.inputParam = options.inputParam;
        this.otherParams = options.otherParams;
        this.debounceTime = options.debounceTime;

        this.oUl = $("<ul class='drop-list' id='dropList5'></ul>")

        this.index = -1;
        this.ajaxType = options.ajaxType || "post";
        this.options = options;
        this.type = options.type || 0;

        var lock = false;

        this.inputField.on('input focus', debounce(function(e) {
            if (lock || !$(this).val()) return;
            self.renderList(self.getNameList());
        }, self.debounceTime)).on('compositionstart', function(e) {
            lock = true;
        }).on('compositionend', function(e) {
            lock = false;
            $(this).trigger('input');
        });

        $(document).on('keyup', function(e) {
            var keyCode = +e.keyCode;
            if (e.keyCode == 40 && self.oUl.children().length > 0) {
                self.doMove(1);
            } else if (e.keyCode == 38 && self.oUl.children().length > 0) {
                self.doMove(-1);
            } else if (e.keyCode == 13 && self.oUl.children().length > 0 && self.oUl.children().hasClass("active")) {
                $('#searchItemId5').val($('#dropList5 li.active').attr("value"))
                self.doInput(self.oUl.children().filter("[class='active']").html());
            }
        });

        this.oUl.on("click", function(e) {
            var e = e || window.event;
            e.stopPropagation();
            if (e.target.nodeName === "LI") {
                self.doInput(e.target.innerHTML)
            }
            $('#searchItemId5').val(e.target.getAttribute('value'))
        });
        $(document).on("click", function() {
            self.oUl.empty();
        });
    }

    DropSearch5.prototype.getNameList = function() {
        var self = this,
            list = [],
            param = [];
        param.push(this.inputParam + "=" + $.trim(this.inputField.val()));
        if (this.otherParams) {
            for (var item in this.otherParams) {
                param.push(item + "=" + this.otherParams[item]);
            }
        }
        $.ajax({
            type: self.ajaxType,
            url: self.searchUrl,
            data: param.join("&"),
            async: false,
            success: function(data) {
                self.options.ajaxHandler && self.options.ajaxHandler(data, list);
            }
        });
        return list;
    }


    DropSearch5.prototype.renderList = function(list) {
        var list = list.slice(0,10);
        if (!list || !list.length) {
			this.oUl.empty();
			return;
		}
        this.index = -1;
        var self = this,
            html = [];
        self.oUl.empty();

        $.each(list, function(i) {
                html += "<li value="+ list[i].medicalInsuranceCode +">" + list[i].threeDirectoryName + "</li>";
        });


        self.oUl.append(html);
        self.inputField.parent().append(self.oUl).css("position", "relative");
        self.oUl.css({
            top: self.inputField.outerHeight() + 2,
            left: self.inputField.get(0).offsetLeft,
            width: self.inputField.outerWidth()
        });
    }

    DropSearch5.prototype.doMove = function(count) {
        var aLi = this.oUl.children();
        this.index = this.index + count;
        if (this.index > aLi.length - 1) {
            this.index = 0;
        } else if (this.index < 0) {
            this.index = aLi.length - 1;
        }
        aLi.removeClass("active").eq(this.index).addClass("active");
    }

    DropSearch5.prototype.doInput = function(data) {
        this.inputField.val(data);
        this.oUl.empty();
        if (this.options.clickHandler) {
            this.options.clickHandler(data);
        }
    }

    DropSearch5.init = function(options) {
        new this(options);
    };

    function debounce(fn, interval) {
        var timeout = null;
        interval = interval || 300;
        return function() {
            var self = this;
            clearTimeout(timeout);
            timeout = setTimeout(function() {
                fn.apply(self, arguments);
            }, interval);
        };
    }
    window["DropSearch5"] = DropSearch5;
})(jQuery);