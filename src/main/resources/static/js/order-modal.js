$(document).ready(function() {
    var url = window.location.href;
    if (url.indexOf('modal') != -1) {
        $('#exampleModal').modal('show');
    }
});