package koreatech.teamproject_propt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SpecActivity extends AppCompatActivity {
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 2;

    private ListView listView;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    public CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_spec);

        // 일단 이미 DB에 데이터가 있다면 리스트뷰에 내용을 작성할 것
        // 1. 데이터 읽어오기, 리스트에 추가 -> setAdapter
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 year, month, dayOfMonth를 얻을 수 있음.
                // 선택된 년-월-일을 가지고 노드 : 2021 - child가 월 - child가 일 -child가 arm,chest,back,lowerbody
                items.clear();
                LinearLayout dialogView = (LinearLayout) View.inflate(SpecActivity.this, R.layout.profile_dialog, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setView(dialogView);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //확인 버튼을 클릭했을때
                        // 리스트 뷰에 추가하면서, firebase DB에도 추가해줘야함. 이번에 작성한 내용으로 DB에 덮어쓰기를 할 것.
                        // 1. arm을 setValue로 업데이트하는방식.
                        String arm = ((TextView) dialogView.findViewById((R.id.arm))).getText().toString();
                        Toast.makeText(mContext,arm+"Test",Toast.LENGTH_LONG).show();
                        items.add(arm);
                        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
                        listView = findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                    }
                });
                alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //취소 버튼을 클릭
                        // 아무런 작동 X
                    }
                });
                alert.show();
            }
        });
    }
}
