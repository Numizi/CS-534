def main():
	infile = "/home/christopher/CS-534/result/train_split_8.txt"
	outfile = "/home/christopher/CS-534/results/split_8.txt"

	# grab lines, splitting on whitespace
	lines = [line.split() for line in open(infile)]

	f = open(outfile, "w")
	for line in lines:

		# new sentence
		if (len(line) == 0):
			f.write('\n')

		# ending sentence
		elif (line[0] == "."):
			f.write(line[0] + "\t")
			f.write(line[1] + "\t")
			f.write(line[2] + "\t")
			f.write(line[3] + "\n")
			f.write("\n")

		else:
			f.write(line[0] + "\t")
			f.write(line[1] + "\t")
			f.write(line[2] + "\t")
			f.write(line[3] + "\n")
	f.close()


if __name__ == '__main__':
    main()