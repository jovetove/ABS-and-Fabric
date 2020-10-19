var options2 = {
    chart: {
        width: 380,
        type: 'pie',
    },
    labels: ['上证指数', '深圳成指', '创业板', '中小板', '科创板'],
    series: [43.03, 32.71, 6.7, 10.01, 1.23],
    responsive: [{
        breakpoint: 480,
        options2: {
            chart: {
                width: 200
            },
            legend: {
                position: 'bottom'
            }
        }
    }]
}

var chart2 = new ApexCharts(
    document.querySelector("#chart2"),
    options2
);

chart2.render();