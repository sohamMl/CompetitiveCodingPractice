resultF = open('result.txt','r')
expectedF = open('expected.txt','r') 

result=[]
for line in resultF:
    result.append(line.strip())

expected=[]
for line in expectedF:
    expected.append(line.strip())

# print(result)
# print(expected)

for i in range(0,len(result)):
    if result[i] != expected[i]:
        print(str(i+1)+"  "+result[i]+" "+expected[i])

