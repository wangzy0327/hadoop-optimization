import sys
res = {}

if __name__ == "__main__":
    for line in sys.stdin:
        line = line.strip().strip('\n')
        if line == "exit":
            break
        dict1 = eval(line)
        for key,value in dict1.items():
            if key in res:
                res[key] += dict1[key]
            else:
                res[key] = dict1[key]
    for key,value in res.items():
        sys.stdout.write("%s : %d\n" % (key,value))
    pass

