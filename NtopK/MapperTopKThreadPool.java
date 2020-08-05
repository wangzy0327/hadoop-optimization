import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


public class MapperTopKThreadPool {

    private static void worker(Integer[] arr,int coreNum,int k){
        int splitSize = (int)Math.ceil(arr.length*1.0d/coreNum);
//        int k = 3;
        int coreSize = coreNum;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreSize,
                coreSize+1,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        FutureTask[] futureTasks = new FutureTask[(int)Math.ceil(arr.length/splitSize)];
        try{
            for(int j = 1;j <= futureTasks.length;j++){
                if((j-1)*splitSize >= arr.length)
                    break;
                Integer[] arr2 = Arrays.copyOfRange(arr,(j-1)*splitSize,Math.min(j*splitSize,arr.length));
                futureTasks[j-1] = new FutureTask(()->Heap.findTopK(arr2,Math.min(k,arr2.length)));
                threadPool.execute(futureTasks[j-1]);
            }
            Integer[] nums;
            List<Integer> list = new ArrayList<>();
            for(int i = 0;i < futureTasks.length;i++) {
                Integer[] tmp = (Integer[]) futureTasks[i].get();
                list.addAll(Arrays.asList(tmp));
            }
            nums =  list.toArray(new Integer[list.size()]);
            Arrays.sort(nums, (o1,o2)-> o2- o1);
            Integer[] res = Arrays.copyOfRange(nums,0,Math.min(nums.length,k));
            System.out.println(Arrays.toString(res));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void main(String[] args) {
        if(args.length < 2){
            throw new IllegalArgumentException("参数不正确，请输入两个参数");
        }
        int k = Integer.valueOf(args[0]);
        int coreNum = Integer.valueOf(args[1]);
        List<Integer> arrList = new ArrayList<>();
        FileInputStream inputStream = null;
        String path = "/user/root/input/big-foo.txt";
        Scanner sc = null;
        Integer[] arr = null;
        sc = new Scanner(System.in);
        while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(("exit").equals(line.trim()))
                    break;
                arrList.add(Integer.valueOf(line));
                if(arrList.size() >= 100000){
                    arr = arrList.toArray(new Integer[arrList.size()]);
                    arrList.clear();
                    worker(arr,coreNum,k);
                    arr = null;
                }
        }
        arr = arrList.toArray(new Integer[arrList.size()]);
        arrList.clear();
        if(arr != null && arr.length != 0)
            worker(arr,coreNum,k);

//        int coreNum = Runtime.getRuntime().availableProcessors();
//        System.out.println("processor core : "+coreNum);

    }

}

