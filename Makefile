# the compile command compiles all java files
compile: *.java
	javac -d ./ *.java

# registry starts the rmiregistry
registry:
	rmiregistry &

# clean removes all compiled files
clean:
	rm *.class

# server runs the server
server:
	java -cp ./ CalculatorServer &

# client runs the client, takes input from the TestInput files
client: CalculatorClient.class
	java -cp ./ CalculatorClient > Output.txt

# this compares the output of the program with the 2 expected outputs 
# the reason for 2 expected outputs is because the pop command in line 6
# of TestInput1.txt could print first or the isEmpty in line 6 of TestInput3.txt 
outputCompare:
	diff Output.txt ExpectedOutput0.txt & diff Output.txt ExpectedOutput1.txt

# test runs the test cases
test: CalculatorClient.class
	java -cp ./ CalculatorClient