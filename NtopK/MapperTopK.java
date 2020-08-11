import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MapperTopK {
    private static void printList(List<Integer> list){
        for(int i = 0;i < list.size();i++){
            if (i == 0)
                System.out.print("[");
            if (i != list.size() - 1)
                System.out.print(list.get(i)+", ");
            else
                System.out.print(list.get(i));
            if (i == list.size() - 1)
                System.out.println("]");
        }
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
        int k = Integer.valueOf(args[0]);
        List<Integer> arrList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int execLen = 10000;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(("exit").equals(line.trim()))
                break;
            arrList.add(Integer.valueOf(line));
            if(arrList.size() >= execLen){
                List<Integer> res = Heap.findTopK(arrList,Math.min(arrList.size(),k));
                sleepHelper();
                printList(res);
                arrList.clear();
            }
        }
        if(arrList != null && arrList.size() != 0){
            List<Integer> res = Heap.findTopK(arrList,Math.min(arrList.size(),k));
            printList(res);
        }
    }
}

