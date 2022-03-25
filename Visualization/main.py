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