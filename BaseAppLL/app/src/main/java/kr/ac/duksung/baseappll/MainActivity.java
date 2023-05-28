package kr.ac.duksung.baseappll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R 클래스로 가서 정의된 이걸 올려라

        button1 = (Button) findViewById(R.id.button1);  // button1의 참조값 저장, 타입캐스팅
        button2 = (Button) findViewById(R.id.button2);  // 생성된 객체의 참조값 반환, 객체간의 연결 작업

//      버튼 1
//      버튼 클래스에는 버튼을 눌린 걸 감지하는 기능은 없기 때문에 리스너 객체를 부착시킴
// 리스너 객체는 클래스로부터 생성, 버튼은 xml로 부터 생성

        /*
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "첫번째 버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show(); // 사용자에게 잠시 알리기 위해 toast 메시지 사용
            }
        });
*/
// 함수 인터페이스일 때 람다식 사용 가능
//      버튼 2
        // 람다식을 사용
        button1.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "첫번째 버튼을 눌렀어요",
                    Toast.LENGTH_SHORT).show();
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "두번째 버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}