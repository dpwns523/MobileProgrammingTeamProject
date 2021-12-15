//package koreatech.teamproject_propt;
//
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class CommunityActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_community);
//    }
//}


package koreatech.teamproject_propt;

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


public class CommunityActivity extends AppCompatActivity{
//        implements NavigationView.OnNavigationItemSelectedListener {

//    private BottomNavigationView bottomNavigationView;

    //운동목표같은 리스트 누르면 이동하게끔 받는 변수선언
    View food, before_after, free_talking, questions, stimulus, tips;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigationMyProfile:
//                    return true;
//                case R.id.navigationMyCourses:
//                    return true;
//                case R.id.navigationHome:
//                    return true;
//                case  R.id.navigationSearch:
//                    return true;
//                case  R.id.navigationMenu:
//                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                    drawer.openDrawer(GravityCompat.START);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_community);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
////
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
//
//        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        //handling floating action menu
        //전체메뉴 각각 view에 대한 id값을 받음
//        food = (View) findViewById(R.id.food);
//        before_after = (View) findViewById(R.id.before_after);
        free_talking = (View) findViewById(R.id.free_talking);
//        questions = (View) findViewById(R.id.questions);
//        stimulus = (View) findViewById(R.id.stimulus);
//        tips = (View) findViewById(R.id.tips);
        //운동방법에 대한 setOnClickListener
//        food.setOnClickListener(v -> {
//                    Intent intent = new Intent(CommunityActivity.this, main_exercise_way.class);
//                    startActivity(intent);
//                }
//        );

        //before_after에 대한 setOnClickListener
//        before_after.setOnClickListener(v -> {
//                    Intent intent = new Intent(CommunityActivity.this, main_exercise_report.class);
//                    startActivity(intent);
//                }
//        );

        free_talking.setOnClickListener(v -> {
                    Intent intent = new Intent(CommunityActivity.this, FreeTalkingActivity.class);
                    startActivity(intent);
                }
        );

//        questions.setOnClickListener(v -> {
//                    Intent intent = new Intent(CommunityActivity.this, main_exercise_report.class);
//                    startActivity(intent);
//                }
//        );
//
//        tips.setOnClickListener(v -> {
//                    Intent intent = new Intent(CommunityActivity.this, main_exercise_report.class);
//                    startActivity(intent);
//                }
//        );
//
//        stimulus.setOnClickListener(v -> {
//                    Intent intent = new Intent(CommunityActivity.this, main_exercise_report.class);
//                    startActivity(intent);
//                }
//        );


    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_dark_mode) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
