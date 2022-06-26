// require D3.js V7

const margin = {top: 300, right: 35, bottom: 40, left: 300},
    width = 800 - margin.left - margin.right,
    height = 800 - margin.top - margin.bottom;
    radius = Math.min(width, height) / 2;


// draw pie chart
const svg_pie = d3.select("body")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", `translate(${margin.left}, ${margin.top})`);

d3.csv("data.csv").then( function(data) {
    // data.csv is a csv file with the following columns:
    // kcmc: course name
    // bfzcj: score

    var color = d3.scaleOrdinal().domain(data.map(d => d.kcmc)).range(d3.schemeSet3);
    
    var pie = d3.pie()
	.sort(null)
	.value(function(d) {
		return d.bfzcj;
	}).sort(null);

    var path = d3.arc()
    .outerRadius(radius)
    .innerRadius(0);

    var label = d3.arc()
    .outerRadius(radius + 10)
    .innerRadius(radius + 10);

    var arc = svg_pie.selectAll(".arc")
    .data(pie(data))
    .enter()
    .append("g")
    .attr("class", "arc");

    arc.append("path")
    .attr("d", path)
    .attr("fill", function(d) { return color(d.data.kcmc); });

    arc.append("text")
    .attr("transform", function(d) { return "translate(" + label.centroid(d) + ")"; })
    .attr("dy", "0.35em")
    .text(function(d) { return d.data.kcmc; });

    arc.append("text")
    .attr("transform", function(d) { return "translate(" + label.centroid(d) + ")"; })
    .attr("dy", "1.35em")
    .text(function(d) { return d.data.bfzcj; });
    
}
);
