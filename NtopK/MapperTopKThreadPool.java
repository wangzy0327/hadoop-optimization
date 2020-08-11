import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class MapperTopKThreadPool {

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static void worker(ThreadPoolExecutor threadPool,int splitSize,List<Integer> arrList,int k) {
//        FutureTask[] futureTasks = new FutureTask[threadNum];
        int threadNum = threadPool.getCorePoolSize();
        Thread[] threads = new Thread[threadNum];
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for(int j = 1;j <= threads.length;j++){
            if((j-1)*splitSize >= arrList.size())
                break;
            List<Integer> arrList2 = new ArrayList<>();
            for(int i = (j-1)*splitSize;i < Math.min(j*splitSize,arrList.size());i++)
                arrList2.add(new Integer(arrList.get(i)));
            threads[j-1] = new Thread(()->{
                List<Integer> threadList = Heap.findTopK(arrList2,Math.min(k,arrList2.size()));
                sleepHelper();
                readWriteLock.writeLock().lock();
                printList(threadList);
                readWriteLock.writeLock().unlock();
//                countDownLatch.countDown();
            });
            threadPool.execute(threads[j-1]);
        }
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
        int execLen = 100000;
        int queueLen = execLen*10;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreNum,
                coreNum * 2,
                2L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(queueLen),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try {
            //        FileInputStream inputStream = null;
            //        String path = "/user/root/input/big-foo.txt";
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (("exit").equals(line.trim()))
                    break;
                arrList.add(Integer.valueOf(line));
                if (arrList.size() >= execLen) {
                    int splitSize = (int)Math.ceil(arrList.size()*1.0d/coreNum);
                    worker(threadPool, splitSize, arrList, k);
                    arrList.clear();
                }
            }
            if (arrList != null && arrList.size() != 0){
                int splitSize = (int)Math.ceil(arrList.size()*1.0d/coreNum);
                worker(threadPool, splitSize, arrList, k);
            }
        }
        finally {
            threadPool.shutdown();
        }

//        int coreNum = Runtime.getRuntime().availableProcessors();
//        System.out.println("processor core : "+coreNum);

    }

}
