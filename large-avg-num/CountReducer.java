import java.util.Scanner;

public class CountReducer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long i = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("exit".equals(line))
                break;
            if("".equals(line))
                continue;
            i += Long.valueOf(line).longValue();
        }
        System.out.println(i);
    }
}
