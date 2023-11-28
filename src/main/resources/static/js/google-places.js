$(document).ready(function () {
    var addressInput = document.getElementById('address');




    var addressOptions = {
        types: ['address'],
        componentRestrictions: {country: 'ua'}
    };


    var addressAutocomplete = new google.maps.places.Autocomplete(addressInput, addressOptions);


});


