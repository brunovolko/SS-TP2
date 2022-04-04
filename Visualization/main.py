from types import NoneType
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
side_length = 1.0


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
        
        x, y, z = np.indices((cells_height, cells_width, cells_depth))

        cubes = list()
        distances_to_center = list()
        voxelarray = None
        first_time = True

        max_distance_to_center = np.sqrt((cells_width/2)**2 + (cells_height/2)**2 + (cells_depth/2)**2)
        
        

        for cell in scenario:
            coordinates = [int(x) for x in cell.split(' ')]
            cube = (x >= coordinates[0]) & (x <= coordinates[0]+1) & (y >= coordinates[1]) & (y <= coordinates[1]+1) & (z >= coordinates[2]) & (z <= coordinates[2]+1)
            cubes.append(cube)
            
            distances_to_center.append(np.sqrt((coordinates[0] - cells_height/2)**2 + (coordinates[1] - cells_width/2)**2 + (coordinates[2] - cells_depth/2)**2))
            if first_time:
                voxelarray = cube
                first_time = False
            else:
                voxelarray = voxelarray | cube
        if type(voxelarray) == NoneType:
            voxelarray = (x > 0) & (x < 0) & (y > 0) & (y < 0) & (z > 0) & (z < 0)

        
            

        colors = np.empty(voxelarray.shape, dtype=float)
        idx = 0
        for cube in cubes:
            colors[cube] = distances_to_center[idx] / max_distance_to_center
            idx += 1

        

        ax = plt.figure().add_subplot(projection='3d')
        ax.set_xlabel('Rows')
        ax.set_ylabel('Cols')
        ax.set_zlabel('Depth')
        plt.gca().invert_yaxis()
        ax.voxels(voxelarray, facecolors=plt.cm.viridis(colors))

    else:
        data = np.zeros((cells_height, cells_width))

        max_distance_to_center = np.sqrt((cells_width/2)**2 + (cells_height/2)**2)

        for cell in scenario:
            coordinates = [int(x) for x in cell.split(' ')]
            distance_to_center = np.sqrt((coordinates[0] - cells_height/2)**2 + (coordinates[1] - cells_width/2)**2) / max_distance_to_center
            
            data[coordinates[0]][coordinates[1]] = distance_to_center

        
        cmap = colors.LinearSegmentedColormap.from_list('my_colormap', [(0, '#ffffff'),(0.0000000001, '#ff0000'), (0.6, '#ff9900'), (1, '#dec41b')])

        fig, ax = plt.subplots()
        ax.set_xlabel('Rows')
        ax.set_ylabel('Cols')
        #ax.invert_xaxis()
        #ax.invert_yaxis()
        #plt.xticks([])#np.arange(-0.5, cells_height+1, 10.0))
        ax.imshow(data, cmap=cmap)

        # draw gridlines
        ax.grid(which='major', axis='both', linestyle='-', color='k', linewidth=0.5)
        ax.set_xticks(np.arange(-.5, cells_height*side_length, side_length))
        ax.set_yticks(np.arange(-.5, cells_width*side_length, side_length))
        xtick_labels = [(str(x) if x % 3 == 0 else "") for x in range(cells_height+1)]
        ytick_labels = [(str(y) if y % 3 == 0 else "") for y in range(cells_width+1)]
        ax.set_xticklabels(xtick_labels)
        ax.set_yticklabels(ytick_labels)
        ax.invert_yaxis()
        
    plt.savefig("figures/"+str(counter)+".png")
    images.append(imageio.imread("figures/"+str(counter)+".png"))
    plt.close()
    counter += 1

    
imageio.mimsave('animation.gif', images, duration=0.15)


