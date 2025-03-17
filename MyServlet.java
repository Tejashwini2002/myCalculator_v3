package myPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	    private String[] jokes = {
	        "Why do Java developers wear glasses? Because they don't C#!",
	        "I told my computer I needed a break, and now it won't stop sending me Kit Kat bars.",
	        "Why don't programmers like nature? It has too many bugs.",
	        "How many programmers does it take to change a light bulb? None, it's a hardware problem.",
	    };
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String expression = request.getParameter("expression");
        String randomJoke = jokes[(int) (Math.random() * jokes.length)];
        String result;

		try {
            if (expression == null || expression.isEmpty()) {
                result = "Expression is empty!";
            } else {
                result = evaluateExpression(expression);
            }
            response.sendRedirect("NewFile.jsp?ans=" + result + "&joke=" + randomJoke);
        } catch (Exception e) {
            response.sendRedirect("NewFile.jsp?ans=Invalid Expression!&joke=" + randomJoke);
        }
	}
	private String evaluateExpression(String expression) throws Exception {
	    try {
	        // Step 1: Parse and evaluate the expression
	        double result = evaluate(expression);
	        return String.valueOf(result);
	    } catch (ArithmeticException ex) {
	        return "Math Error (e.g., division by zero)";
	    } catch (Exception ex) {
	        return "Invalid Expression!";
	    }
	}

	private double evaluate(String expression) throws Exception {
	    // Remove all whitespace from the expression
	    expression = expression.replaceAll("\\s+", "");

	    // Stacks for numbers (operands) and operators
	    java.util.Stack<Double> operands = new java.util.Stack<>();
	    java.util.Stack<Character> operators = new java.util.Stack<>();

	    // Iterate through each character in the expression
	    for (int i = 0; i < expression.length(); i++) {
	        char ch = expression.charAt(i);

	        // Case 1: If it's a digit, parse the full number
	        if (Character.isDigit(ch)) {
	            StringBuilder sb = new StringBuilder();
	            while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
	                sb.append(expression.charAt(i));
	                i++;
	            }
	            i--; // Step back since the loop will increment `i`
	            operands.push(Double.parseDouble(sb.toString()));
	        }

	        // Case 2: If it's an opening parenthesis, push it to the operator stack
	        else if (ch == '(') {
	            operators.push(ch);
	        }

	        // Case 3: If it's a closing parenthesis, solve the sub-expression
	        else if (ch == ')') {
	            while (!operators.isEmpty() && operators.peek() != '(') {
	                processStacks(operands, operators);
	            }
	            operators.pop(); // Pop the '('
	        }

	        // Case 4: If it's an operator, process higher precedence operators first
	        else if (isOperator(ch)) {
	            while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
	                processStacks(operands, operators);
	            }
	            operators.push(ch);
	        }
	    }

	    // Process remaining operators in the stack
	    while (!operators.isEmpty()) {
	        processStacks(operands, operators);
	    }

	    // The final result will be in the operands stack
	    return operands.pop();
	}

	// Helper method to process stacks
	private void processStacks(java.util.Stack<Double> operands, java.util.Stack<Character> operators) throws Exception {
	    if (operands.size() < 2) throw new Exception("Invalid Expression");
	    double b = operands.pop();
	    double a = operands.pop();
	    char operator = operators.pop();

	    switch (operator) {
	        case '+':
	            operands.push(a + b);
	            break;
	        case '-':
	            operands.push(a - b);
	            break;
	        case '*':
	            operands.push(a * b);
	            break;
	        case '/':
	            if (b == 0) throw new ArithmeticException("Division by zero");
	            operands.push(a / b);
	            break;
	        default:
	            throw new Exception("Unknown operator");
	    }
	}

	// Helper method to check if a character is an operator
	private boolean isOperator(char ch) {
	    return ch == '+' || ch == '-' || ch == '*' || ch == '/';
	}

	// Helper method to determine operator precedence
	private int precedence(char operator) {
	    switch (operator) {
	        case '+':
	        case '-':
	            return 1;
	        case '*':
	        case '/':
	            return 2;
	        default:
	            return 0;
	    }
	}
	
    
    	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
