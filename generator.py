#This script generates the inputs for the algorithm
import random
number_of_points = int(input("Enter the number of points to generate: "))
k_min = int(input("Enter min K value: "))
k_max = int(input("Enter max K value: "))
k = random.randint(k_min, k_max)
file_name = str(number_of_points) + "_points.txt"
f = open(file_name, "w")
f.write(str(number_of_points) + "\n")
f.write(str(k) + "\n")
set_of_points = []
print("...generating " + str(number_of_points) + " points...")
while len(set_of_points) < number_of_points:
    new_point = [random.randint(-1000,1000), random.randint(-1000,1000), random.randint(-1000,1000)]
    if new_point not in set_of_points:
        set_of_points.append(new_point)
print("...writing to file " + file_name + "...")
for i in range(len(set_of_points)):
    f.write(str(set_of_points[i][0]) + " " + str(set_of_points[i][1]) + " " + str(set_of_points[i][2]))
    if i != len(set_of_points)-1:
        f.write("\n")
f.close()
print("...done!")