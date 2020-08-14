import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Heap {

    //自上向下堆化，最小堆
    private static void heapify(List<Integer> arr,int start,int end){
        while(true){
            int minPos = start;
            if(start*2+1 <= end && arr.get(start) > arr.get(start*2+1))
                minPos = start*2+1;
            if(start*2+2 <= end && arr.get(minPos) > arr.get(start*2+2))
                minPos = start*2+2;
            if(minPos == start)
                break;
            int tmp = arr.get(minPos);
            arr.set(minPos,arr.get(start));
            arr.set(start,tmp);
            start = minPos;
        }
    }

    public static List<Integer> findTopK(List<Integer> arr, int k){
        k = (k > arr.size())?arr.size():k;
        List<Integer> res = new ArrayList<>();
        for(int i = 0;i < k;i++)
            res.add(new Integer(arr.get(i)));
        for(int i = k/2-1;i>=0;i--)
            heapify(res,i,res.size()-1);
        for(int i = k;i < arr.size();i++)
            if(arr.get(i) > res.get(0)){
                res.set(0,new Integer(arr.get(i)));
                heapify(res,0,res.size()-1);
            }
        return res;
    }

    public static List<Integer> findTopK(List<Integer> arr, int k,int start,int end){
        k = (k > (end - start))?(end - start):k;
        List<Integer> res = new ArrayList<>();
        for(int i = start;i < start + k;i++)
            res.add(new Integer(arr.get(i)));
        for(int i = k/2-1;i >= 0;i--)
            heapify(res,i,res.size()-1);
        for(int i = start + k;i < end;i++)
            if(arr.get(i) > res.get(0)){
                res.set(0,new Integer(arr.get(i)));
                heapify(res,0,res.size()-1);
            }
        return res;
    }

    public static void findTopK(List<Integer> arr, List<Integer> res,int k,int start,int end){
        k = (k > (end - start))?(end - start):k;
        for(int i = start;i < start + k;i++)
            res.add(new Integer(arr.get(i)));
        for(int i = k/2-1;i >= 0;i--)
            heapify(res,i,res.size()-1);
        for(int i = start + k;i < end;i++)
            if(arr.get(i) > res.get(0)){
                res.set(0,new Integer(arr.get(i)));
                heapify(res,0,res.size()-1);
            }
    }
}
