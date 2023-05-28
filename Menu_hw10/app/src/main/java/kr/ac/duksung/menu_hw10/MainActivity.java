package kr.ac.duksung.menu_hw10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 가변배열: 필요에 따라 크기 가변, 제너릭 클래스 형태
    // 아직 배열 객체 생성 안함, 변수만 선언
    ArrayList<String> meals;

    // '삭제취소' 버튼 클릭했을 때 삭제한 것 저장할 변수 선언
    String backup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("메뉴"); // 제목 설정

        meals = new ArrayList<String>();   // 객체 생성(기본 생성자, 빈 가변배열 객체), 참조값 반환
        // 메뉴 이름 추가
        meals.add("Caprese Salad");
        meals.add("Chicken and Potatoes");
        meals.add("Pasta with Meatballs");

        ListView mealList = (ListView) findViewById(R.id.listView);
        // final 사용, data source
        // MainActivity 안에 정의된 내부 클래스
        // 자바 규칙: 내부 클래스는 외부 클래스(Main)의 필드에 마음대로 접근 가능(movies)
        // 하지만 내부 클래스에서 지역변수에 접근하기 위해서는 final로 선언되어야함
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, meals);

        mealList.setAdapter(adapter);

        mealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), meals.get(i), Toast.LENGTH_LONG).show();
                // 가변 배열이니까 get()으로 요소 접근

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);  // 두번째 액티비티와 연결
                intent.putExtra("name", meals.get(i));  // 선택된 메뉴 이름 넘겨주기
                startActivity(intent);
            }
        });

        // 추가 버튼, text
        Button addBtn = (Button) findViewById(R.id.button);

        final EditText addEdit = (EditText) findViewById(R.id.editText);
        // 리스너 객체 부착
        // 익명 클래스로 구현( 중요!!!!!!)
        addBtn.setOnClickListener(new View.OnClickListener() {  // 익명 클래스
            @Override
            public void onClick(View view) {    // 클릭이 일어난 버튼에 대한 참조값 파라미터로
                meals.add(addEdit.getText().toString());   // 사용자가 입력한 텍스트 얻어옴(메뉴 이름), 가변배열에 추가
                adapter.notifyDataSetChanged(); //  adapter에 배열이 추가된 사항 알려줌, listView에 적용돼 확인 가능

            }
        });

        // '삭제취소' 버튼
        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {   // '삭제취소' 버튼이 눌렸을 때
            @Override
            public void onClick(View view) {
                if(backup!=null) {  // 삭제된 메뉴가 존재한다면
                    meals.add(backup);   // 가변 배열에 메뉴 이름 추가
                    adapter.notifyDataSetChanged();
                    backup = null;  // 한번만 '삭제취소'할 수 있도록 변수 null로 초기화
                }
            }
        });


        // 오래 누르고 있을 때 삭제
        // ( 사건 발생 listView의 참조값, cell의 참조값, 몇번째 cell, cell id )
        mealList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int which = i;    // 몇번째 cell인지 저장, final 키워드(같은 이유)
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("삭제?");

                dlg.setMessage(meals.get(i));  // 내용(메뉴이름), i 직접사용
                dlg.setNegativeButton("취소", null);  // null: 아무 일도 안하겠다.
                // 확인 버튼에 부착된 인터페이스 사용
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 버튼이 눌렸을 때 실행될 메서드: 핸들러

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {   // 내부 클래스
                        backup = meals.get(which);  // 삭제할 메뉴 이름 저장
                        meals.remove(which);   // 전역변수 접근, i에 접근할 수 없으니 final 변수 별도로 선언
                        adapter.notifyDataSetChanged(); // 지역변수 접근(final), 변경 사실 알림(listView에 적용)
                    }
                });
                dlg.show();
                return true;
            }
        });
    }
}