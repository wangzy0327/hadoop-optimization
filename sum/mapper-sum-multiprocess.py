import math
import multiprocessing
import sys
from multiprocessing import Process

import numpy as np


def worker(sub_list:list):
    res = np.sum(sub_list)
    sys.stdout.write("%s\n" % (res))
    # res = sum(sub_list)


def handler(num:list, thread_num:int):
    # return_dict = manager.dict()
    # return_list = manager.list()

    step = math.ceil(len(num) / thread_num)

    jobs = []
    #创建线程
    for i,value in enumerate(range(0,len(num),step)):
        subnum = num[value:value + step]
        p = Process(target=worker,args=(subnum,))
        jobs.append(p)
        p.start()


    # for i in range(thread_num):
    #     p = Process(target=worker, args=(i, return_list))
    #     jobs.append(p)
    #     p.start()

    # for proc in jobs:
    #     proc.join()
    # 最后的结果是多个进程返回值的集合
    # result = np.sum(return_list)
    # sys.stdout.write("%s\n" % (result))
    # print(sum(return_list))
    # print(return_dict.values())


if __name__ == "__main__":

    result = 0

    thread_num = int(sys.argv[1])

    cpus = multiprocessing.cpu_count()

    thread_num = cpus + 1

    num=[]

    for data in sys.stdin:
        data = data.strip()
        if data == "":
            continue
        if data == "exit":
            break
        num.append(int(data))
        if len(num) >= 50000000:
            handler(num, thread_num)
            num.clear()
            num = []
    if num is not None and len(num) != 0:
        handler(num, thread_num)



