from os import pardir
import os.path as path
p = path.abspath(path.join(__file__,"../../../test/graphs/generated/gen1.gr"))
file = open(p, "w")
file.write("hello world!")
