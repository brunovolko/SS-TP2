from dis import dis
from matplotlib import pyplot as plt
import numpy as np
from matplotlib import colors
import os
import shutil
import imageio

static_file = open('static_input.txt', 'r')

dynamic_file = open('dynamic_output.txt', 'r')

is3d = bool(int(static_file.readline()))
cells_width = int(static_file.readline())
cells_height = int(static_file.readline())
if is3d:
    cells_depth = int(static_file.readline())
side_length = float(static_file.readline())


scenarios = list()
t = 0
while True:
    line = dynamic_file.readline()
    if not line:
        scenarios.append(current_scenario)
        break
    if line[0] == 't':
        if t == 0:
            current_scenario = list()
            t += 1
        else:
            scenarios.append(current_scenario)
            current_scenario = list()
            t += 1
    else:
        current_scenario.append(line[:-1])
try:
    if os.path.exists("figures"):
        shutil.rmtree("figures")
        
    os.mkdir("figures")
except FileExistsError:
    pass


images = []



counter = 0
for scenario in scenarios:
    if is3d:        
        
        x, y, z = np.indices((cells_width, cells_height, cells_depth))

        cubes = list()
        distances_to_center = list()
        voxelarray = None
        first_time = True

        max_distance_to_center = np.sqrt((cells_width/2)**2 + (cells_height/2)**2 + (cells_depth/2)**2)
        
        

        for cell in scenario:
            coordinates = [int(x) for x in cell.split(' ')]
            cube = (x >= coordinates[0]) & (x <= coordinates[0]+1) & (y >= coordinates[1]) & (y <= coordinates[1]+1) & (z >= coordinates[2]) & (z <= coordinates[2]+1)
            cubes.append(cube)
            distances_to_center.append(np.sqrt(np.sum((coordinates[0] - cells_width/2)**2 + (coordinates[1] - cells_height/2)**2 + (coordinates[2] - cells_depth/2)**2)))
            if first_time:
                voxelarray = cube
                first_time = False
            else:
                voxelarray = voxelarray | cube

        
            

        colors = np.empty(voxelarray.shape, dtype=float)
        idx = 0
        for cube in cubes:
            colors[cube] = distances_to_center[idx] / max_distance_to_center
            idx += 1

        

        ax = plt.figure().add_subplot(projection='3d')
        ax.voxels(voxelarray, facecolors=plt.cm.viridis(colors))

    else:
        data = np.zeros((cells_height, cells_width))

        for cell in scenario:
            coordinates = cell.split(' ')
            data[int(coordinates[0])][int(coordinates[1])] = float(1)

        cmap = colors.ListedColormap(['white', 'gray'])

        fig, ax = plt.subplots()
        ax.imshow(data, cmap=cmap)

        # draw gridlines
        ax.grid(which='major', axis='both', linestyle='-', color='k', linewidth=1)
        ax.set_xticks(np.arange(-.5, cells_width*side_length, side_length))
        ax.set_yticks(np.arange(-.5, cells_height*side_length, side_length))
        
    plt.savefig("figures/"+str(counter)+".png")
    images.append(imageio.imread("figures/"+str(counter)+".png"))
    plt.close()
    counter += 1

    

imageio.mimsave('animation.gif', images)



        

'''

fig, ax = plt.subplots()

static_file.readline() #N
L = float(static_file.readline())

intervals = L/float(10) #Mismo que en java


loc = plticker.MultipleLocator(base=intervals)
ax.xaxis.set_major_locator(loc)
ax.yaxis.set_major_locator(loc)
plt.grid()
ax.set_title('Click a point to see its neighbours')
linebuilder = PointClickDetection(ax, static_file, dynamic_file, results_df)

plt.show()
'''