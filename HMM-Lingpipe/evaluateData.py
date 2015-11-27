#! /usr/bin/python
import sys
import numpy as np 

def evaluate():
	infileG = "test.txt"#the actual data
	infileP = "output.txt"#the prediicted data
	outfile = "result.txt"#combine them together so they have the format ('words','type','B or I of actual data','B or I of predicted data' )

	linesG = np.loadtxt(infileG, dtype= 'S', delimiter=' ')
	linesP = np.loadtxt(infileP, dtype= 'S', delimiter=' ')

	(yG,xG) = linesG.shape
	(yP,xP) = linesP.shape
	
	result = np.empty (shape = (yG,4), dtype= 'S100')
	(yR,xR) = result.shape
	

	result [:,(0,1,2)] = linesG
	print (result)

	i = 0
	j = 0
	while (i < yG and j < yP):
		if (result[i][2] == 'O'):
			result[i][3] = 'O'
			i = i + 1
		else:
			result[i][3] = linesP[j][1]
			i = i + 1	
			j = j + 1

	print (result)
	np.savetxt('result.txt', result, fmt = '%.100s', delimiter=' ', newline='\n')		

if __name__ == '__main__':
	evaluate()