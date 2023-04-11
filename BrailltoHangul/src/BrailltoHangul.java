import java.util.*;

public class BrailltoHangul {
    // 모든 한글에 대한 점자를 매치해놓은 HashMap 변수 선언 (파이썬의 딕셔너리와 비슷)
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


    // 점자를 받아 한글 초성 중성 종성으로 변환한 후 한글을 조합!해야함
    // 초성과 중성에 각각 글자에 해당하는 index를 곱한 후 유니코드에서 한글 시작부분(44032)에 더하면 한글이 조합된다.
    //아래는 한글에 해당하는 index 맵이다.
    /////////////////////////////////////////////////
    // 0xAC00 ~ 0xD7A3 (11172 자)
    //
    // 초성: ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
    // 중성: ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
    // 종성: fill ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
    /////////////////////////////////////////////////
    private static HashMap<Character, Integer> ChosungIndex;
    private static HashMap<Character, Integer> JoongsungIndex;
    private static HashMap<Character, Integer> JongsungInedx;
    static {
        ChosungIndex = new HashMap<Character, Integer>();
        ChosungIndex.put('ㄱ', 0);
        ChosungIndex.put('ㄴ', 1);
        ChosungIndex.put('ㄷ', 2);
        ChosungIndex.put('ㄹ', 3);
        ChosungIndex.put('ㅁ', 4);
        ChosungIndex.put('ㅂ', 5);
        ChosungIndex.put('ㅅ', 6);
        ChosungIndex.put('ㅇ', 7);
        ChosungIndex.put('ㅈ', 8);
        ChosungIndex.put('ㅊ', 9);
        ChosungIndex.put('ㅋ', 10);
        ChosungIndex.put('ㅌ', 11);
        ChosungIndex.put('ㅍ', 12);
        ChosungIndex.put('ㅎ', 13);

        JoongsungIndex = new HashMap<Character, Integer>();
        JoongsungIndex.put('ㅏ', 0);
        JoongsungIndex.put('ㅐ', 1);
        JoongsungIndex.put('ㅑ', 2);
        JoongsungIndex.put('ㅒ', 3);
        JoongsungIndex.put('ㅓ', 4);
        JoongsungIndex.put('ㅔ', 5);
        JoongsungIndex.put('ㅕ', 6);
        JoongsungIndex.put('ㅖ', 7);
        JoongsungIndex.put('ㅗ', 8);
        JoongsungIndex.put('ㅘ', 9);
        JoongsungIndex.put('ㅙ', 10);
        JoongsungIndex.put('ㅚ', 11);
        JoongsungIndex.put('ㅛ', 12);
        JoongsungIndex.put('ㅜ', 13);
        JoongsungIndex.put('ㅝ', 14);
        JoongsungIndex.put('ㅞ', 15);
        JoongsungIndex.put('ㅟ', 16);
        JoongsungIndex.put('ㅠ', 17);
        JoongsungIndex.put('ㅡ', 18);
        JoongsungIndex.put('ㅢ', 19);
        JoongsungIndex.put('ㅣ', 20);

        JongsungInedx = new HashMap<Character, Integer>();
        JongsungInedx.put(' ', 0);
        JongsungInedx.put('ㄱ', 1);
        JongsungInedx.put('ㄲ', 2);
        JongsungInedx.put('ㄳ', 3);
        JongsungInedx.put('ㄴ', 4);
        JongsungInedx.put('ㄵ', 5);
        JongsungInedx.put('ㄶ', 6);
        JongsungInedx.put('ㄷ', 7);
        JongsungInedx.put('ㄹ', 8);
        JongsungInedx.put('ㄺ', 9);
        JongsungInedx.put('ㄻ', 10);
        JongsungInedx.put('ㄼ', 11);
        JongsungInedx.put('ㄽ', 12);
        JongsungInedx.put('ㄾ', 13);
        JongsungInedx.put('ㄿ', 14);
        JongsungInedx.put('ㅀ', 15);
        JongsungInedx.put('ㅁ', 16);
        JongsungInedx.put('ㅂ', 17);
        JongsungInedx.put('ㅄ', 18);
        JongsungInedx.put('ㅅ', 19);
        JongsungInedx.put('ㅆ', 20);
        JongsungInedx.put('ㅇ', 21);
        JongsungInedx.put('ㅈ', 22);
        JongsungInedx.put('ㅊ', 23);
        JongsungInedx.put('ㅋ', 24);
        JongsungInedx.put('ㅌ', 25);
        JongsungInedx.put('ㅍ', 26);
        JongsungInedx.put('ㅎ', 27);
    }

    //점자를 각각 초성 중성 종성으로 변환하는 함수
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

        int chosung = ChosungIndex.get(chosungStr.charAt(0));
        int jungsung = JoongsungIndex.get(jungsungStr.charAt(0));
        int jongsung = JongsungInedx.get(jongsungStr.charAt(0));

        char hangulChar = combine(chosung, jungsung, jongsung);

        System.out.println("한글 자모 조합 결과: " + hangulChar);

    }


}





// 퀴즈 낼 때만 점자 입력 필요?
// 우리는 퀴즈 랜덤으로 문제 냄. 예를 들어 '사과'
// 우리는 문제를 내면서 바로 '사과'라는 한글과 그에 맞는 점자 알고있음 a - HangultoBraille을 이용해서.
// 사용자가 점자를 입력하면.
// 굳이 그 점자를 한글로 바꾸고 조합하여 '사과'와 일치하는지 체크하는게 아니라
// 입력 점자 그대로를 a와 일치 시켜 확인.