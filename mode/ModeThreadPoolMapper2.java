import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ModeThreadPoolMapper2 {
    private static void worker(ThreadPoolExecutor threadPool,List<Map<String,Long>> mapList,List<String> arrList,int splitSize){
        int threadNum = threadPool.getCorePoolSize();
        for(int j = 1;j <= threadNum;j++){
            int start = (j-1)*splitSize;
            if(start >= arrList.size())
                break;
            int end = Math.min(j*splitSize,arrList.size());
            Map<String,Long> map = mapList.get(j-1);
            threadPool.execute(()->handler(arrList,map,start,end));
        }
    }


    private static void handler(List<String> arr,Map<String,Long> map,int start,int end){
        for (int i = start;i < end;i++) {
            String line = arr.get(i);
            if (!map.containsKey(line))
                map.put(line, 1l);
            else
                map.put(line, map.get(line) + 1);
        }
    }

    private static void sleepHelper(){
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int coreNum = Integer.valueOf(args[0]);
        Scanner sc = new Scanner(System.in);
        List<String> arrList = new ArrayList<>();
        Map<String,Long> res = new HashMap<>(1000);
        List<Map<String,Long>> mapList = new ArrayList<>();
        for(int i = 0;i < coreNum;i++)
            mapList.add(new HashMap<>(500));
        int execLen = 1000000000;
        int queueLen = execLen;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreNum,
                coreNum,
                2L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(queueLen),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try{
            while(sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if("".equals(line))
                    continue;
                if(("exit").equals(line))
                    break;
                arrList.add(line);
            }
            if(arrList != null && arrList.size() > 0) {
                int splitSize = (int) Math.ceil(arrList.size() * 1.0d / coreNum);
                //long start = System.nanoTime();
                worker(threadPool, mapList, arrList, splitSize);
//                worker2(threadPool);
                threadPool.shutdown();
                threadPool.awaitTermination(1, TimeUnit.HOURS);
                for(int i = 0;i < coreNum;i++){
                    Map<String,Long> map = mapList.get(i);
                    for(Map.Entry<String,Long> entry : map.entrySet())
                        if(res.containsKey(entry.getKey()))
                            res.put(entry.getKey(),entry.getValue()+res.get(entry.getKey()));
                        else
                            res.put(entry.getKey(),entry.getValue());
                }
                //long time = System.nanoTime() - start;
                //System.out.printf("Tasks take %.3f us to run\n",time/1e3);
                res.forEach((k,v)->System.out.println(k+"\t"+v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            threadPool.shutdown();
        }
    }
}
