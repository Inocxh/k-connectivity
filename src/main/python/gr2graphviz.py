import os.path as path
from sys import argv

source = argv[1]
target = path.basename(path.splitext(source)[0])+".dot"

source_file = open(path.abspath(source))
target_file = open(target,"w")

target_file.write("graph G {\n")

next(source_file) #Skip the first line containing n and m
for line in source_file:
    s = line.split(" ")
    target_file.write(s[0] + " -- " + s[1])
target_file.write("\n}")
    
print("Output written to: " + path.abspath(target));