import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;

import java.io.FileReader;

public class jsonopen {
    public static void main(String[] args) {
        // JSON 파일 경로
        String filePath = "src/data.json";

        try (FileReader fileReader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) JSONValue.parse(fileReader);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object item : dataArray) {
                JSONObject jsonItem = (JSONObject) item;
                String word = (String) jsonItem.get("word");
                JSONArray brailleArray = (JSONArray) jsonItem.get("braille");

                // 값 출력 또는 원하는 작업 수행
                System.out.println("Word: " + word);
                System.out.println("Braille: " + brailleArray.toString());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}