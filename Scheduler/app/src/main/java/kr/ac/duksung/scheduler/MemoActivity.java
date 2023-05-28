package kr.ac.duksung.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        android.util.Log.i("lifecycle", "Memo:onCreate");   // 로그 남기기

        // 지역변수, 안에서만 사용 가능, final: 상수로 설정
        final TextView textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {  // 앞 페이지에서 메세지 받아서 textView에 표시
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();    // Intent 객체 가져옴
                // 만약 정수값이라면 보낼때는 string이지만 받을 때는 getIntExtra("키값");
                String appointment = intent.getStringExtra("appoint");  //여러개 보낼때는 여러번 키값 써서 받아옴
                String memo = editText.getText().toString();    // 사용자 입력값 가져와 string으로 변환
                textView.setText(memo + " : " + appointment);   // 메모 + 날짜 + 시간
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.i("lifecycle", "Memo:onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.i("lifecycle", "Memo:onStop");
    }
}

