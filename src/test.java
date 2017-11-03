
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/2411cd28104f8b1a/history_20171030/q/NY/New_York.json");
        System.out.println(json.toString());
        JSONObject history = (JSONObject) json.get("history");
        JSONArray dailysummary = (JSONArray) history.get("dailysummary");
        for (int i = 0; i < dailysummary.length(); i++) {

            JSONObject summaryobjects = dailysummary.getJSONObject(i);

            System.out.println(summaryobjects.getString("maxhumidity"));
            System.out.println(summaryobjects.getString("maxtempm"));
            System.out.println(summaryobjects.getString("mintempm"));
            System.out.println(summaryobjects.getString("precipm"));

        }
    }
}