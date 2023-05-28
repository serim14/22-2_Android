package kr.ac.duksung.homework4_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 버튼 비주얼적으로 끌어다 놓는 작업: 버튼 클래스로부터 버튼 객체 만들어 드롭하는 것임

public class MainActivity extends AppCompatActivity {
    Button button;
    RadioButton rdoDate, rdoTime;
    DatePicker datePicker;
    TimePicker timePicker;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 무대올리기
        //     android.util.Log.i("lifecycle", "Main:onCreate");

        button = (Button) findViewById(R.id.button);    // 참조값 가져오기
        rdoDate = (RadioButton) findViewById(R.id.radioButton1);
        rdoTime = (RadioButton) findViewById(R.id.radioButton2);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        // 처음에는 두 picker를 안보이게 설정
        timePicker.setVisibility(View.INVISIBLE);
        datePicker.setVisibility(View.INVISIBLE);

        rdoDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePicker.setVisibility(View.INVISIBLE);
                datePicker.setVisibility(View.VISIBLE);
            }
        });

        rdoTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.INVISIBLE);
            }
        });

        // 날짜 변경 감지 객체
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {   // 사용자 선택 년/월/일 제공
                date = i + "/" + (i1+1) + "/" + i2;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = timePicker.getHour() + ":" + timePicker.getMinute(); // 시간/분 얻어옴
                String appointment = date + " " + time;
                Toast.makeText(getApplicationContext(), appointment, Toast.LENGTH_LONG).show();
                //        Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                //        intent.putExtra("appointment", appointment);
                //        startActivity(intent);
            }
        });
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.i("lifecycle", "Main:onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.i("lifecycle", "Main:onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        android.util.Log.i("lifecycle", "Main:onRestart");
    }


     */
}