package kr.ac.duksung.meal_hw9;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Integer[] images = {R.drawable.meal0, R.drawable.meal1, R.drawable.meal2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 이미지뷰
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("상세정보");

        TextView tv = (TextView) findViewById(R.id.textView);
        Button moreBtn = (Button) findViewById(R.id.button);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();    // 받고
        Integer position = intent.getIntExtra("image", 0);  // 안왔을 경우 0, 이미지 인덱스 값
        String mealName = intent.getStringExtra("name");    // 음식 이름 받아오기

        imageView.setImageResource(images[position]);
        tv.setText(mealName);   // 음식 이름 표시


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
