## Introduction

The purpose of this assignment is to guide you through the creation of charts and maps using the Google Charts and the Leaflet JavaScript libraries.

For this assignment, may be asked to answer questions, perform research, and/or write code.

- Provide complete answers to all written questions.
- When asked for examples, be specific.
- When asked to perform research, cite your sources.
- Submit your answers in a document separate from code.

Include source files for all code in your submission.  Follow good styling and provide complete documentation (comment blocks, inline comments for complicated code, etc.).

Work on the assignment is to be done with ***your assigned group***.  You are welcome to collaborate with class members, but the submitted assignment must be the work of only your group.

***NOTE:*** You are responsible for being able to perform all material for this assignment.  It may make sense to "divide and conquer" the assignment, but make sure *all* group members understand *all* items in the assignment.

## Background

There are a lot of third party libraries provided by developers that allow us to focus our web applications on overall function and user experience rather than the detailed logic of how web application components work.  Graphical visualization is one such component that can help users 'see' data rather than just reading it from a table.

Google Charts ([https://developers.google.com/chart](https://developers.google.com/chart)) is a JavaScript library for charting data.  The library draws charts using Scalable Vector Graphics (SVG) and also allow the user to interact with chart in several ways.  The library supports several chart types.  For a complete list of all charts available through Google Charts: [https://developers.google.com/chart/interactive/docs](https://developers.google.com/chart/interactive/docs)

Leaflet ([https://leafletjs.com/](https://leafletjs.com/)) is a free JavaScript library for displaying and interacting with maps.  It lets you display a map at a particular location, add pinpoints, zoom in and out, etc.  However, as a user of the library you are required to provide your own map image.  For that, we will use OpenStreetMap ([https://www.openstreetmap.org/](https://www.openstreetmap.org/)) which is free open source map data.

This assignment will guide you through the creation of a Google chart, then ask you to create your own.  Finally, you will work with Leaflet to create a map and work with pinpoints. 

## Google Charts

### Installation and setup

The Google Charts library is used though Google's content delivery network (CDN).  A CDN is a way to provide external libraries to web applications without requiring the application to download and host the library itself.

Importing the Google charts library from their CDN requires adding a couple lines to our HTML header.  The following HTML imports the Google charts from the CDN.

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Google Column Chart Exercise</title>

        <!-- Include the Google bar chart library -->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    </head>
</html>
```

### Additional Setup

To create a chart using the library we need to include some additional elements:

1. A script containing configuration of the library and the logic needed to create the chart
2. Additional HTML elements to use for the chart.

At the very minimum the Google Charts library requires a single ```<div>``` element with an identifier (id).  The library will create additional children of this element in the document to draw the chart SVG.

This can be done by creating a new JavaScript file (```mychart.js```) then adding the script using ```<script>``` to the 'index.html' in the ```<head>```.

```html
<script type="text/javascript" src="mychart.js"></script>
```

This code will act as the JavaScript 'controller' code for your chart.

Next, the ```<body>``` containing the ```<div>``` for the chart needs to be added to the 'index.html'.

```html
<body>
    <h2>My Column Chart</h2>
    <div id="chart_div"></div>
</body>
```

This will create a heading to label the chart and more importantly a ```<div>``` tag that the Google Chart library will use to display the chart.  Your JavaScript 'controller' code will tell the Google library which identifier (```id``` for the ```<div>``` tag) to use to draw the chart.  Without this, the Google chart library will not be able to create the chart.

The following files have been provided for you in your repository:

- [src/mychart.html](src/mychart.html) - HTML file that includes the import of the Google Charts library.  Use this as a starter for your forms page.  It also includes the necessary body to provide Google charts with the element to draw the chart.
- [src/mychart.js](src/mychart.js) - JavaScript file to use for the code to control the Google chart.

***NOTE:*** Include additional JS and CSS file(s) if needed.  At the top of ***EACH SOURCE FILE*** include a comment block with your name, assignment name, and section number.

## Loading the Chart Library 

The Google Charts library CDN only imports the 'loader' for the library itself.  Instead of including the all the code for the library, the loader contains functions available to load the library dynamically from Google.  To use the library functions, the code must first be loaded.  To load the Google Chart library function you use the following JavaScript:

```javascript
google.charts.load('current', {packages: ['corechart']});
```

This loads the 'current' version of the 'corechart' library which is what we need.  This load happens asynchronously which means that the 'load' function returns before the library functions are loaded.  In order to use the library you need to wait for the load to finish.  The Google loader notifies your code that the load has finished by using a callback function.  So, you'll need to make a callback function and tell that to Google chart loader.

To tell the Google chart loader to load the chart when your page loads and specify a callback function, use the following:

```javascript
const drawChart = () => {
    // TODO: add the code to draw the chart here
};

window.onload = () => {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
};
```

***NOTE:*** Since Google Charts is an external library, WebStorm might give you some warnings about undefined values.  You can ignore them since the variables will be available when the library is loaded from Google.

More information on the Google Chart loading can be found in the Google Chart documentation: [https://developers.google.com/chart/interactive/docs/basic_load_libs](https://developers.google.com/chart/interactive/docs/basic_load_libs)

### Preparing the Chart Data

Google Charts requires all data to be contained with in a DataTable JavaScript object.  This object is similar to a database table.  In short, the table is a 2-dimensional array of elements where the elements themselves have a type and an optional label.

For example, let's use the population of wolves and moose on Isle Royal: [https://www.nps.gov/isro/learn/nature/wolf-moose-populations.htm](https://www.nps.gov/isro/learn/nature/wolf-moose-populations.htm) from 1980 to 2019.

| Year | # of Wolves | # of Moose |
|:----:|:-----------:|:----------:|
| 1980 |     50      |    664     |
| 1981 |     30      |    650     |
| 1982 |     14      |    700     |
| 1983 |     23      |    900     |
| 1984 |     24      |    811     |
| 1985 |     22      |    1062    |
| 1986 |     20      |    1025    |
| 1987 |     16      |    1380    |
| 1988 |     12      |    1653    |
| 1989 |     11      |    1397    |
| 1990 |     15      |    1216    |
| 1991 |     12      |    1313    |
| 1992 |     12      |    1600    |
| 1993 |     13      |    1880    |
| 1994 |     15      |    1800    |
| 1995 |     16      |    2400    |
| 1996 |     22      |    1200    |
| 1997 |     24      |    500     |
| 1998 |     14      |    700     |
| 1999 |     25      |    750     |
| 2000 |     29      |    850     |
| 2001 |     19      |    900     |
| 2002 |     17      |    1000    |
| 2003 |     19      |    900     |
| 2004 |     29      |    750     |
| 2005 |     30      |    540     |
| 2006 |     30      |    385     |
| 2007 |     21      |    450     |
| 2008 |     23      |    650     |
| 2009 |     24      |    530     |
| 2010 |     19      |    510     |
| 2011 |     16      |    515     |
| 2012 |      9      |    750     |
| 2013 |      8      |    975     |
| 2014 |      9      |    1050    |
| 2015 |      3      |    1250    |
| 2016 |      2      |    1300    |
| 2017 |      2      |    1600    |
| 2018 |      2      |    1500    |
| 2019 |     14      |    2060    |

The data has 3 'columns': the year, the number of wolves, and the number of moose.

To create a DataTable for our data we need to add a column for each data type and then add the rows.

To add the columns (data types) use the addColumn function:

```javascript
const data = new google.visualization.DataTable();
data.addColumn('number', 'Year');
data.addColumn('number', '# of Wolves');
data.addColumn('number', '# of Moose');
```

***NOTE:*** the first parameter to the addColumn function is the data type; the second is the label.

To add the rows (data values) use the addRow function:

```javascript
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
```

Add this code to your ```drawChart``` function in ```mychart.js```.

***NOTE:*** the addRow takes an array as a parameter which indicates the value for each column in the row.

***NOTE:*** We will be creating a Column chart from this data.  Since there are 3 values in each row, Google Charts will automatically convert this to 2 separate data series.

## Customizing the Chart

A Google Chart can be customized for labeling, color formatting, spacing, etc. using an ```options``` object.

The options are declared as a literal JavaScript associative object with key/value pairs to specify what option is being set and what value to use.

For example the options object, to set the chart to:

- 1200 pixels wide by 400 pixels tall
- force the legend to be on top of the chart
- have a title of "Wolves and Moose on Isle Royale"
- set the spacing so the chart takes up 90% of the width and 80% of the height

would look like this:
```javascript
const options = {
    width: 1200,
    height: 400,
    legend: 'top',
    title: 'Wolves and Moose on Isle Royale',
    chartArea: { width: '90%', height: '80%'},
};
```

The options parameter is optional.

Add this options parameter to your ```drawChart``` function in ```mycharts.js```.

There are a lot of other customizations you can make to a Google Chart.  To find out more see the Google Charts documentation on chart customization: [https://developers.google.com/chart/interactive/docs/customizing_charts](https://developers.google.com/chart/interactive/docs/customizing_charts)

## Drawing and Interacting with the Chart

To draw a Google Chart you must tell the chart library which HTML element to use (e.g. which ```div``` tag) and what type of chart you want.

For our example the element is 'chart_div' and the chart type is a column chart.  For that, the JavaScript looks like this:
```javascript
const chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
chart.draw(data, options);
```

Add this code to your ```drawChart``` function in ```mychart.js```

If all worked correctly, you should get a page that looks something like this:
![Wolves and Moose Column Chart - Version 1!](chart1.png)

Now that your chart is drawn, try interacting with it:

- Hover your mouse over a bar
- Click on a bar
- Hover your mouse over the legend
- Click on one of the data sets in the legend

Experiment with other interactions (if any) you can make with the chart.

This is neat, but there are a couple problems with readability:

1. You can't really see the wolves data since the number of wolves is so much smaller than the number of moose
2. The year on the bottom is displayed like a number with a comma separating 3 digits from the right.

We can fix these by customizing the axes using the options object.

To fix the display of the wolves data we can set the scale of the vertical axis to be a [log scale](https://en.wikipedia.org/wiki/Logarithmic_scale).  To do that, set the vAxis option for scaleType.  The resulting options object would look like this:

```javascript
const options = {
    width: 1200,
    height: 400,
    legend: 'top',
    title: 'Wolves and Moose on Isle Royale',
    chartArea: { width: '90%', height: '80%'},
    vAxis: {
        scaleType: 'log',
    },
};
```

Notice that the vAxis is its own object in itself which has its own set of key/value pairs.

Additional information on axis scaling can be found on the Google Charts documentation on axis customization: [https://developers.google.com/chart/interactive/docs/customizing_axes#axis-scale](https://developers.google.com/chart/interactive/docs/customizing_axes#axis-scale).

To fix way the year is printed we can change the formatting of the horizontal axis.  To do that, set the hAxis format option.  The resulting options object (including the vAxis option) would look like this: 

```javascript
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
```

Notice that the format in the hAxis option is blank.  This clears all formatting.

Additional information on number formatting can be found on the Google Charts documentation on axis customization: [https://developers.google.com/chart/interactive/docs/customizing_axes#number-formats](https://developers.google.com/chart/interactive/docs/customizing_axes#number-formats).

Make these updates to the options object in your ```drawChart``` function, and you should now see a chart that looks like this:
![Wolves and Moose Column Chart - Version 2!](chart2.png)

***SUBMISSION REQUIREMENT:*** Include the completed Wolves and Moose Column Chart with the updated log scale axes in your submission.

### Create a Time Chart

***SUBMISSION REQUIREMENT:*** Each member of the group must complete this section for own schedule.  You are welcome to put both charts in the same HTML file, however make sure ***EACH*** pie chart is labeled ***EACH*** member's name.

Now that you are familiar with creating a column chart, you will now create a [pie chart](https://developers.google.com/chart/interactive/docs/gallery/piechart) showing how you spend your time for an **average week**.  Your pie chart must have at least **5 different categories** and your time must total 100% of your week.

Here are some sample categories:

- Eating
- Sleeping
- Class
- Homework
- Studying
- Commuting
- Video Games
- Reading
- Drawing
- Web Surfing

In addition, your pie chart should have at least **4 different customization options**.

The files [src/piechart.html](src/piechart.html) and [src/piechart.js](src/piechart.js) have been given to get you started on your pie chart.

Additional styling via custom CSS is optional.  If you do choose to use additional styling, make sure you include this in a separate ```.css``` file.

## Leaflet 

Leaflet ([https://leafletjs.com/](https://leafletjs.com/)) is a free JavaScript library for displaying and interacting with maps.  It lets you display a map at a particular location, add pinpoints, zoom in and out, etc.

In this section you will read through several tutorials for the Leaflet library.  The programming interface is not difficult, but takes some practice to get used to it.

### Leaflet Tutorials

Complete the following tutorials.  For each tutorial create a web application following the instructions.

***SUBMISSION REQUIREMENT:*** Include the code you created as part of your submission.

- Leaflet Quick Start Guide - [https://leafletjs.com/examples/quick-start](https://leafletjs.com/examples/quick-start)
- Markers With Custom Icons - [https://leafletjs.com/examples/custom-icons](https://leafletjs.com/examples/custom-icons)

***NOTE:*** Some tutorials provide complete working solutions to the examples.  Feel free to use this code for reference but do not copy the code directly.  Your submission should be your own work.

### Leaflet Map

Now that you are familiar with Leaflet, complete the JavaScript file [src/leaflet.js](src/leaflet.js) to create an interactive map.

The map web application must perform the following:

- When the user moves ***OR*** zooms the map, the page must update with the latitude and longitude of the center of map.
- When the user clicks on the "Add Center Marker" button, a marker pinpoint must be added to the map at the center of the map.
- When the user clicks on the map, but does not move or zoom the map view, a pinpoint marker must be added to the map at the point the user clicked on.
- When the user clicks on the "Clear Markers" button, ***ALL*** existing markers must be removed
- Your map markers must be customized in some way.  Choose an interesting image or icon or create your own.  When using an image from the web make sure you cite your sources.

The following files are also given to get you started:
- [src/leaflet.html](src/leaflet.html) - HTML file for the map app structure
- [src/leaflet.css](src/leaflet.css) - Stylesheet for the map app, sets up the needed styling for the leaflet map and spacing for menu items.

Feel free to add additional styling or HTML components as necessary.  However, make sure the required functionality is implemented at a minimum.

***SUBMISSION REQUIREMENT:*** Include the code you created as part of your submission.

## Research

### Charting Research

Google Charts and Leaflet are, arguably, popular libraries available on to web for charting and mapping, there are many others.

Research charting, mapping, ***AND/OR*** other visualization libraries and find one that interests you:
- Describe how to install the library (CDN or direct download or other)
- What features types does it support?
- What advantages and disadvantages do you see the library having over libraries (Google Charts, Leaflet, etc.).
- Provide your own example using the library
  - ***NOTE:*** The example should include actual code, not just a screen capture.

## Vibe With Me

Vibe coding ([https://www.merriam-webster.com/slang/vibe-coding/](https://www.merriam-webster.com/slang/vibe-coding/)) is the process of using an AI to assist in code creation.  Large language model tools such as Claude, GPT, and others have become increasingly sophisticated and able to perform all sorts of coding tasks, especially in the world of website creation.

Choose a large language model (there are several) and use it to assist creating a website that uses charts the wolf data.   Consider the following in your prompt to the language model:

- Asking it to use a different charting library than Google charts
- Having the language model draw its own chart
- Using different axis scales or charts other than a bar chart

Include the generated code in your submission along with a file that answers the following:

- What is the name (and who is the creator) of the language model that you used?
- Critique the code, what problems do you see with what was generated?  Are there any bugs?  If so, how would you fix them?
- Run the generated page through the HTML and CSS validator
    - What mistakes did the model make?
    - What can be done to fix them?
    - What sorts of refinements can be done to your prompts to help the language model create a better looking site with fewer bugs?
- What are the exact prompts (including your revisions) that you used to create your code?

## Deliverables

When you are ready to submit your assignment prepare your repository:

- Make sure your name, assignment name, and section number are in comments on ALL submitted files.
- Make sure you have completed all activities and answered all questions.
- Make sure you cite your sources.
- Make sure your assignment code is commented thoroughly.
- Include in your submission, a set of suggestions for improvement and/or what you enjoyed about this assignment.
- Make sure all files are committed and pushed to the main branch of your repository.

***NOTE***: Do not forget to 'add', 'commit', and 'push' all new files and changes to your repository

### Additional Submission Notes

If/when using resources from material outside what was presented in class (e.g., Google search, Stack Overflow, etc.) document the resource used in your submission.  Include exact URLs for web pages where appropriate.

***NOTE:*** Sources that are not original research and/or unreliable sources are not to be used.  For example:

- Wikipedia is not a reliable source, nor does it present original research: [https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source](https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source)
- Large language models are not reliable sources: [https://stackoverflow.blog/2025/06/30/reliability-for-unreliable-llms/](https://stackoverflow.blog/2025/06/30/reliability-for-unreliable-llms/)

***NOTE:*** Except for "Vibe With Me", large language models should not be used for any part of this assignment.

For more information, please see the [MSOE CS Code of Conduct](https://msoe.s3.amazonaws.com/files/resources/swecsc-computing-code-of-conduct.pdf).

## Grading Criteria (50 Points)

- (5 Points) Submitted files and code structure
  - Submitted files follow submission guidelines
  - Files are contain name, assignment, section
  - Sources outside of course material are cited
  - Readable code/file structure
  - Code is well documented
  - Code passes the HTML validator without errors
  - Code passes the CSS validator without errors
  - HTML contains only structure - no logic code or styling
- (5 Points) Suggestions
  - List of suggestions for improvement and/or what you enjoyed about this assignment
- (20 Points) Google Charts
  - (10 Points) Wolves and Moose Chart
  - (10 Points) Time Chart
- (20 Points)  Leaflet
  - (10 Points) Leaflet Tutorials - with included code for examples
  - (10 Points) Leaflet Map
- (20 Points) Research
- (20 Points) Vibe With Me