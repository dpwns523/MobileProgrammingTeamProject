package koreatech.teamproject_propt;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/*
    일일 운동 목표 설정 Activity
    캘린더에서 날짜를 클릭하면 운동을 기록할 수 있는 Dialog가 나타남.
    이미 기록해둔 날짜로 갔다면 DB에서 데이터를 읽어오고 리스트뷰에 나타나며, 추가적인 작성 가능.
    부위별 운동 기록이기 때문에, 덮어쓰기.
 */

public class main_exercise_report extends AppCompatActivity {
    private Context mContext = this;
<<<<<<< HEAD
    private static final int ACTIVITY_NUM = 2;
    private ListAdapter adapter;
    private ListView listView;
    private int num;
=======
    private static final int ACTIVITY_NUM = 4;
>>>>>>> 099d14a432efe72bacfe7321459c1e9bb0101419

    private ListView listView;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    public CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_exercise_report);
        // 하단 네비게이션 바에 대한 액션 처리 객체
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
        // 일단 이미 DB에 데이터가 있다면 리스트뷰에 내용을 작성할 것
        // 1. 데이터 읽어오기, 리스트에 추가 -> setAdapter
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 year, month, dayOfMonth를 얻을 수 있음.
                // 선택된 년-월-일을 가지고 노드 : 2021 - child가 월 - child가 일 -child가 arm,chest,back,lowerbody
                items.clear();
                LinearLayout dialogView = (LinearLayout) View.inflate(main_exercise_report.this, R.layout.record_dialog, null);
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