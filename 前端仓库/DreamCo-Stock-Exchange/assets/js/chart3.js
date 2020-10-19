var options3 = {
    chart: {
      height: 350,
      type: 'line',
      stacked: false,
    },
    stroke: {
      width: [0, 2, 5],
      curve: 'smooth'
    },
    plotOptions: {
      bar: {
        columnWidth: '50%'
      }
    },
    colors: ['#3A5794', '#A5C351', '#E14A84'],
    series: [{
      name: '上证指数',
      type: 'column',
      data: [43, 39, 40, 39, 40, 44, 43, 43, 44, 42.01, 43.03]
    }, {
      name: '深圳成指',
      type: 'area',
      data: [31, 29, 33, 32, 30, 27, 28, 29, 30, 27, 32.71]
    }, {
      name: '科创板',
      type: 'line',
      data: [1.2, 25, 36, 30, 45, 35, 64, 52, 59, 36, 39]
    }],
    fill: {
      opacity: [0.85,0.25,1],
              gradient: {
                  inverseColors: false,
                  shade: 'light',
                  type: "vertical",
                  opacityFrom: 0.85,
                  opacityTo: 0.55,
                  stops: [0, 100, 100, 100]
              }
    },
    labels: ['01/01/2019', '02/01/2019','03/01/2019','04/01/2019','05/01/2019','06/01/2019','07/01/2019','08/01/2019','09/01/2019'],
    markers: {
      size: 0
    },
    xaxis: {
      type:'datetime'
    },
    yaxis: {
      min: 0
    },
    tooltip: {
      shared: true,
      intersect: false,
      y: {
        formatter: function (y) {
          if(typeof y !== "undefined") {
            return  y.toFixed(0) + " views";
          }
          return y;
          
        }
      }
    },
    legend: {
      labels: {
        useSeriesColors: true
      },
      markers: {
        customHTML: [
          function() {
            return '<span></span>'
          }, function() {
            return '<span></span>'
          }, function() {
            return '<span></span>'
          }
        ]
      }
    }
  }

  var chart3 = new ApexCharts(
    document.querySelector("#chart3"),
    options3
  );

  chart3.render();
