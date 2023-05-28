package kr.ac.duksung.baseappcl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R 클래스로 가서 정의된 이걸 올려라

        button1 = (Button) findViewById(R.id.button1);  // button1의 참조값 저장, 타입캐스팅


//   함축2 - 교재 코드, 기본적인 안드로이드 코드 형식(기본형)

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        });


//  함축1 - 중간형 코드(익명 클래스)
/*
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        };  // 한문장
        button1.setOnClickListener(listener);   // 총 2개의 문장
*/

/*
        class ButtonListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show(); // 3개의 파라미터 가짐,
            }
        }
*/
/*
        ButtonListener listener = new ButtonListener();
        button1.setOnClickListener(listener);
*/
    }

}