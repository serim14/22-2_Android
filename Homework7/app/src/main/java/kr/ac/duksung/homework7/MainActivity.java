package kr.ac.duksung.homework7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button equalBtn;
    EditText edit1, edit2;
    RadioGroup rGroup;
    RadioButton btnAdd, btnSub, btnMul, btnDiv;
    String num1, num2;
    TextView tv;

    // 1. laucher 객체 참조 변수 선언
    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("입력");

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        btnAdd = (RadioButton) findViewById(R.id.btnAdd);
        btnSub = (RadioButton) findViewById(R.id.btnSub);
        btnMul = (RadioButton) findViewById(R.id.btnMul);
        btnDiv = (RadioButton) findViewById(R.id.btnDiv);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);

        equalBtn = (Button) findViewById(R.id.equalBtn);


        tv = (TextView) findViewById(R.id.tv);

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

                    // deprecated - 새로나온 버전이 있으니 그걸 써라
                    // startActivityForResult(intent, 0);  // Activity 시작
// 3번 호출
                    launcher.launch(intent);
                }
            }
        });


        // 익명클래스: 사용할 현장에서 클래스 이름없이 선언하고 마는 클래스, 1회성성
       launcher = registerForActivityResult(                           // launcher 객체 생성, 객체 생성해 주소값 반환
                new ActivityResultContracts.StartActivityForResult(),   // Contract 객체 파라미터
                result -> {                                             // Callback 람다 파라미터
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        String data = intent.getStringExtra("cal");
                        data = data + "\n" + tv.getText().toString();   // 누적 시켜서 표현
                        tv.setText(data);
                    }
                });
    }

    /*
    @Override
    // callback 함수
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   // @Nullable: Null인지 체크하고 사용해라(어노테이션)
        super.onActivityResult(requestCode, resultCode, data);  // setResult(1, 2) -> 1-2, 2-3 번째 파라미터가 됨
        if (resultCode == RESULT_OK) {  // ok 사인이면 양방 통신에 문제가 없었다는 의미
            String result = data.getStringExtra("cal");

            // tv.setText(result + "\n" + tv.getText());
            result = result + "\n" + tv.getText().toString();   // 누적 시켜서 표현
            tv.setText(result);
        }
    }

     */
}
