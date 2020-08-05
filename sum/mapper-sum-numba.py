
from numba import jit
import sys

result = 0

@jit(nopython=True)
def add(l:list):
    sum = 0
    for ele in l:
        sum += ele
    return sum


if __name__ == "__main__":
    l = []
    for data in sys.stdin:
        data = data.strip()
        if data == "exit":
            break
        if len(l) >= 100000:
            result += add(l)
            l.clear()
        l.append(int(data))
    result += add(l)
    sys.stdout.write("%s\n" % (result))


