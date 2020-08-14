import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SumThreadPoolMapper {
    private static void worker(ThreadPoolExecutor threadPool, List<Long> sumList, List<Long> arrList, int splitSize){
        int threadNum = threadPool.getCorePoolSize();
        for(int j = 1;j <= threadNum;j++){
            int start = (j-1)*splitSize;
            if(start >= arrList.size())
                break;
            int end = Math.min(j*splitSize,arrList.size());
            int index = j - 1;
            threadPool.execute(()->{Long value = handler(arrList,start,end);sumList.set(index,value);});
        }
    }

    public static Long handler(List<Long> arrList,int start,int end){
        long sum = 0L;
        for(int i = start;i < end;i++)
            sum += arrList.get(i).longValue();
        return sum;
    }

    private static void sleepHelper(){
        try {
            TimeUnit.MICROSECONDS.sleep(100);
//            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int coreNum = Integer.valueOf(args[0]);
        Scanner sc = new Scanner(System.in);
        List<Long> arrList = new ArrayList<>();
//        Map<String,Long> res = new HashMap<>(1000);
        Long res = 0L;
        List<Long> sumList = new ArrayList<>(coreNum);
        for(int i = 0;i < coreNum;i++)
            sumList.add(Long.valueOf(0L));
        int execLen = 100000;
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
                arrList.add(Long.valueOf(line));
//                if(arrList.size()>=execLen){
//                    int splitSize = (int)Math.ceil(arrList.size()*1.0d/coreNum);
//                    worker(threadPool,arrList,splitSize);
//                    arrList.clear();
//                }
            }
            if(arrList != null && arrList.size() > 0) {
                int splitSize = (int) Math.ceil(arrList.size() * 1.0d / coreNum);
                long start = System.nanoTime();
                worker(threadPool, sumList, arrList, splitSize);
                threadPool.shutdown();
                threadPool.awaitTermination(1,TimeUnit.HOURS);
                for(Long ele:sumList)
                    res += ele;
                long time = System.nanoTime() - start;
 //               System.out.printf("Tasks take %.3f us to run\n",time/1e3);
                System.out.println(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

}

