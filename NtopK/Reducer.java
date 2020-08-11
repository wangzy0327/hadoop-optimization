import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reducer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> arrList = new ArrayList<>();
        int k = 1;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String data = line.trim().replace("[","").replace("]","");
            String[] datas = data.split(",");
            if(k < datas.length)
                k = datas.length;
            for(String tmp: datas)
                arrList.add(Integer.valueOf(tmp.trim()));
            if(arrList.size() > 100000){
//                arr = arrList.toArray(new Integer[arrList.size()]);
//                Arrays.sort(arr);
//                Integer[] newArr = Arrays.copyOfRange(arr,arr.length-k-1,arr.length-1);
                List<Integer> newArr = Heap.findTopK(arrList,k);
                arrList = newArr;
            }
        }
//        arr = arrList.toArray(new Integer[arrList.size()]);
        List<Integer> res = Heap.findTopK(arrList,k);
        printList(res);
    }
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
}

