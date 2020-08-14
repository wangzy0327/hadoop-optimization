import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StdReducer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrList = new ArrayList<>();
        double devSquare = 0d;
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if("".equals(line))
                continue;
            if(("exit").equals(line))
                break;
            arrList.add(Double.valueOf(line));
        }
        if(arrList != null && arrList.size() > 0) {
            for(Double ele:arrList)
                devSquare += ele;
            double res = Math.pow(devSquare,0.5);
            System.out.println(res);
        }
    }
}
