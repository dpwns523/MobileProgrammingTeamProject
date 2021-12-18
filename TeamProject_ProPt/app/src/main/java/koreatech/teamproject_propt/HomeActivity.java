package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
/*
    메인 Home 화면 Activity
    -기능 설명..@
 */

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context mcontext = this;
    private BottomNavigationView bottomNavigationView;

    //운동목표같은 리스트 누르면 이동하게끔 받는 변수선언
    View exercise_way,exercise_report;
    View community_card;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {   // BottomNavigation 선택 이벤트 처리
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    Intent intent2 = new Intent(mcontext, user_info.class); // 2
                    mcontext.startActivity(intent2);
                    return true;
                case R.id.navigationHome:
                    //Intent intent3 = new Intent(mcontext, HomeActivity.class); // 2
                    //mcontext.startActivity(intent3);
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        //전체메뉴 각각 view에 대한 id값을 받음

        exercise_way = (View) findViewById(R.id.exercise_way);
        community_card = (View) findViewById(R.id.community_card);
        exercise_report = (View) findViewById(R.id.exercise_report);

        View exercise_way = (View) findViewById(R.id.exercise_way);
        View exercise_report = (View) findViewById(R.id.exercise_report);
        View timer = (View)findViewById(R.id.timer);
        View user_exercise_spec = (View)findViewById(R.id.user_exercise_spec);
        View user_spec = (View)findViewById(R.id.user_spec);
        View community_card = (View)findViewById(R.id.community_card);
        View profileCircleImageView = (View)findViewById(R.id.profileCircleImageView);


        // 커뮤니티 onClickListener
        community_card.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, PostListActivity.class);
                startActivity(intent);
            }
        );
        //운동방법 onClickListener
        exercise_way.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeActivity.this, main_exercise_way.class);
                    startActivity(intent);
                }
        );

        //일일운동목표 onClickListener
        exercise_report.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeActivity.this, main_exercise_report.class);
                    startActivity(intent);
                }
        );
        //타이머 onClickListener
        timer.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        );
        //사용자 운동기록 onClickListener
        user_exercise_spec.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RecordActivity.class);
            startActivity(intent);

            }
        );
        //사용자 스펙 기록 onClickListener
        user_spec.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SpecActivity.class);
            startActivity(intent);
                }
        );
        //맨 우측 상단 프로필아이콘 onClickListener
        profileCircleImageView.setOnClickListener(v -> {

                }
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
