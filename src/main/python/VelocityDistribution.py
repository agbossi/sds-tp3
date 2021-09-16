import matplotlib.pyplot as plt
import math
import pandas as pd

# bins calculated from quantiles using Freedman-Diaconis rule
def calculate_optimal_bins(data_frame, column):
    # Computing IQR
    Q1 = data_frame[column].quantile(0.25)
    Q3 = data_frame[column].quantile(0.75)
    IQR = Q3 - Q1

    # Freedman-Diaconis rule
    bin_width = 2 * IQR / len(data_frame) ** (1. / 3)
    bins = (data_frame[column].max() - data_frame[column].min()) / bin_width
    return bins


def histogram(data_frame, column):
    # Get optimal beans for variable
    bins = math.ceil(calculate_optimal_bins(data_frame, column))
    # Plot pandas histogram from dataframe with df.plot.hist (not df.hist)
    ax = data_frame[column].plot.hist(bins=bins, density=True, edgecolor='w', linewidth=0.5, label=column)

    # Save default x-axis limits for final formatting because the pandas kde
    # plot uses much wider limits which usually decreases readability
    xlim = ax.get_xlim()

    # Plot pandas KDE
    data_frame[column].plot.density(color='k', alpha=0.5, ax=ax)  # same as df['var'].plot.kde()

    # Reset x-axis limits and edit legend and add title
    ax.set_xlim(xlim)
    # ax.legend(labels=['KDE'], frameon=False)
    # ax.set_title('Pandas histogram overlaid with KDE', fontsize=14, pad=15)
    # plt.title(column + " " + sample)
    plt.show()


velocity_df = pd.read_csv('velocidades.csv')
histogram(velocity_df, 'v')
