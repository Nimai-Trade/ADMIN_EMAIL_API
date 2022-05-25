package com.nimai.admin.service;

import org.json.JSONException;
import java.io.IOException;
import org.json.JSONObject;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService
{
    public static void sendHttpGetRequest(final String fromCode, final String toCode) throws IOException, JSONException {
        final String GET_URL = "https://openexchangerates.org/api/latest.json?app_id=6d22174cd4d740a1a602c55c4a78d44b&base=USD&symbols=" + fromCode;
        final URL url = new URL(GET_URL);
        final HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
        httpUrlConnection.setRequestMethod("GET");
        final int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode == 200) {
            final BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            final StringBuffer response = new StringBuffer();
            String inputline;
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
                System.out.println();
            }
            in.close();
            final JSONObject obj = new JSONObject(response.toString());
            System.out.println("===========************" + response.toString());
            final Double exchangerates = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println("=========************" + exchangerates);
        }
    }
}