import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;

public class CalculatorClient implements Runnable
{
    private Calculator stub;

    public CalculatorClient(Calculator stub)
    {
        this.stub = stub;
    }

    private void arguments(String arguments[])
    {
        if (arguments[0].contains("pushOperation"))
        {
            try
            {
                this.stub.pushOperation(arguments[1]);
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
                this.stub.pushValue(Integer.valueOf(arguments[1]));
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
                System.out.println(this.stub.pop());
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
                System.out.println(this.stub.isEmpty());
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
                System.out.println(this.stub.delayPop(Integer.valueOf(arguments[1])));
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.toString());
            }
        }
    };

    public void fileProcessor()
    {
        try
        {
            String file = "./Test_input_1" + Thread.currentThread().getName() + ".txt";
            file = file.replace("Thread-", "");

            Scanner in = new Scanner(new File(file));
            in.useDelimiter("\n");

            boolean exit = false;
            
            while(exit == false)
            {
                String arguments = "";
                arguments += in.nextLine();

                String argument_split[] = arguments.split(" ");

                if (argument_split[0].contains("exit"))
                {
                    exit = true;
                }
                arguments(argument_split);
            }
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.toString());
        }
    }

    public void run()
    {
        try
        {
            // Example of some operations to test the RMI methods
            stub.pushValue(10);
            stub.pushValue(20);
            stub.pushOperation("max");
            System.out.println("Max value: " + stub.pop()); // Should print 20

            stub.pushValue(15);
            stub.pushValue(25);
            stub.pushOperation("min");
            System.out.println("Min value: " + stub.pop()); // Should print 15

            stub.pushValue(5);
            stub.pushValue(10);
            System.out.println("Popped value after delay: " + stub.delayPop(2000)); // Should print 10 after 2 seconds

            // Add more logic if necessary to test different scenarios
        } 
        catch (Exception e)
        {
            System.err.println("Client run exception: " + e.toString());
            e.printStackTrace();
        }
    }

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