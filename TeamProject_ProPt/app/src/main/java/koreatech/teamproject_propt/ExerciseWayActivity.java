package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

/*
    Exercise Way 운동 방법 액티비티

    기능설명 :
    1.tabview를 활용하여 가슴,팔,등,하체의 tab목록을 생성
    2.recyclerview와 cardview를 adapter를 이용해서 연결해줌
    3.tabview의 tab목록을 누르게되면 그에 맞는 cardview를 생성하게됨
 */

public class ExerciseWayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Context mcontext = this;
    private BottomNavigationView bottomNavigationView;
    //변수들을 선언해줍니다.
    RecyclerView recyclerView;
    String s1[], s2[];
    int images[] = {};

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_exercise_way);

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

        //recyclerview,tabview 기본 설정 (레이아웃 매니저,변수 지정)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        recyclerView = findViewById(R.id.recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //tabview에 있는 tab목록 누르기 전에 기본으로 생성되는 cardview목록
        s1 = getResources().getStringArray(R.array.chest);
        s2 = getResources().getStringArray(R.array.chest_description);
        images = new int[]{R.drawable.pic_chest1, R.drawable.pic_chest2,
                R.drawable.pic_chest3, R.drawable.pic_chest4};

        //adapter연결
        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);

        /*tablayout listener를 설정하게됨*/
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //탭 선택된다면 실행되는 메소드
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                save_list(pos);
            }

            //탭 안 선택되면 실행되는 메소드
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            //탭 다시 선택되면 실행되는 메소드
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /*
    어느 운동 부위를 눌렀느냐 따라서 그에 맞는 array값을 배열에 할당하게 됨
    그 후, adapter를 통해 recyclerView와 연결
     */
    public void save_list(int pos) {

        if (pos == 0) {
            s1 = getResources().getStringArray(R.array.chest);
            s2 = getResources().getStringArray(R.array.chest_description);
            images = new int[]{R.drawable.pic_chest1, R.drawable.pic_chest2,
                    R.drawable.pic_chest3, R.drawable.pic_chest4};
        } else if (pos == 1) {
            s1 = getResources().getStringArray(R.array.arm);
            s2 = getResources().getStringArray(R.array.arm_description);
            images = new int[]{R.drawable.pic_arm1, R.drawable.pic_arm2,
                    R.drawable.pic_arm3, R.drawable.pic_arm4};
        } else if (pos == 2) {
            s1 = getResources().getStringArray(R.array.back);
            s2 = getResources().getStringArray(R.array.back_description);
            images = new int[]{R.drawable.pic_back1, R.drawable.pic_back2,
                    R.drawable.pic_back3, R.drawable.pic_back4};
        } else if (pos == 3) {
            s1 = getResources().getStringArray(R.array.lower_body);
            s2 = getResources().getStringArray(R.array.lower_body_description);
            images = new int[]{R.drawable.pic_lower_body1, R.drawable.pic_lower_body2,
                    R.drawable.pic_lower_body3, R.drawable.pic_lower_body4};
        }
        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
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
            Intent intent = new Intent(ExerciseWayActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(ExerciseWayActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동

        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(ExerciseWayActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(ExerciseWayActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(ExerciseWayActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(ExerciseWayActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ExerciseWayActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
