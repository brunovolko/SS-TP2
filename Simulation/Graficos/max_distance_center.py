import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

porc = {
    1: "15%",
    2: "30%",
    3: "45%",
    4: "60%",
    5: "75%",
    6: "90%"
}

folders = ['Couples2d', 'Lifegame2d', 'NobodyAlone2d', 'Fixednumber3d', 'Lifegame3d', 'Spatialmove3d']
for folder in folders:
    for i in range(6):
        idx = i+1
        data = pd.read_csv(folder+'/p'+str(idx)+'.txt', sep='\t')
        y = [int(a) for a in data['max_distance']]
        plt.plot(range(len(y)), y, label=porc[idx])
    plt.legend(loc='best')
    plt.title("Regla "+folder)
    plt.xlabel('Paso temporal')
    plt.ylabel('Distancia máxima al centro')


    plt.savefig(folder+'/grafico_max_distance_center_'+folder+'.png')
    plt.cla()