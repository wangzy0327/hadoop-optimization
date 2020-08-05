# -*- coding: UTF-8 -*-
import random

# 打开一个文件
fo = open("foo.txt", "w")
print("文件名: ", fo.name)
n = 1000000
for i in range(0,n):
    print(f"第{i}次\t 随机数 还剩{n-i}次")
    fo.write(str(random.randint(0,100))+"\n")

# 关闭打开的文件
fo.close()
