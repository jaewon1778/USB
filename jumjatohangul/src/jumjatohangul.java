import java.text.Normalizer;
import java.util.*;

public class jumjatohangul {

    public static final HashMap<Character, int[][]> MATCH_H2B_CHO = new HashMap<Character, int[][]>() {{
        put('ㄱ', new int[][] {{0,0,0,1,0,0}});
        put('ㄴ', new int[][] {{1,0,0,1,0,0}});
        put('ㄷ', new int[][] {{0,1,0,1,0,0}});
        put('ㄹ', new int[][] {{0,0,0,0,1,0}});
        put('ㅁ', new int[][] {{1,0,0,0,1,0}});
        put('ㅂ', new int[][] {{0,0,0,1,1,0}});
        put('ㅅ', new int[][] {{0,0,0,0,0,1}});
        put('ㅇ', new int[][] {{1,1,0,1,1,0}});
        put('ㅈ', new int[][] {{0,0,0,1,0,1}});
        put('ㅊ', new int[][] {{0,0,0,0,1,1}});
        put('ㅋ', new int[][] {{1,1,0,1,0,0}});
        put('ㅌ', new int[][] {{1,1,0,0,1,0}});
        put('ㅍ', new int[][] {{1,0,0,1,1,0}});
        put('ㅎ', new int[][] {{0,1,0,1,1,0}});
        put('ㄲ', new int[][] {{0,0,0,0,0,1}, {0,0,0,1,0,0}});
        put('ㄸ', new int[][] {{0,0,0,0,0,1}, {0,1,0,1,0,0}});
        put('ㅃ', new int[][] {{0,0,0,0,0,1}, {0,0,0,1,1,0}});
        put('ㅆ', new int[][] {{0,0,0,0,0,1}, {0,0,0,0,0,1}});
        put('ㅉ', new int[][] {{0,0,0,0,0,1}, {0,0,0,1,0,1}});
    }};

    public static final Map<Character, int[][]> MATCH_H2B_JOONG = new HashMap<Character, int[][]>() {{
        put('ㅏ', new int[][]{{1,1,0,0,0,1}});
        put('ㅑ', new int[][]{{0,0,1,1,1,0}});
        put('ㅓ', new int[][]{{0,1,1,1,0,0}});
        put('ㅕ', new int[][]{{1,0,0,0,1,1}});
        put('ㅗ', new int[][]{{1,0,1,0,0,1}});
        put('ㅛ', new int[][]{{0,0,1,1,0,1}});
        put('ㅜ', new int[][]{{1,0,1,1,0,0}});
        put('ㅠ', new int[][]{{1,0,0,1,0,1}});
        put('ㅡ', new int[][]{{0,1,0,1,0,1}});
        put('ㅣ', new int[][]{{1,0,1,0,1,0}});
        put('ㅐ', new int[][]{{1,1,1,0,1,0}});
        put('ㅔ', new int[][]{{1,0,1,1,1,0}});
        put('ㅒ', new int[][]{{0,0,1,1,1,0}, {1,1,1,0,1,0}});
        put('ㅖ', new int[][]{{0,0,1,1,0,0}});
        put('ㅘ', new int[][]{{1,1,1,0,0,1}});
        put('ㅙ', new int[][]{{1,1,1,0,0,1}, {1,1,1,0,1,0}});
        put('ㅚ', new int[][]{{1,0,1,1,1,1}});
        put('ㅝ', new int[][]{{1,1,1,1,0,0}});
        put('ㅞ', new int[][]{{1,1,1,1,0,0}, {1,1,1,0,1,0}});
        put('ㅟ', new int[][]{{1,0,1,1,0,0}, {1,1,1,0,1,0}});
        put('ㅢ', new int[][]{{0,1,0,1,1,1}});
    }};

    private static final Map<Character, int[][]> MATCH_H2B_JONG = new HashMap<Character, int[][]>() {{
        put('ㄱ', new int[][]{{1, 0, 0, 0, 0, 0}});
        put('ㄴ', new int[][]{{0, 1, 0, 0, 1, 0}});
        put('ㄷ', new int[][]{{0, 0, 1, 0, 1, 0}});
        put('ㄹ', new int[][]{{0, 1, 0, 0, 0, 0}});
        put('ㅁ', new int[][]{{0, 1, 0, 0, 0, 1}});
        put('ㅂ', new int[][]{{1, 1, 0, 0, 0, 0}});
        put('ㅅ', new int[][]{{0, 0, 1, 0, 0, 0}});
        put('ㅇ', new int[][]{{0, 1, 1, 0, 1, 1}});
        put('ㅈ', new int[][]{{1, 0, 1, 0, 0, 0}});
        put('ㅊ', new int[][]{{0, 1, 1, 0, 0, 0}});
        put('ㅋ', new int[][]{{0, 1, 1, 0, 1, 0}});
        put('ㅌ', new int[][]{{0, 1, 1, 0, 0, 1}});
        put('ㅍ', new int[][]{{0, 1, 0, 0, 1, 1}});
        put('ㅎ', new int[][]{{0, 0, 1, 0, 1, 1}});
        put('ㄲ', new int[][]{{1, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0}});
        put('ㄳ', new int[][]{{1, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
        put('ㄵ', new int[][]{{0, 1, 0, 0, 1, 0}, {1, 0, 1, 0, 0, 0}});
        put('ㄶ', new int[][]{{0, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 1}});
        put('ㄺ', new int[][]{{0, 1, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0}});
        put('ㄻ', new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1}});
        put('ㄼ', new int[][]{{0, 1, 0, 0, 0, 0}, {1, 1, 0, 0, 0, 0}});
        put('ㄽ', new int[][]{{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
        put('ㄾ', new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 1, 0, 0, 1}});
        put('ㄿ', new int[][]{{0, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1}});
        put('ㅀ', new int[][]{{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 1, 1}});
        put('ㅄ', new int[][]{{1, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}});
    }};

    public static final Map<Character, int[][]> MATCH_H2B_GRAMMAR = new HashMap<Character, int[][]>() {{
        put('ㄱ', new int[][]{{1, 1, 0, 1, 0, 1}});
        put('ㄴ', new int[][]{{1, 0, 0, 1, 0, 0}});
        put('ㄷ', new int[][]{{0, 1, 0, 1, 0, 0}});
        put('ㅁ', new int[][]{{1, 0, 0, 0, 1, 0}});
        put('ㅂ', new int[][]{{0, 0, 0, 1, 1, 0}});
        put('ㅅ', new int[][]{{1, 1, 1, 0, 0, 0}});
        put('ㅈ', new int[][]{{0, 0, 0, 1, 0, 1}});
        put('ㅋ', new int[][]{{1, 1, 0, 1, 0, 0}});
        put('ㅌ', new int[][]{{1, 1, 0, 0, 1, 0}});
        put('ㅍ', new int[][]{{1, 0, 0, 1, 1, 0}});
        put('ㅎ', new int[][]{{0, 1, 0, 1, 1, 0}});

        put('것', new int[][]{{0, 0, 0, 1, 1, 1}, {0, 1, 1, 1, 0, 0}});

        put('ㅆ', new int[][]{{0, 0, 1, 1, 0, 0}});
    }};
    public static final Map<String, int[][]> MATCH_H2B_GRAMMAR2 = new HashMap<String, int[][]>() {{
        put("ㅓㄱ", new int[][] {{1,0,0,1,1,1}});
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

    private static HashMap<Character, Integer> chosungMap;
    private static HashMap<Character, Integer> jungsungMap;
    private static HashMap<Character, Integer> jongsungMap;
    static {
        chosungMap = new HashMap<Character, Integer>();
        chosungMap.put('ㄱ', 0);
        chosungMap.put('ㄴ', 1);
        chosungMap.put('ㄷ', 2);
        chosungMap.put('ㄹ', 3);
        chosungMap.put('ㅁ', 4);
        chosungMap.put('ㅂ', 5);
        chosungMap.put('ㅅ', 6);
        chosungMap.put('ㅇ', 7);
        chosungMap.put('ㅈ', 8);
        chosungMap.put('ㅊ', 9);
        chosungMap.put('ㅋ', 10);
        chosungMap.put('ㅌ', 11);
        chosungMap.put('ㅍ', 12);
        chosungMap.put('ㅎ', 13);

        jungsungMap = new HashMap<Character, Integer>();
        jungsungMap.put('ㅏ', 0);
        jungsungMap.put('ㅐ', 1);
        jungsungMap.put('ㅑ', 2);
        jungsungMap.put('ㅒ', 3);
        jungsungMap.put('ㅓ', 4);
        jungsungMap.put('ㅔ', 5);
        jungsungMap.put('ㅕ', 6);
        jungsungMap.put('ㅖ', 7);
        jungsungMap.put('ㅗ', 8);
        jungsungMap.put('ㅘ', 9);
        jungsungMap.put('ㅙ', 10);
        jungsungMap.put('ㅚ', 11);
        jungsungMap.put('ㅛ', 12);
        jungsungMap.put('ㅜ', 13);
        jungsungMap.put('ㅝ', 14);
        jungsungMap.put('ㅞ', 15);
        jungsungMap.put('ㅟ', 16);
        jungsungMap.put('ㅠ', 17);
        jungsungMap.put('ㅡ', 18);
        jungsungMap.put('ㅢ', 19);
        jungsungMap.put('ㅣ', 20);

        jongsungMap = new HashMap<Character, Integer>();
        jongsungMap.put(' ', 0);
        jongsungMap.put('ㄱ', 1);
        jongsungMap.put('ㄲ', 2);
        jongsungMap.put('ㄳ', 3);
        jongsungMap.put('ㄴ', 4);
        jongsungMap.put('ㄵ', 5);
        jongsungMap.put('ㄶ', 6);
        jongsungMap.put('ㄷ', 7);
        jongsungMap.put('ㄹ', 8);
        jongsungMap.put('ㄺ', 9);
        jongsungMap.put('ㄻ', 10);
        jongsungMap.put('ㄼ', 11);
        jongsungMap.put('ㄽ', 12);
        jongsungMap.put('ㄾ', 13);
        jongsungMap.put('ㄿ', 14);
        jongsungMap.put('ㅀ', 15);
        jongsungMap.put('ㅁ', 16);
        jongsungMap.put('ㅂ', 17);
        jongsungMap.put('ㅄ', 18);
        jongsungMap.put('ㅅ', 19);
        jongsungMap.put('ㅆ', 20);
        jongsungMap.put('ㅇ', 21);
        jongsungMap.put('ㅈ', 22);
        jongsungMap.put('ㅊ', 23);
        jongsungMap.put('ㅋ', 24);
        jongsungMap.put('ㅌ', 25);
        jongsungMap.put('ㅍ', 26);
        jongsungMap.put('ㅎ', 27);
        /////////////////////////////////////////////////
// 0xAC00 ~ 0xD7A3 (11172 자)
//
// 초성: ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
// 중성: ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
// 종성: fill ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
/////////////////////////////////////////////////
    }


    private static char convertBrailleToHangul(int[][] jumja) {
        for (char hangul : MATCH_H2B_CHO.keySet()) {
            int[][] check = MATCH_H2B_CHO.get(hangul);
            if (Arrays.deepEquals(check, jumja)) {
                return(hangul);
            }
        }
        for (char hangul : MATCH_H2B_JOONG.keySet()) {
            int[][] check = MATCH_H2B_JOONG.get(hangul);
            if (Arrays.deepEquals(check, jumja)) {
                return(hangul);
            }
        }
        for (char hangul : MATCH_H2B_JONG.keySet()) {
            int[][] check = MATCH_H2B_JONG.get(hangul);
            if (Arrays.deepEquals(check, jumja)) {
                return(hangul);
            }
        }
        return ' ';

    }

    public static char combine(int x1, int x2, int x3) {
        int x = (x1 * 21 * 28) + (x2 * 28) + x3;
        return (char) (x + 0xAC00);
    }

    public static void main(String[] args) {
        ArrayList<Character> hanguls = new ArrayList<Character>();

        int [][] jumja1 = new int[][] {{0,0,0,1,0,0}};
        int [][] jumja2 = new int[][] {{0,0,1,1,1,0}};
        int [][] jumja3 = new int[][] {{0, 1, 0, 0, 0, 1}};

        hanguls.add(convertBrailleToHangul(jumja1));
        hanguls.add(convertBrailleToHangul(jumja2));
        hanguls.add(convertBrailleToHangul(jumja3));

        String chosungStr = hanguls.get(0).toString();
        String jungsungStr = hanguls.get(1).toString();
        String jongsungStr = hanguls.get(2).toString();

        int chosung = chosungMap.get(chosungStr.charAt(0));
        int jungsung = jungsungMap.get(jungsungStr.charAt(0));
        int jongsung = jongsungMap.get(jongsungStr.charAt(0));

        char hangulChar = combine(chosung, jungsung, jongsung);

        System.out.println("한글 자모 조합 결과: " + hangulChar);

    }


}