// Class: SWE2511 - JavaScript Libraries
// Names: Colin Glynn and Alex Horton
// Class Section: 111

const drawChart = () => {
    const data = new google.visualization.DataTable();
    data.addColumn('number', 'Year');
    data.addColumn('number', '# of Wolves');
    data.addColumn('number', '# of Moose');
    data.addRow([1980, 50, 664]);
    data.addRow([1981, 30, 650]);
    data.addRow([1982, 14, 700]);
    data.addRow([1983, 23, 900]);
    data.addRow([1984, 24, 811]);
    data.addRow([1985, 22, 1062]);
    data.addRow([1986, 20, 1025]);
    data.addRow([1987, 16, 1380]);
    data.addRow([1988, 12, 1653]);
    data.addRow([1989, 11, 1397]);
    data.addRow([1990, 15, 1216]);
    data.addRow([1991, 12, 1313]);
    data.addRow([1992, 12, 1600]);
    data.addRow([1993, 13, 1880]);
    data.addRow([1994, 15, 1800]);
    data.addRow([1995, 16, 2400]);
    data.addRow([1996, 22, 1200]);
    data.addRow([1997, 24, 500]);
    data.addRow([1998, 14, 700]);
    data.addRow([1999, 25, 750]);
    data.addRow([2000, 29, 850]);
    data.addRow([2001, 19, 900]);
    data.addRow([2002, 17, 1000]);
    data.addRow([2003, 19, 900]);
    data.addRow([2004, 29, 750]);
    data.addRow([2005, 30, 540]);
    data.addRow([2006, 30, 385]);
    data.addRow([2007, 21, 450]);
    data.addRow([2008, 23, 650]);
    data.addRow([2009, 24, 530]);
    data.addRow([2010, 19, 510]);
    data.addRow([2011, 16, 515]);
    data.addRow([2012, 9, 750]);
    data.addRow([2013, 8, 975]);
    data.addRow([2014, 9, 1050]);
    data.addRow([2015, 3, 1250]);
    data.addRow([2016, 2, 1300]);
    data.addRow([2017, 2, 1600]);
    data.addRow([2018, 2, 1500]);
    data.addRow([2019, 14, 2060]);

    // options for the chart
    const options = {
        width: 1200,
        height: 400,
        legend: 'top',
        title: 'Wolves and Moose on Isle Royale',
        chartArea: { width: '90%', height: '80%'},
        vAxis: {
            scaleType: 'log',
        },
        hAxis: {
            format: '',
        },
    };

    // get the div
    const chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
    chart.draw(data, options);
};

window.onload = () => {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
};