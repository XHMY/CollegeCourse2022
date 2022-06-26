// require D3.js V7

const margin = {top: 350, right: 0, bottom: 0, left: 450},
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

    var label_bfzcj = d3.arc()
    .outerRadius(radius - 30)
    .innerRadius(radius - 30);

    var arc = svg_pie.selectAll(".arc")
    .data(pie(data))
    .enter()
    .append("g")
    .attr("class", "arc");

    arc.append("path")
    .attr("d", path)
    .attr("fill", function(d) { return color(d.data.kcmc); });

    arc.append("text")
    .attr("transform", function(d) { return "translate(" + label_bfzcj.centroid(d) + ")"; })
    .attr("fill", "gray")
    .attr("text-anchor", "middle")
    .text(function(d) { return d.data.bfzcj; });

    arc.append("line")
    .attr("stroke", "#cde6c7")
    .attr("x1", function (d) {
        return label_bfzcj.centroid(d)[0] * 1.5;
    })
    .attr("y1", function (d) {
        return label_bfzcj.centroid(d)[1] * 1.34;
    })
    .attr("x2", function (d) {
        return label_bfzcj.centroid(d)[0] * 1.7;
    })
    .attr("y2", function (d) {
        return label_bfzcj.centroid(d)[1] * 1.5;
    });

    arc.append("text")
    .attr("fill", "#145b7d")
    .attr("transform", function (d) {
        var x = label_bfzcj.centroid(d)[0] * 2.2;
        var y = label_bfzcj.centroid(d)[1] * 1.7;
        return "translate(" + x + "," + y + ")";
    })
    .attr("text-anchor", "middle")
    .text(function (d) {
        return d.data.kcmc;
    });


    d3.selectAll(".arc").on("mouseover", function(d) {
        d3.select(this).transition()
        .duration(200)
        .attr("transform", "scale(1.1)");
    }
    ).on("mouseout", function(d) {
        d3.select(this).transition()
        .duration(200)
        .attr("transform", "scale(1)");
    }
    );

    // add title
    svg_pie.append("text")
    .attr("x", width / 2)
    .attr("y", -300)
    .attr("text-anchor", "middle")
    .style("font-size", "20px")
    .text("曾一凡大三上学期成绩统计");

}
);
