class Heap {

    //自上向下堆化，最小堆
    private static void heapify(Integer[] arr,int start,int end){
        while(true){
            int minPos = start;
            if(start*2+1 <= end && arr[start] > arr[start*2+1])
                minPos = start*2+1;
            if(start*2+2 <= end && arr[minPos] > arr[start*2+2])
                minPos = start*2+2;
            if(minPos == start)
                break;
            int tmp = arr[minPos];
            arr[minPos] = arr[start];
            arr[start] = tmp;
            start = minPos;
        }
    }

    public static Integer[] findTopK(Integer[] arr,int k){
        Integer[] result = new Integer[k];
        for(int i = 0;i < k;i++)
            result[i] = arr[i];
        for(int i = k/2-1;i>=0;i--)
            heapify(result,i,result.length-1);
        for(int i = k;i < arr.length;i++)
            if(arr[i] > result[0]){
                result[0] = arr[i];
                heapify(result,0,result.length-1);
            }
        return result;
    }
}
