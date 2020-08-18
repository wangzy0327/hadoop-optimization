import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SumReducer {
    private static Long handler(List<Long> arrList){
        long sum = 0L;
        for(Long tmp:arrList)
            sum += tmp;
        return sum;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int execLen = 100000;
        List<Long> arrList = new ArrayList<>();
        Long sum = 0L;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("exit".equals(line))
                break;
            if("".equals(line))
                continue;
            arrList.add(Long.valueOf(line));
            if(arrList.size() >= execLen){
                sum += handler(arrList);
                arrList.clear();
            }
        }
        if(arrList != null && arrList.size()!=0)
            sum += handler(arrList);
        System.out.println(sum);
    }
}