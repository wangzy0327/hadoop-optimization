import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ModeThreadPoolMapper {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static void worker(ThreadPoolExecutor threadPool,List<String> arrList,int splitSize){
        int threadNum = threadPool.getCorePoolSize();
        Thread[] threads = new Thread[threadNum];
        for(int j = 1;j <= threads.length;j++){
            if((j-1)*splitSize >= arrList.size())
                break;
            List<String> arrList2 = new ArrayList<>();
            for (int i = (j-1)*splitSize;i < Math.min(j*splitSize,arrList.size());i++)
                arrList2.add(new String(arrList.get(i).toCharArray()));
            threads[j-1] = new Thread(()->handler(arrList2));
            threadPool.execute(threads[j-1]);
        }
    }

    private static void handler(List<String> arr){
        Map<String,Long> map = new HashMap<>(1000);
        for (String line:arr) {
            if (!map.containsKey(line))
                map.put(line, 1l);
            else
                map.put(line, map.get(line) + 1);
        }
        readWriteLock.writeLock().lock();
        map.forEach((k,v)->System.out.println(k+" : "+v));
        readWriteLock.writeLock().unlock();
        sleepHelper();
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
        int coreNum = Integer.valueOf(args[0]);
        Scanner sc = new Scanner(System.in);
        List<String> arrList = new ArrayList<>();
//        Map<String,Long> res = new HashMap<>(1000);
        int execLen = 1000000;
        int queueLen = execLen*10;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreNum,
                coreNum*2,
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
                if(arrList.size()>execLen){
                    int splitSize = (int)Math.ceil(arrList.size()*1.0d/coreNum);
                    worker(threadPool,arrList,splitSize);
                    arrList.clear();
                }
            }
            if(arrList != null && arrList.size() > 0) {
                int splitSize = (int) Math.ceil(arrList.size() * 1.0d / coreNum);
                worker(threadPool, arrList, splitSize);
            }
        } finally {
            threadPool.shutdown();
        }
    }
}

