# How to run Demo.java
1.install lingpipe
2.the first args is the path of the training data
3.run it

# How to use HmmPhraseChunker.java
Phrase Chunker using lingpipe and the CoNLL data set

How to use:

1.convertTestData.py is use to convert the data from the Conll formatt:
I NP B_NP
am VP B_VP
to a sentence format: 
I am

2.the first args is the path of the training data, the second args is the path of the converted test data

3.evaluateData.py is use to combined the predicted value of HmmPhraseChunker.java and the actual value of the test data. It will the format:
Word Part-of-Speech Actual-Result Predicted-Result