$(document).ready(function () {

    init();

    /**
     * 初始化数据
     */
    function init() {
        createPage('');
    }

    /**
     * 创建分页
     * @param data 数据
     */
    function createPage(data) {
        $('#pagination').pagination({
            pages: 100,
            displayedPages: 3,
            hrefTextPrefix: '',
            prevText:  '上一页' ,
            nextText:  '下一页',
            cssStyle: '',
            listStyle: 'pagination justify-content-end',
            onPageClick: function (pageNumber, event) {
                // Callback triggered when a page is clicked
                // Page number is given as an optional parameter
                // nextPage(pageNumber);
            }
        });
    }
});