package koreatech.teamproject_propt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    일일 운동 기록 Activity
    캘린더에서 날짜를 클릭하면 이전의 기록했던 운동 기록을 저장하고 있음.
    부위별 운동을 기록하는 액티비티
 */

public class ExerciseReportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context mContext = this;
    Context mcontext = this;
    private BottomNavigationView bottomNavigationView;
    private static final int ACTIVITY_NUM = 2;
    private ListView listView;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    public CalendarView calendarView;
    private ExerciseModel exerciseModel;

    LocalDate now = LocalDate.now();

    // 현재 날짜로 초기화
    private int myear = now.getYear();
    private int mmonth = now.getMonthValue() - 1;
    private int mdate = now.getDayOfMonth();

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {   // BottomNavigation 선택 이벤트 처리
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    Intent intent2 = new Intent(mcontext, UserProfileActivity.class);
                    mcontext.startActivity(intent2);
                    return true;
                case R.id.navigationHome:
                    Intent intent3 = new Intent(mcontext, HomeActivity.class);
                    mcontext.startActivity(intent3);
                    return true;
                case R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    static class ExerciseModel {
        private String arm;
        private String chest;
        private String back;
        private String leg;

        public String getArm() {
            return arm;
        }

        public void setArm(String arm) {
            this.arm = arm;
        }

        public String getChest() {
            return chest;
        }

        public void setChest(String chest) {
            this.chest = chest;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getLeg() {
            return leg;
        }

        public void setLeg(String leg) {
            this.leg = leg;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_report);
//        // 하단 네비게이션 바에 대한 액션 처리 객체
//        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        exerciseModel = new ExerciseModel();


        // firebaseAuth에서 유저 id 가져옴
        String uid = FirebaseAuth.getInstance().getUid();

        // firebase 객체 사용을 위한 firebase database, database reference 객체 초기화
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(uid).child("ExcerciseData");

        // 일단 이미 DB에 데이터가 있다면 리스트뷰에 내용을 작성할 것
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 year, month, dayOfMonth를 얻을 수 있음.
                // 선택된 년-월-일을 가지고 노드 : 2021 - child가 월 - child가 일 -child가 arm,chest,back,lowerbody
                items.clear();
                myear = year;
                mmonth = month;
                mdate = dayOfMonth;

                // items를 어댑터에 등록하고, 리스트뷰에 어댑터 등록
                adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
                listView = findViewById(R.id.listView);
                listView.setAdapter(adapter);

                databaseReference.child(String.valueOf(year)).child(String.valueOf(month + 1)).child(String.valueOf(dayOfMonth)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // snapshot을 사용해 firebase 데이터베이스에 저장된 목표값 가져옴
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            exerciseModel = snapshot.getValue(ExerciseModel.class);
                            items.add("팔 : " + exerciseModel.getArm());
                            items.add("가슴 : " + exerciseModel.getChest());
                            items.add("등 : " + exerciseModel.getBack());
                            items.add("하체 : " + exerciseModel.getLeg());

                            adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
                            listView = findViewById(R.id.listView);
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void onClick(View v) {
        LinearLayout dialogView = (LinearLayout) View.inflate(ExerciseReportActivity.this, R.layout.record_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setView(dialogView);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) { //확인 버튼을 클릭했을때
                // 각 운동 목표값 기록
                String arm = ((TextView) dialogView.findViewById((R.id.arm))).getText().toString();
                items.add("팔 : " + arm);

                String chest = ((TextView) dialogView.findViewById((R.id.chest))).getText().toString();
                items.add("가슴 : " + chest);

                String back = ((TextView) dialogView.findViewById((R.id.back))).getText().toString();
                items.add("등 : " + back);

                String leg = ((TextView) dialogView.findViewById((R.id.lower_body))).getText().toString();
                items.add("하체 : " + leg);

                //해쉬맵 테이블을 firebase 데이터베이스에 저장
                HashMap<Object,String> hashMap = new HashMap<>();

                hashMap.put("arm", arm);
                hashMap.put("chest", chest);
                hashMap.put("back", back);
                hashMap.put("leg", leg);

                databaseReference.child(String.valueOf(myear)).child(String.valueOf(mmonth + 1)).child(String.valueOf(mdate)).setValue(hashMap);

                // 모델 클래스에 값 저장
                exerciseModel.setArm(arm);
                exerciseModel.setChest(chest);
                exerciseModel.setBack(back);
                exerciseModel.setLeg(leg);

                // 리스트뷰에 수정 사항 반영
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_page) {   // 프로필 화면 이동
            Intent intent = new Intent(ExerciseReportActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(ExerciseReportActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동

        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(ExerciseReportActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(ExerciseReportActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(ExerciseReportActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(ExerciseReportActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ExerciseReportActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}