import java.util.Scanner;

public class MaxReducer {
    public static void main(String[] args) {
        int max = Integer.MIN_VALUE;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            int tmp = Integer.valueOf(line).intValue();
            if(tmp > max)
                max = tmp;
        }
        System.out.println(max);
    }
}
