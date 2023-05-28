package kr.ac.duksung.advisor2;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    WebView webView;    // webView 참조값 저장 공간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // 미니멀 기능 가진 브라우저
        webView = (WebView) findViewById(R.id.webView); // 참조값 저장
        WebSettings webSet = webView.getSettings(); // 세팅 관여 객체
        // jquery사용한 자바스크립트 코드 같이 오기 때문에 (웹서버가 보내는 코드에 자바스크립트 코드 포함, default: disable)
        webSet.setJavaScriptEnabled(true);  // webView 생성 후엔 자바 enable 하지 않기 때문에
        webView.loadUrl("http://203.252.213.36:8080/FinalProject/");   // index.jsp가 보내주는 html 페이지 웹뷰에 표시

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }   // 버튼 눌리면 webActivity 끝냄, Main 화면이 보임
        });
        /*
        class MyWebViewClient extends WebViewClient {   // 내부클래스 정의, 상속
            @Override
            // 재정의
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);   // 내가 관리하는 webView에 로딩하겠다
            }
        }
        webView.setWebViewClient(new MyWebViewClient());    // 부착
        */
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
