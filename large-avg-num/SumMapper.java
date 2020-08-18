import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SumMapper {
    private static void handler(List<Long> arrList){
        long sum = 0;
        for(Long tmp:arrList)
            sum += tmp;
        System.out.println(sum);
    }
    private static void sleepHelper(){
        try {
            TimeUnit.SECONDS.sleep(1);
//            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int execLen = 100000;
        List<Long> arrList = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("exit".equals(line))
                break;
            if("".equals(line))
                continue;
            arrList.add(Long.valueOf(line));
	    /*
            if(arrList.size() >= execLen){
                handler(arrList);
                sleepHelper();
                arrList.clear();
            }
            */
        }
        if(arrList!=null && arrList.size()!=0)
            handler(arrList);
    }
}
