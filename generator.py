#This script generates the inputs for the algorithm
import random
number_of_points = int(input("Enter the number of points to generate: "))
k_min = int(input("Enter min K value: "))
k_max = int(input("Enter max K value: "))
print("...generating " + str(number_of_points) + " points...")
k = random.randint(k_min, k_max)
file_name = str(number_of_points) + "_points.txt"
f = open(file_name, "w")
print("...writing to file " + file_name + "...")
f.write(str(number_of_points) + "\n")
f.write(str(k) + "\n")
for i in range(0,number_of_points):
    f.write(str(random.randint(-1000,1000)) + " " + str(random.randint(-1000,1000)) + " " + str(random.randint(-1000,1000)))
    if(i != number_of_points-1):
        f.write("\n")
f.close()
print("...done!")