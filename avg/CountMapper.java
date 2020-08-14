import java.util.Scanner;

public class CountMapper {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long i = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("exit".equals(line))
                break;
            if("".equals(line))
                continue;
            i++;
        }
        System.out.println(i);
    }
}

