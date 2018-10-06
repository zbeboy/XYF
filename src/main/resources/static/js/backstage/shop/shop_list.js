$(document).ready(function () {

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            edit: '/web/backstage/shop/edit'
        };
    }

    /**
     * 保存数据
     */
    $('#save').click(function () {
        sendAjax();
    });

    /**
     * 发送数据到后台
     */
    function sendAjax() {
        Messenger().run({
            progressMessage: '正在保存数据...'
        }, {
            url: web_path + getAjaxUrl().edit,
            type: 'post',
            data: $('#dataForm').serialize(),
            success: function (data) {
                Messenger().post({
                    message: data.msg,
                    type: data.state ? 'info' : 'error',
                    showCloseButton: true
                });
            },
            error: function (xhr) {
                if ((xhr != null ? xhr.status : void 0) === 404) {
                    return "请求错误";
                }
                return true;
            }
        });
    }

});