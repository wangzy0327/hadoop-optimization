# -*- coding: UTF-8 -*-
import random

# 打开一个文件
fo = open("foo.txt", "a")
print("文件名: ", fo.name)
n = 3000000
num = 1000000
for i in range(1,n):
    print(f"第{i}次\t 随机数 还剩{n-i}次")
    fo.write(str(random.randint(1,num))+"\n")

# 关闭打开的文件
fo.close()
