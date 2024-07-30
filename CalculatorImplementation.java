import java.rmi.RemoteException;
import java.util.*;

public class CalculatorImplementation implements Calculator
{
    // Stack to store values/operations 
    // private Stack<Integer> stack;

    // Using a map now for uique stack implementation
    private Map<String, Stack<Integer>> values = new HashMap<>();

    // Constructor 
    public CalculatorImplementation() throws RemoteException
    {
        super();
        // this.stack = new Stack<>();
    }

    // LCM - recurrison to find lcm of current and next element 
    // second attempt - implement using a helper function 
    private static int helper_gcd(int x, int y)
    {
        while (y != 0)
        {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }
    private static int helper_lcm(int x, int y)
    {
        return (x * y) / helper_gcd(x, y);
    }
    private static int lcm(Stack<Integer> stack)
    {
        int return_lcm = stack.get(0);
        for (int i = 1; i < stack.size(); i++)
        {
            return_lcm = helper_lcm(return_lcm, stack.get(i));
        }
        return return_lcm;
    }
    private static int gcd(Stack<Integer> stack)
    {
        int return_gcd = stack.get(0);
        for (int i = 1; i < stack.size(); i++)
        {
            return_gcd = helper_gcd(return_gcd, stack.get(i));
        }
        return return_gcd;
    }
    
    public String UserID()
    {
        String id = UUID.randomUUID().toString();
        this.values.put(id, new Stack<>());
        return id;
    }

    // Public methods 
    public void pushValue(String id, int val)
    {
        this.values.get(id).push(val);
    }

    public void pushOperation(String id, String operator)
    {
        if(this.values.get(id).size() > 0)
        {
            int result;
            if (operator.contains("min"))
            {
                result = Collections.min(this.values.get(id));
            }
            else if (operator.contains("max"))
            {
                result = Collections.max(this.values.get(id));
            }
            else if (operator.contains("lcm"))
            {
                result = lcm(this.values.get(id));
            }
            else 
            {
                result = gcd(this.values.get(id));
            }
            this.values.get(id).clear();
            this.values.get(id).add(result);
        }
    }

    public Integer pop(String id)
    {
        if(this.values.get(id).size() == 0)
        {
            return null;
        }
        else
        {
            return this.values.get(id).pop();
        }
    }

    public boolean isEmpty(String id)
    {
        return this.values.get(id).isEmpty();
    }

    public Integer delayPop(String id, int millis)
    {
        if(this.values.get(id).size() > 0)
        {
            int result = -1;
            try
            {
                Thread.sleep(millis);
                result = this.values.get(id).pop();
            }
            catch (Exception e)
            {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
            return result;
        }
        else
        {
            return null;
        }
    }
}