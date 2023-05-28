package kr.ac.duksung.homework5_cal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalActivity extends AppCompatActivity {

    int calResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        setTitle("계산");

        Button backBtn = (Button) findViewById(R.id.backBtn);   // 복귀 버튼
        final TextView textView = (TextView) findViewById(R.id.textView2);


        Intent intent = getIntent();    // Intent 객체 가져옴
        int num1 = intent.getIntExtra("num1",0);  //여러개 보낼때는 여러번 키값 써서 받아옴
        int num2 = intent.getIntExtra("num2",0);    // int로 받기
        String operator = intent.getStringExtra("operator");


        switch (operator) { // 연산자에 따라 계산 해주기
            case "+":
                calResult = num1 + num2;
                break;
            case "-":
                calResult = num1 - num2;
                break;
            case "*":
                calResult = num1 * num2;
                break;
            case "/":
                calResult = num1 / num2;
                break;
        }

        // 리터럴 스트링 객체 생성
        textView.setText("("+num1 + ")" + operator + "(" + num2 + ")=" + calResult);   // 텍스트 표시

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

