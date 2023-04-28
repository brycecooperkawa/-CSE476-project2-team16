package edu.msu.CSE476_team16.proj1;

import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Cloud {

    public static final String MAGIC = "uAss+5%FP'hK&65";
    private static final String LOGIN_URL = "https://webdev.cse.msu.edu/~samoyhun/cse476/project2/login.php";
    private static final String CREATE_URL = "https://webdev.cse.msu.edu/~samoyhun/cse476/project2/create-user.php";

    private static final String UTF8 = "UTF-8";


    public boolean createAccount(String username, String password) {
        String query = CREATE_URL + "?user=" + username + "&magic=" + MAGIC + "&password=" + password;

        try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }

            InputStream stream = conn.getInputStream();

            /**
             * Create an XML parser for the result
             */
            try {
                XmlPullParser xmlR = Xml.newPullParser();
                xmlR.setInput(stream, UTF8);

                xmlR.nextTag();      // Advance to first tag
                xmlR.require(XmlPullParser.START_TAG, null, "toucan");

                String status = xmlR.getAttributeValue(null, "status");
                if(status.equals("no")) {
                    return false;
                }

                // We are done
            } catch(XmlPullParserException ex) {
                return false;
            } catch(IOException ex) {
                return false;
            }
            stream.close();

        } catch (MalformedURLException e) {
            // Should never happen
            return false;
        } catch (IOException ex) {
            return false;
        }

        return false;
    }

}



