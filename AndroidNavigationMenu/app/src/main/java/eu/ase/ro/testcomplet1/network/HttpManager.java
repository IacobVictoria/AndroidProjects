package eu.ase.ro.testcomplet1.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpManager {
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String url;

    public HttpManager(String URL) {
        this.url = URL;
    }

    public List<String> call() {
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            List<String> result = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (Exception e) {
            Log.e("HTTP Conection", e.getMessage());
        }finally {

            try {
                bufferedReader.close();
            } catch (Exception e) {
                Log.e("BUffer reader Conection", e.getMessage());
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                Log.e("input stream Conection", e.getMessage());
            }
            try {
                inputStreamReader.close();
            } catch (Exception e) {
                Log.e("Input stream reader Conection", e.getMessage());
            }
            httpURLConnection.disconnect();
        }
        return null;
    }
}
