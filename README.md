# **JAVA RMI Calculator Program**

## **Objective:**

The objective of this assignment is to gain a deeper understanding of Remote Mehtod Invocation(RMI) in Java by developing a distributed calcultor program.The calculator server operates on a stack, and each client has its own dedicated stack, supporting concurrent operations across multiple clients.

## **Assumptions:**

1. ### **Valid Operations:** ###
All operations pushed onto the stack are always sensible. For instance, an operator will only be pushed after at least one value has been added to the stack. A **'pop'** operation not be used if the stack is empty.
2. ### **Supported Operators:** ###
The operators supported for this calculator program are as specified in the assignment description ('min', 'max', 'gcd', 'lcm').
3. ### **Integer Values:** ###
Only integer values are pushed onto the stack.

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
