import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class Learnig {
    // dot에 대한 점자를 매치해놓은 HashMap 변수 선언 (파이썬의 딕셔너리와 비슷)
    public static final HashMap<String, int[]> DOT = new HashMap<String, int[]>() {{
        put("1점", new int[] {1,0,0,0,0,0});
        put("2점", new int[] {0,1,0,0,0,0});
        put("3점", new int[] {0,0,1,0,0,0});
        put("4점", new int[] {0,0,0,1,0,0});
        put("5점", new int[] {0,0,0,0,1,0});
        put("6점", new int[] {0,0,0,0,0,1});
    }};

    public static final HashMap<String, int[][]> CHO = new HashMap<String, int[][]>() {{
        put("ㄱ", new int[][] {{0,0,0,1,0,0}});
        put("ㄴ", new int[][] {{1,0,0,1,0,0}});
        put("ㄷ", new int[][] {{0,1,0,1,0,0}});
        put("ㄹ", new int[][] {{0,0,0,0,1,0}});
        put("ㅁ", new int[][] {{1,0,0,0,1,0}});
        put("ㅂ", new int[][] {{0,0,0,1,1,0}});
        put("ㅅ", new int[][] {{0,0,0,0,0,1}});
        put("ㅇ", new int[][] {{1,1,0,1,1,0}});
        put("ㅈ", new int[][] {{0,0,0,1,0,1}});
        put("ㅊ", new int[][] {{0,0,0,0,1,1}});
        put("ㅋ", new int[][] {{1,1,0,1,0,0}});
        put("ㅌ", new int[][] {{1,1,0,0,1,0}});
        put("ㅍ", new int[][] {{1,0,0,1,1,0}});
        put("ㅎ", new int[][] {{0,1,0,1,1,0}});
        put("ㄲ", new int[][] {{0,0,0,0,0,1}, {0,0,0,1,0,0}});
        put("ㄸ", new int[][] {{0,0,0,0,0,1}, {0,1,0,1,0,0}});
        put("ㅃ", new int[][] {{0,0,0,0,0,1}, {0,0,0,1,1,0}});
        put("ㅆ", new int[][] {{0,0,0,0,0,1}, {0,0,0,0,0,1}});
        put("ㅉ", new int[][] {{0,0,0,0,0,1}, {0,0,0,1,0,1}});
    }};

    // 모든 중성에 대한 점자를 매치해놓은 HashMap 변수 선언
    public static final Map<String, int[][]> JOONG = new HashMap<String, int[][]>() {{
        put("ㅏ", new int[][]{{1,1,0,0,0,1}});
        put("ㅑ", new int[][]{{0,0,1,1,1,0}});
        put("ㅓ", new int[][]{{0,1,1,1,0,0}});
        put("ㅕ", new int[][]{{1,0,0,0,1,1}});
        put("ㅗ", new int[][]{{1,0,1,0,0,1}});
        put("ㅛ", new int[][]{{0,0,1,1,0,1}});
        put("ㅜ", new int[][]{{1,0,1,1,0,0}});
        put("ㅠ", new int[][]{{1,0,0,1,0,1}});
        put("ㅡ", new int[][]{{0,1,0,1,0,1}});
        put("ㅣ", new int[][]{{1,0,1,0,1,0}});
        put("ㅐ", new int[][]{{1,1,1,0,1,0}});
        put("ㅔ", new int[][]{{1,0,1,1,1,0}});
        put("ㅒ", new int[][]{{0,0,1,1,1,0}, {1,1,1,0,1,0}});
        put("ㅖ", new int[][]{{0,0,1,1,0,0}});
        put("ㅘ", new int[][]{{1,1,1,0,0,1}});
        put("ㅙ", new int[][]{{1,1,1,0,0,1}, {1,1,1,0,1,0}});
        put("ㅚ", new int[][]{{1,0,1,1,1,1}});
        put("ㅝ", new int[][]{{1,1,1,1,0,0}});
        put("ㅞ", new int[][]{{1,1,1,1,0,0}, {1,1,1,0,1,0}});
        put("ㅟ", new int[][]{{1,0,1,1,0,0}, {1,1,1,0,1,0}});
        put("ㅢ", new int[][]{{0,1,0,1,1,1}});
    }};

    // 모든 종성에 대한 점자를 매치해놓은 HashMap 변수 선언
    private static final Map<String, int[][]> JONG = new HashMap<String, int[][]>() {{
        put("ㄱ", new int[][]{{1, 0, 0, 0, 0, 0}});
        put("ㄴ", new int[][]{{0, 1, 0, 0, 1, 0}});
        put("ㄷ", new int[][]{{0, 0, 1, 0, 1, 0}});
        put("ㄹ", new int[][]{{0, 1, 0, 0, 0, 0}});
        put("ㅁ", new int[][]{{0, 1, 0, 0, 0, 1}});
        put("ㅂ", new int[][]{{1, 1, 0, 0, 0, 0}});
        put("ㅅ", new int[][]{{0, 0, 1, 0, 0, 0}});
        put("ㅇ", new int[][]{{0, 1, 1, 0, 1, 1}});
        put("ㅈ", new int[][]{{1, 0, 1, 0, 0, 0}});
        put("ㅊ", new int[][]{{0, 1, 1, 0, 0, 0}});
        put("ㅋ", new int[][]{{0, 1, 1, 0, 1, 0}});
        put("ㅌ", new int[][]{{0, 1, 1, 0, 0, 1}});
        put("ㅍ", new int[][]{{0, 1, 0, 0, 1, 1}});
        put("ㅎ", new int[][]{{0, 0, 1, 0, 1, 1}});
        put("ㄲ", new int[][]{{1, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0}});
        put("ㄳ", new int[][]{{1, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
        put("ㄵ", new int[][]{{0, 1, 0, 0, 1, 0}, {1, 0, 1, 0, 0, 0}});
        put("ㄶ", new int[][]{{0, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 1}});
        put("ㄺ", new int[][]{{0, 1, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0}});
        put("ㄻ", new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1}});
        put("ㄼ", new int[][]{{0, 1, 0, 0, 0, 0}, {1, 1, 0, 0, 0, 0}});
        put("ㄽ", new int[][]{{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
        put("ㄾ", new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 1, 0, 0, 1}});
        put("ㄿ", new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1}});
        put("ㅀ", new int[][]{{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 1, 1}});
        put("ㅄ", new int[][]{{1, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
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

    public static final Map<String, int[][]> GRAMMAR2 = new HashMap<String, int[][]>() {{
        put("ㅓㄱ", new int[][] {{1,0,0,1,1,1}}); // '초성'+'ㅓ'+'ㄱ' 일 때 'ㅓㄱ'에 해당하는 점자를 의미
        put("ㅓㄴ", new int[][] {{0,1,1,1,1,1}});
        put("ㅓㄹ", new int[][] {{0,1,1,1,1,0}});
        put("ㅕㄴ", new int[][] {{1,0,0,0,0,1}});
        put("ㅕㄹ", new int[][] {{1,1,0,0,1,1}});
        put("ㅕㅇ", new int[][] {{1,1,0,1,1,1}});
        put("ㅗㄱ", new int[][] {{1,0,1,1,0,1}});
        put("ㅗㄴ", new int[][] {{1,1,1,0,1,1}});
        put("ㅗㅇ", new int[][] {{1,1,1,1,1,1}});
        put("ㅜㄴ", new int[][] {{1,1,0,1,1,0}});
        put("ㅜㄹ", new int[][] {{1,1,1,1,0,1}});
        put("ㅡㄴ", new int[][] {{1,0,1,0,1,1}});
        put("ㅡㄹ", new int[][] {{0,1,1,1,0,1}});
        put("ㅣㄴ", new int[][] {{1,1,1,1,1,0}});
    }};

    public static final HashMap <String, int[][]> GRAMMAR3 = new HashMap<String, int[][]>() {{
        put("것", new int[][] {{0,1,0,1,0,1},{0,1,1,0,1,0}});
        put("그래서", new int[][] {{1,0,0,0,0,0},{0,1,1,0,1,0}});
        put("그러나", new int[][] {{1,0,0,0,0,0},{1,1,0,0,0,0}});
        put("그러면", new int[][] {{1,0,0,0,0,0},{0,0,1,1,0,0}});
        put("그러므로", new int[][] {{1,0,0,0,0,0},{0,0,1,0,0,1}});
        put("그런데", new int[][] {{1,0,0,0,0,0},{1,1,0,1,1,0}});
        put("그리고", new int[][] {{1,0,0,0,0,0},{1,0,0,0,1,1}});
        put("그리하여", new int[][] {{1,0,0,0,0,0},{1,0,0,1,0,1}});
    }};

    // 단어 배우기 -> 점위치 출력 코드
    private static String Learnig_dot (String dot_num) {
        String result;
        result = Arrays.toString(DOT.get(dot_num));
        return result;
    }

    // 단어 배우기 -> 한글 (자음,모음) 출력 코드
    private static String Learnig_hangul(String hangul) {
        if (CHO.containsKey(hangul)) {
            System.out.println(hangul);
            return Arrays.deepToString(CHO.get(hangul));
        }

        if (JOONG.containsKey(hangul)) {
            System.out.println(hangul);
            return Arrays.deepToString(JOONG.get(hangul));
        }

        if (JONG.containsKey(hangul)) {
            System.out.println(hangul);
            return Arrays.deepToString(JONG.get(hangul));
        }

        return null;
    }


    // 단어 배우기 -> 한글 (약자) 출력 코드
    private static String Learnig_Grammar(String grammar) {
        if (GRAMMAR1.containsKey(grammar)) {
            System.out.println(grammar);
            return Arrays.deepToString(GRAMMAR1.get(grammar));
        }

        if (GRAMMAR2.containsKey(grammar)) {
            System.out.println(grammar);
            return Arrays.deepToString(GRAMMAR2.get(grammar));
        }

        if (GRAMMAR3.containsKey(grammar)) {
            System.out.println(grammar);
            return Arrays.deepToString(GRAMMAR3.get(grammar));
        }

        return null;
    }



    public static void main(String[] args) {
        String dot_num = "1점";
        String hangul = "ㅓ";
        String grammar= "ㅓㄱ";
        String Result = Learnig_dot(dot_num);
        String Result2 = Learnig_hangul(hangul);
        String Result3 = Learnig_Grammar(grammar); //각각 인자들 모두 string으로 넣고 리턴값도 모두 string으로 받을 수 있습니담

        System.out.println(Result);
        System.out.println(Result2);
        System.out.println(Result3);
    }
}










//int[][] 그자체로 리턴을 하면 배열 내용이 리턴이 안되고 이상한값 리턴됨 ㅠ
//private static int[][] Learnig_hangul (Character hangul) {
//    if (CHO.containsKey(hangul)) {
//        System.out.println(hangul);
//        return (CHO.get(hangul));
//    }
//    return null;
//}