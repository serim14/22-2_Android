package kr.ac.duksung.advisor;

import android.content.Intent;
import android.os.AsyncTask;    //
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Jsoup 라이브러리가 제공하는 클래스들
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;    // Dom 객체
import org.jsoup.nodes.Element; // 태그 기반 element 노드
import org.jsoup.select.Elements;   // element의 집합

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {
    private Document doc;   // dom 객체 전체의 참조값(reference)
    ArrayList<String> employees; // 지도 학생의 수 예측할 수 없으니 가변배열(제너릭 클래스)
    ArrayAdapter<String> adapter;   // 리스트뷰에 표시할 cell

    ArrayList<String> employeeIDList;   // employee의 사번을 담을 가변 배열 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        employees = new ArrayList<String>();
        ListView employeeList = (ListView) findViewById(R.id.listView);
        // simple_list_item_1 : 한 셀에 하나의 값만 넣을 수 있음, xml 파일임(cell을 디자인한 파일)
        // 한 셀에 여러 값 넣으려면 직접 커스텀 해야함
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        employeeList.setAdapter(adapter);

        employeeIDList = new ArrayList<String>();

        // employee가 클릭됐을 때 (사번 toast 메시지)
        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // cell 중 하나 클릭
            @Override
            // 핸들러 호출, (8개 cell 감싸고 있는 뷰의 참조값, 사용자 터치 특정 셀 주소값, 사용자 터치 셀 번호, id 값)
            // 우리가 주로 쓰는건 세번째 파라미터
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // employeeIDList에서 인덱스 i에 해당하는 ID 띄워주기
                Toast.makeText(getApplicationContext(), "사번: "+ employeeIDList.get(i), Toast.LENGTH_LONG).show();
            }
        });

        // local server
        //       String urlString = "http://10.0.2.2:8080/FinalProject/advisorPro.jsp";
        //학교 외부
        String urlString = "http://203.252.213.36:8080/FinalProject/advisorProHome.jsp";
        //학교 내부
        //String urlString = "http://10.219.0.15:8080/FinalProject/advisorProHome.jsp";

        Intent intent = getIntent();
        String department = intent.getStringExtra("department");  // intent에서 받아오기

        // get 방식으로 url 완성
        urlString = urlString + "?dept=" + department;
        JsoupAsyncTask advisorTask = new JsoupAsyncTask();
        advisorTask.execute(urlString); // 파라미터 보냄, 자동적으로 분기
    }
    // <url, 진행상황 모니터링 타입, 결과 반환 타입>
    private class JsoupAsyncTask extends AsyncTask<String, Void, Document> {    // 제너릭 클래스, 쓰레드 분기 기능 제공, 상속받고 시작

        // 메인 쓰레드에서 통신 작업 하면 통신 작업 끝날때까지 먹통돼기 때문에 쓰레드를 분기해 병렬 작업.
        @Override
        // 분기된 후 백그라운드에서 작업할 내용
        // ... 가변 매개변수 문법(파라미터 개수 정해지지 않음)
        // background 쓰레드에서 실행
        protected Document doInBackground(String... params) {   // 재정의, 리턴 타입 Document
            try {   // connect 메서드는 반드시 try-catch문으로 감싸줘야함
                doc = Jsoup.connect(params[0]).get();   // url, get 방식, dom 객체, 통신 작업
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
        protected void onPostExecute(Document doc) {    // 파라미터로 위의 dom 객체, 재정의(상속)
            // Element는 ArrayList를 상속받은거기때문에 사용법 같음(가변배열)
            Elements elements = doc.select("h5");   // xpath "//h5"이지만 문법 다름
            Elements employeeIds = doc.select("h6");    // 사번 가져오기
            for(Element element : elements) {
                employees.add(element.text());   // text노드 중 text만 string 객체로 돌려줌
            }
            for(Element employeeId : employeeIds) {
                employeeIDList.add(employeeId.text());  // h6 텍스트노드에서 text만 뽑아서 저장
            }
            adapter.notifyDataSetChanged(); // student에 들어간 내용만큼 업데이트
        }
    }
}
