import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

folders = ['Couples2d', 'Lifegame2d', 'NobodyAlone2d', 'Fixednumber3d', 'Lifegame3d', 'Spatialmove3d']
for folder in folders:
    data = pd.read_csv(folder+'/datos.txt', sep=' ')
    x = [int(float(p)*100) for p in data['p']]
    y = np.array(data['avg'])
    e = np.array(data['desvio'])

    plt.errorbar(x, y, e, linestyle='None', marker='o')

    plt.xticks(x)

    plt.title("Regla "+folder)
    plt.xlabel('Porcentaje inicial de células vivas')
    plt.ylabel('Transiciones hasta finalizar.')

    plt.savefig(folder+'/grafico_pasos_'+folder+'.png')
    plt.cla()
    