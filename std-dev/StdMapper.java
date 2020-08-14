import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StdMapper {
    public static void main(String[] args) {
        double avg = Double.valueOf(args[0]);
        Scanner sc = new Scanner(System.in);
        List<Long> arrList = new ArrayList<>();
        double devSquare = 0d;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("".equals(line))

                continue;
            if(("exit").equals(line))
                break;
            arrList.add(Long.valueOf(line));
        }
        if(arrList != null && arrList.size() > 0) {
            long start = System.nanoTime();
            for(Long ele:arrList)
                devSquare += Math.pow((avg-ele),2);
            long time = System.nanoTime() - start;
//            System.out.printf("Tasks take %.3f us to run\n",time/1e3);
            System.out.println(devSquare);
        }
    }
}
