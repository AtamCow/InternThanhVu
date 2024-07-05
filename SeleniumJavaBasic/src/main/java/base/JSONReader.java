package base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {
    public static JSONArray readJSON(String filePath) {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.getJSONArray("capabilities");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
