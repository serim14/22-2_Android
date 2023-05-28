package kr.ac.duksung.homework5_cal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button equalBtn;
    EditText edit1, edit2;
    RadioGroup rGroup;
    RadioButton btnAdd, btnSub, btnMul, btnDiv;
    String num1, num2;
    TextView tv;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("입력");


        tv = (TextView) findViewById(R.id.textView2);
        tv.setText("환영합니다~");

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        btnAdd = (RadioButton) findViewById(R.id.btnAdd);
        btnSub = (RadioButton) findViewById(R.id.btnSub);
        btnMul = (RadioButton) findViewById(R.id.btnMul);
        btnDiv = (RadioButton) findViewById(R.id.btnDiv);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);

        equalBtn = (Button) findViewById(R.id.equalBtn);



        // 결과 페이지로 이동
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                num1 = num1.trim();   // 공백 제거
                num2 = num2.trim();

                if(num1.equals("")) {  // trim()한 결과가 빈 문자열이라면
                    Toast.makeText(getApplicationContext(), "첫번째 정수 입력", Toast.LENGTH_LONG).show();
                } else if (btnAdd.isChecked()==false && btnSub.isChecked()==false && btnMul.isChecked()==false && btnDiv.isChecked()==false) {
                    Toast.makeText(getApplicationContext(), "연산자 선택", Toast.LENGTH_LONG).show();
                } else if (num2.equals("")) {
                    Toast.makeText(getApplicationContext(), "두번째 정수 입력", Toast.LENGTH_LONG).show();
                }
                else {  // trim() 결과가 빈 문자열이 아니라면, Intent 만들어서 보내기
                    Intent intent = new Intent(getApplicationContext(), CalActivity.class);

                    // 파라미터 intent에 넘겨주기
                    intent.putExtra("num1", Integer.parseInt(num1));
                    intent.putExtra("num2", Integer.parseInt(num2));

                    switch (rGroup.getCheckedRadioButtonId()) { //
                        case R.id.btnAdd:
                            intent.putExtra("operator", "+");
                            break;
                        case R.id.btnSub:
                            intent.putExtra("operator", "-");
                            break;
                        case R.id.btnMul:
                            intent.putExtra("operator", "*");
                            break;
                        case R.id.btnDiv:
                            intent.putExtra("operator", "/");
                            break;
                    }

                    startActivity(intent);

                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tv.setText((++count)+"번째"+"복귀했습니다~");
    }

}
