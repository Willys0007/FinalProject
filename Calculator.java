
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {


    public static char operator() {
        System.out.println("Choose a operator, either '+' '-' '/' '*':  ");
        char[] operators = {'+', '-', '/', '*'};


        char selectedOperator = 0;
        Scanner scan = new Scanner(System.in);
        char operator = scan.next().charAt(0);
        for (int i = 0; i < operators.length; i++) {
            if (operator == operators[i]) {
                return selectedOperator += operators[i];
            }
        }
        return selectedOperator;
    }


    public static double addNumber() {
        Scanner scannn = new Scanner(System.in);


        System.out.println("Input a number: ");
        double number = scannn.nextDouble();
        return number;
    }


    public static int operatorOrNumberOrGetResultFirst() {
        Scanner scan = new Scanner(System.in);
        int value;
        System.out.println("'1' is to add a number, '2' is to add a operator, '3' is to get the result");
        int whichOperatorIsFirst = scan.nextInt();
        while ((whichOperatorIsFirst != 1) && (whichOperatorIsFirst != 2) && (whichOperatorIsFirst != 3)) {
            System.out.println("Input a viable value: ");
            whichOperatorIsFirst = scan.nextInt();

        }
        if (whichOperatorIsFirst == 1) {
            return 1;
        } else if (whichOperatorIsFirst == 2) {
            return 2;
        } else {
            return 3;
        }

    }


    public static String keepAskingUserForInput() {
        //make a stringbuilder object to concatenate, then convert back to a string and exit out of loop by using return statement
        StringBuilder stringToCalculate = new StringBuilder();

        while (true) {
            int operatorOrNumberOrResult = Calculator.operatorOrNumberOrGetResultFirst();

            switch (operatorOrNumberOrResult) {
                case 1:
                    double initialNumber = Calculator.addNumber();
                    stringToCalculate.append(initialNumber).append(" ");
                    break;

                case 2:
                    char initialOperator = Calculator.operator();
                    stringToCalculate.append(initialOperator).append(" ");
                    break;

                case 3:
                    return stringToCalculate.toString();

                default:
                    System.out.println("Input a valid number, either '1' or '2': ");

            }
        }
    }
}





