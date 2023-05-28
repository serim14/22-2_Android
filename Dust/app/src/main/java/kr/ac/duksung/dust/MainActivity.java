package kr.ac.duksung.dust;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;  // json parser 내장
import org.json.JSONException;  // jsonparser은 try-catch문 사용해야하는 예외 발생시키기 때문에
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    static RequestQueue requestQueue;   // volley 사용, static 선언 이유: Main, Act에서 공유하기 위해

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("서울 구별 미세먼지");

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {  // 버튼 눌렸을 때
            @Override
            public void onClick(View v) {
                textView.setText(null); // textView 지움
                makeRequest();  // 통신 위해 호출
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
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
        requestQueue.add(request);
    }

    public void println(String data) {
        textView.append(data + "\n");
    }   // 오류난 사항을 textView에 출력

    public void parseJson(String json) {    // 파라미터로 제공되는 문자열
        try {   // getJSONObject, getJSONArray -> check 예외 발생
            JSONObject object1 = new JSONObject(json);  // 문자열 주면서 객체 생성
            JSONObject object2 = object1.getJSONObject("ListAirQualityByDistrictService");  // 키 값 주며 반환 요청
            JSONArray array = object2.getJSONArray("row");  // row 키에 연계된 jsonArray 추출, 우리가 원했던 Array 객체 반환
            for(int i=0; i<array.length(); i++) {   // 25번 반복
                JSONObject obj = array.getJSONObject(i);    // 배열의 해당 객체 반환
                // 한글 utf-8로 인코딩 돼서 넘어오는데 안드로이드 스튜디오도 한글인코딩이 utf-8이라 호환 잘됨
                String ku = obj.getString("MSRSTENAME");    // 자치구 이름, 자바 문자열로 반환
                String pm10 = obj.getString("PM10");    // 미세먼지( 업데이트 안됐다면 "점검중" 문자열 반환)
                String grade = obj.getString("GRADE");  // 통합 대기 등급
                println(ku + " / " + pm10 + " --> " + grade);   // textView에 append 시킴
            }
        } catch (JSONException e) {
            e.printStackTrace();    // 디버깅 창에 오류 과정과 메세지 출력하도록
        }
    }
}
