from matplotlib import pyplot as plt
from numpy import float64
import numpy as np
import Stat_analysis as st
import pandas as pd


runs = 3
path = 'C:/Users/Meli/OneDrive/Escritorio/Data/it=50000_n=142_v=2/'
# path = os.path.abspath('../../Data/it=50000_n=100_v=2') # carpeta
df = pd.DataFrame(columns=['v'], dtype=float64)
for run in range(1, runs+1):
    file = 'velocidades_it=50000_n=142_v=2_run=' + str(run) + '.csv'
    df = df.append(pd.read_csv(path + file), ignore_index=True)

iterations = 50000
particles = 100
bins = 284 # st.calculate_optimal_bins(df, 'v', runs)
wtf = pd.cut(df['v'], bins=bins)
wtf_count = wtf.value_counts(sort=False)
wtf_count = wtf_count / runs

max_v = max(df['v']) / bins
x = [(i * max_v) for i in range(1, bins+1)]
y = wtf_count.to_dict().values()
probabilities = [freq / ((iterations / 3) * particles) for freq in y]
xticks = np.linspace(0, df['v'].max(), num=10, endpoint=True)
plt.figure()
plt.bar(x, probabilities, max_v, align='edge')
plt.xlabel("Intervalos de velocidades")
plt.xticks(xticks, rotation=30)
plt.ylabel("Probabilidad")
plt.tight_layout()
plt.show()