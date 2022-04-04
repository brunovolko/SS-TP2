# TP2 Simulación de Sistemas


## Requerimientos

- Java 1.8 (Simulación)
- Python 3 (Visualización)


## Uso

Preparar los archivos

### static_input.txt

Contenido:

```text
ES_3D(1 o 0)
RULESET(lifeGame2d,couples2d,nobodyAlone2d,lifeGame3d,spatialMove3d,fixedNumber3d)
CANT_CELDAS_ANCHO
CANT_CELDAS_ALTO
CANT_CELDAS_PROFUNDIDAD (Solo si es 3d)
```

### dynamic_input.txt

Contenido:

```text
t0
fila1 colum1 profundidad1 (si es 3d)
fila2 colum2 profundidad2 (si es 3d)
...
filaN columN profundidadN (si es 3d)
```

### Ejecutar simulación

Copiar los archivos en el directorio del proyecto java. Ejecutar el archivo Main. El mismo genera un archivo "dynamic_output.txt" con los resultados.

### Visualización

Copiar los archivos "static_input.txt" y "dynamic_output.txt" en la carpeta "Visualization".
Luego ejecutar el archivo main.py usando python.

En caso de no contar con las dependencias, ejecutar lo siguiente:
```bash
pip install -r requirements.txt
```

### Resultado

En la carpeta figures se encontrarán los fotogramas individuales. También en la carpeta Visualization se creará el archivo "animaiton.gif" con la animación.
