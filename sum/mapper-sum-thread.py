# -*- coding: UTF-8 -*-
import sys
import threading
import math


class MyThread (threading.Thread):
    def __init__(self, name, counter:list):
        threading.Thread.__init__(self)
        self.res = 0
        self.name = name
        self.counter = counter
        #print("线程 : "+self.name)

    def run(self):
        self.res = sum(self.counter)

    def get_result(self):
        try:
            return self.res  # 如果子线程不使用join方法，此处可能会报没有self.result的错误
        except Exception:
            return None

if __name__ == "__main__":

    result = 0

    thread_num = int(sys.argv[1])

    num=[]

    thd = []

    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        num.append(int(data))


    step = math.ceil(len(num) / thread_num)

    #创建线程
    for i,value in enumerate(range(0,len(num),step)):
        thd.append(MyThread("thread-"+str(i),num[value:value+step]))

    #启动线程
    for t in thd:
        t.start()

    #等待所有线程执行结束
    for t in thd:
        t.join()
        result = result + t.get_result()

    sys.stdout.write("%s\n" % (result))
    print("主线程结束\n")
