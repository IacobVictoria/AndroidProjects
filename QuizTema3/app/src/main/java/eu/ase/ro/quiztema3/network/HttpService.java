package eu.ase.ro.quiztema3.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpService {

    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String url;

    public HttpService(String url) {
        this.url = url;
    }

    public String call() throws IOException {
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }catch (IOException e) {
            Log.e("HTTP", "connection " + e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Log.e("Close buffer reader", "connection " + e.getMessage());
            }

            try {
                inputStreamReader.close();
            } catch (IOException e) {
                Log.e("Close input stream reader", "connection " + e.getMessage());
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("Close input stream", "connection " + e.getMessage());
            }

            try {
                inputStreamReader.close();
            } catch (IOException e) {
                Log.e("Close input stream reader", "connection " + e.getMessage());
            }

            httpURLConnection.disconnect();
        }
        return null;
    }
}
