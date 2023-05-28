package kr.ac.duksung.duksung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> titleList;    // 기사 제목 저장 리스트
    ArrayList<String> urlList;  // 기사 url 저장 리스트
    ArrayAdapter<String> adapter;   // 인스턴스 변수
    // 화면마다 독립적으로 가지면 메모리 낭비
    static RequestQueue requestQueue;   // volley 쓰기 위함, class 변수(static: 클래스 당 단 한개만 설정되는 변수), 공유

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DuksungHome(News)");  // 제목 설정

        titleList = new ArrayList<String>();    // 한겨레21에서 기사 제목 받아올 가변배열 할당
        urlList = new ArrayList<String>();    // 한겨레21에서 기사 url 받아올 가변배열 할당
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext()); // 초기 객체 생성
        makeRequest();  // 메서드 호출

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // cell 중 하나 클릭
            @Override
            // 핸들러 호출, (8개 cell 감싸고 있는 뷰의 참조값, 사용자 터치 특정 셀 주소값, 사용자 터치 셀 번호, id 값)
            // 우리가 주로 쓰는건 세번째 파라미터
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), titleList.get(i), Toast.LENGTH_LONG).show();    // 기사제목 토스트메시지
                String resultUrl = urlList.get(i);  // 클릭된 기사 기사제목에 일치하는 기사 url 저장

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("resultUrl", resultUrl);    // DetailActivity에 해당 url 넘겨주기
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 옵션 메뉴 상단바에 설정
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 옵션이 선택됐을 때
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.itemRefresh:   // REFRESH 옵션 선택했을 때
                titleList.clear();  // 기사제목 ListView 값 초기화해주기(새로 업데이트된 기사만 표시하기 위함)
                urlList.clear();    // 당연히 기사 제목이 초기화 됐으니 url도 새롭게 리뉴얼 되야함
                makeRequest();  // 업데이트 하기위해 함수호출
                return true;
        }
        return false;
    }

    public void makeRequest() {

        String url = "https://h21.hani.co.kr/";   // 한겨레21 url 설정
        // (통신방식 지정, 연결할 url, 성공했을 때 success handler, 실패했을 때 error handler)
        StringRequest request = new StringRequest(  // 문자열로 받음, 생성자, 4개 파라미터
                Request.Method.GET, // get 방식 (통신방식 지정)
                url,
                new Response.Listener<String>() {   // 제너릭 인터페이스, 객체 파라미터, 익명 클래스 방식
                   @Override
                    // requestQueue 객체 생성 문장
                    public void onResponse(String response) {   // 서버측에서 보내준 문자열 받음, 성공 콜백 함수
                       parseHtml(response);    // 받은 문자열 객체 파라미터로 보냄
                    }
                },
                // error handler 객체
                new Response.ErrorListener() {  // 실패 콜백 함수(서버에서 결과 답을 주게 되면 해당하는 메서드 호출), 객체 파라미터
                    @Override
                    public void onErrorResponse(VolleyError error) {    // 에러 종류 넘겨줌
                        Toast.makeText(getApplicationContext(), "network error",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        // 똑같은 request가 Queue에 들어왔다면 volley는 cache에 저장된 응답 보냄
        request.setShouldCache(false);  // 똑같은 request라도 다른 응답을 그때마다 받고싶다면 false
        requestQueue.add(request);  // 생성한 request 객체 추가 작업
    }

    // 1. 통신->volley / 2. dom 객체 생성->Jsoup.parse()  (Jsoup.connect() 역할을 두가지로 나눠 수행)
    public void parseHtml(String html) {    // 파라미터명 html
        android.util.Log.d("refresh: ", "parseHtml 호출 성공");

        Document doc = Jsoup.parse(html);   // 돔 객체 생성
        // select 메소드가 가장 강력, 스페이스로 태그 분리
        Elements titleElements = doc.select("div.image_on div.title0 div.title1 a h6");   // XPath '//div[@class='image_on']/div[@class='title0']/div[@class='title1']/a/h6'
        Elements urlElements = doc.select("div.image_on div.title0 div.title1 a");  // XPath '//div[@class='image_on'/div[@class='title1']/a/@href'

        // urlList 채워주기
        for(Element urlElement : urlElements) {
            urlList.add(urlElement.attr("href").trim());    // href 속성은 .attr()로 받아오기
            android.util.Log.d("href: ", urlElement.attr("href").trim());
        }

        // titleList 채워주기
        for(Element titleElement : titleElements) {
            titleList.add(titleElement.text().trim());   // 문자열 반환(공백 제거)
            android.util.Log.d("title: ", titleElement.text());   // log cat 출력 ( 키 + 내용)
        }

        android.util.Log.d("test: ", titleList.size() + " items");  // log 찍기
        adapter.notifyDataSetChanged(); // 데이터 변경 알리기
    }
}
