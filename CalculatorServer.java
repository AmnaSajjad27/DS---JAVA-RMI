import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer
{
    /**
     * The main method to start the Calculator RMI server
     * Creates an instance of CalculatorImplementation 
     * Exports it as a remote object and binds it to the RMI registry with the name "Calculator".
     * @param args Argument inputs from command line or from a file 
     */
    public static void main (String[] args)
    {
        try
        {
            // Creating an instance of implementation class
            CalculatorImplementation calculator = new CalculatorImplementation();

            // Export as a remote object 
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(calculator, 0);
            
            // Locate and bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Calculator", stub);

            System.err.println("Server ready");
        }
        // Handle Exceptions 
        catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}