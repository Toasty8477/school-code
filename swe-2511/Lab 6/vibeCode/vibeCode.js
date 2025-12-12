const data = [
    {year:1980, wolves:50, moose:664},
    {year:1981, wolves:30, moose:650},
    {year:1982, wolves:14, moose:700},
    {year:1983, wolves:23, moose:900},
    {year:1984, wolves:24, moose:811},
    {year:1985, wolves:22, moose:1062},
    {year:1986, wolves:20, moose:1025},
    {year:1987, wolves:16, moose:1380},
    {year:1988, wolves:12, moose:1653},
    {year:1989, wolves:11, moose:1397},
    {year:1990, wolves:15, moose:1216},
    {year:1991, wolves:12, moose:1313},
    {year:1992, wolves:12, moose:1600},
    {year:1993, wolves:13, moose:1880},
    {year:1994, wolves:15, moose:1800},
    {year:1995, wolves:16, moose:2400},
    {year:1996, wolves:22, moose:1200},
    {year:1997, wolves:24, moose:500},
    {year:1998, wolves:14, moose:700},
    {year:1999, wolves:25, moose:750},
    {year:2000, wolves:29, moose:850},
    {year:2001, wolves:19, moose:900},
    {year:2002, wolves:17, moose:1000},
    {year:2003, wolves:19, moose:900},
    {year:2004, wolves:29, moose:750},
    {year:2005, wolves:30, moose:540},
    {year:2006, wolves:30, moose:385},
    {year:2007, wolves:21, moose:450},
    {year:2008, wolves:23, moose:650},
    {year:2009, wolves:24, moose:530},
    {year:2010, wolves:19, moose:510},
    {year:2011, wolves:16, moose:515},
    {year:2012, wolves:9, moose:750},
    {year:2013, wolves:8, moose:975},
    {year:2014, wolves:9, moose:1050},
    {year:2015, wolves:3, moose:1250},
    {year:2016, wolves:2, moose:1300},
    {year:2017, wolves:2, moose:1600},
    {year:2018, wolves:2, moose:1500},
    {year:2019, wolves:14, moose:2060}
];

const svg = d3.select("#wolfMooseChart"),
    width = +svg.attr("width"),
    height = +svg.attr("height"),
    margin = {top: 40, right: 40, bottom: 60, left: 70},
    innerWidth = width - margin.left - margin.right,
    innerHeight = height - margin.top - margin.bottom;

const x = d3.scaleLinear()
    .domain(d3.extent(data, d => d.year))
    .range([0, innerWidth]);

const y = d3.scaleLinear()
    .domain([0, d3.max(data, d => Math.max(d.wolves, d.moose))])
    .nice()
    .range([innerHeight, 0]);

const g = svg.append("g")
    .attr("transform", `translate(${margin.left},${margin.top})`);

const lineWolves = d3.line()
    .x(d => x(d.year))
    .y(d => y(d.wolves));

const lineMoose = d3.line()
    .x(d => x(d.year))
    .y(d => y(d.moose));

const tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);

// Wolves line
g.append("path")
    .datum(data)
    .attr("class", "line wolves-line")
    .attr("d", lineWolves);

// Moose line
g.append("path")
    .datum(data)
    .attr("class", "line moose-line")
    .attr("d", lineMoose);

// Axes
g.append("g")
    .attr("transform", `translate(0,${innerHeight})`)
    .call(d3.axisBottom(x).tickFormat(d3.format("d")));

g.append("g")
    .call(d3.axisLeft(y));

// Axis labels
g.append("text")
    .attr("x", innerWidth / 2)
    .attr("y", innerHeight + 40)
    .attr("class", "axis-label")
    .text("Year");

g.append("text")
    .attr("x", -innerHeight / 2)
    .attr("y", -50)
    .attr("transform", "rotate(-90)")
    .attr("class", "axis-label")
    .text("Population Count");

// Dots + Tooltips
g.selectAll(".dot-wolf")
    .data(data)
    .enter().append("circle")
    .attr("class", "dot-wolf")
    .attr("cx", d => x(d.year))
    .attr("cy", d => y(d.wolves))
    .attr("r", 4)
    .attr("fill", "#e74c3c")
    .on("mouseover", (event, d) => {
        tooltip.transition().duration(200).style("opacity", 0.9);
        tooltip.html(`<strong>${d.year}</strong><br>Wolves: ${d.wolves}`)
            .style("left", (event.pageX + 10) + "px")
            .style("top", (event.pageY - 28) + "px");
    })
    .on("mouseout", () => tooltip.transition().duration(500).style("opacity", 0));

g.selectAll(".dot-moose")
    .data(data)
    .enter().append("circle")
    .attr("class", "dot-moose")
    .attr("cx", d => x(d.year))
    .attr("cy", d => y(d.moose))
    .attr("r", 4)
    .attr("fill", "#3498db")
    .on("mouseover", (event, d) => {
        tooltip.transition().duration(200).style("opacity", 0.9);
        tooltip.html(`<strong>${d.year}</strong><br>Moose: ${d.moose}`)
            .style("left", (event.pageX + 10) + "px")
            .style("top", (event.pageY - 28) + "px");
    })
    .on("mouseout", () => tooltip.transition().duration(500).style("opacity", 0));