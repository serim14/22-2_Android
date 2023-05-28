package kr.ac.duksung.finalproject_hw15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;  // json parser 내장
import org.json.JSONException;  // jsonparser은 try-catch문 사용해야하는 예외 발생시키기 때문에
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AirActivity extends AppCompatActivity {
    TextView dateView, airView;
    ArrayList<String> kuList;    // 자치구 이름 저장 리스트
    ArrayAdapter<String> adapter;   // 인스턴스 변수
    String airText=null;    // 통합대기환경 텍스트 저장

    // 가변 배열 선언
    ArrayList<String> airCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        setTitle("자치구별 대기환경오염정보");

        airView = findViewById(R.id.airView);
        dateView = findViewById(R.id.dateView);

        // 가변 배열 생성( 통합대기환경 텍스트 저장용)
        airCondition = new ArrayList<String>();

        kuList = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kuList);
        listView.setAdapter(adapter);

        if (MainActivity.requestQueue == null) {
            MainActivity.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        makeRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // cell 중 하나 클릭
            @Override
            // 핸들러 호출, (8개 cell 감싸고 있는 뷰의 참조값, 사용자 터치 특정 셀 주소값, 사용자 터치 셀 번호, id 값)
            // 우리가 주로 쓰는건 세번째 파라미터
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                airView.setText(airCondition.get(i));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 옵션 메뉴 상단바에 설정
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 옵션이 선택됐을 때
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.itemRefresh:   // REFRESH 옵션 선택했을 때
                kuList.clear();
                airCondition.clear();  // 원래 값 초기화
                makeRequest();  // 업데이트 하기위해 함수호출
                return true;
        }
        return false;
    }

    public void makeRequest() {
        String url = "http://openapi.seoul.go.kr:8088/566f4b646e7365723733494d694f63/json/" +
                "ListAirQualityByDistrictService/1/25"; // 인증키로 받아온 url
        StringRequest request = new StringRequest(  // 서버가 보내준 걸 문자열로 받을 거니 StringRequest 선언
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // callback함수, 성공했을 때 Listener 객체
                        parseJson(response);    // 파싱, json string이 response로 넘어옴
                    }
                },
                new Response.ErrorListener() {  // callback함수, 실패했을 때 Listener 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage()); // 내가 정의한 함수, 오류메세지
                    }
                }
        );

        request.setShouldCache(false);
        MainActivity.requestQueue.add(request); // Main에서 static으로 정의한 requestQueue에 추가
    }

    public void println(String data) {airView.append(data + "\n");}   // 오류난 사항을 textView에 출력

    public void parseJson(String json) {    // 파라미터로 제공되는 문자열
        String dateStr=null;

        try {   // getJSONObject, getJSONArray -> check 예외 발생
            JSONObject object1 = new JSONObject(json);  // 문자열 주면서 객체 생성
            JSONObject object2 = object1.getJSONObject("ListAirQualityByDistrictService");  // 키 값 주며 반환 요청
            JSONArray array = object2.getJSONArray("row");  // row 키에 연계된 jsonArray 추출, 우리가 원했던 Array 객체 반환
            for(int i=0; i<array.length(); i++) {   // 25번 반복
                JSONObject obj = array.getJSONObject(i);    // 배열의 해당 객체 반환
                dateStr = obj.getString("MSRDATE");    // 측정 날짜

                String ku = obj.getString("MSRSTENAME");    // 자치구 이름
                kuList.add(ku.trim());   // 문자열 반환(공백 제거)

                // 값 가져오기
                String ozone = obj.getString("OZONE");    // 오존
                String nitrogen = obj.getString("NITROGEN");    // 이산화질소
                String carbon = obj.getString("CARBON");    // 일산화탄소
                String sulfurous = obj.getString("SULFUROUS"); // 아황산가스
                String pm10 = obj.getString("PM10");    // 미세먼지
                String pm25 = obj.getString("PM25");    // 초미세먼지
                String grade = obj.getString("GRADE");  // 통합 대기환경지수 등급

                airText = "오존: "+ozone+"\n이산화질소: "+nitrogen+"\n일산화탄소: "+carbon
                        +"\n아황산가스: "+sulfurous+"\n미세먼지: "+pm10+"\n초미세먼지: "+pm25+"\n통합대기환경지수 등급: "+grade;

                // 가변 배열에 넣어주기
                airCondition.add(airText);
            }
            try {
                SimpleDateFormat inFormat = new SimpleDateFormat("yyyyMMddHHmm");   // api json에서 받아온 date format
                SimpleDateFormat outFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 기준");  // textView에 표시할 날짜 포맷 지정
                Date formatDate = inFormat.parse(dateStr);  // string을 date 타입으로 변경
                String standDate = outFormat.format(formatDate);    // 변환한 date 타입 새롭게 지정한 포맷으로 변환
                dateView.setText(standDate);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();    // 디버깅 창에 오류 과정과 메세지 출력하도록
        }
    }
}