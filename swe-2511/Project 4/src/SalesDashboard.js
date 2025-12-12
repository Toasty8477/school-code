// Class: SWE2511 - Sales Dashboard
// Name: YOUR NAME HERE
// Class Section: YOUR SECTION HERE

/**
 * chartSetup
 * Set up the Google chart properties and other page events
 */
const chartSetup = () => {

    // Initialize the Google chart package
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(createDefaultDisplay);
};

/**
 * createDefaultDisplay
 * Create the "default" page - display all data with no filters
 */
const createDefaultDisplay = () => {
    // Make sales line chart
    makeLineGraph(true, true, true, true)
    // Make category revenue chart
    makePieChart(0)
    // Make salesperson table
    makeTable(undefined, "")
    
    makeSlider()

    const rangeSlider = document.getElementById("range")
    rangeSlider.addEventListener('input', () => {
        document.getElementById("rangeLabel").innerText = `Showing Categories with Revenue Above $${range.value}`
        makePieChart(Number.parseInt(range.value))
    })

    const q1 = document.getElementById("q1")
    const q2 = document.getElementById("q2")
    const q3 = document.getElementById("q3")
    const q4 = document.getElementById("q4")
    q1.addEventListener('input', () => {
        makeLineGraph(q1.checked, q2.checked, q3.checked, q4.checked)
    })
    q2.addEventListener('input', () => {
        makeLineGraph(q1.checked, q2.checked, q3.checked, q4.checked)
    })
    q3.addEventListener('input', () => {
        makeLineGraph(q1.checked, q2.checked, q3.checked, q4.checked)
    })
    q4.addEventListener('input', () => {
        makeLineGraph(q1.checked, q2.checked, q3.checked, q4.checked)
    })

    const category = document.getElementById("category")
    const name = document.getElementById("name")
    const region = document.getElementById("region")
    const revenue = document.getElementById("revenue")
    const saleSize = document.getElementById("saleSize")
    const performance = document.getElementById("performance")
    const filter = document.getElementById("filter")
    let filterCategory = undefined
    const apply = document.getElementById("apply")

    name.addEventListener('click', () => {
        category.innerText = "Name"
        filterCategory = "name"
    })
    region.addEventListener('click', () => {
        category.innerText = "Region"
        filterCategory = "region"
    })
    revenue.addEventListener('click', () => {
        category.innerText = "Revenue"
        filterCategory = "revenue"
    })
    saleSize.addEventListener('click', () => {
        category.innerText = "Sale Size"
        filterCategory = "sale"
    })
    performance.addEventListener('click', () => {
        category.innerText = "Performance"
        filterCategory = "performance"
    })
    apply.addEventListener('click', () => {
        makeTable(filterCategory, filter.value)
    })
};

const makeLineGraph = (q1, q2, q3, q4) => {
    let months = []
    if (q1) {
        months.push("January")
        months.push("Febuary")
        months.push("March")
    }
    if (q2) {
        months.push("April")
        months.push("May")
        months.push("June")
    }
    if (q3) {
        months.push("July")
        months.push("August")
        months.push("September")
    }
    if (q4) {
        months.push("October")
        months.push("November")
        months.push("December")
    }

    let salesData = new google.visualization.DataTable()
    salesData.addColumn('string', 'X')
    salesData.addColumn('number', 'Actual Revenue')
    salesData.addColumn('number', 'Target Revenue')

    monthlySales.forEach((e) => {
        if (months.includes(e.month)) {
            salesData.addRows([
                [e.month, e.revenue, e.target]
            ])
        }
    })

    let salesOptions = {
        title: "Monthly Revenue Performance",
        height: 300,
        legend: { position: 'top' },
        hAxis: {
            title: 'Month'
        },
        vAxis: {
            title: 'Revenue ($)',
            format: 'currency',
            gridlines: {count: 4}
        }
    }

    let lineChart = new google.visualization.LineChart(document.getElementById('lineChart'))
    lineChart.draw(salesData, salesOptions)
}

const makePieChart = (min) => {
    let categoryData = new google.visualization.DataTable()
    categoryData.addColumn('string', 'Category')
    categoryData.addColumn('number', 'Revenue')

    productCategories.forEach((e) => {
        if (e.revenue > min) {
            categoryData.addRows([
                [e.category, e.revenue]
            ])
        }
    })

    let categoryOptions = {
        title: "Revenue by Product Category",
        pieHole: 0.4,
        height: 300
    }
    let pieChart = new google.visualization.PieChart(document.getElementById('pieChart'))
    pieChart.draw(categoryData, categoryOptions)
}

const makeTable = (filterCategory, filter) => {
    const table = document.getElementById("body")
    table.innerHTML = ""
    topSalespeople.forEach((e) => {
        if (filterCategory === undefined || filter === "") {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        } else if (filterCategory == "name" && e.name.toLowerCase().includes(filter.toLowerCase())) {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        } else if (filterCategory == "region" && e.region.toLowerCase().includes(filter.toLowerCase())) {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        } else if (filterCategory == "revenue" && e.revenue.toString().toLowerCase().includes(filter.toLowerCase())) {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        } else if (filterCategory == "sale" && e.avgDealSize.toString().toLowerCase().includes(filter.toLowerCase())) {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        } else if (filterCategory == "performance" && e.performance.toString().toLowerCase().includes(filter.toLowerCase())) {
            table.innerHTML = table.innerHTML +
            `<tr>
                <td>${e.name}</td>
                <td>${e.region}</td>
                <td>$${e.revenue}</td>
                <td>$${e.avgDealSize}</td>
                <td>${e.performance}%</td>
            </tr>`
        }
    })
}

const makeSlider = () => {
    const range = document.createElement("input")
    range.type = "range"
    range.id = "range"
    range.min = "0"
    range.max = "1000000"
    range.classList.add("form-range")

    const rangeLabel = document.createElement("label")
    rangeLabel.for = "range"
    rangeLabel.id = "rangeLabel"
    rangeLabel.classList.add("form-label")
    const rangeLabelLabel = document.createTextNode(`Showing Categories with Revenue Above $${range.value}`)
    rangeLabel.appendChild(rangeLabelLabel)
    document.getElementById("categoryCard").appendChild(rangeLabel)
    document.getElementById("categoryCard").appendChild(range)
}

window.onload = () => {
    chartSetup();
};
