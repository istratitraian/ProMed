var mapOptions = {
    center: {lat: 45, lng: 27},
    //                center: {lat: 0, lng: 0},
    zoom: 2,
    mapTypeId: google.maps.MapTypeId.ROADMAP
};

var map = new google.maps.Map(document.getElementById('map'), mapOptions);


var infoWindow = new google.maps.InfoWindow({content: "info window!"});


// Try HTML5 geolocation.

function setCurentLocation() {

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude

            };

            var markerOptions = {
                position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
            };

            new google.maps.Marker(markerOptions).setMap(map);

            map.setCenter(pos);
            map.setZoom(16);

//            document.getElementById('map_street').value = map.
            infoWindow.setPosition(pos);
            infoWindow.setContent('Locatia Ta?');
            infoWindow.open(map);

            //                    function(){
            //                        
            //                    };



        }, function () {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        map.setPosition(pos);
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
                'Error: The Geolocation service failed.' :
                'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
    }
}

