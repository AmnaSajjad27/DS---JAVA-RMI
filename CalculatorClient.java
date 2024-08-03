import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;

public class CalculatorClient implements Runnable
{
    private Calculator stub;

    /** 
     * Constructor fot Calculator Client
     * @param stub An instance of the Calculator interface, used to make RMI calls. 
     */

    public CalculatorClient(Calculator stub)
    {
        this.stub = stub;
    }

    /**
     * Function to execute operations specified by the user in the arguments array
     * @param arguments, Array contains the command and its arguments
     * @param id, id is the user id for which the command is being executed
     * The method calls different methods on the stub based on the command.
     * Hanles exceptions if the stub method 
     */

    private void argument_exe(String arguments[], String id)
    {
        if (arguments[0].contains("pushOperation"))
        {
            try
            {
                this.stub.pushOperation(id, arguments[1]);
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
        else if (arguments[0].contains("pushValue"))
        {
            try
            {
                this.stub.pushValue(id, Integer.valueOf(arguments[1]));
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
        else if (arguments[0].contains("pop"))
        {
            try
            {
                System.out.println(this.stub.pop(id));
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
        else if (arguments[0].contains("isEmpty"))
        {
            try
            {
                System.out.println(this.stub.isEmpty(id));
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
        else if (arguments[0].contains("delayPop"))
        {
            try
            {
                System.out.println(this.stub.delayPop(id, Integer.valueOf(arguments[1])));
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
    };

    /**
     * Read and process input and executes them
     * @param id, the user id to identify which user to execute for
     * The method reads input from file, splits into arguments and command 
     * and then executes using the argument_exe method
     * Handles exceptions if oeprations fail 
     */

    public void fileProcessor(String id)
    {
        try
        {
            String file = "./TestInput" + Thread.currentThread().getName() + ".txt";
            file = file.replace("Thread-", "");

            Scanner in = new Scanner(new File(file));
            in.useDelimiter("\n");

            boolean exit = false;
            
            while(exit == false)
            {
                String command_keyword = "";
                command_keyword += in.nextLine();

                String argument_split[] = command_keyword.split(" ");

                if (argument_split[0].contains("exit"))
                {
                    exit = true;
                }
                argument_exe(argument_split, id);
            }
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.toString());
        }
    }

    /**
     * Method gets a user ID by calling userID method on the stub
     * Then calls fileprocessor to process commands 
     * Handles exceptions if the stub method call fails 
     */

    public void run()
    {
        String id = "empty";
        try
        {
            id = this.stub.UserID();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.toString());
        }
        fileProcessor(id);
    }

    /**
     * Main method to start client
     * @param args, method connects to the RMI registry, looks up the Calculator stub
     * and starts multiple client threads
     * Handles exceptions if the RMI lookup or thread start fails
     */

    public static void main(String[] args)
    {
        String host = (args.length < 1) ? null : args[0];
        try
        {
            Registry registry = LocateRegistry.getRegistry(host);
            Calculator stub = (Calculator) registry.lookup("Calculator");

            for (int i = 0; i < 4; i++)
            {
                Thread t = new Thread(new CalculatorClient(stub));
                t.start();
            }
        }
        catch (Exception e)
        {
            System.err.println("CalculatorClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}