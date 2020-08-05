from multiprocessing import Manager
from multiprocessing import Process
import multiprocessing
import math
import sys


def judge(nums:list,return_dict:dict):
    for num in nums:
        if num in return_dict:
            continue
        if num == 1:
            return
        if num == 2 or num == 3:
            return_dict[num] = True
        for i in range(2,math.floor(math.sqrt(num))+1):
            if num % i == 0:
                return
        return_dict[num] = True




if __name__ == "__main__":
    thread_num = int(sys.argv[1])

    num=[]

    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        num.append(int(data))

    cpus = multiprocessing.cpu_count()

    thread_num = cpus + 1

    manager = Manager()
    # return_dict = manager.dict()
    return_dict = manager.dict()

    step = math.ceil(len(num) / thread_num)

    jobs = []
    # 创建线程
    for i, value in enumerate(range(0, len(num), step)):
        p = Process(target=judge, args=(num[value:value + step],return_dict))
        jobs.append(p)
        p.start()

    # for i in range(thread_num):
    #     p = Process(target=worker, args=(i, return_list))
    #     jobs.append(p)
    #     p.start()

    for proc in jobs:
        proc.join()
    # 最后的结果是多个进程返回值的集合
    if return_dict != None:
        res = set(return_dict.keys())
    sys.stdout.write("%s\n" % (res))
