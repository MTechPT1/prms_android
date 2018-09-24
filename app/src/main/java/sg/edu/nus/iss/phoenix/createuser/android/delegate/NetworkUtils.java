package sg.edu.nus.iss.phoenix.createuser.android.delegate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    static String getResponseFromHttpUrl(URL url) throws IOException {
        String result = null;

        HttpURLConnection urlConnection = null;
         urlConnection = (HttpURLConnection) url.openConnection();


        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput){
                return scanner.next();
            }else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }
}
