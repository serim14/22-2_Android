package kr.ac.duksung.converter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // mainActivity 객체 생성되면 자동 호출(main의 역할)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

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
    }
}