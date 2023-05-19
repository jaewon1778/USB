import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class json_DB {
    public static void main(String[] args) {
        // JSON 파일 경로 설정
        String filePath = "src/data.json";

        // 데이터베이스 연결 정보 설정
        String dbUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String dbUsername = "username";
        String dbPassword = "password";

        try {
            // MySQL 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // JSON 파일 읽기
            FileReader fileReader = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            // JSON 객체 순회
            for (Object obj : jsonObject.values()) {
                JSONObject json = (JSONObject) obj;

                // JSON 데이터 추출
                String word = (String) json.get("word");
                JSONArray brailleArray = (JSONArray) json.get("braille");

                // brailleArray를 문자열로 변환하여 데이터베이스에 저장
                String braille = brailleArray.toJSONString();

                // 데이터베이스에 저장하기 위한 쿼리 작성
                String sql = "INSERT INTO your_table_name (word, braille) VALUES (?, ?)";

                // PreparedStatement를 사용하여 쿼리 실행
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, word);
                statement.setString(2, braille);
                statement.executeUpdate();

                // 사용한 리소스 정리
                statement.close();
            }

            // 데이터베이스 연결 종료
            conn.close();

            System.out.println("JSON 데이터가 데이터베이스에 저장되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
