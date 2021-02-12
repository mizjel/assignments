function setupMap(){
    var layer = new L.StamenTileLayer("terrain");
    var map = new L.Map("discoverablemap", {
        center: new L.LatLng(52.1026406, 5.175044799999999),
        zoom: 10
    });
    map.whenReady(function(){ addMarkers(this); });
    map.addLayer(layer);
}
async function addMarkers(map){

    let attractions = await getAttractions();
    attractions.forEach(function(item, index){ addMarker(item, map); });
}
function addMarker(attraction, map){
    var marker = L.marker([attraction.location.lat, attraction.location.lon]).addTo(map);
    marker.bindPopup("<b>" + attraction.name + "</b><br>" + attraction.description).openPopup();
}
async function getAttractions(){
    let attractionsJson;
    try{
        let response = await fetch("api/attractions");
        attractionsJson = await response.json();
    } catch(ex){
        console.error(ex);
    }
    return Object.assign(attractionsJson);
}