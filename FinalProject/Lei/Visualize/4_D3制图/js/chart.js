// set the dimensions and margins of the graph
const margin = {top: 50, right: 30, bottom: 40, left: 200},
    width = 800 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

// append the svg object to the body of the page
const svg = d3.select("body")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", `translate(${margin.left}, ${margin.top})`);

// Parse the Data
d3.csv("data.csv").then( function(data) {

  // Add X axis
  const x = d3.scaleLinear()
    .domain([0, 100])
    .range([ 0, width]);
  svg.append("g")
    .attr("transform", `translate(0, ${height})`)
    .call(d3.axisBottom(x))
    .selectAll("text")
    .attr("transform", "translate(-10,0)rotate(-45)")
    .style("text-anchor", "end");

  // Y axis
  const y = d3.scaleBand()
    .range([ 0, height ])
    .domain(data.map(d => d.kcmc))
    .padding(.1);
  svg.append("g")
    .call(d3.axisLeft(y))

  svg.append('g')
    .attr('class', 'grid')
    .attr('transform', `translate(0, ${height})`)
    .call(d3.axisBottom()
        .scale(x)
        .tickSize(-height, 0, 0)
        .tickFormat(''))

  //Bars
  svg.selectAll("myRect")
    .data(data)
    .join("rect")
    .attr("x", x(0) )
    .attr("y", d => y(d.kcmc))
    .attr("width", d => x(d.bfzcj))
    .attr("height", y.bandwidth())
    .attr("fill", "#00BFFF")

})

svg.append('text')
    .attr('x', - (height / 2) - margin)
    .attr('y', margin / 2.4)
    .attr('transform', 'rotate(-90)')
    .attr('text-anchor', 'middle')
    .text('科目')

svg.append('text')
    .attr('x', width / 2 + margin)
    .attr('y', 10)
    .attr('text-anchor', 'middle')
    .text('Most loved programming languages in 2018')