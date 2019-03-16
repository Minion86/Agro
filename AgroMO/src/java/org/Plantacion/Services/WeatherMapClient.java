/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.Services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.Plantacion.Dto.WeatherMap;

/**
 *
 * @author nmartinez
 */
public class WeatherMapClient {

    private static final String REST_URI
            = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static final String REST_URI_TOKEN
            = "4edd79d8cc132245dd4396897ad5d76e";

    private Client client = ClientBuilder.newClient();

    public WeatherMap getJsonWeatherMap(String ciudad, String pais) {
        return client
                .target(REST_URI + ciudad + "," + pais + "&APPID=" + REST_URI_TOKEN)
                .request(MediaType.APPLICATION_JSON)
                .get(WeatherMap.class);
    }
}
