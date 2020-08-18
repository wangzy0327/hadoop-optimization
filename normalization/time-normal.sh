#/bin/bash
sum=0.0
n=20
for((i=1;i<=n;i++))
do
hdfs dfs -rm -r output-min/
hdfs dfs -rm -r output-max/
hdfs dfs -rm -r output-normal/
before=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false  -files /root/examples/normalization/MaxMapper.class,/root/examples/normalization/MaxReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-max -mapper "java MaxMapper " -reducer "java MaxReducer"
after=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime=`date -d "$before" +%s%N`
aftertime=`date -d "$after" +%s%N`
diff=$((10#$aftertime-10#$beforetime))
difftime=`echo "scale=9;$diff/1000000000"|bc`
a=`hdfs dfs -cat output-max/*`
if [ -z "$a" ]
then
  echo "empty"
  continue
else
  echo "not empty"
  printf "第"$i"次运行max\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime >> res-max.txt
fi
echo "max : "$a
before1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before1
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false   -files /root/examples/normalization/MinMapper.class,/root/examples/normalization/MinReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-min -mapper 'java MinMapper ' -reducer 'java MinReducer'
after1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime1=`date -d "$before1" +%s%N`
aftertime1=`date -d "$after1" +%s%N`
diff1=$((10#$aftertime1-10#$beforetime1))
difftime1=`echo "scale=9;$diff1/1000000000"|bc`
b=`hdfs dfs -cat output-min/*`
if [ -z "$b" ]
then
  echo "empty"
  continue
else
  echo "not empty"
  printf "第"$i"次运行min\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime1 >> res-min.txt
fi
echo "min : "$b
before2=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before2
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false -files /root/examples/normalization/NormalMapper.class,/root/examples/normalization/NormalReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-normal -mapper 'java NormalMapper 0 101' -reducer 'java NormalReducer' -cmdenv 'min='$a -cmdenv 'max='$b
after2=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime2=`date -d "$before2" +%s%N`
aftertime2=`date -d "$after2" +%s%N`
diff2=$((10#$aftertime2-10#$beforetime2))
difftime2=`echo "scale=9;$diff2/1000000000"|bc`
d=`hdfs dfs -cat output-normal/*`
if [ -z "$d" ]
then
  echo "empty"
  continue
else
  echo "not empty"
  printf "第"$i"次运行normal\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime2 >> res-normal.txt
fi
echo "normalization : "$d
sum=`echo "scale=9;$sum+$difftime+$difftime1+$difftime2"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

