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
    일일 자신의 신체 스펙 기록 Activity
    캘린더에서 날짜를 선택한 후 버튼을 눌러 각 스펙을 기록해 놓는다.
 */

public class SpecActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 2;
    private ListView listView;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    public CalendarView calendarView;
    private BodySpecModel bodySpecModel;
    //BottomNavigation 이벤트 리스너
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {   // BottomNavigation 선택 이벤트 처리
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    Intent intent2 = new Intent(mContext, UserProfileActivity.class); // 2
                    mContext.startActivity(intent2);
                    return true;
                case R.id.navigationHome:
                    Intent intent3 = new Intent(mContext, HomeActivity.class); // 2
                    mContext.startActivity(intent3);
                    return true;
                case R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    LocalDate now = LocalDate.now();

    // 현재 날짜로 초기화
    private int myear = now.getYear();
    private int mmonth = now.getMonthValue() - 1;
    private int mdate = now.getDayOfMonth();

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    static class BodySpecModel {
        private String weight;
        private String fat;
        private String muscle;

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getMuscle() {
            return muscle;
        }

        public void setMuscle(String muscle) {
            this.muscle = muscle;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_spec);

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

        bodySpecModel = new BodySpecModel();

        // firebaseAuth에서 유저 id 가져옴
        String uid = FirebaseAuth.getInstance().getUid();

        // firebase 객체 사용을 위한 firebase database, database reference 객체 초기화
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(uid).child("BodySpecData");

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
                            bodySpecModel = snapshot.getValue(BodySpecModel.class);
                            items.add("체중 : " + bodySpecModel.getWeight());
                            items.add("체지방 : " + bodySpecModel.getFat());
                            items.add("근육량 : " + bodySpecModel.getMuscle());

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
        LinearLayout dialogView = (LinearLayout) View.inflate(SpecActivity.this, R.layout.profile_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setView(dialogView);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) { //확인 버튼을 클릭했을때
                // 각 운동 목표값 기록
                String weight = ((TextView) dialogView.findViewById((R.id.body))).getText().toString();
                items.add("체중 : " + weight);

                String fat = ((TextView) dialogView.findViewById((R.id.body_fat))).getText().toString();
                items.add("체지방 : " + fat);

                String muscle = ((TextView) dialogView.findViewById((R.id.muscle_mass))).getText().toString();
                items.add("근육량 : " + muscle);

                //해쉬맵 테이블을 firebase 데이터베이스에 저장
                HashMap<Object,String> hashMap = new HashMap<>();

                hashMap.put("weight", weight);
                hashMap.put("fat", fat);
                hashMap.put("muscle", muscle);

                databaseReference.child(String.valueOf(myear)).child(String.valueOf(mmonth + 1)).child(String.valueOf(mdate)).setValue(hashMap);

                // 모델 클래스에 값 저장
                bodySpecModel.setWeight(weight);
                bodySpecModel.setFat(fat);
                bodySpecModel.setMuscle(muscle);

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
            Intent intent = new Intent(SpecActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(SpecActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(SpecActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(SpecActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(SpecActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(SpecActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면

        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SpecActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}