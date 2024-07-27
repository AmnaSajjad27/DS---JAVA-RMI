// Interface defining remote operations
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote
{
    // Push a value to the top of the stack 
    public void pushValue(int val) throws RemoteException;

    // Push a string containing an operator to the stack 
    public void pushOperation(String operator) throws RemoteException;

    // Pop the top of the client and return it to the client
    public Integer pop() throws RemoteException;

    // Return true if stack is empty
    public boolean isEmpty() throws RemoteException;

    // Wait Millis milliseconds before carrying out the pop operation
    public Integer delayPop(int millis) throws RemoteException;
}