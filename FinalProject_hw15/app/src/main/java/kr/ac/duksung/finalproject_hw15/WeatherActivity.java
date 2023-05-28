package kr.ac.duksung.finalproject_hw15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    Button airBtn;  // <지자체별 대기환경오염정도> 버튼
    TextView textView;  // 중기전망조회 표시할 텍스트뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("서울 구별 미세먼지");

        airBtn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        textView.setText("");
        makeRequest();

        // AirActivity로 넘어가는 버튼(지자체별 대기환경오염정도)
        airBtn.setOnClickListener(new View.OnClickListener() {  // 익명 클래스
            @Override
            public void onClick(View view) {    // 클릭이 일어난 버튼에 대한 참조값 파라미터로
                Intent intent = new Intent(getApplicationContext(), AirActivity.class);
                startActivity(intent);
            }
        });

        if (MainActivity.requestQueue == null) {
            MainActivity.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void makeRequest() {
        Date now = Calendar.getInstance().getTime();    // 지금 시각 받아오기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");  // 시간 포맷 정의
        String timeNow = formatter.format(now); // 시간 포맷 적용

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd"); // 시간 제외 날짜
        String dayNow = formatter2.format(now); // 포맷 적용

        String updateTime = dayNow+"0600";    // 오늘 날짜 + 업데이트 기준인 6시

        if (timeNow.compareTo(updateTime)>=0) { // 06:00을 지난 경우
            String url = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst?serviceKey=%2BEOBkIKmvWjvcqcKtTpSpKeu5oHIXyrfRwwJW%2Feyzvi%2FKKX1plvI%2FSW%2FNAV6K2vn2NcaSxN089g0nWn1v6JvgQ%3D%3D" +
                    "&pageNo=1&numOfRows=10&dataType=json&stnId=109&tmFc=" + updateTime;
            // 인증키로 받아온 url
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
        else {  // 06:00 이전 시각 -> 업데이트가 안된 경우
            textView.setText("not available until 06:00");
        }
    }

    public void println(String data) {
        textView.append(data + "\n");
    }   // 오류난 사항을 textView에 출력

    public void parseJson(String json) {    // 파라미터로 제공되는 문자열
        try {   // getJSONObject, getJSONArray -> check 예외 발생
            JSONObject object1 = new JSONObject(json);  // 문자열 주면서 객체 생성
            JSONObject resObj = object1.getJSONObject("response");
            JSONObject bodyObj = resObj.getJSONObject("body");
            JSONObject itemsObj = bodyObj.getJSONObject("items");
            JSONArray array = itemsObj.getJSONArray("item");  // item -> Jsonarray로 구성

            // 기상 정보 가져오기
            for(int i=0; i<array.length(); i++) {   // 25번 반복
                JSONObject obj = array.getJSONObject(i);    // 배열의 해당 객체 반환
                String weatherText = obj.getString("wfSv");    // 자치구 이름, 자바 문자열로 반환
                textView.setText(weatherText);
            }


        } catch (JSONException e) {
            e.printStackTrace();    // 디버깅 창에 오류 과정과 메세지 출력하도록
        }
    }
}

