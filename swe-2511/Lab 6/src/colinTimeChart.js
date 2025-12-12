// Class: SWE2511 - JavaScript Libraries
// Name: Colin Glynn
// Class Section: 111

const drawChart = () => {
    // ====== Chart 1: Colin Glynn ======
    const data1 = google.visualization.arrayToDataTable([
        ['Activity', 'Percentage of Time'],
        ['Sleeping', 25],
        ['Class', 20],
        ['Homework/Studying', 20],
        ['Eating', 15],
        ['Hanging with Friends', 10],
        ['Exercise', 10]
    ]);

    const options = {
        title: 'Colin Glynn - Average Week Breakdown',
        is3D: true,
        pieSliceText: 'percentage',
        slices: {
            0: {color: '#4a90e2'},
            1: {color: '#50e3c2'},
            2: {color: '#f5a623'},
            3: {color: '#d0021b'},
            4: {color: '#9013fe'},
            5: {color: '#7ed321'}
        },
        legend: {position: 'right', textStyle: {fontSize: 14}},
        backgroundColor: '#f0f4f8'
    };

    var chart1 = new google.visualization.PieChart(document.getElementById('colin_chart'));
    chart1.draw(data1, options);
}

window.onload = () => {
    google.charts.load('current', { packages: ['corechart'] });
    google.charts.setOnLoadCallback(drawChart);
}
