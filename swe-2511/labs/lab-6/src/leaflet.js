// Class: SWE2511 - JavaScript Libraries
// Names: YOUR NAMES HERE
// Class Section: YOUR SECTION HERE

let map
const markerLayer = L.layerGroup()

const createMap = () => {
    map = L.map('map_div').setView([51.505, -0.09], 13)

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        }).addTo(map)
}

const addCenterMarker = () => {
    const marker = L.marker(map.getCenter())
    markerLayer.addLayer(marker).addTo(map)
}

const removeMarkers = () => {
    markerLayer.clearLayers()
}

window.addEventListener( "DOMContentLoaded", () => {
    const centerText = document.getElementById('latlogcenter')
    createMap()
    document.getElementById('addmarker').onclick = addCenterMarker
    document.getElementById('clear').onclick = removeMarkers
    map.on("mouseup", () => {
        centerText.innerHTML = `${map.getCenter().lat}, ${map.getCenter().lng}`
    })
    map.on("zoom", () => {
        centerText.innerHTML = `${map.getCenter().lat}, ${map.getCenter().lng}`
    })
})