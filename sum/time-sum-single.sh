#/bin/bash
sum=0.0
n=10
for((i=1;i<=n;i++))
do
before=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
echo $before
cat big.txt | python3 mapper-sum.py | python3 reducer.py >> result.txt
after=`date -u "+%Y-%m-%d %H:%M:%S.%N"`
beforetime=`date -d "$before" +%s%N`
aftertime=`date -d "$after" +%s%N`
diff=$((10#$aftertime-10#$beforetime))
difftime=`echo "scale=9;$diff/1000000000"|bc`
a=`cat result.txt`
echo "result : "$a
if [ $a -eq 2200000000 ]; then
   echo "not empty";
   printf "第"$i"次运行\t时间差:%0.9f s \t还差"$(($n-$i))"次\n" $difftime >> res-sum-single.txt
else
  echo "empty";
fi
rm -rf result.txt
sum=`echo "scale=9;$sum+$difftime"|bc`
done
echo "sum time : "$sum
avg=`echo "scale=9;$sum/$n"|bc`
echo "avg time : "$avg

