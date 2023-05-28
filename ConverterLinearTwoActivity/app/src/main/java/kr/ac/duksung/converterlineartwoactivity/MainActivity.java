package kr.ac.duksung.converterlineartwoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;  // 디자인한 위젯 접근시 사용
    Button button;
    TextView textView;
    Button goSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // mainActivity 객체 생성되면 자동 호출(main의 역할)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        goSecond = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fahrenStr = editText.getText().toString();   // 문자열 가져와서 자바 문자열로 변환
                fahrenStr = fahrenStr.trim();   // 공백 제거
                if(fahrenStr.equals("")) {  // trim()한 결과가 빈 문자열이라면
                    textView.setText("화씨값을 입력하세요~");
                    Toast.makeText(getApplicationContext(), "화씨값을 입력!!",
                            Toast.LENGTH_LONG).show();

                } else {    // trim() 결과가 빈 문자열이 아니라면, 섭씨값 계산
                    Double fahren = Double.parseDouble(fahrenStr);  // 문자열을 실수 값으로 변환환
                    Double celsius = (fahren - 32.0) / 1.8;
                    textView.setText("섭씨 " + celsius.toString());
                }
            }
        });
        goSecond.setOnClickListener(new View.OnClickListener() {    // 전환 버튼이 클릭됐을 때
            @Override
            public void onClick(View view) {
                // 화면 전환 전담 객체
                // 파라미터 두개 Intent(MainActivity 객체, 어떤 화면을 불러올 지 그 클래스 지정) - 확장자명도 꼭 줘야함
                // 어느 액티비티 객체에서 - 어느 액티비티 객체로 넘어갈 지
                // (어디서, 어디로)
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);  // 전환 전담 객체 실행
            }
        });
    }
}
