results.ods and results_vary_training_size.ods contain raw results from tests
=============================================================================

YamCha (SVM) directions
-----------------------

Installation instructions (note TinySVM must be installed as well)
http://chasen.org/~taku/software/yamcha/#install
Tarballs containing TinySVM and YamCha installation files are also included
Create a folder within CS-534 named yamcha
This folder must contain Makefile described in installation instructions

Model Training (will save model to file)
----------------------------------------
cd CS-534/yamcha
make CORPUS=TRAINING_DATA MULTI_CLASS=MULTI_CLASS_STRAT MODEL=MODEL_NAME SVM_PARAM="-t1 -dPOLY -cCVAL" train
	where TRAINING_DATA = training data
		  MULTI_CLASS_STRAT = 1 or 2 (1=pairwise, 2=one vs rest)
		  MODEL_NAME = name for model
		  POLY = some integer (order of polynomial kernel)
		  CVAL = some real value (weighting for slack)

Model Prediction (will save prediction to output file)
------------------------------------------------------
yamcha -m MODEL_NAME.model < TEST_DATA > OUTPUT_FILE
	where MODEL_NAME = name for model (same as before)
		  TEST_DATA = test data
		  OUTPUT_FILE = file to store output in

This will create an outfile in which second to last column will contain ground truth and last column will contain chunk prediciton

Model Evaluation
----------------
cd CS-534
perl conll-eval.pl -d '\t' < OUTPUT_FILE
	where OUTPUT_FILE is file created by the Model Prediction step



====================================================================================================================================

OpenNLP (CRF) Directions
------------------------
cd CS-534/apache-opennlp-1.6.0

Model Training (will save model to file)
----------------------------------------
bin/opennlp ChunkerTrainerME -encoding UTF-8 -lang en -data TRAINING_DATA -model MODEL_NAME.bin
	where TRAINING_DATA = training data
	  MODEL_NAME = name for model

Model Evaluation (predicts on corpus and performs evaluation)
-------------------------------------------------------------
bin/opennlp ChunkerEvaluator -encoding UTF-8 -data TEST_DATA -model MODEL_NAME.bin
	where MODEL_NAME = name for model
		  TEST_DATA = test data


=====================================================================================================================================

LingPipe (HMM) Directions
-------------------------
Install lingpipe
1. Download: http://alias-i.com/lingpipe/web/download.html
2. Set up eclipse for lingpipe: http://alias-i.com/lingpipe/demos/tutorial/eclipse/read-me.html

# How to run Demo.java (also used for training HMM)
1.the first argument is the path of the training data

# How to use HmmPhraseChunker.java
Phrase Chunker using lingpipe and the CoNLL data set

How to use:

1.CS-534/HMM-Lingpipe/convertTestData.py is use to convert the data from the Conll format:
I NP B_NP
am VP B_VP
to a sentence format: 
I am

2.the first arg is the path of the training data, the second args is the path of the converted test data (must be manually edited inside code)

3.CS-534/HMM-Lingpipe/evaluateData.py is use to combined the predicted value of HmmPhraseChunker.java and the actual value of the test data. It will the format:
Word Part-of-Speech Actual-Result Predicted-Result
4. Once output file is produced, must be converted using CS-534/convertHMMOutput.py
5. To evaluate predictions, use conll-eval.pl on output file in similar manner to YamCha evaluation
cd CS-534
perl conll-eval.pl -d '\t' < OUT_FILE