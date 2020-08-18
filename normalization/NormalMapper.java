import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NormalMapper {
    public static void main(String[] args) {
        System.out.println(System.getenv("min"));
        int min = Integer.valueOf(System.getenv("min")).intValue();
        int max = Integer.valueOf(System.getenv("max")).intValue();
        int diff = (max-min);
        List<Double> res = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            int tmp = Integer.valueOf(line).intValue();
            double cvtTmp = (tmp - min)*1.0d/diff;
            res.add(cvtTmp);
        }
        res.stream().limit(10).forEach(System.out::println);
    }
}
