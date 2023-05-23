import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class learning_number {
    public static final HashMap<String , int[][]> NUMBER = new HashMap<String, int[][]>() {{
        put("0", new int[][] {{0,1,0,1,1,0}});
        put("1", new int[][] {{1,0,0,0,0,0}});
        put("2", new int[][] {{1,1,0,0,0,0}});
        put("3", new int[][] {{1,0,0,1,0,0}});
        put("4", new int[][] {{1,0,0,1,1,0}});
        put("5", new int[][] {{1,0,0,0,1,0}});
        put("6", new int[][] {{1,1,0,1,0,0}});
        put("7", new int[][] {{1,1,0,1,1,0}});
        put("8", new int[][] {{1,1,0,0,1,0}});
        put("9", new int[][] {{0,1,0,1,0,0}});
    }};

    private static ArrayList<Object> convertToBraille(String number) {
        ArrayList<Object> result = new ArrayList<Object>();
        result.add(new int[]{0, 0, 1, 1, 1, 1});

        for (int i = 0; i < number.length(); i++) {
            int[][] NumArray = NUMBER.get(String.valueOf(number.charAt(i)));
            for (int j = 0; j < NumArray.length; j++) {
                result.add(NumArray[j]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String number = "815";
        ArrayList<Object> FinalResult = convertToBraille(number);
        String Braille = Arrays.deepToString(FinalResult.toArray());
        System.out.println(Braille);
    }

}
