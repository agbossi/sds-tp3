# Simulacion de Sistemas - TP 3
Movimiento Browniano

Para correr la simulacion, el comando que se debe ejecutar es
```
mvn compile exec:java -Dexec.mainClass=App
```
Existen 3 clases posibles para ejecutar:
- App: Corre la simulacion con los parametros de la consigna, dejando simulation.xyz el archivo necesario para correr la simulacion en Ovito
- Ej4: Corre la simulacion para calcular el coeficiente de difusion de la particula grande
- Ej4b: Corre la simulacion para calcular el coeficiente de difusion de la particulas chicas

Para graficar, es necesario tener instalado matplotlib, numpy y pandas

podemos correr los scipts solo con
```
python3 <script>
```
