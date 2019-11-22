package com.example.myapplication;

import net.sourceforge.jeval.EvaluationException;

import java.util.Stack;

public class calculator {
    private static Stack<Integer> num;
    private static Stack<Character> operator;
    private static String S;

    public String evaluate(String s) throws EvaluationException {
        int a = 0, b, c;
        char ch;
        S = s + '#';
        char[] ss = S.toCharArray();
        boolean flag = true;
        num.empty();
        operator.empty();
        operator.push('#');
        for (int i = 0; i < ss.length; i++) {
            ch = ss[i];
            switch (ch) {
                case '0':
                case '1':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '2':
                    a = a * 10 + (ch - '0');
                    flag = true;
                    break;
                default:
                    if (flag==true || (flag == false && operator.peek() == '(')) {
                        num.push(a);
                        a = 0;
                        flag = false;
                    }
                    if (isp(operator.peek()) == icp(ch)) {
                        operator.pop();
                        break;
                    } else if (isp(operator.peek()) < icp(ch)) {
                        operator.push(ch);
                        break;
                    } else {
                        while (isp(operator.peek()) > icp(ch)) {
                            b = num.peek();
                            num.pop();
                            c = num.peek();
                            switch (operator.peek()) {
                                case '+':
                                    c = c + b;
                                    num.pop();
                                    num.push(c);
                                    operator.pop();
                                    break;
                                case '-':
                                    c = c - b;
                                    num.pop();
                                    num.push(c);
                                    operator.pop();
                                    break;
                                case '*':
                                    c = c * b;
                                    num.pop();
                                    num.push(c);
                                    operator.pop();
                                    break;
                                case '/':
                                    c = c / b;
                                    num.pop();
                                    num.push(c);
                                    operator.pop();
                                    break;
                                case '%':
                                    c = c % b;
                                    num.pop();
                                    num.push(c);
                                    operator.pop();
                                    break;
                            }
                        }
                        if (isp(operator.peek()) == icp(ch)) {
                            operator.pop();
                            break;
                        } else if (isp(operator.peek()) < icp(ch)) {
                            operator.push(ch);
                            break;
                        }
                    }
            }

        }
        return num.peek().toString();
    }
    //栈内优先级
    private int isp(char i) {
        switch (i) {
            case '#':
                return 0;
            case '(':
                return 1;
            case '*':
            case '/':
            case '%':
                return 5;
            case '+':
            case '-':
                return 3;
            case ')':
                return 6;
        }
        return 0;
    }
    //栈外优先级
    private int icp(char i){
        switch (i)
        {
            case '#':return 0;
            case '(':return 6;
            case'*':case'/':case'%':return 4;
            case'+':case'-':return 2;
            case')':return 1;
        }
        return 0;
    }
}
