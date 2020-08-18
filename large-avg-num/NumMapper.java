import java.util.Scanner;

public class NumMapper {
    public static void main(String[] args) {
        double avg = Double.valueOf(args[0]);
        Scanner sc = new Scanner(System.in);
        long i = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("exit".equals(line))
                break;
            if("".equals(line))
                continue;
            long num = Long.valueOf(line).longValue();
            if(num > avg)
                i++;
        }
        System.out.println(i);
    }
}
