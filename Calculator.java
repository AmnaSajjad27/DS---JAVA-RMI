// Interface defining remote operations
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote
{
    // Push a value to the top of the stack 
    public void pushValue(String id, int val) throws RemoteException;

    // Push a string containing an operator to the stack 
    public void pushOperation(String id, String operator) throws RemoteException;

    // Pop the top of the client and return it to the client
    public Integer pop(String id) throws RemoteException;

    // Return true if stack is empty
    public boolean isEmpty(String id) throws RemoteException;

    // Wait Millis milliseconds before carrying out the pop operation
    public Integer delayPop(String id, int millis) throws RemoteException;

    // Adding functionality to implement multiple users each with their own stack 
    public String UserID() throws RemoteException;
}