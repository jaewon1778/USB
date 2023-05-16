import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File;


public class Quiz {
    public static final HashMap<String, int[][]> GRAMMAR3 = new HashMap<String, int[][]>() {{
        put("것", new int[][] {{0,1,0,1,0,1},{0,1,1,0,1,0}});
        put("그래서", new int[][] {{1,0,0,0,0,0},{0,1,1,0,1,0}});
        put("그러나", new int[][] {{1,0,0,0,0,0},{1,1,0,0,0,0}});
        put("그러면", new int[][] {{1,0,0,0,0,0},{0,0,1,1,0,0}});
        put("그러므로", new int[][] {{1,0,0,0,0,0},{0,0,1,0,0,1}});
        put("그런데", new int[][] {{1,0,0,0,0,0},{1,1,0,1,1,0}});
        put("그리고", new int[][] {{1,0,0,0,0,0},{1,0,0,0,1,1}});
        put("그리하여", new int[][] {{1,0,0,0,0,0},{1,0,0,1,0,1}});
    }};
    public static final Map<String, int[][]> GRAMMAR1 = new HashMap< String, int[][]>() {{
        put("가", new int[][]{{1, 1, 0, 1, 0, 1}});  // 'ㄱ'+'ㅏ' 일 때 '가'에 해당하는 점자를 의미
        put("나", new int[][]{{1, 0, 0, 1, 0, 0}});
        put("다", new int[][]{{0, 1, 0, 1, 0, 0}});
        put("마", new int[][]{{1, 0, 0, 0, 1, 0}});
        put("바", new int[][]{{0, 0, 0, 1, 1, 0}});
        put("사", new int[][]{{1, 1, 1, 0, 0, 0}});
        put("자", new int[][]{{0, 0, 0, 1, 0, 1}});
        put("카", new int[][]{{1, 1, 0, 1, 0, 0}});
        put("타", new int[][]{{1, 1, 0, 0, 1, 0}});
        put("파", new int[][]{{1, 0, 0, 1, 1, 0}});
        put("하", new int[][]{{0, 1, 0, 1, 1, 0}});
    }};



    public static void main(String[] args) {
        File file = new File("file.txt");
        // 두 개의 HashMap 중에서 아무 HashMap을 선택
        Map<String, int[][]> randomHashMap = getRandomHashMap(GRAMMAR3, GRAMMAR1);
        String randomKey = getRandomKey(randomHashMap);
        int[][] randomValue = randomHashMap.get(randomKey);
        String randomValueString =  java.util.Arrays.deepToString(randomValue);

        saveValueToFile(randomValueString);
        //String savedValueString = loadValueFromFile();

        System.out.println(randomKey);
    }


    public static Map<String, int[][]> getRandomHashMap(Map<String, int[][]>... maps) {
        Random random = new Random();
        int index = random.nextInt(maps.length);
        return maps[index];
    }

    public static String getRandomKey(Map<String, int[][]> map) {
        Random random = new Random();
        int index = random.nextInt(map.size());
        return (String) map.keySet().toArray()[index];
    }


    //경로는 직접 입력
    private static final String FILE_PATH = System.getProperty("user.home") + "/IdeaProjects/test/src/file.txt";

    private static void saveValueToFile(String value) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}