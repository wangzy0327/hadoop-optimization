#/bin/bash
sum=0.0
n=20
for((i=1;i<=n;i++))
do
before1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before1
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false  -files /root/examples/std-dev/StdMapper.class,/root/examples/std-dev/StdReducer.class -input /user/root/input/mid-foo.txt -output /user/root/output-std-dev -mapper 'java StdMapper 50.019 ' -reducer 'java StdReducer'
after1=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime1=`date -d "$before1" +%s%N`
aftertime1=`date -d "$after1" +%s%N`
diff1=$((10#$aftertime1-10#$beforetime1))
difftime1=`echo "scale=9;$diff1/1000000000"|bc`
b=`hdfs dfs -cat output-std-dev/*`
if [ -z "$b" ]
then
  echo "empty"
else
  echo "not empty"
  printf "第"$i"次运行std-dev\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime1 >> res-std-dev.txt
fi
hdfs dfs -rm -r output-avg/
hdfs dfs -rm -r output-std-dev/
sum=`echo "scale=9;$sum+$difftime+$difftime1"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

