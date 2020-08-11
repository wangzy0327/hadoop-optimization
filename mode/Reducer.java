import java.util.*;

public class Reducer {
    private static String modeKey = null;
    private static Long modeValue = 0L;
    public static void main(String[] args) {
        Map<String,Long> map = new HashMap<>(1000);
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if(("exit").equals(line))
                break;
            String[] eles = line.split(":");
            if(eles.length != 2)
                throw new IllegalArgumentException();
            if(map.containsKey(eles[0].trim())) {
                map.put(eles[0].trim(), map.get(eles[0].trim()) + Long.parseLong(eles[1].trim()));
                if(modeKey == null || modeValue < map.get(eles[0].trim())) {
                    modeKey = eles[0].trim();
                    modeValue =  map.get(eles[0].trim());
                }
            }
            else{
                map.put(eles[0].trim(),Long.parseLong(eles[1].trim()));
                if(modeKey == null || modeValue < map.get(eles[0].trim())) {
                    modeKey = eles[0].trim();
                    modeValue =  map.get(eles[0].trim());
                }
            }
        }
//        map.forEach((k,v)->{
//            if(modeKey == null){
//                modeKey = k;
//                modeValue = v;
//            }
//            else if(v > modeValue){
//                modeKey = k;
//                modeValue = v;
//            }
//        });
        System.out.println(modeKey+" : "+modeValue);
    }
}

