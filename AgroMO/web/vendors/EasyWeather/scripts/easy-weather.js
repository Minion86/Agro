/*****************************************
 -IMPORTANT: OPENWEATHER API WILL NOT 
 WORK WITH "HTTPS://"
 
 -IF NOT WORKING, MAKE SURE BROWSER
 ADDRESS BAR IS USING "HTTP://"
 ******************************************/
'use strict';

$(document).ready(function () {
  
});
// Function gets a date in the future. Gets current date if numbersOfDaysToAdd = 0
var getFutureDate = function (day) {
    var someDate = new Date();
    var numberOfDaysToAdd = day;
    someDate.setDate(someDate.getDate() + numberOfDaysToAdd);

    var dd = someDate.getDate();
    var mm = someDate.getMonth();
    var y = someDate.getFullYear();
    var d = someDate.getDay();

    // convert month number to month name
    var month = new Array();
    month[0] = 'Enero';
    month[1] = 'Febrero';
    month[2] = 'Marzo';
    month[3] = 'Abril';
    month[4] = 'Mayo';
    month[5] = 'Junio';
    month[6] = 'Julio';
    month[7] = 'Agosto';
    month[8] = 'Septiembre';
    month[9] = 'Octubre';
    month[10] = 'Noviembre';
    month[11] = 'Diciembre';

    // convert day number into day name
    var day = new Array();
    day[1] = 'Lunes';
    day[2] = 'Martes';
    day[3] = 'Miércoles';
    day[4] = 'Jueves';
    day[5] = 'Viernes';
    day[6] = 'Sábado';
    day[0] = 'Domingo';

    var futureMonth = month[mm];
    var futureDay = day[d];

    var someFormattedDate = futureDay + ", " + futureMonth + ' ' + dd + ', ' + y;

    return someFormattedDate;
}
// Function evaluates "clouds" conditions between 1 and 100 and returns image index
var getClouds = function (clouds) {
    if (100 < clouds && clouds < 90) {
        // return Cloudy Object
        var condition = {
            cloudNumber: 6,
            cloudText: 'Nublado'
        };
        return condition;
    } else if (89 < clouds && clouds < 60) {
        // return Mostly Cloudy
        var condition = {
            cloudNumber: 5,
            cloudText: 'Mayormente Nublado'
        };
        return condition;
    } else if (59 < clouds && clouds < 30) {
        // return Partly Cloudy
        var condition = {
            cloudNumber: 4,
            cloudText: 'Parcialmente Nublado'
        };
    } else if (29 < clouds && clouds > 20) {
        // return Mostly Sunny
        var condition = {
            cloudNumber: 3,
            cloudText: 'Mayormente Soleado'
        };
        return condition;
    } else if (19 > clouds && clouds > 10) {
        // return Sunny to Mostly Sunny
        var condition = {
            cloudNumber: 2,
            cloudText: 'Soleado a Mayormente Soleado'
        };
        return condition;
    } else {
        // return Sunny
        var condition = {
            cloudNumber: 1,
            cloudText: 'Soleado'
        };
        return condition;
    }
}

// Function: Handlebar Module / CRPA ("Crapa") (Create, Reference, Pass & Append)
var getWeather = function (theForecast) {

    var maximos = [];
    var minimos = [];
    var average = [];
    var dia = [];
    var noche = [];
    var dias = [];

    // create loop to get x days worth of data.  "list" is key name.
    for (var i = 1; i < theForecast.list.length; i++) {
        // get future dates
        var futureDate = getFutureDate(i);
        var cloudsCondition = getClouds(theForecast.list[i].clouds);

        // build weather data object for Handlebars
       
            var now= futureDate;
            var average=  (Math.round(theForecast.list[i].temp.day));
            var high=  (Math.round(theForecast.list[i].temp.max));
            var low=  (Math.round(theForecast.list[i].temp.min));
            var morning=  (Math.round(theForecast.list[i].temp.morn));
            var nighttime=  (Math.round(theForecast.list[i].temp.night));
            var cloudInfo= cloudsCondition.cloudNumber;
            var cloudInfoText= cloudsCondition.cloudText;
        

        maximos.push(high);
        minimos.push(low);
        average.push(average);
        dia.push(morning);
        noche.push(nighttime);
        dias.push(futureDate);
       
        var data =  "<div class='section'>\n\
<h2 class='date'>" + now + "</h2>\n\
	<div class='weather-description'>\n\
			<div id='weather-info'>\n\
\n\
 <h2>Average: " + average + "&deg;</h2>\n\
				  <h3>Máximo: " + high + "&deg;</h3>\n\
				<h3>Mínimo: " + low + "&deg;</h3>\n\
                <h3>Mañana: " + morning + "&deg;</h3>\n\
                <h3>Noche: " + nighttime + "&deg;</h3>\n\
<img src='http://webdesignertroy.com/dump/clouds/cloud-" + cloudInfo + ".jpg' class='cloud-image'/>\n\
</div>\n\
</div></div>";



        // APPEND fullText to the div.container
        jQuery('.carousel-inner').append(data);
    }

    maximosMinimos(maximos, minimos,average, dias);
    diaNoche(dia,noche, dias);
};


function maximosMinimos(maximos, minimos,average, dias)
{
    var config = {
        type: 'line',
        data: {
            labels: dias,
            datasets: [{
                    label: 'Máximo',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 0.2)',
                    data: maximos,
                    fill: false,
                }, {
                    label: 'Mínimo',
                    fill: false,
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 0.2)',
                    data: minimos,
                }, {
                    label: 'Average',
                    fill: false,
                    backgroundColor: 'rgba(255, 159, 64, 0.2)',
                    borderColor: 'rgba(255, 159, 64, 0.2)',
                    data: average,
                }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: 'Gráfica de temperatura máxima y mínima'
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Día'
                        }
                    }],
                yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Grados Centígrados'
                        }
                    }]
            }
        }
    };

    var ctx = document.getElementById('canvas1').getContext('2d');
    window.myLine = new Chart(ctx, config);
}


function diaNoche(dia,noche, dias)
{
    var config = {
        type: 'line',
        data: {
            labels: dias,
            datasets: [{
                    label: 'Día',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 0.2)',
                    data: dia,
                    fill: false,
                }, {
                    label: 'Noche',
                    fill: false,
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 0.2)',
                    data: noche,
                }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: 'Gráfica de temperatura en el día y en la noche'
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Día'
                        }
                    }],
                yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Grados Centígrados'
                        }
                    }]
            }
        }
    };

    var ctx = document.getElementById('canvas2').getContext('2d');
    window.myLine = new Chart(ctx, config);
}

// Function: Call api.openweathermap.com
var APICall = function (theCity) {
    // get API url
    var weatherUrl = "//api.openweathermap.org/data/2.5/forecast/daily?q=" + theCity;
    // get API key
    var apiKey = "b0b34e0501286ae903bab8dde901b6ae";
    // get "unit" as imperial
    var unitType = "metric";
    // get "cnt" as number of days up to 16 days
    var daysTotal = 8;

    // start jQuery-based API Call
    jQuery.get({
        url: weatherUrl + "&APPID=" + apiKey + "&units=" + unitType + "&cnt=" + daysTotal,
        success: function (objectFromOWM) {
            getWeather(objectFromOWM);
        },
        error: function () {
            console.log("error");
        }
    });

};

