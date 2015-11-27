# Phrase-Chunker
Phrase Chunker using lingpipe and the CoNLL data set

How to use:

convertTestData.py is use to convert the data from the Conll formatt:
I NP B_NP
am VP B_VP
to a sentence format: 
I am

HmmPharseChunker.java is the HMM learner. It is use to learned the data and make prediction

evaluateData.py is use to compare the predicted value to the actual value from the test(Because the output from HmmPharseChunker.java is needed to modify a little to easily compare with the actual data) 