// Interface defining remote operations
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator
{
    // Push a value to the top of the stack 
    public void pushValue(int val);

    // Push a string containing an operator to the stack 
    public void pushOperation(String operator);

    // Pop the top of the client and return it to the client
    public int pop();

    // Return true if stack is empty
    public boolean isEmpty();

    // Wait Millis milliseconds before carrying out the pop operation
    public int delayPop(int millis);
}