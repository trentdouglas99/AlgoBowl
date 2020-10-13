import math
import statistics
#import numpy as np
#import matplotlib.pyplot as plt
from operator import attrgetter
class subsets_and_averages:
    def __init__(self, subset, average, average_point):
        self.subset = subset
        self.average = average
        self.average_point = average_point


f = open("input_group242.txt")
testInput = []
n = int(f.readline())
k = int(f.readline())
points = []
for x in f:
  point = []
  input = x.split()
  point.append(int(input[0]))
  point.append(int(input[1]))
  point.append(int(input[2]))

  points.append(point)

f.close()

reference = []
for i in points:
    reference.append(i)


# print(n)
# print(k)
# for i in points:
#     print(i)

testInput = points

def powerSet(items):

    N = len(items)
    newList = []
    for i in range(2**N):
        subset=[]
        for j in range(N):
            if (i >> j) % 2 == 1:
                subset.append(items[j])

        newList.append(subset)
    return newList

def removenullSetAndSingles(allCombinations):
    allCombinations.remove([])
    counter = 0
    while (counter != len(allCombinations)-1):
        if(len(allCombinations[counter]) == 1):
            allCombinations.remove(allCombinations[counter])
            counter -= 1
        counter += 1
    return allCombinations;

def calcComboDistances(allPairsOrMore):
    subsets_and_averages_list = []
    for i in allPairsOrMore:
        average_point, average_distance = calcAvgDistance(i)
        class_element = subsets_and_averages(i, average_distance, average_point)
        subsets_and_averages_list.append(class_element)
    return subsets_and_averages_list

def calcAvgDistance(subset):
    xs = []
    ys = []
    zs = []
    x_distances = []
    y_distances = []
    z_distances = []
    euclidian_distances = []
    for i in subset:
        xs.append(i[0])
        ys.append(i[1])
        zs.append(i[2])

    average_x = statistics.mean(xs)
    average_y = statistics.mean(ys)
    average_z = statistics.mean(zs)
    average_point = [average_x, average_y, average_z]

    for i in range(len(subset)):
        x = subset[i][0]
        for j in xs:
            x_distances.append(abs(x - j))

        y = subset[i][1]
        for j in ys:
            y_distances.append(abs(y - j))

        z = subset[i][2]
        for j in zs:
            z_distances.append(abs(z - j))


    for i in range(len(x_distances)):
        euclidian_distances.append(math.sqrt(pow(x_distances[i],2) + pow(y_distances[i], 2) + pow(z_distances[i], 2)))

    average_distance = statistics.mean(euclidian_distances)

    return average_point, average_distance

def pickPapas(k, sorted_list_of_subsets):
    papaGroups = []
    used_points = []
    i = 0
    while len(papaGroups) != k:
        found = False
        for j in sorted_clean_power_set_with_distances[i].subset:
            if(j in used_points):
                found = True
                break
            else:
                found = False
            used_points.append(j)
        if(not found):
            papaGroups.append(sorted_list_of_subsets[i])
        i = i+1




    return papaGroups

def removeChosenPoints(original, list_of_papas):

    for i in list_of_papas:
        for point in i.subset:
            original.remove(point)
    return original

def add_leftovers(list_of_papas, leftovers):
    min_distance = 10000
    papa_group = list_of_papas[0]
    for leftover in leftovers:
        leftover_x = leftover[0]
        leftover_y = leftover[1]
        leftover_z = leftover[2]
        for papa in list_of_papas:
            papa_point_x = papa.average_point[0]
            papa_point_y = papa.average_point[1]
            papa_point_z = papa.average_point[2]
            x_difference = abs(leftover_x - papa_point_x)
            y_difference = abs(leftover_y - papa_point_y)
            z_difference = abs(leftover_z - papa_point_z)
            euclidian_distance = math.sqrt(pow(x_difference,2) + pow(y_difference, 2) + pow(z_difference, 2))
            if(euclidian_distance < min_distance):
                min_distance = euclidian_distance
                papa_group = papa
        list_of_papas[list_of_papas.index(papa_group)].subset.append(leftover)

    return list_of_papas


def calcDistance(finalSet):
    distances = []
    for group in finalSet:
        fakeFinal = group.subset
        for i in range(len(fakeFinal)):
            fakefakeFinal = []
            for aaaa in fakeFinal:
                fakefakeFinal.append(aaaa)

            poi = fakeFinal[i]
            fakefakeFinal.remove(fakeFinal[i])
            for j in range(len(fakefakeFinal)):
                poi_x = poi[0]
                poi_y = poi[1]
                poi_z = poi[2]
                compare_point_x = fakefakeFinal[j][0]
                compare_point_y = fakefakeFinal[j][1]
                compare_point_z = fakefakeFinal[j][2]
                x_difference = abs(poi_x - compare_point_x)
                y_difference = abs(poi_y - compare_point_y)
                z_difference = abs(poi_z - compare_point_z)
                manhattan_distance = x_difference + y_difference + z_difference
                distances.append(manhattan_distance)
            fakefakeFinal = []
            for aaaa in fakeFinal:
                fakefakeFinal.append(aaaa)
    return distances



powerSet = powerSet(testInput)
clean_power_set = removenullSetAndSingles(powerSet)
clean_power_set_with_distances = calcComboDistances(clean_power_set)
sorted_clean_power_set_with_distances = sorted(clean_power_set_with_distances, key=attrgetter('average'))
list_of_papas = pickPapas(k, sorted_clean_power_set_with_distances)
leftovers = removeChosenPoints(testInput, list_of_papas)
finalSet = add_leftovers(list_of_papas, leftovers)


distance_max = calcDistance(finalSet)

print(max(distance_max))

for i in finalSet:
    indexes = []
    for j in i.subset:
        index = reference.index(j)
        indexes.append(index)
    for index in indexes:
        print(index+1, end =" ")
    print()

