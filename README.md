# IR
Information Retrieval

See Problem Statement.

Method to compile and run the files:
------------------------------------
1. All files to be placed in the same location/folder.

2. To add teh external jar files, type the following command from the location where the java files are placed :
source /usr/local/corenlp350/classpath.sh

3. Compile all the java files with the following command:
javac -cp $CLASSPATH *.java

4. Run the program with the following command:
java Tokenise

5. Once the program finishes, the output can be seen in the terminal. The output gives,
for each query, Top 5 documents are retrieved and the following values for each of the top documents are displayed.
1. The query vector using weight 1 and Weight 2
2. The Rank of the Document (Top 5)
3. Score of the Query, Document
4. Document Identifier
5. Document Vector
6. Headline.


