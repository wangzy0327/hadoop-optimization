import sys

result = set()

for line in sys.stdin:
    line = line.strip().strip('\n')
    if line == "exit":
        break
    list1 = line.strip('{').strip('}').split(',')
    tmp = [i.strip() for i in list1]
    set1 = set(map(int, tmp))
    result = result.union(set1)

sys.stdout.write("%s\n" % (result))
