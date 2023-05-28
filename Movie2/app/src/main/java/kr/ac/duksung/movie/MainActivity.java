package kr.ac.duksung.movie;


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
    // 아직 배열 객체 생성 안함
    ArrayList<String> movies;
    /* 고정배열: 수정 불가능
    String [] movies = {"Chasing Amy","Mallrats", "Dogma", "Clerks",
            "Jay & Silent Bod Strike Back", "Red States", "Cop Out", "Jersey Girl"};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<String>();   // 객체 생성(기본 생성자, 빈 가변배열 객체), 참조값 반환
        // 영화 제목 추가
        movies.add("Chasing Amy"); movies.add("Mallrats"); movies.add("Dogma");
        movies.add("Clerks"); movies.add("Jay & Silent Bod Strike Back");
        movies.add("Red States"); movies.add("Cop Out"); movies.add("Jersey Girl");

        ListView movieList = (ListView) findViewById(R.id.listView);
        // final 사용, data source
        // MainActivity 안에 정의된 내부 클래스
        // 자바 규칙: 내부 클래스는 외부 클래스(Main)의 필드에 마음대로 접근 가능(movies)
        // 하지만 내부 클래스에서 지역변수에 접근하기 위해서는 final로 선언되어야함
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, movies);

        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), movies.get(i), Toast.LENGTH_LONG).show();
                                                        /* movies[i] */
                // 가변 배열이니까 get()으로 요소 접근

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);   // 바로 검색
                intent.putExtra(SearchManager.QUERY, movies.get(i));
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
                movies.add(addEdit.getText().toString());   // 텍스트 얻어옴(영화 제목), 가변배열에 추가
                adapter.notifyDataSetChanged(); //  adapter에 배열이 추가된 사항 알려줌, listView에 적용돼 확인 가능
            }
        });

        // 오래 누르고 있을 때 삭제
        // ( 사건 발생 listView의 참조값, cell의 참조값, 몇번째 cell, cell id )
        movieList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int which = i;    // 몇번째 cell인지 저장, final 키워드(같은 이유)
                // dialog 위젯: 프로그램 실행 중에 동적으로 사용자가 어떤 일을 했을 때 사용
                // -> xml 파일에 미리 만드는 것이 아니라 그때그때 생성
                // 사용자 확인, 선택, 추가 정보
                // MainActivity 안에서 dialog 만들겠다
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("삭제?");
                dlg.setMessage(movies.get(i));  // 내용(영화제목), i 직접사용
                dlg.setNegativeButton("취소", null);  // null: 아무 일도 안하겠다.
                // 확인 버튼에 부착된 인터페이스 사용
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 버튼이 눌렸을 때 실행될 메서드: 핸들러
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {   // 내부 클래스
                        // 가변배열에서 삭제
                        movies.remove(which);   // 전역변수 접근, i에 접근할 수 없으니 final 변수 별도로 선언
                        adapter.notifyDataSetChanged(); // 지역변수 접근(final), 변경 사실 알림(listView에 적용)
                    }
                });
                dlg.show(); // dialog 우리 눈에 보임
                return true;    // 내가 이 사건에 대해 전담 처리 할게, 내가 도맡아 할게, 다른 위젯에 전파 안하겠다(OnItemClick은 호출이 안된다)
            }
        });
    }
}