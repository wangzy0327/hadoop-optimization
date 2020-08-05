#/bin/bash
sum=0.0
n=20
for((i=1;i<=n;i++))
do
before=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false -D mapred.min.split.size=104857600 -D mapred.map.tasks=20 -files /root/examples/sum/mapper-sum.py,/root/examples/sum/reducer-sum.py -input /user/root/input/big.txt -output /user/root/output-sum -mapper "python3 mapper-sum.py " -reducer "python3 reducer-sum.py"
after=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime=`date -d "$before" +%s%N`
aftertime=`date -d "$after" +%s%N`
diff=$((10#$aftertime-10#$beforetime))
difftime=`echo "scale=9;$diff/1000000000"|bc`
a=`hdfs dfs -cat output-sum/*`
echo "result : "$a
if [ $a -eq 2200000000 ]; then
   echo "not empty";
   printf "第"$i"次运行\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime >> res-sum.txt
else
  echo "empty";
 fi
hdfs dfs -rm -r output-sum/
sum=`echo "scale=9;$sum+$difftime"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

