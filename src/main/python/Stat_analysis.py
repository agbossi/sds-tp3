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
    return int(bins)
