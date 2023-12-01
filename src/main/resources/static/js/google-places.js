$(document).ready(function () {
    let addressInput = document.getElementById('address');




    let addressOptions = {
        types: ['address'],
        componentRestrictions: {country: 'ua'}
    };


    let addressAutocomplete = new google.maps.places.Autocomplete(addressInput, addressOptions);


});


