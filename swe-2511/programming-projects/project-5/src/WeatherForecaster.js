// Class: SWE2511 - Weather Forecaster
// Name: Alex Horton
// Class Section: 111

const weatherPointsAPI = "https://api.weather.gov/points";
let map
const iconLayer = L.layerGroup()


/**
 * displayError - Displays an error message
 * @param message - the message to display
 */
const displayError = (message) => {
    const errorMessage = document.getElementById("errorDisplay");
    const forecast = document.getElementById("forecast")
    const conditions = document.getElementById("conditionsContainer")

    forecast.classList.add("visually-hidden")
    conditions.classList.add("visually-hidden")
    errorMessage.innerText = message;
    errorMessage.classList.remove("visually-hidden");
}

/**
 * clearError - clears displaying of an error message
 */
const clearError = () => {
    const errorMessage = document.getElementById("errorDisplay");
    const forecast = document.getElementById("forecast")
    const conditions = document.getElementById("conditionsContainer")

    forecast.classList.remove("visually-hidden")
    conditions.classList.remove("visually-hidden")
    errorMessage.classList.add("visually-hidden");
    errorMessage.innerText = "";
}

const createMap = (coordinates) => {
    map = L.map('mapContainer').setView(coordinates, 13)

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        }).addTo(map)
}

const getEndpoints = async (coords) => {
    const response = await fetch(`${weatherPointsAPI}/${coords[0]},${coords[1]}`)
    if (response.ok) {
        clearError()
        const endpoints = await response.json()
        const forecastEndpoint = endpoints.properties.forecast
        const conditionsEndpoint = endpoints.properties.observationStations
        getConditions(conditionsEndpoint)
        getForecast(forecastEndpoint)
        getStations(conditionsEndpoint)
    } else {
        const responseData = await response.json()
        displayError(responseData.title)
    }
}

const getConditions = async (conditionsEndpoint) => {
    const response = await fetch(conditionsEndpoint)
    if (response.ok) {
        const stationsData = await response.json()
        const stationResponse = await fetch(`${stationsData.features[0].id}/observations/latest`)
        if (stationResponse.ok) {
            const conditionsData = await stationResponse.json()
            const name = conditionsData.properties.stationName
            const temp = conditionsData.properties.temperature.value
            const wind = conditionsData.properties.windSpeed.value
            const humidity = conditionsData.properties.relativeHumidity.value
            const image = conditionsData.properties.icon
            const current = conditionsData.properties.textDescription
            showConditions(name, current, temp, humidity, wind, image)
        }
    } else {
        displayError("Error getting current conditions")
    }
}

const getForecast = async (forecastEndpoint) => {
    const response = await fetch(forecastEndpoint)
    if (response.ok) {
        const responseData = await response.json()
        const forecast = responseData.properties.periods
        const region = responseData.geometry.coordinates[0]
        const regionName = "Milwaukee"
        showForecast(forecast, region, regionName)
    } else {
        displayError("Error getting forecast")
    }
}

const getStations = async (stationsEndpoint) => {
    const icon = new L.Icon({iconUrl: "cloud.png"})

    const response = await fetch(stationsEndpoint)
    if (response.ok) {
        const responseData = await response.json()
        const stationList = responseData.features
        stationList.forEach((e) => {
            const point = e.geometry.coordinates
            point.reverse()
            const marker = L.marker(point, {icon: icon})
            iconLayer.addLayer(marker).addTo(map)
        })
    } else {
        displayError("Error getting weather stations")
    }
}

const showConditions = (name, current, temp, humidity, wind, image) => {
    const icon = document.getElementById("currentConditionsImage")
    const stationName = document.getElementById("name")
    const currentConditions = document.getElementById("currentWeather")
    const tempF = document.getElementById("currentTempF")
    const tempC = document.getElementById("currentTempC")
    const relHumidity = document.getElementById("currentHumidity")
    const windSpeed = document.getElementById("currentWind")

    icon.src = image
    stationName.innerText = `Current Conditions - ${name}`
    currentConditions.innerText = `${current}`
    tempF.innerText = `${(temp*1.8 + 32).toFixed(2)}°F`
    tempC.innerText = `${temp}°C`
    relHumidity.innerText = `${humidity.toFixed(2)}%`
    windSpeed.innerText = `${wind} kph`
}

const showForecast = (forecast, regionBound, location) => {
    const containter = document.getElementById("forecastContainer")
    const region = document.getElementById("region")
    region.innerText = `Forecast for ${location}`
    forecast.forEach( (e) => {
        const card = document.createElement("div")
        card.classList.add("card")
        card.classList.add("forecastCard")
        card.classList.add("m-1")
        const image = document.createElement("img")
        image.src = e.icon
        image.classList.add("card-img-top")
        const body = document.createElement("div")
        body.classList.add("card-body")
        const title = document.createElement("h5")
        title.classList.add("card-title")
        title.innerText = e.name
        const temp = document.createElement("p")
        temp.classList.add("card-text")
        temp.classList.add("fw-bold")
        temp.innerText = `${e.temperature}°F`
        const forecast = document.createElement("p")
        forecast.classList.add("card-text")
        forecast.innerText = e.shortForecast
        body.appendChild(title)
        body.appendChild(temp)
        body.appendChild(forecast)
        card.appendChild(image)
        card.appendChild(body)
        containter.appendChild(card)
    })
    regionBound.forEach((e) => {
        e.reverse()
    })
    const polygon = L.polygon(regionBound)
    iconLayer.addLayer(polygon).addTo(map)
}

/**
 * window.onload - initializes the weather forecaster when the window loads
 */
window.onload = () => {
    const startPoint = [43.044240, -87.906446];// GPS lat/long location of MSOE athletic field
    createMap(startPoint)
    getEndpoints(startPoint)
    map.on("mouseup", () => {
        iconLayer.clearLayers()
        getEndpoints([ map.getCenter().lat, map.getCenter().lng ])
    })
    map.on("zoom", () => {
        iconLayer.clearLayers()
        getEndpoints([ map.getCenter().lat, map.getCenter().lng ])
    })
}