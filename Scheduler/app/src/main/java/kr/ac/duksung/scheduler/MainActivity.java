package kr.ac.duksung.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button;
    RadioButton rdoDate, rdoTime;
    DatePicker datePicker;
    TimePicker timePicker;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 키:출력할 문자열 쌍을 이룸
        android.util.Log.i("lifecycle", "Main:onCreate");   // 로그 남기기

        button = (Button) findViewById(R.id.button);
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

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                date = i + "/" + (i1+1) + "/" + i2;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = timePicker.getHour() + ":" + timePicker.getMinute();
                String appointment = date + " " + time;
                Toast.makeText(getApplicationContext(), appointment, Toast.LENGTH_LONG).show();
                // 화면 전환과 동시에 값을 실어 보냄
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                intent.putExtra("appoint", appointment);    // 딕셔너리 객체, string 값 보냄
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() { // 재정의
        super.onResume();   // 부모꺼 호출
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

}

