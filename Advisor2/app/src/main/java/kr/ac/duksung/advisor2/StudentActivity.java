package kr.ac.duksung.advisor2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private Document doc;   // dom 객체 전체의 참조값(reference)
    ArrayList<String> students; // 지도 학생의 수 예측할 수 없으니 가변배열(제너릭 클래스)
    ArrayAdapter<String> adapter;   // 리스트뷰에 표시할 cell

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        students = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        listView.setAdapter(adapter);
        // local server
        //       String urlString = "http://10.0.2.2:8080/FinalProject/advisorPro.jsp";
        //학교 외부
        String urlString = "http://203.252.213.36:8080/FinalProject/advisorPro.jsp";
        //학교 내부
        //String urlString = "http://10.219.0.15:8080/FinalProject/advisorPro.jsp";

        Intent intent = getIntent();
        String professor = intent.getStringExtra("professor");  // intent에서 받아오기

        // get 방식으로 url 완성
        urlString = urlString + "?advisor=" + professor;
        JsoupAsyncTask advisorTask = new JsoupAsyncTask();
        advisorTask.execute(urlString); // 파라미터 보냄, 자동적으로 분기
    }
    // <url, 진행상황 모니터링 타입, 결과 반환 타입>
    private class JsoupAsyncTask extends AsyncTask<String, Void, Document> {    // 제너릭 클래스

        @Override
        // 분기된 후 백그라운드에서 작업할 내용
        // ... 가변 매개변수 문법(파라미터 개수 정해지지 않음)
        // background 쓰레드에서 실행
        protected Document doInBackground(String... params) {   // 재정의, 리턴 타입 Document
            try {   // connect 메서드ㅡㄴ 반드시 try-catch문으로 감싸줘야함
                doc = Jsoup.connect(params[0]).get();   // url, get 방식, dom 객체
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "network error",
                        Toast.LENGTH_SHORT).show();
            }
            return doc; // dom 객체 리턴
        }

        @Override
        // 실행이 끝나 복귀 후 할 작업 명시
        // main으로 합류되면 실행, dom 객체 받아옴
        // main 쓰레드에서 실행, parsing 작업
        protected void onPostExecute(Document doc) {    // 파라미터로 위의 dom 객체
            // Element는 ArrayList를 상속받은거기때문에 사용법 같음(가변배열)
            Elements elements = doc.select("h5");   // xpath "//h5"이지만 문법 다름
            for(Element element : elements) {
                students.add(element.text());   // text노드 중 text만 string 객체로 돌려줌
            }
            adapter.notifyDataSetChanged(); // student에 들어간 내용만큼 업데이트
        }
    }
}
