#!/bin/bash
n=8
printf "总共复制"$n"次\n"
for((i=1;i<=n;i++))
do
cat 1.txt >> big.txt
printf "第"$i"次\t 内容拷贝 还剩"$(($n-$i))"次\n"
done
