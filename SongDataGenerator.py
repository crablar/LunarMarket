#!/usr/bin/python

main_menu = open("main_menu.txt", 'w')

high_freq_values = [5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6]

low_freq_values = [5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6]

for i in range(0, 13):
	high_freq_values[i] = high_freq_values[i] + i / 2
	low_freq_values[i] -= i / 3

main_menu.write("high_freq_values: " + str(high_freq_values) + "\n")
main_menu.write("high_freq_values: " + str(low_freq_values) + "\n")