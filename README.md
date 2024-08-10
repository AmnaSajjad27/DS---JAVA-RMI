Objective:
The objective of this assignment is to gain understanding of Remote Mehtod Invocation in Java. This assignment develops a calculator program. 

The calculator server operates a stack and the client pushes values and operations on to the stack. For bonus marks, I have implemented the program so that each client has its own stack. 

Assumptions: 
1. All operations pushed are always sensiable i.e. an operator will only be pushed after pushing atleast one value onto the stack i.e. pop will not be used if stack is empty. 
2. Operator provided will be either one of the four specified in the assignment descrption. 
3. Values pushed are always integers. 

Compliling the program:
please note that a makefile has been provided for your convience. 

- To compile the program, use the command "make compile".
- Then to start the rmiregistry, use the command "make registry".
- To start the server, use "make server" and then simmilary for the client, use "make client". 
- Then enter your desired inputs to test the program. 
- To test using existing testing files, run the command "make make". This command cleans the directory of files not needed, then compiles, starts the rmiregistry, starts the server and client and then uses Testinput 0, 1, 2 and 3 to conduct a concurrency test. The output is then displayed on the terminal using outputCompare. 

Program files and structure:
The program has 4 main files: 
1. Calculator.Java 
2. CalculatorClient.java
3. CalculatorImplementation.java
4. CalculatorServer.java

Functionality

Key features and functions 
- String create UserID()

Testing 
