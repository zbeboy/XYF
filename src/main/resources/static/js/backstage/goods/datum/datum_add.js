$(document).ready(function () {

    /*
     ajax url
    */
    var ajax_url = {
        classifies: '/web/backstage/goods/datum/classifies',
        file_upload_url: '/web/backstage/article/cover/upload',
        del_cover: '/web/backstage/article/cover/delete',
        save: '/web/backstage/article/save',
        back: '/web/backstage/article'
    };

    /*
     参数id
    */
    var paramId = {
        classifyId: '#classifyId',
        articleTitle: '#articleTitle',
        articleBrief: '#articleBrief',
        articleCover: '#articleCover',
        articleCoverTemp: '#articleCoverTemp',
        articleSources: '#articleSources',
        articleSourcesName: '#articleSourcesName',
        articleSourcesLink: '#articleSourcesLink',
        articleSn: '#articleSn'
    };

    /*
    参数
    */
    var param = {
        menuId: $(paramId.menuId).val(),
        articleTitle: $(paramId.articleTitle).val(),
        articleBrief: $(paramId.articleBrief).val(),
        articleCover: $(paramId.articleCover).val(),
        articleContent: '',
        articleSources: $("input[name='articleSources']:checked").val(),
        articleSourcesName: $(paramId.articleSourcesName).val(),
        articleSourcesLink: $(paramId.articleSourcesLink).val(),
        articleSn: $(paramId.articleSn).val()
    };

    init();

    function init() {
        initClassify();
    }

    function initClassify(){
        $.get(web_path + ajax_url.classifies, function (data) {
            classifyData(data);
        });
    }

    /**
     * 类别数据
     * @param data 数据
     */
    function classifyData(data) {
        var template = Handlebars.compile($("#classify-template").html());
        $(paramId.classifyId).append(template(data));
    }

});