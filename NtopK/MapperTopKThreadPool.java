import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MapperTopKThreadPool {

    private static void worker(ThreadPoolExecutor threadPool,List<List<Integer>> topKList,int splitSize,List<Integer> arrList,int k) {
//        FutureTask[] futureTasks = new FutureTask[threadNum];
        int threadNum = threadPool.getCorePoolSize();
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for(int j = 1;j <= threadNum;j++){
            int start = (j-1)*splitSize;
            if(start >= arrList.size())
                break;
            int end = Math.min(j*splitSize,arrList.size());
            List<Integer> topK = topKList.get(j-1);
            threadPool.execute(()-> Heap.findTopK(arrList,topK,Math.min(k,end - start),start,end));
        }
    }

    private static void sleepHelper(){
        try {
//            TimeUnit.MICROSECONDS.sleep(100);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        if(args.length < 2){
            throw new IllegalArgumentException("参数不正确，请输入两个参数");
        }
        int k = Integer.valueOf(args[0]);
        int coreNum = Integer.valueOf(args[1]);
        List<Integer> arrList = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> topKList = new ArrayList();
        for(int i = 0;i < coreNum;i++)
            topKList.add(new ArrayList<>(k));
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
        try {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (("exit").equals(line.trim()))
                    break;
                arrList.add(Integer.valueOf(line));
            }
            if (arrList != null && arrList.size() != 0){
                int splitSize = (int)Math.ceil(arrList.size()*1.0d/coreNum);
                long start = System.nanoTime();
                worker(threadPool, topKList, splitSize, arrList, k);
                threadPool.shutdown();
                threadPool.awaitTermination(1,TimeUnit.HOURS);
                for(int i = 0;i < topKList.size();i++) {
                    List<Integer> list = topKList.get(i);
                    for (int j = 0; j < list.size(); j++)
                        res.add(list.get(j));
                }
                long time = System.nanoTime() - start;
                System.out.printf("Tasks take %.3f us to run\n",time/1e3);
                List<Integer> resArr = Heap.findTopK(res,k);
 //               printList(resArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

//        int coreNum = Runtime.getRuntime().availableProcessors();
//        System.out.println("processor core : "+coreNum);

    }

}
