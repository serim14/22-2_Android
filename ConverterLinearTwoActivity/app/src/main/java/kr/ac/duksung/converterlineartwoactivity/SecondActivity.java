package kr.ac.duksung.converterlineartwoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {
    Button goBack;
    ImageView imageView;

    // 이 메소드는 부모로부터 받은 걸 재정의 하는 메소드이다~ 컴파일러에게 알림
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); // 무대를 올리는 역할
        goBack = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.raccoon); // 프로그램에서 이미지 입히기

        goBack.setOnClickListener(new View.OnClickListener() {  // 복귀 버튼
            @Override
            public void onClick(View view) {
                finish();   // 내가 속한 액티비티 종료시키겠다.
            }
        });
    }
}