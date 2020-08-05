import sys

res = {}
if __name__ == "__main__":
    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        key = data.split(":")[0].strip()
        value = data.split(":")[1].strip().strip('[').strip(']').split(",")
        if key not in res:
            res[key] = float(value[0].strip())/int(value[1].strip())
        else:
            res[key] = (res[key] + float(value[0].strip())/int(value[1].strip()))/2
    sys.stdout.write("%s\n" % (res))