const loadMap = () => {
    var map = L.map('map').setView([51.505, -0.09], 13)

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(map)

    var marker = L.marker([51.5, -0.09]).addTo(map)
    
    var circle = L.circle([51.508, -0.11], {
    color: 'red',
    fillColor: '#f03',
    fillOpacity: 0.5,
    radius: 500
    }).addTo(map)

    var polygon = L.polygon([
    [51.509, -0.08],
    [51.503, -0.06],
    [51.51, -0.047]
    ]).addTo(map)

    marker.bindPopup("<b>Hello world!</b><br>I am a popup.").openPopup()
    circle.bindPopup("I am a circle.")
    polygon.bindPopup("I am a polygon.")

    var popup = L.popup()
        .setLatLng([51.513, -0.09])
        .setContent("I am a standalone popup.")
        .openOn(map);

    var LeafIcon = L.Icon.extend({
        options: {
            shadowUrl: '/src/leaflet-icons/leaf-shadow.png',

            iconSize: [38, 95],
            shadowSize: [50, 64],
            iconAnchor: [22, 94],
            shadowAnchor: [4, 62],
            popupAnchor: [-3, -76]
        }
    })

    var greenIcon = new LeafIcon({iconUrl: '/src/leaflet-icons/leaf-green.png'}),
        redIcon = new LeafIcon({iconUrl: '/src/leaflet-icons/leaf-red.png'}),
        orangeIcon = new LeafIcon({iconUrl: '/src/leaflet-icons/leaf-orange.png'})

    L.marker([51.5, -0.09], {icon: greenIcon}).addTo(map).bindPopup("I am a green leaf.")
    L.marker([51.495, -0.083], {icon: redIcon}).addTo(map).bindPopup("I am a red leaf.")
    L.marker([51.49, -0.1], {icon: orangeIcon}).addTo(map).bindPopup("I am an orange leaf.")

    const onMapClick = (e) => {
        popup
            .setLatLng(e.latlng)
            .setContent(`You clicked the map at ${e.latlng.toString()}`)
            .openOn(map)
    }

    map.on('click', onMapClick)
}

window.addEventListener("DOMContentLoaded", () => {
    loadMap()
})