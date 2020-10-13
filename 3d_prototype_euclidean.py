import numpy

class point:
    def __init__(self, x_loc, y_loc, z_loc, ind, set):
        self.x_loc = x_loc
        self.y_loc = y_loc
        self.z_loc = z_loc
        self.ind   = ind
        self.set   = set

def initialize_items(Lines):
    obj_list = []
    for i in range(0,len(Lines)):
        if i > 1:
            x_loc    = Lines[i].split()[0]
            y_loc    = Lines[i].split()[1]
            z_loc    = Lines[i].split()[2]
            item_ind = i-1
            set      = 0
            obj_list.append(point(int(x_loc),int(y_loc), int(z_loc), item_ind, set))
    return obj_list

def get_num_items(Lines):
    num_items = int(Lines[0])
    return num_items

def get_num_sets(Lines):
    num_groups = int(Lines[1])
    return num_groups

def print_points(obj_list):
    for i in range(0, len(obj_list)):
        print("Point", obj_list[i].ind, ": x_loc:", obj_list[i].x_loc, " y_loc:", obj_list[i].y_loc, " z_loc:", obj_list[i].z_loc)

def create_mat(obj_list, num_items):
    dist_mat = numpy.zeros(shape=(num_items, num_items))
    for i in range(0, num_items):
        for j in range(0, num_items):
            dist_mat[i, j] = numpy.sqrt(numpy.power(obj_list[i].y_loc - obj_list[j].y_loc, 2) + numpy.power(obj_list[i].x_loc - obj_list[j].x_loc,2) + numpy.power(obj_list[i].z_loc - obj_list[j].z_loc,2))
    return dist_mat

def create_mat_e(obj_list, num_items):
    dist_mat = numpy.zeros(shape=(num_items, num_items))
    for i in range(0, num_items):
        for j in range(0, num_items):
            dist_mat[i, j] = (abs(obj_list[i].y_loc - obj_list[j].y_loc) + abs(obj_list[i].x_loc - obj_list[j].x_loc) + abs(obj_list[i].z_loc - obj_list[j].z_loc))
    return dist_mat



def get_initial_papas(dist_mat, num_items, dist_mat_e):   # updated
    max_val = 0
    max_val_e = 0
    the_i = 0
    the_j = 0
    for i in range(0, num_items):
        for j in range(0, num_items):
            if ((dist_mat[i, j] > max_val) or ((dist_mat[i, j] == max_val) and dist_mat_e[i, j] > max_val_e)):
                max_val = dist_mat[i, j]
                max_val_e = dist_mat_e[i,j]
                the_i = i
                the_j = j

    no_no_list = []
    no_no_list.append(the_i)
    no_no_list.append(the_j)
    return no_no_list

def get_papas(dist_mat, no_no_list, num_items, dist_mat_e):
    next_point = -1
    new_sum = 0
    new_sum_e = 0
    for i in range(0, num_items):
        if i not in no_no_list:
            nsum = 0
            nsum_e = 0
            for j in range(0, len(no_no_list)):
                nsum = nsum + dist_mat[i, no_no_list[j]]
                nsum_e = nsum_e + dist_mat_e[i, no_no_list[j]]
            if ((nsum > new_sum) or ((nsum == new_sum) and (nsum_e > new_sum_e))):
                new_sum = nsum
                new_sum_e = nsum_e
                next_point = i
    no_no_list.append(next_point)
    return no_no_list

def get_all_papas(dist_mat, no_no_list, num_sets, num_items, dist_mat_e):   # updated
    for i in range(0, num_sets - 2):
        get_papas(dist_mat, no_no_list, num_items, dist_mat_e)
    return no_no_list

def set_sets(no_no_list, obj_list):
    for i in range(0, len(no_no_list)):
        obj_list[no_no_list[i]].set = i + 1

def create_sets(dist_mat, no_no_list, num_items, obj_list, dist_mat_e):
    for i in range(0,num_items):
        if i not in no_no_list:
            the_min = 6000
            the_min_e = 4000
            for j in range(0,len(no_no_list)):
                if ((the_min > dist_mat[i, no_no_list[j]]) or (the_min == dist_mat[i, no_no_list[j]] and (the_min_e > dist_mat_e[i, no_no_list[j]]))):
                    the_min = dist_mat[i, no_no_list[j]]
                    the_min_e = dist_mat_e[i, no_no_list[j]]
                    obj_list[i].set = obj_list[no_no_list[j]].set

def get_results(obj_list, num_items, num_sets):
    all_sets = []
    for i in range(1, num_sets+1):
        the_set = []
        for j in range(0,num_items):
            if obj_list[j].set == i:
                the_set.append(j+1)
        #print("Set", i, ":", the_set)
        all_sets.append(the_set)
    return all_sets

def find_max_min(all_sets, dist_mat):
    max_min = 0
    for i in all_sets:
        for j in i:
            for k in i:
                if dist_mat[j-1, k-1] > max_min:
                    max_min = dist_mat[j-1, k-1]
    return max_min

def run_algo(filepath):
    text_file = open(filepath, "r")
    lines = text_file.readlines()
    num_items = get_num_items(lines)
    num_sets = get_num_sets(lines)
    obj_list = initialize_items(lines)
    dist_mat = create_mat(obj_list, num_items)
    dist_mat_e = create_mat_e(obj_list, num_items)
    no_no_list = get_initial_papas(dist_mat, num_items, dist_mat_e)  # updated
    get_all_papas(dist_mat, no_no_list, num_sets, num_items, dist_mat_e) # updated
    set_sets(no_no_list, obj_list)
    create_sets(dist_mat, no_no_list, num_items, obj_list, dist_mat_e) # updated
    all_sets = get_results(obj_list, num_items, num_sets)
    max_min = find_max_min(all_sets, dist_mat_e)
    print(no_no_list)

    print(max_min)
    print(all_sets)
    #print(dist_mat_e)

run_algo("1000_points.txt")
