# **JAVA RMI Calculator Program**

## **Objective**

The objective of this assignment is to gain a deeper understanding of Remote Mehtod Invocation(RMI) in Java by developing a distributed calcultor program.The calculator server operates on a stack, and each client has its own dedicated stack, supporting concurrent operations across multiple clients.

## **Assumptions**

1. **Valid Operations:**
All operations pushed onto the stack are always sensible. For instance, an operator will only be pushed after at least one value has been added to the stack. A **'pop'** operation not be used if the stack is empty.
2. **Supported Operators:**
The operators supported for this calculator program are as specified in the assignment description ('min', 'max', 'gcd', 'lcm').
3. **Integer Values:**
Only integer values are pushed onto the stack.

## **Program Structure** ##

The program is composed of four main files:

1. **Calculator.java:** This interface defines the methods that can be invoked by the client. It includes operations such as 'pushValue', 'pushOperation', 'pop', 'isEmpty', 'delayPop', and 'CreateUserID'.
2. **CalculatorClient.java:** This file contains the client-side implementation. The client interacts with the server to perform operations on its stack. This file handles command-line inputs and communictes with the server using methods defined in the calculator interface. 
3. **Calculatorimplementation.java:** This file contains the server-side implementation of the Calculator interface. This file manages individual stacks for each client using a unique ID. The operations managed include pushing integer values onto the stack and performing operations such as min, max, gcd and lcm. 
4. **CalculatorServer.java:** This file sets up the RMI registry and binds the CalculatorImplementation to a name, which then allows it to become available for clients to look up and invoke methods remotely.

## **Functionality** ##

### Key Features ###
- **Unique user stacks:** Each client has its own unique stack identified by a userID. This allows multiple clients to perform operations concurrently without interfering with other clients.
- **Stack Operations:** Clients can push integers onto the stack, apply operations such as 'min', 'max', 'gcd', 'lcm' and pop values from the stack. Additionaly, there is a delayedPop method which pops the values from the stack after a specified delay.
- **Concurrency:** The program supports concurrent client operations. It also ensures thread safety and avoids race conditions using synchronised blocks.

### **Methods** ###
- **public String UserID():** Generates and return a unique identifier to each client. This is used to manage seprate stacks.
- **public void pushValue(String id, int val):** Pushes a value onto the stack associated with the provided userID.
- **public void pushOperation(String id, String operator):** Applies the specified operation to the stack associated with UserID.
- **public Integer pop(String id):** Pops and returns the top value to the client.
- **public boolean isEmpty(String id):** Checks if stack is empty.
- **public Integer delayPop(String id, int millis):** Pops the top value from the stack after a specified delay in milliseconds. 

## **Compilation and Execution** ##

### Steps for execution ###
A Makefile has been provided to streamline the process of compiling, running, and testing the program. 

1. **Clean compiled files**
Running 'make clean' to remove all compiled '.class' files in the directory. 
2. **Compile the program**
Running "make compile" to compile all the '.java' files in the directory. 
3. **Start the RMI Registry**
Running "make registry' to start the RMI registry in the background. This allows the server to register remote objects. 
There is a 'Sleep 3' command after starting the RMI registry to ensure there is enough time to properly start the registry before the server and client try to connect.
4. **Start the Server**
Running 'make server' to start the calculator server in the background. The server registers the remote objects with the RMI registry and waits for client connections.
5. **Start the client**
Running 'make client' to start the client and execute the operations specified in the 'TestInput' files. The output is saved in "Output.txt". 
6. **Compare Outputs**
Running 'make outputCompare' to compare the actual output with the expected ouput saved in 'ExpectedOutput0.txt', 'ExpectedOutput1.txt'.

### **Complete workflow** ###
Running 'make make' will execute the complete workflow i.e. it will first clean the directory of old compilied .class files, then compile, start the RMI registry, start the server, then the client and then display the expected output and acutual output on the terminal. The program uses Testinput 0, 1, 2 and 3 to conduct a test.

## **Testing** ##
In this assignment, I have implemented automated testing by creating four input files (TestInput0.txt, TestInput1.txt, TestInput2.txt, TestInput3.txt) that represent four different clients interacting with the server. These files contains various command including edge cases and other valid operations.

Each one of the function implemented (createUserID, pushValue, pushOperation, pop, isEmpty, delayPop) is tested by these four files. The CalculatorClient.java file initialises four threads each representing a differnt client. The fileProcessor() function reads these commands and then the argument_exe() function sends the associated requests to the server to be completed. 

The ouputs from these tests are captured and directed to a file "Output.txt". There are two expected outputs for this specific test case to account for the inherent non-determinism in the order of thread execution. If the test matches either of the two expected outputs, the test is successful. 
