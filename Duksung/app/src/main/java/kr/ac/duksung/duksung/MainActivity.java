package kr.ac.duksung.duksung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    TextView textView;
    ArrayList<String> items;
    ArrayList<String> dates;
    ArrayAdapter<String> adapter;   // 인스턴스 변수
    static RequestQueue requestQueue;   // volley 쓰기 위함, class 변수(static: 클래스 당 단 한개만 설정되는 변수), 공유

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<String>();    // 덕성여대 학사일정에서 항목 받아올 가변배열
        dates = new ArrayList<String>();    // 항목 날짜 가져올 가변배열
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        textView = (TextView) findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(getApplicationContext()); // 초기 객체 생성
        makeRequest();  // 메서드 호출
    }

    public void makeRequest() {
        String url = "http://www.duksung.ac.kr/";   // url 설정
        StringRequest request = new StringRequest(  // 문자열로 받음, 생성자, 4개 파라미터
                Request.Method.GET, // get 방식
                url,
                new Response.Listener<String>() {   // 제너릭 인터페이스스
                   @Override
                    // requestQueue 객체 생성 문장
                    public void onResponse(String response) {   // 서버측에서 보내준 문자열 받음, 성공 콜백 함수
                        /* for Chosun page (homework), 한겨래 21로 대체(euc-kr), 주석해제할 필요 없음
                        try {   // String Exception
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");    // 복원시킨 후 재설정
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(), "encoding error",
                                    Toast.LENGTH_LONG).show();
                        }
                        */
                        parseHtml(response);    // 받은 문자열 객체 파라미터로 보냄
                    }
                },
                new Response.ErrorListener() {  // 실패 콜백 함수(서버에서 결돠 답을 주게 도면 해당하는 메서드 호출)
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
        Document doc = Jsoup.parse(html);   // 돔 객체 생성
        String titleStr = doc.title();  // 덕성여대 전체 페이지의 title 반환
        // select 메소드가 가장 강력, 스페이스로 태그 분리
        Elements itemElements = doc.select("ul li p.con");   // XPath '//ul/li/p[@class="con"]'   , 항목
        Elements dateElements = doc.select("ul li p.date");  // XPath '//ul/li/p[@class="date"]'   , 날짜
        textView.setText(titleStr + " 일정"); // title "덕성여자대학교" 받음
        for(Element element : itemElements) {
            items.add(element.text().trim());   // 문자열 반환(공백 제거)
            android.util.Log.d("test: ", element.text());   // log cat 출력 ( 키 + 내용)
        }
        for(Element element : dateElements) {
            dates.add(element.text().trim());
            android.util.Log.d("test: ", element.text());
        }
        for(int i=0; i<items.size(); i++) {
            items.set(i, items.get(i) + " (" + dates.get(i) + ")"); // items 가변배열 재구성
        }
        android.util.Log.d("test: ", items.size() + " items");  // log 찍기
        adapter.notifyDataSetChanged(); // 데이터 변경 알리기
    }
}
