from matplotlib import pyplot as plt
from numpy import float64
import numpy as np
import Stat_analysis as st
import pandas as pd


# avg_cf_100 = [477.0250960887913, 409.1456988515353, 468.9505566337137]
# avg_ct_100 = [0.0020963257660847772, 0.002132420968168456, 0.002444117102555354]
# t_100 = [104.81628830423887, 106.6210484084228, 122.2058551277677]

# avg_cf_125 = [959.5476516135216, 875.3926658446748, 887.2887641284844]
# avg_ct_125 = [0.0011270288100427182, 0.0011423445032351172, 0.001042157727465078]
# t_125 = [52.1078863732539, 57.11722516175586, 56.351440502135915]

# avg_cf_142 = [1390.340292678683, 1375.0994551061622, 1352.5579507113214]
# avg_ct_142 = [7.192483777287081E-4, 7.272201267237042E-4, 7.393398556225201E-4]
# t_142 = [35.96241888643541, 36.36100633618521, 36.966992781126]

runs = 3
path = 'C:/Users/Meli/OneDrive/Escritorio/Data/it=50000_n=142_v=2/'
# path = os.path.abspath('../../Data/it=50000_n=100_v=2') # carpeta
df = pd.DataFrame(columns=['dt'], dtype=float64)
for run in range(1, runs+1):
    file = 'tiempos_colision_it=50000_n=142_v=2_run=' + str(run) + '.csv'
    df = df.append(pd.read_csv(path + file), ignore_index=True)

bins = 193 # st.calculate_optimal_bins(df, 'dt', runs)
wtf = pd.cut(df['dt'], bins=bins)
wtf_count = wtf.value_counts(sort=False)
wtf_count = wtf_count / runs

max_dt = max(df['dt']) / bins
x = [(i * max_dt) for i in range(1, bins+1)]
y = wtf_count.to_dict().values()
probabilities = [freq / 50000 for freq in y]
xticks = np.linspace(0, df['dt'].max(), num=10, endpoint=True)
plt.figure()
plt.bar(x, probabilities, max_dt, align='edge')
plt.xlabel("Intervalos tiempo de choque")
plt.xticks(xticks, rotation=30)
plt.ylabel("Probabilidad")
plt.tight_layout()
plt.show()
