package kr.ac.duksung.meal_hw9;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 필드로 java의 string 배열 선언
    String [] meals = {"Caprese Salad", "Chicken and Potatoes", "Pasta with Meatballs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메뉴");

        // onCreate() 안에서만 사용할 것이기 때문에 지역변수로 선언
        ListView mealList = (ListView) findViewById(R.id.listView);
        // 제너릭 클래스, 템플릿 클래스( 클래스 안에서 다룰 데이터형이 확정되지 않은 채로 정의
        // 사용할 때 각괄호로 형을 정의
        // 파라미터 3개, (선언돼서 사용될 클래스, cell의 형태 지정 파라미터, cell 들에 공급할 배열)
        // this는 이 onCreate() 메서드가 속한 객체( Activity 객체 지칭 )
        // simple_list_item_1: 간단한 text만 display하는 형태. 형태 지정 파라미터
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, meals);   // cell 배열

        mealList.setAdapter(adapter);  // 적용시킴

        mealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // cell 중 하나 클릭
            @Override
            // 핸들러 호출, (8개 cell 감싸고 있는 뷰의 참조값, 사용자 터치 특정 셀 주소값, 사용자 터치 셀 번호, id 값)
            // 우리가 주로 쓰는건 세번째 파라미터
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), meals[i], Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("image", i);    // 어느 셀이 선택됐는가를 넘겨야함, 정수값
                intent.putExtra("name", meals[i]);  // 선택된 메뉴 이름 넘겨주기
                startActivity(intent);
            }
        });
    }
}
