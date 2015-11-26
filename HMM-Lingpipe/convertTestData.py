def main():
	infile = ""
	outfile = ""

	# grab lines, splitting on whitespace
	lines = [line.split() for line in open(infile)]

	f = open(outfile, "w")
	for line in lines:

		# new sentence
		if (len(line) == 0):
			f.write('\n')

		# grab word
		else:
			f.write(line[0] + " ")
	f.close()


if __name__ == '__main__':
    main()