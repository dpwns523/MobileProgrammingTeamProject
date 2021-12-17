package koreatech.teamproject_propt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class main_exercise_report_item extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_exercise_report);
        Intent intent = getIntent();
        String data = intent.getStringExtra("item");
        TextView tv = findViewById(R.id.textView);
        tv.setText(data);
    }
}