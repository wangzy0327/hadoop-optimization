public class Avg {
    public static void main(String[] args) {
        double sum = Double.valueOf(args[0]);
        double count = Double.valueOf(args[1]);
        System.out.printf("%.3f\n",(sum/count));
    }
}

