from multiprocessing import Process
from multiprocessing import Manager
import multiprocessing
import sys
import math


def worker(sub_list,return_dict):
    for data in sub_list:
        if data not in return_dict:
            return_dict[data] = 1
        if data in return_dict:
            return_dict[data] = return_dict[data] + 1

if __name__ == "__main__":

    result = 0

    thread_num = int(sys.argv[1])

    cpus = multiprocessing.cpu_count()

    thread_num = cpus + 1

    num=[]

    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        num.append(data)

    manager = Manager()
    # return_dict = manager.dict()
    return_dict = manager.dict()

    step = math.ceil(len(num) / thread_num)

    jobs = []
    #创建线程
    for i,value in enumerate(range(0,len(num),step)):
        p = Process(target=worker,args=(num[value:value+step],return_dict))
        jobs.append(p)
        p.start()


    # for i in range(thread_num):
    #     p = Process(target=worker, args=(i, return_list))
    #     jobs.append(p)
    #     p.start()

    for proc in jobs:
        proc.join()
    # 最后的结果是多个进程返回值的集合
    sys.stdout.write("%s\n" % return_dict)
    # print(sum(return_list))
    # print(return_dict.values())
