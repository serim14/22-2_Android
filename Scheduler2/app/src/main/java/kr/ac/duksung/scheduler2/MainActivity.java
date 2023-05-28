package kr.ac.duksung.scheduler2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    // 1. laucher 객체 참조 변수 선언
    ActivityResultLauncher<Intent> launcher;          //제너릭 클래스 , main의 필드로 작업       // 1. launcher 객체 참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("일정관리");

        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                //        startActivityForResult(intent, 0);            // deprecated
                // 3번 호출
                launcher.launch(intent);                // launcher 객체의 launch 메서드로 인텐트 호출
            }
        });
        // 2. launcher 객체 생성( Contract, Callback ) 파라미터로 주기
/* 둘다 사용가능, 이작업은 OnCreate또는 OnStart 메소드 내에서 작업
// 파라미터 2개 제공
// new 연산자는 이 객체를 생성해 반환
// ActivityResultContracts 패키지 안의 기본생성자 이용해서 보내겠다.
// launcher 객체 생성하며 callback함수 가질 수 있도록 함
// 익명클래스 사용해 객체 생성( ActivityResultCallback: 제너릭 인터페이스 )
        launcher = registerForActivityResult(                           // launcher 객체 생성
                new ActivityResultContracts.StartActivityForResult(),   // Contract 객체 파라미터, 문자열을 받을때 적합
                new ActivityResultCallback<ActivityResult>() {          // Callback 객체 파라미터
                    @Override
                    public void onActivityResult(ActivityResult result) {   // 추상메서드 정의( result: 세컨액티비티에서 보낸 intent )
                        if (result.getResultCode() == RESULT_OK) {  // 제대로 전달 됐는지
                            Intent intent = result.getData();   // 세컨 액티비티가보낸 intent 객체 얻어옴
                            String data = intent.getStringExtra("appointment"); // 이 키를 가진 data 반환
                            textView.append(data + "\n");
                        }
                    }
                });
 */
        // Callback 람다 사용
        // ActivityResultCallback가 메서드 하나만 갖는 함수 인터페이스기 때문에 람다식 사용
        launcher = registerForActivityResult(                           // launcher 객체 생성
                new ActivityResultContracts.StartActivityForResult(),   // Contract 객체 파라미터
                result -> {                                             // Callback 람다 파라미터
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            String data = intent.getStringExtra("appointment");
                            textView.append(data + "\n");
                        }
                });
    }

    /*
    // 옛날 callback 함수
    // onCreate랑 동일 레벨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getStringExtra("appointment");
            textView.append(result + "\n");
        }
    }
     */
}
