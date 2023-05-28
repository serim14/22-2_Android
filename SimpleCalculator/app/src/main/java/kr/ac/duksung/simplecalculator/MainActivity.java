package kr.ac.duksung.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edit1, edit2;
    Button btnAdd, btnSub;
    TextView textResult;
    String num1, num2;
    Integer result; // wrapper 클래스, int값을 객체화 시킴

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 화면이 보일때 딱 한번 실행, main의 역할
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 설계해두었던 객체 생성됨(위젯)
        setTitle("초간단 계산기");    // 제목 달기

        edit1 = (EditText) findViewById(R.id.Edit1);
        edit2 = (EditText) findViewById(R.id.Edit2);

        btnAdd = (Button) findViewById(R.id.BtnAdd);
        btnSub = (Button) findViewById(R.id.BtnSub);

        textResult = (TextView) findViewById(R.id.TextResult);


        btnAdd.setOnTouchListener( (arg0, arg1) -> {

            num1 = edit1.getText().toString();  // 가져온게 text형이니 자바의 string으로 변환
            num2 = edit2.getText().toString();
            num1 = num1.trim();   // 공백 제거, 실수로 입력한 공백 제거
            num2 = num2.trim();

            // if(num1=="") -> 메모리 주소가 더른 두 객체이기 때문에 이렇게 하면 안됨
            if(num1.equals("")) {  // trim()한 결과가 빈 문자열이라면(아무것도 입력안해도 빈문자열), 리터럴 객체("" 빈문자열로 고정)
                textResult.setText("숫자1을 입력하세요");
            } else if (num2.equals("")) {
                textResult.setText("숫자2를 입력하세요");
            }
            else {    // trim() 결과가 빈 문자열이 아니라면, 섭씨값 계산
                result = Integer.parseInt(num1) + Integer.parseInt(num2);   // parseInt 문자열을 정수로 변환
                textResult.setText("계산 결과 : " + result.toString()); // 다시 문자열로 변환시켜 표시
            }
            return false;

        });


        // 클래스가 준수하는 인터페이스를 이용해 익명 객체로 리스너 객체 생성(클래스 생성 없이)
        // 클릭 리스너가 아닌 터치 리스너
        // 클릭은 눌렀다 떼는거
        // 터치: 누르는거 떼는거 드래그 하는거, 훨씬 더 정교
        // 터치 리스너는 return false 해야함
        // 클릭 리스너는 반환형 없음
        /*
        btnAdd.setOnTouchListener(new View.OnTouchListener() {  // 인터페이스 명시
            public boolean onTouch(View arg0, MotionEvent arg1) {   // bool형 반환해줘야하기 때문에 return false

                num1 = edit1.getText().toString();  // 가져온게 text형이니 자바의 string으로 변환
                num2 = edit2.getText().toString();
                num1 = num1.trim();   // 공백 제거, 실수로 입력한 공백 제거
                num2 = num2.trim();

                // if(num1=="") -> 메모리 주소가 더른 두 객체이기 때문에 이렇게 하면 안됨
                if(num1.equals("")) {  // trim()한 결과가 빈 문자열이라면(아무것도 입력안해도 빈문자열), 리터럴 객체("" 빈문자열로 고정)
                    textResult.setText("숫자1을 입력하세요");
                } else if (num2.equals("")) {
                    textResult.setText("숫자2를 입력하세요");
                }
                else {    // trim() 결과가 빈 문자열이 아니라면, 섭씨값 계산
                    result = Integer.parseInt(num1) + Integer.parseInt(num2);   // parseInt 문자열을 정수로 변환
                    textResult.setText("계산 결과 : " + result.toString()); // 다시 문자열로 변환시켜 표시
                }

                return false;   // onTouch 끝냄
            }
        });

         */

        btnSub.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {

                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                num1 = num1.trim();   // 공백 제거
                num2 = num2.trim();

                if(num1.equals("")) {  // trim()한 결과가 빈 문자열이라면
                    textResult.setText("숫자1을 입력하세요");

                } else if (num2.equals("")) {
                    textResult.setText("숫자2를 입력하세요");
                }
                else {    // trim() 결과가 빈 문자열이 아니라면, 섭씨값 계산

                    result = Integer.parseInt(num1) - Integer.parseInt(num2);
                    textResult.setText("계산 결과 : " + result.toString());
                }


                return false;
            }
        });
    }
}