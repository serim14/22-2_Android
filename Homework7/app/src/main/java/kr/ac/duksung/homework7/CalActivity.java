package kr.ac.duksung.homework7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalActivity extends AppCompatActivity {

    int calResult;
    String finalResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        setTitle("계산");

        Button backBtn = (Button) findViewById(R.id.backBtn);   // 복귀 버튼
        final TextView textView = (TextView) findViewById(R.id.textView);


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

        finalResult = "("+num1 + ")" + operator + "(" + num2 + ")=" + calResult;
        // 리터럴 스트링 객체 생성
        textView.setText(finalResult);   // 텍스트 표시

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String result = finalResult;

                // 첫 화면에 뭘 보내야 하기 때문에 intent 객체 생성
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);    // Add에서 Main으로 전환(역방향)
                intent.putExtra("cal", result);    // 딕셔너리 객체, string 값 보냄
                setResult(RESULT_OK, intent);   // 첫 화면의 callback 함수 호출해줌
                // 첫번째 화면으로 돌아가는게 안됨
                finish();   // 두번째 액티비티 삭제, 밑에 깔려있던 첫번째 액티비티 실행

            }
        });
    }
}

