package kr.ac.duksung.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

// Constraint layout은 원하는 위치에 배치하는 대신 조건을 설정해줘야 함
// textView는 힌트라도 줘라
// 버튼의 text는 strings.xml 파일에 따로 관리하는 것을 추천
// 여러 언어를 지원하기 위해선 텍스트를 따로 관리하는게 좋음, 동적으로 !!
// Localization 지역화
// 듣는거 + 작업하는거 : Listener 객체
// 익명클래스 람다식은 딱 한번만 필요할 때 사용
// 두 액티비티간 통신 역할: Activity 객체


public class MainActivity extends AppCompatActivity {

    Button button1, btnWeb;
    EditText editText;
    ImageView imageView;

    RadioButton radioOreo, radioPie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R 클래스로 가서 정의된 이걸 올려라
        setTitle("Homework1");

        button1 = (Button) findViewById(R.id.btnText);
        btnWeb = (Button) findViewById(R.id.btnOpen);
        editText = (EditText) findViewById(R.id.editText);

        radioOreo = (RadioButton) findViewById(R.id.oreo);
        radioPie = (RadioButton) findViewById(R.id.pie);

        imageView = (ImageView) findViewById(R.id.imageView);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();   // 문자열 가져와서 자바 문자열로 변환
                msg = msg.trim();
                Toast.makeText(getApplicationContext(), msg,
                        Toast.LENGTH_SHORT).show(); // 사용자에게 잠시 알리기 위해 toast 메시지 사용
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(editText.getText().toString()));
                startActivity(intent);
            }
        });

        radioOreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setImageResource(R.drawable.oreo);

            }
        });

        radioPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setImageResource(R.drawable.pie);

            }
        });

    }
}