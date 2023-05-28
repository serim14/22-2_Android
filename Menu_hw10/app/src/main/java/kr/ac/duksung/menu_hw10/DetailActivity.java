package kr.ac.duksung.menu_hw10;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 이미지뷰
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("상세정보");

        TextView tv = (TextView) findViewById(R.id.textView);
        Button moreBtn = (Button) findViewById(R.id.button);

        Intent intent = getIntent();    // 받고
        String mealName = intent.getStringExtra("name");    // 음식 이름 받아오기

        tv.setText(mealName+" 선택!");   // 음식 이름 표시

        // 버튼 클릭했을 때 구글에 해당 음식 검색
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, mealName); // MainActivity에서 넘어온 mealName 검색
                startActivity(intent);
            }
        });
    }
}
