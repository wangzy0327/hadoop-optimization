import sys

res = {}
if __name__ == "__main__":
    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        key = data.split(":")[0].strip()
        value = float(data.split(":")[1].strip())
        if key not in res:
            res[key] = [value,1]
        else:
            res[key][0] = res[key][0] + value
            res[key][1] = res[key][1] + 1
    sys.stdout.write("%s\n" % (res))