$(document).ready(function () {

    /*
   ajax url.
   */
    var ajax_url = {
        datas: '/data/goods/list',
        item: '/data/goods/item'
    };

    /*
    参数
   */
    var param = {
        pageNumber: 0,
        pageSize: 6,
        sortName: 'goods_serial',
        sortOrder: 'desc',
        extraSearch: ''
    };

    init();

    /**
     * 初始化数据
     */
    function init() {
        $.get(web_path + ajax_url.datas, param, function (data) {
            createPage(data);
            listData(data);
        });
    }

    /**
     * 列表数据
     * @param data 数据
     */
    function listData(data) {
        var template = Handlebars.compile($("#goods-template").html());
        Handlebars.registerHelper('goodsPic', function () {
            return new Handlebars.SafeString(Handlebars.escapeExpression(web_path + this.picUrl));
        });

        Handlebars.registerHelper('goodsRecommend', function () {
            return new Handlebars.SafeString(Handlebars.escapeExpression(recommend(this.goodsRecommend)));
        });

        Handlebars.registerHelper('goodsBrief', function () {
            var v = "";
            if (this.goodsBrief.length > 20) {
                v = this.goodsBrief.substring(0, 20) + "...";
            } else {
                v = this.goodsBrief;
            }
            return new Handlebars.SafeString(Handlebars.escapeExpression(v));
        });

        $('#goods').html(template(data));
    }

    function recommend(re) {
        var r = "";
        for (var i = 0; i < re; i++) {
            r += '★';
        }

        for (var j = re; j < 5; j++) {
            r += '☆';
        }
        return r;
    }

    /**
     * 创建分页
     * @param data 数据
     */
    function createPage(data) {
        $('#pagination').pagination({
            pages: getTotalPages(data),
            displayedPages: getDisplayedPages(data),
            hrefTextPrefix: '',
            prevText: '上一页',
            nextText: '下一页',
            cssStyle: '',
            listStyle: 'pagination justify-content-end',
            onPageClick: function (pageNumber, event) {
                // Callback triggered when a page is clicked
                // Page number is given as an optional parameter
                nextPage(pageNumber);
            }
        });
    }

    /**
     * 获取显示按钮数
     * @param data
     * @returns {*|number}
     */
    function getDisplayedPages(data) {
        var displayedPages = 0;
        if (data.iTotalDisplayRecords > 3 || data.iTotalDisplayRecords === 1) {
            displayedPages = 3
        } else {
            displayedPages = data.iTotalDisplayRecords
        }
        return displayedPages
    }

    /**
     * 获取总页数
     * @param data
     * @returns {number}
     */
    function getTotalPages(data) {
        return data.iTotalDisplayRecords % data.length === 0 ? data.iTotalDisplayRecords / data.length : Math.ceil(data.iTotalDisplayRecords / data.length);
    }

    /**
     * 下一页
     * @param pageNumber 当前页
     */
    function nextPage(pageNumber) {
        param.pageNumber = pageNumber;
        $.get(web_path + ajax_url.datas, param, function (data) {
            listData(data);
        });
    }

    $('.classify').click(function () {
        param.pageNumber = 0;
        if ($(this).hasClass('active')) {
            param.extraSearch = "";
            init();
            $(this).removeClass('active');
        } else {
            var searchParam = {
                classifyId: $(this).attr('data-id')
            };
            param.extraSearch = JSON.stringify(searchParam);
            init();
            var cl = $('#classifies').children();
            for (var i = 0; i < cl.length; i++) {
                $(cl[i]).removeClass('active');
            }
            $(this).addClass('active');
        }
    });

    $('#goods').delegate('.goods-item', "click", function () {
        $.get(web_path + ajax_url.item + '/' + $(this).attr('data-id'), function (data) {
            if(data.state){
                $('#itemTitle').text(data.goods.goodsName);
                $('#itemPic').attr('src',web_path + data.goods.picUrl);
                $('#itemPrice').text('￥' + data.goods.goodsPrice);
                $('#itemBrief').text(data.goods.goodsBrief);
                $('#itemRecommend').text(recommend(data.goods.goodsRecommend));
                $('#stars').text(data.goods.goodsRecommend + ' 星');
            } else {
                $('#itemInfo').html('未查询到商品信息');
            }

            $('#itemModal').modal('show');

        });

    });
});