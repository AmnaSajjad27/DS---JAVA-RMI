import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImplementation implements Calculator {
    private final Map<String, ConcurrentLinkedDeque<Integer>> values = new ConcurrentHashMap<>();

    public CalculatorImplementation() throws RemoteException {
        super();
    }

    private static int helper_gcd(int x, int y) {
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

    private static int helper_lcm(int x, int y) {
        return (x * y) / helper_gcd(x, y);
    }

    private static int lcm(ConcurrentLinkedDeque<Integer> stack) {
        int return_lcm = stack.peekFirst();
        for (Integer value : stack) {
            return_lcm = helper_lcm(return_lcm, value);
        }
        return return_lcm;
    }

    private static int gcd(ConcurrentLinkedDeque<Integer> stack) {
        int return_gcd = stack.peekFirst();
        for (Integer value : stack) {
            return_gcd = helper_gcd(return_gcd, value);
        }
        return return_gcd;
    }

    public String UserID() {
        String id = UUID.randomUUID().toString();
        values.put(id, new ConcurrentLinkedDeque<>());
        return id;
    }

    public void pushValue(String id, int val) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        if (stack != null) {
            synchronized (stack) {
                stack.push(val);
            }
        }
    }

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

    public boolean isEmpty(String id) {
        ConcurrentLinkedDeque<Integer> stack = values.get(id);
        synchronized (stack)
        {
            return stack == null || stack.isEmpty();

        }
    }

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