import math
import multiprocessing
import sys
from multiprocessing import Process


def find_top_k_largest(nums: list, k: int):
    """堆排序思想"""
    result = [None]*k

    """最小堆"""
    def heapify(a, start, end):
        """ 自上向下堆化
        Args:
            a (list): 输入数组
            start (int): 堆化目标在数组的位置
            end (int): 堆在数组的截止位置
        """
        while True:
            min_pos = start  # 初始化最大值所在位置为目标所在
            if start * 2 + 1 <= end and a[start] > a[start * 2 + 1]:
                # 如果左叶子节点存在，且大于目标值，则将最大值所在位置指向该节点所在位置
                min_pos = start * 2 + 1
            if start * 2 + 2 <= end and a[min_pos] > a[start * 2 + 2]:
                # 如果右叶子节点存在，且大于目标值，则将最大值所在位置指向该节点所在位置
                min_pos = start * 2 + 2
            if min_pos == start:
                # 如果目标即为最小，完成该节点堆化，跳出循环
                break
            # 交换位置，将最小值
            a[start], a[min_pos] = a[min_pos], a[start]
            start = min_pos

    # 建堆,只需要对前半节点堆化 init
    for i in range(k // 2 - 1, -1, -1):
        heapify(nums, i, len(nums) - 1)

    for i in range(0,min(k,len(nums))):
        result[i] = nums[i]

    # 排序，只需要循环K次，排序TOP K个节点
    i = k
    while i < len(nums):
        if nums[i] > result[0]:
            result[0] = nums[i]
            heapify(result,0,len(result)-1)
        i += 1
    sys.stdout.write("%s\n" % (result))


def worker(num:list, thread_num:int, k:int):
        # return_dict = manager.dict()
        # return_list = manager.list()

        step = math.ceil(len(num) / thread_num)

        jobs = []
        # 创建线程
        for i, value in enumerate(range(0, len(num), step)):
            subnum = num[value:value + step]
            p = Process(target=find_top_k_largest, args=(subnum, min(k, len(subnum))))
            jobs.append(p)
            p.start()

        # for i in range(thread_num):
        #     p = Process(target=worker, args=(i, return_list))
        #     jobs.append(p)
        #     p.start()

        #for proc in jobs:
        #    proc.join()
        # 最后的结果是多个进程返回值的集合


if __name__ == "__main__":
    k = int(sys.argv[1])
    thread_num = int(sys.argv[2])

    num=[]

    cpus = multiprocessing.cpu_count()

    thread_num = cpus + 1

    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        if data == "":
            continue
        num.append(int(data))
        if len(num) >= 20000:
            worker(num,thread_num,k)
            num.clear()
            num = []
    if num is not None and len(num) != 0:
        worker(num, thread_num, k)
