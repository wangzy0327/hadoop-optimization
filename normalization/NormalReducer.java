import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NormalReducer {
    public static void main(String[] args) {
        List<Double> res = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            res.add(Double.valueOf(line));
        }
        res.stream().limit(10).forEach(s->System.out.printf("%.3f\n",s));
    }
}
