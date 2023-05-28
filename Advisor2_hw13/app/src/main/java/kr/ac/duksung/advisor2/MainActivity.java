package kr.ac.duksung.advisor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 옵션 메뉴
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 옵션이 선택됐을 때
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.itemNative:   // Native 옵션 선택했을 때
            Intent intent = new Intent(getApplicationContext(), ProfessorActivity.class);
            startActivity(intent);
            // 토스트메시지 부분 추가하기
            return true;
        case R.id.itemWeb:  // Web 옵션 선택했을 때
            Uri uri = Uri.parse("http://203.252.213.36:8080/FinalProject/advisorForm.jsp"); // 크롬으로 연결
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent1);
            return true;
        }
        return false;
    }
}
