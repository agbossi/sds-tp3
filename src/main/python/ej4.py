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

fig = plt.figure('msd', figsize=(16,10))
ax = fig.add_subplot(1,1,1)
ax.errorbar(t, msd, yerr=std_err, color='blue', alpha=0.02, capthick=1)
ax.plot(t, msd, color='tab:orange')
m, b = np.polyfit(t, msd, 1)
ax.plot(t, m*np.asarray(t) + b, label=f'D= {m/2:.4f}'+r'$m^2$')
ax.set_xlabel('Tiempo (s)')
ax.set_ylabel(r'Desplazamiento cuadratico medio ($m^2$)')
ax.legend()
plt.show()