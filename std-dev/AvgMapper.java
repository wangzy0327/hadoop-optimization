import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AvgMapper {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Long> arrList = new ArrayList<>();
        long sum = 0;
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
            long count = arrList.size();
            for(Long ele:arrList)
                sum += ele;
            long time = System.nanoTime() - start;
            //System.out.printf("Tasks take %.3f us to run\n",time/1e3);
            System.out.println(sum+"\t"+count);
        }
    }
}
