import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

N = 50

df = pd.read_csv('msd-big-particle-calc.csv', sep=';')

x0 = {'x': df['x'][0], 'y': df['y'][0]}

msd = []
t = []
std_err = []
sd = []
modsq = 0
for i, row in df.iterrows():
    x = row['x'] - x0['x']
    y = row['y'] - x0['y']
    modsq += x*x + y*y
    sd.append(x*x + y*y)
    if i % N == 0:
        msd.append(modsq/N)
        t.append(row['t0'])
        std_err.append(np.std(sd))
        sd = []
        modsq = 0

slopes = np.linspace(0, 0.3, 10000)
error = np.empty(10000)
x = np.asarray(t)
for i in range(10000):
    error[i] = np.sum((msd - slopes[i]*x)**2)

fig = plt.figure("Error", figsize=(16,10))
ax = fig.add_subplot(1,1,1)
ax.plot(slopes, error)
ax.plot(slopes[np.argmin(error)], error[np.argmin(error)], marker='o', color='b', label=f'm= {slopes[np.argmin(error)]:.4f}')
ax.set_xlabel(r'Pendiente ($m^2$/s)')
ax.set_ylabel(r'Error ($m^2$)')
ax.legend()
plt.show()