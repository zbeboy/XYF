$(document).ready(function () {
    var url = window.location.href;
    $('ul.navbar-nav a').filter(function () {
        return this.href === url;
    }).parent().addClass('active');
});
