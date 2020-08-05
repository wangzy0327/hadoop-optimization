import sys

res = {}
if __name__ == "__main__":
    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        if data not in res:
            res[data] = 1
        if data in res:
            res[data] = res[data] + 1
    sys.stdout.write("%s\n" % (res))
