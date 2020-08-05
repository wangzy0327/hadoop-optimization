#!/bin/bash
n=99900000
m=10
printf "总共复制"$n"次"
for((j=1;j<=n;j++))
do
  for((i=1;i<=m;i++))
  do
  #echo $i
  echo $i >> 1.txt
  done
printf "第"$j"次\t 内容拷贝 还剩"$(($n-$j))"次\n"
done
