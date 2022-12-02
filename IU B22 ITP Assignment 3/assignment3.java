import java.util.Scanner;


/* This is the Main Class that contains all variables and method.
 */
public class Main {
    /** New scanner object that has a parameter System.in.
     *To read from the standard input stream of the program.
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * main method that contains all other methods inside it.
     *  @param args Each string inside the array is a command line argument.
     */
    public static void main(String[] args) {

        //building an object of Main class
        Main m = new Main();
        int cmNumber;
        Calculator calculator = null;
        CalculatorType calculatorType = m.readCalculator();
        OperationType operationType;
        if (calculatorType == CalculatorType.INCORRECT) {
            m.reportFatalError("Wrong calculator type");
        } else {
            cmNumber = m.readCommandsNumber();
            if (cmNumber == -1) {
                m.reportFatalError("Amount of commands is Not a Number");
            } else {
                if (calculatorType == CalculatorType.INTEGER) {
                    calculator = new IntegerCalculator();
                } else if (calculatorType == CalculatorType.DOUBLE) {
                    calculator = new DoubleCalculator();
                } else if (calculatorType == CalculatorType.STRING) {
                    calculator = new StringCalculator();
                } else {
                    m.reportFatalError("Wrong calculator type");
                }

            }
            //Started a for loop to read lines from the input n times
            for (int i = 0; i < cmNumber; i++) {

                String operator;
                String arg1;
                String arg2;

                try {
                    operator = m.scanner.next();
                    operationType = m.parseOperation(operator);
                    arg1 = m.scanner.next();
                    arg2 = m.scanner.next();
                    switch (operationType) {
                        case ADDITION:
                            System.out.println(calculator.add(arg1, arg2));
                            break;

                        case SUBTRACTION:
                            System.out.println(calculator.subtract(arg1, arg2));
                            break;

                        case MULTIPLICATION:
                            System.out.println(calculator.multiply(arg1, arg2));
                            break;

                        case DIVISION:
                            System.out.println(calculator.divide(arg1, arg2));
                            break;

                        case INCORRECT:
                            m.reportFatalError("Wrong operation type");
                            break;

                        default:
                            m.reportFatalError("Wrong operation type");
                            break;

                    }
                } catch (Exception e) {
                    m.reportFatalError("Wrong operation type");
                }
            }
        }
    }

    /**
     * readCommandsNumber a function that reads the number of the commands and check if it is valid.
     *
     * @return returns integer value which is the number of commands also it can return the value of -1 it is invalid.
     */
    private int readCommandsNumber() {

        String cmNumber;
        try {
            cmNumber = scanner.nextLine();
            Integer.parseInt(cmNumber);
            final int maximum = 50;
            if (Integer.parseInt(cmNumber) >= 1 && Integer.parseInt(cmNumber) <= maximum) {
                return Integer.parseInt(cmNumber);
            } else {
                return -1;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * operationType is a function that used to decide the operation type.
     *
     * @param operation A string type parameter that stores the operation.
     * @return return an object type OperationType.
     */

    private OperationType parseOperation(String operation) {
        if (operation.equals("+")) {
            return OperationType.ADDITION;
        } else if (operation.equals("-")) {
            return OperationType.SUBTRACTION;
        } else if (operation.equals("*")) {
            return OperationType.MULTIPLICATION;
        } else if (operation.equals("/")) {
            return OperationType.DIVISION;
        } else {
            return OperationType.INCORRECT;
        }
    }

    /**
     * readCalculator is a function that read the calculator type and check if it valid or not.
     *
     * @return returns a CalculatorType object.
     */
    private CalculatorType readCalculator() {

        try {
            String type;
            type = scanner.nextLine();
            if (type.equals("DOUBLE")) {
                return CalculatorType.DOUBLE;
            } else if (type.equals("INTEGER")) {
                return CalculatorType.INTEGER;
            } else if (type.equals("STRING")) {
                return CalculatorType.STRING;
            } else {
                return CalculatorType.INCORRECT;
            }
        } catch (Exception e) {
            return CalculatorType.INCORRECT;
        }

    }

    /**
     * reportFatalError is a function that prints an error message.
     *
     * @param err Type string parameter that will contain the error message.
     */
    private void reportFatalError(String err) {
        System.out.println(err);
    }
}

/**
 * The types of the valid calculators.
 */
enum CalculatorType { INTEGER, STRING, DOUBLE, INCORRECT }

/**
 * The types of the valid operations.
 */
enum OperationType { ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, INCORRECT }

/**
 * An abstract class that define the calculator type and the methods of the calculator.
 */
abstract class Calculator {
    /**
     * The Addition method.
     *
     * @param a Type string parameter that will contain the first argument of the addition .
     * @param b Type string parameter that will contain the second argument of the addition .
     * @return it returns a string type which the result of the addition
     */
    public abstract String add(String a, String b);

    /**
     * The subtract method.
     *
     * @param a Type string parameter that will contain the first argument of the subtraction .
     * @param b Type string parameter that will contain the second argument of the subtraction .
     * @return it returns a string type which the result of the subtraction.
     */
    public abstract String subtract(String a, String b);

    /**
     * The Multiply method.
     *
     * @param a Type string parameter that will contain the first argument of the multiplication .
     * @param b Type string parameter that will contain the second argument of the multiplication .
     * @return it returns a string type which the result of the multiplication
     */
    public abstract String multiply(String a, String b);

    /**
     * The Division method.
     *
     * @param a Type string parameter that will contain the first argument of the division .
     * @param b Type string parameter that will contain the second argument of the division .
     * @return it returns a string type which the result of the division
     */
    public abstract String divide(String a, String b);
}

/**
 * A Class that represent a string calculator and the methods, and it inherits the calculator class.
 */

class StringCalculator extends Calculator {

    /**
     * This is an override of the add method that it belong to the class calculator of operation Addition.
     * it adds two strings.
     *
     * @param a A String Type parameter that represent the first string.
     * @param b A String Type parameter that represent the second string.
     * @return returns the two strings added or an error message
     */
    public String add(String a, String b) {
        return a + b;
    }


    /**
     * This is an override of the subtract method that it belong to the class calculator of operation subtraction.
     * it subtracts two strings.
     *
     * @param a A String Type parameter that represent the first string.
     * @param b A String Type parameter that represent the second string.
     * @return returns the two strings subtracted or an error message
     */
    public String subtract(String a, String b) {
        return "Unsupported operation for strings";
    }

    /**
     * This is an override of the multiply method that it belong to the class calculator of operation multiplication.
     * it multiply two strings.
     *
     * @param a A String Type parameter that represent the first string.
     * @param b A String Type parameter that represent the second string.
     * @return returns the first string that is multiplied by the second parameter or an error message
     */
    public String multiply(String a, String b) {

        int length;
        try {
            length = Integer.parseInt(b);
            if (length < 0) {
                return "Wrong argument type";
            } else {
                String output = "";
                for (int i = 0; i < length; i++) {
                    output += a;
                }
                return output;
            }
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }

    /**
     * This is an override of the divide method that it belong to the class calculator of operation division.
     *  divide two strings.
     *
     * @param a A String Type parameter that represent the first string.
     * @param b A String Type parameter that represent the second string.
     * @return returns the two strings divided or an error message
     */
    public String divide(String a, String b) {
        return "Unsupported operation for strings";
    }

}

/**
 * A Class that represent a double calculator and the methods, and it inherits the calculator class.
 */
class DoubleCalculator extends Calculator {

    /**
     * This is an override of the add method that it belong to the class calculator of operation Addition.
     * it adds two Doubles.
     *
     * @param a A String Type parameter that represent the first Double.
     * @param b A String Type parameter that represent the second Double.
     * @return returns the two Doubles added as a string or an error message
     */
    public String add(String a, String b) {
        double d1;
        double d2;
        double res;
        String result;
        try {
            d1 = Double.parseDouble(a);
            d2 = Double.parseDouble(b);
            res = d1 + d2;
            result = Double.toString(res);
        } catch (Exception e) {
            result = "Wrong argument type";
        }
        return result;
    }

    /**
     * This is an override of the subtract method that it belong to the class calculator of operation subtraction.
     * subtract two Doubles.
     *
     * @param a A String Type parameter that represent the first Double.
     * @param b A String Type parameter that represent the second Double.
     * @return returns the two Doubles subtracted as a string or an error message
     */
    public String subtract(String a, String b) {
        double d1;
        double d2;
        double res;
        String result;
        try {
            d1 = Double.parseDouble(a);
            d2 = Double.parseDouble(b);
            res = d1 - d2;
            result = Double.toString(res);
            return result;

        } catch (Exception e) {
            return "Wrong argument type";
        }
    }


    /**
     * This is an override of the multiply method that it belong to the class calculator of operation multiplication.
     * it multiply two Doubles.
     *
     * @param a A String Type parameter that represent the first Double.
     * @param b A String Type parameter that represent the second Double.
     * @return returns the two Doubles multiplied as a string or an error message
     */
    public String multiply(String a, String b) {
        double d1;
        double d2;
        double res;
        String result;

        try {
            d1 = Double.parseDouble(a);
            d2 = Double.parseDouble(b);
            res = d1 * d2;
            result = Double.toString(res);
            return result;
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }

    /**
     * This is an override of the divide method that it belong to the class calculator of operation division .
     * divide two Doubles.
     *
     * @param a A String Type parameter that represent the first Double.
     * @param b A String Type parameter that represent the second Double.
     * @return returns the two Doubles divided as a string or an error message
     */
    public String divide(String a, String b) {
        double d1;
        double d2;
        double res;
        String result;
        try {
            d1 = Double.parseDouble(a);
            d2 = Double.parseDouble(b);
            if (d2 == 0) {
                return "Division by zero";
            } else {
                res = d1 / d2;
                result = Double.toString(res);
                return result;
            }
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }
}

/**
 * A Class that represent an integer calculator and the methods, and it inherits the calculator class.
 */
class IntegerCalculator extends Calculator {


    /**
     * This is an override of the add method that it belong to the class calculator of operation Addition.
     * it adds two Integers.
     *
     * @param a A String Type parameter that represent the first Integer.
     * @param b A String Type parameter that represent the second Integer.
     * @return returns the two Integers added as a string or an error message
     */
    public String add(String a, String b) {
        int i1;
        int i2;
        int res;
        String result;
        try {
            i1 = Integer.parseInt(a);
            i2 = Integer.parseInt(b);
            res = i1 + i2;
            result = Integer.toString(res);
            return result;
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }

    /**
     * This is an override of the subtract method that it belong to the class calculator of operation subtraction.
     * subtract two Integers.
     *
     * @param a A String Type parameter that represent the first Integer.
     * @param b A String Type parameter that represent the second Integer.
     * @return returns the two Integers subtracted as a string or an error message
     */
    public String subtract(String a, String b) {
        int i1;
        int i2;
        int res;
        String result;
        try {
            i1 = Integer.parseInt(a);
            i2 = Integer.parseInt(b);
            res = i1 - i2;
            result = Integer.toString(res);
            return result;
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }


    /**
     * This is an override of the divide method that it belong to the class calculator of operation division.
     * divide two Integer.
     *
     * @param a A String Type parameter that represent the first Integer.
     * @param b A String Type parameter that represent the second Integer.
     * @return returns the two Integers divided as a string or an error message
     */
    public String divide(String a, String b) {
        int i1;
        int i2;
        int res;
        String result;
        try {
            i1 = Integer.parseInt(a);
            i2 = Integer.parseInt(b);
            if (i2 == 0) {
                return "Division by zero";
            } else {
                res = i1 / i2;
                result = Integer.toString(res);
                return result;
            }
        } catch (Exception e) {
            return "Wrong argument type";
        }
    }

    /**
     * This is an override of the multiply method that it belong to the class calculator of operation multiplication.
     * it multiply two Integers.
     *
     * @param a A String Type parameter that represent the first Integer.
     * @param b A String Type parameter that represent the second Integer.
     * @return returns the two Integers multiplied as a string or an error message
     */
    public String multiply(String a, String b) {
        int i1;
        int i2;
        int res;
        String result;
        try {
            i1 = Integer.parseInt(a);
            i2 = Integer.parseInt(b);
            res = i1 * i2;
            result = Integer.toString(res);
            return result;

        } catch (Exception e) {
            return "Wrong argument type";
        }
    }
}
