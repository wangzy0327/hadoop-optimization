import java.util.*;
import java.util.concurrent.TimeUnit;

public class ModeMapper {
    public static void main(String[] args) {
        Map<String,Long> map = new HashMap<>(1000);
        Scanner sc = new Scanner(System.in);
        int execLen = 100000;
        int i = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("".equals(line))
                continue;
            if(("exit").equals(line))
                break;
            hander(map,line);
            sleepHelper();
            i++;
            if(i > execLen){
                map.forEach((k,v)-> System.out.println(k+" : "+v));
                map.clear();
                i = 0;
            }
        }
        map.forEach((k,v)-> System.out.println(k+" : "+v));
    }

    private static void sleepHelper(){
        try {
            TimeUnit.SECONDS.sleep(1);
//            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void hander(Map<String,Long> map,String line){
        if(!map.containsKey(line))
            map.put(line,1l);
        else
            map.put(line,map.get(line)+1);
    }
}

