from os import path
import pandas as pd
import matplotlib.pyplot as plt

# path1 = 'C://Users//User//IdeaProjects//sds-tp3//src//main//python//big_particle_trajectory_it=30746_n=100_v=0.1_run=3.csv'
path2 = 'C://Users//User//IdeaProjects//sds-tp3//src//main//python//big_particle_trajectory_it=50000_n=100_v=2.0_run=3.csv'
# path3 = 'C://Users//User//IdeaProjects//sds-tp3//src//main//python//big_particle_trajectory_it=15022_n=100_v=6.0_run=3.csv'

# data1= pd.read_csv(path1)
data2= pd.read_csv(path2)
# data3= pd.read_csv(path3)
# print(data.x)

# data = numpy.genfromtxt(path, delimiter=";", names=["x", "y","vx","vy"])

# data1.plot(x="x", y="v=0.1m/s")
data2.plot(x="x", y="v=2m/s")
# data3.plot(x="x", y="v=6m/s")
plt.xlabel("x [m]")
plt.ylabel("y [m]")
plt.xlim(0, 6)
plt.ylim(0, 6)
plt.show()