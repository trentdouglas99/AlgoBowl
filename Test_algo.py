import math
import statistics
from operator import attrgetter
class subsets_and_averages:
    def __init__(self, subset, average, average_point):
        self.subset = subset
        self.average = average
        self.average_point = average_point

testInput = [[1,1],[-3,2],[9,-5],[3,-6],[-2,0],[-4,-6],[-1,-1],[0,0],[-7,5],[-1,9]]

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
    for i in allCombinations:
        if(len(i) == 0 or len(i) == 1):
            allCombinations.remove(i);
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
    x_distances = []
    y_distances = []
    euclidian_distances = []
    for i in subset:
        xs.append(i[0])
        ys.append(i[1])

    average_x = statistics.mean(xs)
    average_y = statistics.mean(ys)
    average_point = [average_x, average_y]

    for i in range(len(subset)):
        x = subset[i][0]
        for j in xs:
            x_distances.append(abs(x - j))

        y = subset[i][1]
        for j in ys:
            y_distances.append(abs(y - j))
    for i in range(len(x_distances)):
        euclidian_distances.append(math.sqrt(pow(x_distances[i],2) + pow(y_distances[i], 2)))

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
        for papa in list_of_papas:
            papa_point_x = papa.average_point[0]
            papa_point_y = papa.average_point[1]
            x_difference = abs(leftover_x - papa_point_x)
            y_difference = abs(leftover_y - papa_point_y)
            euclidian_distance = math.sqrt(pow(x_difference,2) + pow(y_difference, 2))
            if(euclidian_distance < min_distance):
                min_distance = euclidian_distance
                papa_group = papa


k = input ("pick k: ")
k = int(k)

powerSet = powerSet(testInput)
clean_power_set = removenullSetAndSingles(powerSet)
clean_power_set_with_distances = calcComboDistances(clean_power_set)
sorted_clean_power_set_with_distances = sorted(clean_power_set_with_distances, key=attrgetter('average'))
list_of_papas = pickPapas(k, sorted_clean_power_set_with_distances)

for i in list_of_papas:
    print(i.subset)
    #print(i.average_point)
    #print(str(i.average))

leftovers = removeChosenPoints(testInput, list_of_papas)

finalSet = add_leftovers(list_of_papas, leftovers)

