package com.golitsyn.main;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.Locale;

public class Main {
    // try too understand Currency for ISO 4217
//    public static void main(String[] args) {
//        System.out.println(Currency.getInstance("USD").getSymbol(Locale.US));
//        System.out.println(Currency.getInstance("USD").getDefaultFractionDigits());
//        System.out.println(Currency.getInstance("USD").getNumericCode());
//
//    }

    private static final String URL = "http://localhost:8080/";

    public static void main(String[] args) throws Exception {
        Main http = new Main();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

    // HTTP GET request
    private void sendGet() throws Exception {


        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + URL);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost() throws Exception {

        String urlS = "http://localhost:8080/";
        urlS += "search/api/getSearchResult";
        final URL url = new URL(urlS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");

        JSONObject cred   = new JSONObject(
                "{\"customer\":\"PLUTO1\"" +
                        ",\"ccyPair\":\"EURUSD\"" +
                        ",\"trader\":\"JohannBaumfiddler\"}");

        OutputStream os = conn.getOutputStream();
        os.write(cred.toString().getBytes("UTF-8"));
        os.close();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        System.out.println(conn.getResponseCode());
    }

}
