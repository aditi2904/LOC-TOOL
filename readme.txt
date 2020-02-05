line-counter
Application that counts number of actual java code lines in input file or folder. 
It also tells no of java files using .java extension by filtering out the file with these extensions using FileUtils.listFiles method.
Unique Fils are identified by comparing the hash value of the files.
Blank files are identified if file is empty.
Comments are calculated using the conditions if the line starts with /* and ends with */ or ||.
Line of Code are calculated using total no of lines subtracting no of comments and blanklines.

how to run

Pass a full path to java file or folder that may contain java files as first command line argument.