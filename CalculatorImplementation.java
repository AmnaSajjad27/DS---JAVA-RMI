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

    // Private methods performing lcm and gcd

    private static int helper(int x, int y)
    {
        if (y == 0)
        {
            return x;
        }
        return helper(y, x % y);
    }

    // LCM - recurrison to find lcm of current and next element 
    private static int lcm(Stack<Integer> stack)
    {
        int return_lcm = stack.get(0);

        for (int i = 1; i < stack.size(); i++)
        {
            int num = stack.get(i);
            return_lcm = (return_lcm * num) / helper(return_lcm, num);
        }
        return return_lcm;
    }

    // GCD 
    private static int gcd(Stack<Integer> stack)
    {
        int return_gcd = stack.get(0);
        for (int i = 1; i < stack.size(); i++)
        {
            return_gcd = helper(return_gcd, stack.get(i));
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