#! /usr/bin/python
import sys
import numpy as np 
from os import listdir
from os.path import isfile, join

def evaluate(infileG,infileP, outfile):
	#infileG = "test_split_5.txt"#the actual data
	#infileP = "SpTrSpTe.txt"#the prediicted data
	#outfile = "SpTrSpTe_result.txt"#combine them together so they have the format ('words','type','B or I of actual data','B or I of predicted data' )

	linesG = np.loadtxt(infileG, dtype= 'S', delimiter=' ')
	linesP = np.loadtxt(infileP, dtype= 'S', delimiter=' ')

	(yG,xG) = linesG.shape
	(yP,xP) = linesP.shape
	
	result = np.empty (shape = (yG,4), dtype= 'S100')
	(yR,xR) = result.shape
	
	result[:] = 'O'
	print (result)
	result [:,(0,1,2)] = linesG
	print (result)

	i = 0
	j = 0
	while (i < yG and j < yP):
		if linesG[i][0] == linesP[j][0]:
			result[i][3] = linesP[j][1]
			i = i + 1	
			j = j + 1
		else:
			if linesG[i][2] == 'O':#special cases when it see omit
				if linesG[i][0] == linesP [j][0]:
					result[i][3] = linesP[j][1]
					i = i + 1	
					j = j + 1		
				else:
					result[i][3] = 'O'
					i = i + 1
			elif linesG[i][0] == ',' or linesG[i][0] == '.' : #special case when omit is not actually omit
				if linesG[i][0] == linesP [j][0]:
					result[i][3] = linesP[j][1]
					i = i + 1	
					j = j + 1		
				else:
					result[i][3] = 'O'
					i = i + 1
			elif linesP[j][0] in linesG[i][0]: #when the long words is cut, ex MG1-plan = MG1 and -plan
				k = 1
				stop = True
				while (stop and k < 20 and i + 1 < yG and j + k < yP):
					if (linesG[i + 1][0] == linesP[j + k][0]):
						stop = False
					else:
						k = k + 1
				result[i][3] = 'O'	
				i = i + 1
				j = j + k
			else: #when the words is treated as omitted word
				k = 1
				stop = True
				while (stop and k < 1000 and i + k < yG and j < yP):
					if (linesG[i + k][0] == linesP[j][0]):
						stop = False
					else:
						result[i+k][3] = 'O'	
						k = k + 1
				i = i + k

					
	print (result)

	np.savetxt(outfile, result, fmt = '%.100s', delimiter=' ', newline='\n')		

if __name__ == '__main__':
	mypath = "outfile"
	for f in listdir(mypath):
		print mypath + "/" + f
		infileP = mypath + "/" + f
		infileG = "test_split_5.txt"
		outfile = "result/" + str(f)
		evaluate (infileG,infileP,outfile)  