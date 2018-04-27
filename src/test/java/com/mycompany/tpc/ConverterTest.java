/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tpc;

import static com.mycompany.tpc.Converter.httpClient;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joseppmoreira
 */
public class ConverterTest {

    public ConverterTest() {
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void testNumConverter_whenCurrencyExists() {
        String access_key = "5e03aaeb679b999b82b1caaf";
        String from = "USD";
        String to = "EUR";

        HttpGet get = new HttpGet("https://v3.exchangerate-api.com/bulk/" + access_key + "/" + from);
        assertNotNull(get);

        try {
            CloseableHttpResponse response = httpClient.execute(get);
            assertNotNull(response);

            HttpEntity entity = response.getEntity();
            assertNotNull(entity);

            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            assertNotNull(exchangeRates);

            double currency = exchangeRates.getJSONObject("rates").getDouble(to);
            assertNotNull(currency);
            double rates = 0.82635264;
            assertEquals(rates, currency);

            response.close();
        } catch (ClientProtocolException e) {
        } catch (IOException | ParseException e) {
        }
    }
    
}
