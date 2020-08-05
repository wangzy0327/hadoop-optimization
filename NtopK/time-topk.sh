#/bin/bash
sum=0.0
n=10
for((i=1;i<=n;i++))
do
before=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.9.2.jar -D stream.non.zero.exit.is.failure=false -D mapred.min.split.size=104857600 -D mapred.map.tasks=20 -files /root/examples/NtopK/mapper-n-topk.py,/root/examples/NtopK/reducer.py -input /user/root/input/big-foo.txt -output /user/root/output-topk -mapper "python3 mapper-n-topk.py 10 " -reducer "python3 reducer.py"
after=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime=`date -d "$before" +%s%N`
aftertime=`date -d "$after" +%s%N`
diff=$((10#$aftertime-10#$beforetime))
difftime=`echo "scale=9;$diff/1000000000"|bc`
a=`hdfs dfs -cat output-topk/*`
if [ -z "$a" ]
then
  echo "empty"
else
  echo "not empty"
  printf "第"$i"次运行\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime >> res-topk.txt
fi
hdfs dfs -rm -r output-topk/
sum=`echo "scale=9;$sum+$difftime"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

