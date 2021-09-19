from numpy import float64

import Stat_analysis as st
import pandas as pd


runs = 3
path = '/home/abossi/Desktop/Data/it=50000_n=100_v=2/'
# path = os.path.abspath('../../Data/it=50000_n=100_v=2') # carpeta
df = pd.DataFrame(columns=['dt'], dtype=float64)
for run in range(1, runs+1):
    file = 'tiempos_colision_it=50000_n=100_v=2_run:' + str(run) + '.csv'
    df = df.append(pd.read_csv(path + file), ignore_index=True)

bins = st.calculate_optimal_bins(df, 'dt')
wtf = pd.cut(df['dt'], bins=bins)
wtf_count = wtf.value_counts()
wtf_count / 3
pd.Series.hist(wtf_count)
print('wtf')
