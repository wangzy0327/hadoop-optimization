# hadoop框架优化

hadoop的mapreduce计算框架，采用两种手段进行优化，这里采用了hadoop streaming编程工具，只需自定义mapper和reducer的可执行程序或脚本，支持多语言来完成 。可结合hadoop在k8s上的部署进行操作，当数据量较大时，需要适当调整hadoop内存等相关配置，hadoop在k8s下的部署可参考：

- hadoop-cluster-k8s: [hadoop在k8s上的部署](https://github.com/wangzy0327/hadoop-cluster-k8s)

1. 每个maptask进行内部并行化，提高计算性能
2. 对切片数量进行调参优化，设置最佳的切片数量，达到计算性能提升

### 切片优化

hadooop提供了一个设置map个数的参数mapred.map.tasks，我们可以通过这个参数来控制map的个数。但是通过这种方式设置map的个数，并不是每次都有效的。原因是mapred.map.tasks只是一个hadoop的参考数值，最终map的个数，还取决于其他的因素。

   为了方便介绍，先来看几个名词：

block_size : hdfs的文件块大小，默认为64M，可以通过参数dfs.block.size设置

total_size : 输入文件整体的大小

input_file_num : 输入文件的个数

**（1）默认map个数**

   如果不进行任何设置，默认的map个数是和blcok_size相关的。

   default_num = total_size / block_size;

**（2）期望大小**

   可以通过参数mapred.map.tasks来设置程序员期望的map个数，但是这个个数只有在大于default_num的时候，才会生效。

   goal_num = mapred.map.tasks;

**（3）设置处理的文件大小**

   可以通过mapred.min.split.size 设置每个task处理的文件大小，但是这个大小只有在大于block_size的时候才会生效。

   split_size = max(mapred.min.split.size, block_size);

   split_num = total_size / split_size;

**（4）计算的map个数**

​    compute_map_num = min(split_num,  max(default_num, goal_num))

​    除了这些配置以外，mapreduce还要遵循一些原则。 mapreduce的每一个map处理的数据是不能跨越文件的，也就是说    min_map_num >= input_file_num。 所以，最终的map个数应该为：

   final_map_num = max(compute_map_num, input_file_num)

   经过以上的分析，在设置map个数的时候，可以简单的总结为以下几点：

（1）如果想增加map个数，则设置mapred.map.tasks 为一个较大的值。

（2）如果想减小map个数，则设置mapred.min.split.size 为一个较大的值。

（3）如果输入中有很多小文件，依然想减少map个数，则需要将小文件merger为大文件，然后使用准则2。

### 内部并行化

采用多进程/多线程方式进行mapper内部的并行化操作。

python3语言由于多线程对Python虚拟机的访问由全局解释器（GIL）来控制，这个锁保证同时只有一个线程在运行，并不能进行多核的并行执行。所以采用multiprocess多子进程方式进行并行优化。

Java语言采用ThreadPoolExecutor线程池进行内部并行化

### 示例

1. sum  累加操作
2. NtopK  找出N个数中最大的k个值（采用堆排序）
3. prime  找出数据集中的素数
4. pv （Page View） 统计数据集中用户访问页面的浏览量或点击量
5. multifile  多文件数据集进行wordcount计算
6. max-min 统计数据集中频数最大的单词和频数最小的单词
7. mean  统计人员的平均分数（多次mapreduce）
8. mode  统计众数
9. avg  求平均值
10. std-dev  求标准差

