import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Quiz_check {

    private static final String FILE_PATH = System.getProperty("user.home") + "/IdeaProjects/test/src/file.txt";
    static File file = new File(FILE_PATH);

    public static void main(String[] args) throws FileNotFoundException {
        int [][] jumja = {{0, 0, 0, 0, 0, 1}};
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[][] storedArray = parseJumjaArray(line);
            if (isArraysEqual(jumja, storedArray)) {
                System.out.println(Arrays.deepToString(storedArray));
                System.out.println("oh~ you're right!");
            }
        }
    }

    private static int[][] parseJumjaArray(String line) {

        String cleanedLine = line.replaceAll("\\[|\\]", "");

        // 쉼표를 구분자로 사용하여 문자열 분할
        String[] elements = cleanedLine.split(",");

        int[][] jumjaArray = new int[1][elements.length];
        for (int i = 0; i < elements.length; i++) {
            jumjaArray[0][i] = Integer.parseInt(elements[i].trim());
        }

        return jumjaArray;
    }

    private static boolean isArraysEqual(int[][] array1, int[][] array2) {
        // 두 점자 배열이 일치하는지 비교하는 로직 구현
        // 예: 두 배열의 크기와 요소가 일치하는지 확인
        // 여기서는 단순히 Arrays.deepEquals 메소드를 사용한 예시를 제공합니다.
        return Arrays.deepEquals(array1, array2);
    }






}
