package kr.ac.duksung.duksung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    WebView webView;    // webView 참조값 저장 공간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 미니멀 기능 가진 브라우저
        webView = (WebView) findViewById(R.id.webView); // 참조값 저장
        WebSettings webSet = webView.getSettings(); // 세팅 관여 객체
        // jquery사용한 자바스크립트 코드 같이 오기 때문에 (웹서버가 보내는 코드에 자바스크립트 코드 포함, default: disable)
        webSet.setJavaScriptEnabled(true);  // webView 생성 후엔 자바 enable 하지 않기 때문에

        Intent intent = getIntent();
        String resultUrl = intent.getStringExtra("resultUrl");  // MainActivity에서 넘어온 기사 url 받기
        webView.loadUrl("https://h21.hani.co.kr"+resultUrl);   // 한겨레 메인 링크+파싱해온 기사링크 합쳐서 웹뷰에 표시

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() { // Home버튼
            @Override
            public void onClick(View view) {
                finish();
            }   // 버튼 눌리면 webActivity 끝냄, Main 화면이 보임
        });

        // 내 webView에 두번째, 세번째 액티비티 로딩해줘, 내부클래스 정의
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { // 정의하지 않으면 두번째 페이지는 chrome 끌고옴
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    // 재정의 하지 않으면 여러번 로딩이 일어났어도 처음 화면으로 돌아감
    // 첫 화면이 아니라 앞 페이지로 넘어가고 싶을 때 사용
    public void onBackPressed() {   // 재정의
        if(webView.canGoBack()){
            webView.goBack();
        }else{  // 더이상 돌아갈 화면 없을 때(첫화면)
            super.onBackPressed();  // 원래 기능(팝핑)
        }
    }
}
