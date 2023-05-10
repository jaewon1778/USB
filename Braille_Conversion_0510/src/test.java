import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class test {

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

    public static void main(String[] args) {

        ArrayList<Object> result = new ArrayList<Object>();

        //int[][] twoDimArray = MATCH_H2B_CHO.get('ㄱ');
        //int[] oneDimArray = twoDimArray[0];

        int[][] twoDimArray = MATCH_H2B_CHO.get('ㄲ');
        for (int i = 0; i < twoDimArray.length; i++) {
            result.add(twoDimArray[i]);
        }

        int[][] twoDimArray2 = MATCH_H2B_CHO.get('ㄱ');
        for (int i = 0; i < twoDimArray2.length; i++) {
            result.add(twoDimArray2[i]);
        }

        System.out.println(Arrays.deepToString(result.toArray()));
        //System.out.println(result.toString());

    }

}
