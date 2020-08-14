import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AvgReducer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<SumCount> arrList = new ArrayList<>();
        long sum = 0;
        long count = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("".equals(line))
                continue;
            if(("exit").equals(line))
                break;
            String[] datas = line.split("\t");
            SumCount sumCount = new SumCount(Long.valueOf(datas[0]),Long.valueOf(datas[1]));
            arrList.add(sumCount);
        }
        if(arrList != null && arrList.size() > 0) {
            for(SumCount ele:arrList){
                sum += ele.getSum();
                count += ele.getCount();
            }
            System.out.printf("%.3f\n",sum*1.0d/count);
        }
    }
}
