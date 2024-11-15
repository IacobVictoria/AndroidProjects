package eu.ase.ro.vacationplanningapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.vacationplanningapp.domain.DateConverter;
import eu.ase.ro.vacationplanningapp.domain.Type;
import eu.ase.ro.vacationplanningapp.domain.Vacation;

public class HttpManager {
    private BufferedReader bufferedReader;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private HttpURLConnection httpURLConnection;
    private final String url;

    public HttpManager(String url) {
        this.url = url;
    }

    public List<Vacation> call() throws IOException {
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            List<Vacation> vacations = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] objects = line.split(",");

                Date date = DateConverter.toDate(objects[0]);
                String location = objects[1];
                Type type = Type.valueOf(objects[2]);
                List<String> activities = new ArrayList<>();
                for (int i = 3; i < objects.length; i++) {
                    String act = objects[i];
                    activities.add(act);
                }

                vacations.add(new Vacation(date, location, activities, type));

            }
            return vacations;
        }catch (IOException e){

        }finally {
            bufferedReader.close();
            inputStream.close();
            inputStreamReader.close();
            httpURLConnection.disconnect();
        }
      return null;
    }
}
