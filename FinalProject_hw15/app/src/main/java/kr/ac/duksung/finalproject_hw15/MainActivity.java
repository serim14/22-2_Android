package kr.ac.duksung.finalproject_hw15;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

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
import android.widget.Button;
import android.widget.EditText;
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
    // 화면마다 독립적으로 가지면 메모리 낭비
    static RequestQueue requestQueue;   // volley 쓰기 위함, class 변수(static: 클래스 당 단 한개만 설정되는 변수), 공유

    Button loginBtn;
    EditText idText;
    EditText pwText;

    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("서울 날씨 어때?");

        loginBtn = (Button) findViewById(R.id.loginBtn);
        idText = (EditText) findViewById(R.id.idText);
        pwText = (EditText) findViewById(R.id.pwText);




        requestQueue = Volley.newRequestQueue(getApplicationContext()); // 초기 객체 생성


        loginBtn.setOnClickListener(new View.OnClickListener() {  // 익명 클래스
            @Override
            public void onClick(View view) {    // 클릭이 일어난 버튼에 대한 참조값 파라미터로
                id = idText.getText().toString().trim();  // 가져온게 text형이니 자바의 string으로 변환
                pw = pwText.getText().toString().trim();
                makeRequest();
            }
        });


    }


    public void makeRequest() {
        android.util.Log.d("login: ", "makeRequest 호출 성공");
        //String url = "https://h21.hani.co.kr/";   // 한겨레21 url 설정

        String url = "http://203.252.213.36:8080/FinalProject/loginPro.jsp?id=" + id +"&passwd=" + pw;
        // (통신방식 지정, 연결할 url, 성공했을 때 success handler, 실패했을 때 error handler)
        StringRequest request = new StringRequest(  // 문자열로 받음, 생성자, 4개 파라미터
                Request.Method.GET, // get 방식 (통신방식 지정)
                url,
                new Response.Listener<String>() {   // 제너릭 인터페이스, 객체 파라미터, 익명 클래스 방식
                    @Override
                    // requestQueue 객체 생성 문장
                    public void onResponse(String response) {   // 서버측에서 보내준 문자열 받음, 성공 콜백 함수
                        android.util.Log.d("login: ", "onResponse 호출");

                        parseHtml(response);    // 받은 문자열 객체 파라미터로 보냄
                    }
                },
                // error handler 객체
                new Response.ErrorListener() {  // 실패 콜백 함수(서버에서 결과 답을 주게 되면 해당하는 메서드 호출), 객체 파라미터
                    @Override
                    public void onErrorResponse(VolleyError error) {    // 에러 종류 넘겨줌
                        android.util.Log.d("login: ", "onErrorResponse 호출");

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
        Elements respondText = doc.select("body");
        String respondNum = respondText.text().trim();
        android.util.Log.d("login: ", respondNum);

        if (respondNum.equals("1")) {
            Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle("Login Failed!");
            dlg.setMessage("Please try again.");  // 내용
            // 확인 버튼에 부착된 인터페이스 사용
            dlg.setPositiveButton("확인", null);
            dlg.show(); // dialog 우리 눈에 보임
        }


    }
}
