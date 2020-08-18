#/bin/bash
sum=0.0
n=20
for((i=1;i<=n;i++))
do
hdfs dfs -rm -r output-sum/
hdfs dfs -rm -r output-count/
hdfs dfs -rm -r output-num/
before=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false  -files /root/examples/large-avg-num/SumMapper.class,/root/examples/large-avg-num/SumReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-sum -mapper "java SumMapper " -reducer "java SumReducer"
after=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime=`date -d "$before" +%s%N`
aftertime=`date -d "$after" +%s%N`
diff=$((10#$aftertime-10#$beforetime))
difftime=`echo "scale=9;$diff/1000000000"|bc`
a=`hdfs dfs -cat output-sum/*`
if [ -z "$a" ]
then
  echo "empty"
  break
else
  echo "not empty"
  printf "第"$i"次运行sum\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime >> res-sum.txt
fi
echo "sum : "$a
before1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before1
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false  -files /root/examples/large-avg-num/CountMapper.class,/root/examples/large-avg-num/CountReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-count -mapper 'java CountMapper '$a'' -reducer 'java CountReducer'
after1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime1=`date -d "$before1" +%s%N`
aftertime1=`date -d "$after1" +%s%N`
diff1=$((10#$aftertime1-10#$beforetime1))
difftime1=`echo "scale=9;$diff1/1000000000"|bc`
b=`hdfs dfs -cat output-count/*`
if [ -z "$b" ]
then
  echo "empty"
  continue
else
  echo "not empty"
  printf "第"$i"次运行count\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime1 >> res-count.txt
fi
echo "count : "$b
c=`java Avg $a $b`
if [ -z "$c" ]
then
  echo "empty"
  continue
fi
echo "avg : "$c
before2=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before2
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false  -files /root/examples/large-avg-num/NumMapper.class,/root/examples/large-avg-num/NumReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-num -mapper 'java NumMapper '$c'' -reducer 'java NumReducer'
after2=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime2=`date -d "$before2" +%s%N`
aftertime2=`date -d "$after2" +%s%N`
diff2=$((10#$aftertime2-10#$beforetime2))
difftime2=`echo "scale=9;$diff2/1000000000"|bc`
d=`hdfs dfs -cat output-num/*`
if [ -z "$d" ]
then
  echo "empty"
  continue
else
  echo "not empty"
  printf "第"$i"次运行num-avg\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime2 >> res-num-avg.txt
fi
echo "large-avg-num : "$d
sum=`echo "scale=9;$sum+$difftime+$difftime1+$difftime2"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

