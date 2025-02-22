# CGI-HemTest

This project is a simple HTTP server that processes incoming POST requests containing text and returns the 10 most frequent words along with their frequencies in JSON format. The server is implemented in Java using the `com.sun.net.httpserver.HttpServer` class and Google's Gson library for JSON conversion. I have setup three different files, HemTest is used for setting up the HTTP server, WordCountHandler is used for everything related to the server requests and WordCounter is the file containing the logical operations.
I also added a test file, I mainly used it to test my code as the printed result does not have the exact same format as in the given example, but I included it if you wanted to look at is as well. 

# Prerequisites

-Java Development Kit (JDK) 8 or higher
-Gson library

# Compilation 
1. clone the project git clone git@github.com:gusbo214/CGI-kodtest.git
2. go to the CGI-kodtest folder
3. Compile the files with the command "javac -d output -cp "lib/gson-2.9.1.jar" HemTest.java WordCountHandler.java WordCounter.java" this will create the folder output where all java classes are stored.
4. Then run "java -cp "lib/gson-2.9.1.jar;output" HemTest", if everything compiled correctly the message "Servern är nu uppsatt på...." should be shown

# Running the program
1. Open a terminal, I used gitBash to send commands, and type the command "curl -H "Content-Type: text/plain" -X POST -d "Banan Äpple Katt Hund Banan Hund Katt Hund" https://localhost:3000/countWords". The expected output should look something like this {"Hund":3,"Banan":2,"Katt":2,"Äpple":1}

2. Alternatively, if you want to run the test run the command "javac -d testoutput Test.java" this will create the testoutput folder for the test class
3. Run "java -cp testoutput Test" This will do some testing and print the results in the console.
