import java.rmi.RemoteException;
import java.util.*;

public class CalculatorImplementation implements Calculator
{
    // Stack to store values/operations -- MAY USE MAP LATER FOR UNIQUE STACK 
    private Stack<Integer> stack;

    // Constructor 
    public CalculatorImplementation() throws RemoteException
    {
        super();
        this.stack = new Stack<>();
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

    // Public methods 
    public void pushValue(int val)
    {
        this.stack.push(val);
    }

    public void pushOperation(String operator)
    {
        if(this.stack.size() > 0)
        {
            int result;
            if (operator.contains("min"))
            {
                result = Collections.min(this.stack);
            }
            else if (operator.contains("max"))
            {
                result = Collections.max(this.stack);
            }
            else if (operator.contains("lcm"))
            {
                result = lcm(this.stack);
            }
            else 
            {
                result = gcd(this.stack);
            }
            this.stack.clear();
            this.stack.push(result);
        }
    }

    public Integer pop()
    {
        if(this.stack.size() == 0)
        {
            return null;
        }
        else
        {
            return this.stack.pop();
        }
    }

    public boolean isEmpty()
    {
        return this.stack.isEmpty();
    }

    public Integer delayPop(int millis)
    {
        if(this.stack.size() > 0)
        {
            int result = -1;
            try
            {
                Thread.sleep(millis);
                result = this.stack.pop();
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