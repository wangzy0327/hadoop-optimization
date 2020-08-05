import sys
res = {}
max_res = {}


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
        if max_res and list(max_res.values())[0] < res[key]:
            max_res.clear()
            max_res[key] = res[key]
        else:
            max_res[key] = res[key]
    sys.stdout.write("%s : %d\n" % (list(max_res)[0],list(max_res.values())[0]))
    # for key,value in res.items():
    #     sys.stdout.write("%s : %d\n" % (key,value))
    # pass

