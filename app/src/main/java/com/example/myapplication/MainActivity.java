package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.calculator.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sourceforge.jeval.*;
import net.sourceforge.jeval.function.*;
import net.sourceforge.jeval.function.math.*;
import net.sourceforge.jeval.function.string.*;
import net.sourceforge.jeval.operator.*;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //创建Button对象   也就是activity_main.xml里所设置的ID
    Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_pt;
    Button btn_mul,btn_div,btn_add,btn_sub;
    Button btn_clr,btn_del,btn_eq;
    EditText et_input,et_output;
    boolean clr_flag;    //判断et编辑文本框中是否清空
    boolean cal;            //判断是否完成计算，即是否按“=”键
    String result;
    int sign;               //记录最后一位算术符号的位置
//    Map<String,Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化对象
        setContentView(R.layout.activity_main);
        btn_0= (Button) findViewById(R.id.btn_0);
        btn_1= (Button) findViewById(R.id.btn_1);
        btn_2= (Button) findViewById(R.id.btn_2);
        btn_3= (Button) findViewById(R.id.btn_3);
        btn_4= (Button) findViewById(R.id.btn_4);
        btn_5= (Button) findViewById(R.id.btn_5);
        btn_6= (Button) findViewById(R.id.btn_6);
        btn_7= (Button) findViewById(R.id.btn_7);
        btn_8= (Button) findViewById(R.id.btn_8);
        btn_9= (Button) findViewById(R.id.btn_9);
        btn_pt= (Button) findViewById(R.id.btn_pt);
        btn_add= (Button) findViewById(R.id.btn_add);
        btn_sub= (Button) findViewById(R.id.btn_sub);
        btn_mul= (Button) findViewById(R.id.btn_mul);
        btn_div= (Button) findViewById(R.id.btn_div);
        btn_clr= (Button) findViewById(R.id.btn_clr);
        btn_del= (Button) findViewById(R.id.btn_del);
        btn_eq= (Button) findViewById(R.id.btn_eq);
        et_input= (EditText) findViewById(R.id.et_input);
        et_output=(EditText)findViewById(R.id.et_output);

        //给按钮设置的点击事件
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_pt.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_eq.setOnClickListener(this);
        cal=false;
        sign=0;

//        map.put("+",1);
//        map.put("-",1);
//        map.put("×",2);
//        map.put("÷",2);
    }

    @Override
    public void onClick(View v) {
        String str=et_input.getText().toString();
        switch (v.getId()){
            case   R.id.btn_0:
                //如果一开始输入0，忽略，不进行操作
                if(((Button)v).getText().equals(str))
                {
                    break;
                }
                clr_flag=false;
                str+=((Button)v).getText();
                et_input.setText(str);
                getResult();
                break;
            case   R.id.btn_1:
            case   R.id.btn_2:
            case   R.id.btn_3:
            case   R.id.btn_4:
            case   R.id.btn_5:
            case   R.id.btn_6:
            case   R.id.btn_7:
            case   R.id.btn_8:
            case   R.id.btn_9:
                clr_flag=false;
                if(str.equals("0")||cal)
                {
                    str="";
                    cal=false;
                    sign=0;
                }
                str+=((Button)v).getText();
                et_input.setText(str);
                getResult();
                break;
            case   R.id.btn_pt:
                //如果已经结束运算，则输入小数点时——清除原有答案，并且输入小数0.xxx
                if(cal)
                {
                    str="";
                    sign=0;
                }
                cal=false;
                if(str.substring(sign,str.length()).indexOf(".")!=-1)
                {
                    break;
                }
                clr_flag=false;
                //如果字符串为空或者以加减乘除结尾，则在小数点前自动补0
                if(str.equals("")||"+".equals(str.substring(str.length()-1,str.length()))||"-".equals(str.substring(str.length()-1,str.length()))
                        ||"×".equals(str.substring(str.length()-1,str.length()))||"÷".equals(str.substring(str.length()-1,str.length())))
                {
                    str+="0";
                }
                if(!((Button)v).getText().equals(str.substring(str.length()-1,str.length())))
                {
                    str+=((Button)v).getText();
                }
                et_input.setText(str);
                getResult();
                break;
            case   R.id.btn_add:
            case   R.id.btn_mul:
            case   R.id.btn_div:
                cal=false;
                clr_flag=false;
                if(str.equals(""))
                {
                    str+="0";
                }
                //如果输入的字符跟已有字符串的最后一位冲突
                if("+".equals(str.substring(str.length()-1,str.length()))||"-".equals(str.substring(str.length()-1,str.length()))
                ||"×".equals(str.substring(str.length()-1,str.length()))||"÷".equals(str.substring(str.length()-1,str.length())))
                {
                    str=str.substring(0,str.length()-1);
                }
                str+=((Button)v).getText();
                sign=str.length()-1;
                et_input.setText(str);
                getResult();
                break;
            case   R.id.btn_sub:
                cal=false;
                clr_flag=false;
                //如果输入的字符跟已有字符串的最后一位冲突
                if(str.equals(""))
                {
                    str+="+";
                }
                if("+".equals(str.substring(str.length()-1,str.length()))||"-".equals(str.substring(str.length()-1,str.length())))
                {
                    str=str.substring(0,str.length()-1);
                }
                str+=((Button)v).getText();
                sign=str.length()-1;
                et_input.setText(str);
                getResult();
                break;
            case R.id.btn_clr:
                cal=false;
                clr_flag=true;
                sign=0;
                result="";
                et_input.setText("");
                et_output.setText("");
                break;
            case R.id.btn_del: //判断是否为空或者是否已经完成了计算，然后再进行删除
                if(clr_flag||cal){
                    str="";
                    result="";
                    cal=false;
                    sign=0;
                }
                //如果不为空，并且正在进行计算，则编辑框的数字需要逐个删除
                else if(str!=null&&!str.equals("")){
                    str=str.substring(0,str.length()-1);
                }
                et_input.setText(str);
                et_output.setText(str);
                if(!str.equals("")) getResult();
                break;
            case R.id.btn_eq: //单独运算最后结果
                et_input.setText(result);
                et_output.setText("");
                cal=true;
                break;
        }
    }

    private void getResult() {
        result=et_input.getText().toString();
        et_input.setText(result);
        if("+".equals(result.substring(result.length()-1,result.length()))||"-".equals(result.substring(result.length()-1,result.length())))
        {
            result+="0";
        }

        if("×".equals(result.substring(result.length()-1,result.length()))||"÷".equals(result.substring(result.length()-1,result.length())))
        {
            result+="1";
        }

        result=result.replace('×','*');
        result=result.replace('÷','/');
        Evaluator Cal=new Evaluator();
//        calculator Cal=new calculator();
        try{
            result=Cal.evaluate(result);
            if(result.endsWith("0")){
                result=result.substring(0,result.length()-2);
            }
            et_output.setText(result);

        }catch (EvaluationException e) {
            et_output.setText("错误");
        }
    }
}