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
    };

    // methods performing min, max, lcm and gcd

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

    private static int gcd(Stack<Integer> stack)
    {
        int return_gcd = stack.get(0);
        for (int i = 1; i < stack.size(); i++)
        {
            return_gcd = helper(return_gcd, stack.get(i));
        }
        return return_gcd;
    }


}