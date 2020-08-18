import java.util.Scanner;

public class MinReducer {
    public static void main(String[] args) {
        int min = Integer.MAX_VALUE;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            int tmp = Integer.valueOf(line).intValue();
            if(tmp < min)
                min = tmp;
        }
        System.out.println(min);
    }
}
