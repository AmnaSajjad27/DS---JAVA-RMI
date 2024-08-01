import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;

public class CalculatorImplementation implements Calculator
{
    // Stack to store values/operations 
    // private Stack<Integer> stack;

    // Using a map now for uique stack implementation
    private final Map<String, ConcurrentLinkedDeque<Integer>> values = new HashMap<>();
    // Implementing a lock to ensure synchronising 

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
    private static int lcm(ConcurrentLinkedDeque<Integer> stack)
    {
        int return_lcm = stack.peekFirst();
        for (Integer value : stack)
        {
            return_lcm = helper_lcm(return_lcm, value);
        }
        return return_lcm;
    }
    private static int gcd(ConcurrentLinkedDeque<Integer> stack)
    {
        int return_gcd = stack.peekFirst();
        for (Integer value : stack)
        {
            return_gcd = helper_gcd(return_gcd, value);
        }
        return return_gcd;
    }
    
    public String UserID()
    {
        String id = UUID.randomUUID().toString();
        this.values.put(id, new ConcurrentLinkedDeque<>());
        return id;
    }

    // Public methods 
    public void pushValue(String id, int val)
    {
        ConcurrentLinkedDeque<Integer> stack = this.values.get(id);
        if (stack != null)
        {
            synchronized(stack)
            {
                stack.push(val);
            }
        }
    }

    public void pushOperation(String id, String operator)
    {
        ConcurrentLinkedDeque<Integer> stack = this.values.get(id);
        if(stack != null && !stack.isEmpty())
        {
            synchronized (stack)
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
                stack.clear();
                stack.add(result);
            }
        }
    }

    public Integer pop(String id)
    {
        ConcurrentLinkedDeque<Integer> stack = this.values.get(id);
        if(stack != null && !stack.isEmpty())
        {
            synchronized (stack)
            {
                return stack.pop();
            }
        }
        else
        {
            return null;
        }
    }


    public boolean isEmpty(String id)
    {
        ConcurrentLinkedDeque<Integer> stack = this.values.get(id);
        return stack == null || stack.isEmpty();
    }

    public Integer delayPop(String id, int millis)
    {
        ConcurrentLinkedDeque<Integer> stack = this.values.get(id);
        if(stack != null && !stack.isEmpty())
        {
            int result = -1;
            try
            {
                Thread.sleep(millis);
                synchronized (stack)
                {
                    return stack.pop();
                }
            }
            catch (Exception e)
            {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
            return result;
        }
        return null;  
    }
}
