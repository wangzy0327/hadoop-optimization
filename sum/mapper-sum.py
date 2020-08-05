import sys

result = 0

for data in sys.stdin:
    data = data.strip()
    if data == "":
        continue
    elif data == "exit":
        break
    result = result + int(data)
sys.stdout.write("%s\n" % (result))
