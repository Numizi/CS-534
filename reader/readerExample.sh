cd code
javac reader/Main.java
javac reader/Reader.java
java -Xmx2G reader.Main <orig_filename> <complete_sentence_Filename> <incomplete_sentence_Filename> <bootstrapMultiplier>
