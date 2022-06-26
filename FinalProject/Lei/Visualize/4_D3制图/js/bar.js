// require D3.js V7

// set the dimensions and margins of the graph
const margin = {top: 50, right: 35, bottom: 40, left: 200},
    width = 800 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

// draw bar chart
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
    .on("mouseover", function(d) {
      d3.select(this)
        .attr("fill", "orange")
        .attr("stroke", "red")
        .attr("stroke-width", "1");
    }
    )
    .on("mouseout", function(d) {
      d3.select(this)
        .attr("fill", "#00BFFF")
        .attr("stroke", "none")
        .attr("stroke-width", "0");
    }
    );
  
  // Add a label to the right of each bar
  svg.selectAll("myText")
    .data(data)
    .join("text")
    .attr("x", d => x(d.bfzcj) + 5)
    .attr("y", d => y(d.kcmc) + y.bandwidth() / 2)
    .attr("font-size", "12px")
    .text(d => d.bfzcj)
    .attr("fill", "blue")
    .attr("text-anchor", "start");

  svg.append("text")
    .attr("x", width / 2)
    .attr("y", 0 - (margin.top / 2))
    .attr("text-anchor", "middle")
    .style("font-size", "20px")
    .text("曾一凡大三上学期成绩统计");
})
