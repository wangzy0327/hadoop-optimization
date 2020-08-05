import math
import sys


def judge(num:int)->bool:
    if num == 1:
        return False
    if num == 2 or num == 3:
        return True
    for i in range(2,math.floor(math.sqrt(num))+1):
        if num % i == 0:
            return False
    return True


if __name__ == "__main__":

    # for i in range(2, math.floor(math.sqrt(2))):
    #     print("hello")
    # judge(15)


    res = set()
    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        data = int(data)
        if data in res:
            continue
        elif judge(data):
            res.add(data)

    sys.stdout.write("%s\n" % (res))
