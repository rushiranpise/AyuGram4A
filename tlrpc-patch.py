import re


with open('./TMessagesProj/src/main/java/org/telegram/tgnet/TLRPC.java', encoding='utf-8') as f:
    data = f.read()

r1 = re.compile(r'noforwards = (.+);')
r2 = re.compile(r'restricted = (.+);')

data = r1.sub('noforwards = false;', data)
data = r2.sub('restricted = false;', data)

with open('./TMessagesProj/src/main/java/org/telegram/tgnet/TLRPC.java', 'w', encoding='utf-8') as f:
    f.write(data)
