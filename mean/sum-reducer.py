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
                res[key][0] = res[key][0] + value[0]
                res[key][1] = res[key][1] + value[1]
            else:
                res[key] = [value[0],value[1]]
    for key,value in res.items():
        sys.stdout.write("%s : %s\n" % (key,value))
    pass
