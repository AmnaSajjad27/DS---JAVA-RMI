import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImplementation implements Calculator {
    private final Map<String, ConcurrentLinkedDeque<Integer>> values = new ConcurrentHashMap<>();

    /**
     * Constructor for CalculatorImplementation 
     * @throws RemoteException if there is an issue with the RMI
     */

    public CalculatorImplementation() throws RemoteException {
        super();
    }

    /**
     * Helper function to calculate GCD of two numbers 
     * @param x First number
     * @param y Second number
     * @return The GCD of x and y
     */

    private static int helper_gcd(int x, int y) {
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

    /**
     * Helper function to calculate the LCM of two numbers
     * @param x First number
     * @param y Second number
     * @return The LCM of x and y
     */

    private static int helper_lcm(int x, int y) {
        return (x * y) / helper_gcd(x, y);
    }

    /**
     * Calculate the least LCM of all values in the stack 
     * @param stack, The stack containing values
     * @return the LCM of all value in the stack 
     */

    private static int lcm(ConcurrentLinkedDeque<Integer> stack) {
        int return_lcm = stack.peekFirst();
        for (Integer value : stack) {
            return_lcm = helper_lcm(return_lcm, value);
        }
        return return_lcm;
    }

    /**
     * Calculates the GCD of all values in the stack
     * @param stack, The stack containing values
     * @return The GCD of all values in the stack 
     */

    private static int gcd(ConcurrentLinkedDeque<Integer> stack) {
        int return_gcd = stack.peekFirst();
        for (Integer value : stack) {
            return_gcd = helper_gcd(return_gcd, value);
        }
        return return_gcd;
    }

    /**
     * Generates a new unique ID and initialises a new stack for the user
     * @return unique id for the new user 
     */

    public String UserID() {
        String id = UUID.randomUUID().toString();
        values.put(id, new ConcurrentLinkedDeque<>());
        return id;
    }

    /**
     * Pushes a value on to the stack associated with the given id
     * @param id, The user id
     * @param val, the value to be pushed onto the stack 
     * synchronized to ensure thread safety
     */

    public void pushValue(String id, int val) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        if (stack != null) {
            synchronized (stack) {
                stack.push(val);
            }
        }
    }

    /**
     * Applies operations on the stack associated with the user's unique id
     * @param id, user id
     * @param operator, the operation to be applied 
     * synchronized to ensure thread safety
     */

    public void pushOperation(String id, String operator) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        if (stack != null && !stack.isEmpty()) {
            synchronized (stack) {
                int result;
                if (operator.contains("min")) {
                    result = Collections.min(stack);
                } else if (operator.contains("max")) {
                    result = Collections.max(stack);
                } else if (operator.contains("lcm")) {
                    result = lcm(stack);
                } else {
                    result = gcd(stack);
                }
                stack.clear();
                stack.add(result);
            }
        }
    }

    /**
     * Pops a value from the stack associated with the unique user id
     * @param id, the user id
     * @return the popped value 
     * synchronized to ensure thread safety
     */

    public Integer pop(String id) 
    {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        if (stack != null && !stack.isEmpty()) 
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

    /**
     * checks if stack is empty
     * @param id, the user id
     * @return True if stack is empty, false otherwise
     */

    public boolean isEmpty(String id) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        synchronized (stack)
        {
            return stack == null || stack.isEmpty();

        }
    }

    /**
     * Pops a value from the stack associated with the given user ID after a delay
     * @param id The user id
     * @param millis The delay in milliseconds before popping the value
     * @return The popped value 
     */

    public Integer delayPop(String id, int millis) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        if (stack != null && !stack.isEmpty()) {
            try {
                Thread.sleep(millis);
                synchronized (stack) {
                    return stack.pop();
                }
            } catch (InterruptedException e) {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
        }
        return null;
    }
}