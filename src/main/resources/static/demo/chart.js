function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(',', '').replace(' ', '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}
// Pie Chart
function chart(myChart) {
    let colorList = []
    let hoverList = []
    let labelList = []
    let dataList = []
    chartList.forEach(function (item, i) {
        labelList.push(item.landingTitle)
        dataList.push(item.dataCount)
        let rgb1 = Math.floor(Math.random() * (255 + 1))
        let rgb2 = Math.floor(Math.random() * (255 + 1))
        let rgb3 = Math.floor(Math.random() * (255 + 1))
        let color = 'rgba(' + rgb1 + ',' + rgb2 + ',' + rgb3 + ', 0.7)'
        let hover = 'rgba(' + rgb1 + ',' + rgb2 + ',' + rgb3 + ', 1)'
        colorList.push(color)
        hoverList.push(hover)
    })
    new Chart(myChart, {
        type: 'bar',
        data: {
            labels: labelList,
            datasets: [{
                data: dataList,
                backgroundColor: colorList,
                hoverBackgroundColor: hoverList,
                hoverBorderColor: "rgba(0, 0, 0, 0.1)",
                hoverBorderWidth: 2
            }],
        },
        options: {
            maintainAspectRatio: true,
            tooltips: {
                backgroundColor: "rgb(0, 0, 0)",
                bodyFontColor: "#fff",
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 10,
                yPadding: 10,
                displayColors: false,
                caretPadding: 10,
                callbacks: {
                    label: function(item, data) {
                        return data['labels'][item['index']] + ': ' + number_format(data['datasets'][0]['data'][item['index']]) + ' 명';
                    }
                }
            },
            legend: {
                display: false,
            },
            cutoutPercentage: 80,
        },
    })
}
