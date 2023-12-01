$(document).ready(function() {
    let url = window.location.href;
    if (url.indexOf('modal') != -1) {
        $('#exampleModal').modal('show');
    }
});