import sys
res = {}

if __name__ == "__main__":
    for line in sys.stdin:
        line = line.strip().strip('\n')
        if line == "exit":
            break
        dict1 = eval(line)
        for key,value in dict1.items():
            res[key] = dict1[key]
    for key,value in res.items():
        sys.stdout.write("%s : %f\n" % (key,value))
    pass
