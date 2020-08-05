# -*- coding: UTF-8 -*-
import random

# 打开一个文件
fo = open("foo.txt", "a")
print("文件名: ", fo.name)
n = 30000000
alpha = 26
num = 100
for i in range(1,n):
    s = ""
    print(f"第{i}次\t 随机数 还剩{n-i}次")
    #随机取大写字母
    c1 = chr(random.randint(65, 90))#取大写字母
    #随机取小写字母
    c2 = chr(random.randint(97, 122))
    c3 = chr(random.randint(97, 122))
    s+=c1
    s+=c2
    s+=c3
    fo.write(s+" : "+str(random.randint(50,num))+"\n")

# 关闭打开的文件
fo.close()
