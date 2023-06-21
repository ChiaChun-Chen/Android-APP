package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.resources.TextAppearance;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edT;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnNeg, btnDot, btnC, btnBrackets, btnPercent, btnPlus, btnMinus, btnMul, btnDiv, btnEqual, btnBack;
    int index;
    int isEqual;    //a flag to check whether the user has push equal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isEqual=0;  //init the flag with zero

        edT = findViewById(R.id.txtView);
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btnNeg = findViewById(R.id.btn_negative);
        btnDot = findViewById(R.id.btn_dot);
        btnC = findViewById(R.id.btn_c);
        btnBrackets = findViewById(R.id.btn_brackets);
        btnPercent = findViewById(R.id.btn_percent);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMul = findViewById(R.id.btn_multiply);
        btnDiv = findViewById(R.id.btn_divide);
        btnEqual = findViewById(R.id.btn_equal);
        btnBack = findViewById(R.id.btn_back);

        edT.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnNeg.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnBrackets.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //prevent from showing the default keyboard
        edT.setShowSoftInputOnFocus(false);
    }

    private void addText(String s){
        //check whether this is a new round of the calculation
        //if isEqual==1, that means this should be a new round and need to reset everything.
        if (isEqual == 1) {
            edT.setText("");
            index = 0;
            isEqual = 0;
        }

        //get the index of the cursor
        index = edT.getSelectionStart();
        Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();

        //add text to the chosen place, and refresh the cursor position and the new string
        String str = edT.getText().toString();
        edT.setText(str.substring(0,index)+s+str.substring(index));
        index=index+s.length();
        edT.setSelection(index);

        if(str.length()<10){
            edT.setTextSize(50);
        } else if (str.length()<25) {
            edT.setTextSize(40);
        } else if (str.length()<35) {
            edT.setTextSize(35);
        }else {
            edT.setTextSize(30);
        }
    }

    private void deleteText(){
        index = edT.getSelectionStart();

        //need to check whether there is something to delete, or there will be a crash.
        if(index!=0){
            String str = edT.getText().toString();

            //since there must not be a number like 03 or anything start with 0,
            //hence if we would like to delete 0.3 from the dot, we should also delete 0
            if(str.charAt(index-1)=='.' && str.charAt(index-2)=='0'){
                edT.setText(str.substring(0,index-2)+str.substring(index));
                index=index-2;
                edT.setSelection(index);
            }else {
                edT.setText(str.substring(0, index - 1) + str.substring(index));
                index = index - 1;
                edT.setSelection(index);
            }
        }
    }

    private boolean onClick_negative(String s){
        //check whether this is a new round of the calculation
        //if isEqual==1, that means this should be a new round and need to reset everything.
        if (isEqual == 1) {
            edT.setText("");
            index = 0;
            isEqual = 0;
        }

        index = edT.getSelectionStart();

        //need to minus one since the index will always point to the next place so that it is always one more than the actual length.
        int tempIndex = index-1;

        for(int i=tempIndex; i>0 ;i--){

            //to find the first anti-number's index
            //add a condition "s.charAt(i)!='.'" so that it won't have a bug on float.
            if(!Character.isDigit(s.charAt(i))&&s.charAt(i)!='.'&&s.charAt(i)!='%'){
                Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                //if the there exists "(-" then cancel it, otherwise add "(-"
                //need to be careful for the index
                if(s.charAt(i)=='-' && s.charAt(i-1)=='('){
                    edT.setText(s.substring(0,i-1)+s.substring(i+1));
                    index=index-2;
                    edT.setSelection(index);
                    return true;
                } else{
                    edT.setText(s.substring(0,i+1)+"(-"+s.substring(i+1));
                    index=index+2;
                    edT.setSelection(index);
                    return false;
                }
            }
            Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        }

        //case for the first positive number
        edT.setText("(-"+s);
        index=index+2;
        edT.setSelection(index);
        return true;
    }

    private boolean onClick_dot(String s){
        //check whether this is a new round of the calculation
        //if isEqual==1, that means this should be a new round and need to reset everything.
        if (isEqual == 1) {
            edT.setText("");
            index = 0;
            isEqual = 0;
        }

        index = edT.getSelectionStart();

        //need to minus one since the index will always point to the next place so that it is always one more than the actual length.
        int tempIndex = index-1;

        if(index!=0){
            if(Character.isDigit(s.charAt(tempIndex))){
                addText(".");
                return true;
            }else {
                addText("0.");
                return false;
            }
        }

        //case for the first positive number
        edT.setText("0."+s);
        index=index+2;
        edT.setSelection(index);
        return true;
    }

    private void onClick_brackets(String s){
        //check whether this is a new round of the calculation
        //if isEqual==1, that means this should be a new round and need to reset everything.
        if (isEqual == 1) {
            edT.setText("");
            index = 0;
            isEqual = 0;
        }

        index = edT.getSelectionStart();

        int left=0, right=0;
        if(index!=0){
            if(s.charAt(index-1)=='+'||s.charAt(index-1)=='-'||s.charAt(index-1)=='x'||s.charAt(index-1)=='/'||s.charAt(index-1)=='('){
                addText("(");
            }else{

                //count the amount of left right brackets
                for (int i = 0; i < index; i++) {
                    if(s.charAt(i)=='('){
                        left+=1;
                    } else if (s.charAt(i)==')') {
                        right+=1;
                    }
                }

                if(left<=right){
                    addText("x(");
                }else {
                    addText(")");
                }
            }
        }else {
            //case that start from bracket
            addText("(");
        }
    }

    private void onClick_operator(String s){
        index = edT.getSelectionStart();
        String str = edT.getText().toString();

        //check for not allowing two operator exist continually
        if(str.charAt(index-1)=='+'||str.charAt(index-1)=='-'||str.charAt(index-1)=='x'||str.charAt(index-1)=='/'){
            edT.setText(str.substring(0, index-1)+s+str.substring(index));
            edT.setSelection(index); //important! If didn't set, the selectionStart will be zero.
        }else {
            addText(s);
        }
    }

    private void changeNotationFromInfixToPostfix(String s){
        String postfix = "";
        Stack<Character> operator = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if(Character.isDigit(s.charAt(i))||s.charAt(i)=='.'){
                //for digits and dot
                //for continuous digits and dot, we must not add " " between them
                //for the brackets, we add " " in its condition, and the first place bracket should not add " " too.
                if(i!=0&&(!Character.isDigit(s.charAt(i-1))&&s.charAt(i-1)!='.'&&s.charAt(i-1)!='(')){
                    postfix = postfix+" "+s.charAt(i);
                }else {
                    postfix = postfix+s.charAt(i);
                }
            } else if (s.charAt(i)=='%') {
                //ex. 3% -> 3 100 /
                postfix = postfix+" 100 /";
            } else if (s.charAt(i)=='-'&&s.charAt(i-1)=='(') {
                //case for negative number
                //the form of negative numbers is (-xx, so we first rewrite it into (0-xx,
                //then we can push '(' into the stack, add 0 to postfix, and finally push '-' into the stack.
                if(i-1!=0){
                    postfix = postfix+" 0";
                }else { //case when it is the first number
                    postfix = postfix+"0";
                }
                operator.push('(');
                operator.push('-');
            } else if (s.charAt(i)=='+'||s.charAt(i)=='-') {
                while (!operator.isEmpty()&&operator.peek()!='('){
                    postfix = postfix+" "+operator.peek();
                    operator.pop();
                }
                operator.push(s.charAt(i));
            } else if (s.charAt(i)=='x'||s.charAt(i)=='/') {
                if(!operator.isEmpty()&&(operator.peek()=='x'||operator.peek()=='/')&&operator.peek()!='('){
                    postfix = postfix+" "+operator.peek();
                    operator.pop();
                }
                operator.push(s.charAt(i));
            } else if (s.charAt(i)=='(') {
                //since numbers with brackets will be no empty like -(33+xx
                //hence we should add " " between the two elements if the brackets were not in the first place.
                if(i!=0){
                    postfix = postfix + " ";
                }
                operator.push(s.charAt(i));
            } else if (s.charAt(i)==')') {
                while (operator.peek()!='('){
                    postfix = postfix+" "+operator.peek();
                    operator.pop();
                }
                operator.pop(); //pop out '('
            }
        }
        while (!operator.isEmpty()){
            //since sometimes there could miss some ')', so while popping out last things in the stack,
            //we should provide to add '(' into the postfix
            if (operator.peek() != '(') {
                postfix = postfix+" "+operator.peek();
            }
            operator.pop();
        }
        //calculateTheAnswer(postfix);
        edT.setText(String.valueOf(calculateTheAnswer(postfix)));
        isEqual = 1;
    }

    private float calculateTheAnswer(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        String[] postList = s.split(" ");
        Stack<Float> numberStack = new Stack<>();
        float a=0, b=0;
        float temp;

        for (int i=0; i<postList.length; i++){
            if(postList[i].equals("+")){    //why can't I written into postList[i]=="+" ?
                b=numberStack.peek();
                numberStack.pop();
                a=numberStack.peek();
                numberStack.pop();
                numberStack.push(a+b);
            } else if (postList[i].equals("-")) {
                b=numberStack.peek();
                numberStack.pop();
                a=numberStack.peek();
                numberStack.pop();
                numberStack.push(a-b);
            } else if (postList[i].equals("x")) {
                b=numberStack.peek();
                numberStack.pop();
                a=numberStack.peek();
                numberStack.pop();
                numberStack.push(a*b);
            } else if (postList[i].equals("/")) {
                b=numberStack.peek();
                numberStack.pop();
                a=numberStack.peek();
                numberStack.pop();
                numberStack.push(a/b);
            }else {
                Toast.makeText(this, "postList[i] "+String.valueOf(postList[i]), Toast.LENGTH_SHORT).show();

                temp = Float.parseFloat(postList[i]);
                numberStack.push(temp);
                Toast.makeText(this, "else "+String.valueOf(numberStack.peek()), Toast.LENGTH_SHORT).show();
            }
        }

        return numberStack.peek();
        //Toast.makeText(this, String.valueOf(numberStack.peek()), Toast.LENGTH_SHORT).show();
        //return 1;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.txtView:
                Toast.makeText(this, String.valueOf(edT.getSelectionStart()), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_0:
                addText("0");
                break;
            case R.id.btn_1:
                addText("1");
                break;
            case R.id.btn_2:
                addText("2");
                break;
            case R.id.btn_3:
                addText("3");
                break;
            case R.id.btn_4:
                addText("4");
                break;
            case R.id.btn_5:
                addText("5");
                break;
            case R.id.btn_6:
                addText("6");
                break;
            case R.id.btn_7:
                addText("7");
                break;
            case R.id.btn_8:
                addText("8");
                break;
            case R.id.btn_9:
                addText("9");
                break;
            case R.id.btn_negative:
                //add "(-" or minus "(-"
                onClick_negative(edT.getText().toString());
                break;
            case R.id.btn_dot:
                //add "0." or "."
                onClick_dot(edT.getText().toString());
                break;
            case R.id.btn_c:
                edT.setText("");
                index=0;
                isEqual=0;
                break;
            case R.id.btn_brackets:
                //add "(" or ")"
                onClick_brackets(edT.getText().toString());
                break;
            case R.id.btn_percent:
                addText("%");
                break;
            case R.id.btn_plus:
                onClick_operator("+");
                break;
            case R.id.btn_minus:
                onClick_operator("-");
                break;
            case R.id.btn_multiply:
                onClick_operator("x");
                break;
            case R.id.btn_divide:
                onClick_operator("/");
                break;
            case R.id.btn_equal:
                //change a new line, and calculate the answer.
                changeNotationFromInfixToPostfix(edT.getText().toString());
                break;
            case R.id.btn_back:
                //delete one number
                deleteText();
                break;
            default:
                break;
        }
    }
}