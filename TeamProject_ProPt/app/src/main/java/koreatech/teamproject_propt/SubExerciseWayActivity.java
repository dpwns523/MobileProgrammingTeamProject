package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/*
    운동방법 화면 구성.

    기능구현 :
    운동목록에서 누른 값을 액티비티로 출력하게 됨
 */
public class SubExerciseWayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Context mContext = this;
    private BottomNavigationView bottomNavigationView;
    ImageView img;
    Button btn;
    TextView title_tv,description,part_tv;
    String titles,descriptions,parts,url;
    int image;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_exercise_way);

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


        img = findViewById(R.id.imageView2);
        title_tv = findViewById(R.id.sub_exercise_title);
        description = findViewById(R.id.description);
        part_tv = findViewById(R.id.user_info_id);
        btn = findViewById(R.id.youtube);

        //main_exercise_way에서 extra값으로 넣어준 값을 받아오게 됨
        image = getIntent().getIntExtra("image",1);
        titles = getIntent().getStringExtra("title");
        descriptions = getIntent().getStringExtra("description");

        //버튼을 누르면 그 url로 연결이 되게됩니다.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //xml의 imageview,textview에 값을 삽입
        img.setImageResource(image);
        title_tv.setText(titles);
        description.setText(descriptions);
        part(titles);
        part_tv.setText(parts);
    }

    //운동 이름을 받아서 운동 효과 범위와 범위에 맞는 유튜브 링크로 반환하는 함수
    public void part(String part_description) {
        switch(part_description) {
            case "벤치 프레스" :
                parts = "가슴";
                url = "https://www.youtube.com/watch?v=A2kHURY746E";
                break;
            case "인클라인 벤치프레스" :
                parts = "가슴";
                url = "https://www.youtube.com/watch?v=4HvI_mFhzVQ";
                break;
            case "덤벨 프레스" :
                parts = "가슴";
                url = "https://www.youtube.com/watch?v=xTQL6jvVMNA";
                break;
            case "체스트 프레스" :
                parts = "가슴";
                url = "https://www.youtube.com/watch?v=ppPQgmgpafM";
                break;
            case "바벨 컬" :
                parts = "팔";
                url = "https://www.youtube.com/watch?v=Dlg0W_5mq98";
                break;
            case "얼터네이팅 덤벨 컬" :
                parts = "팔";
                url = "https://www.youtube.com/watch?v=IfhyHYFJOZ0";
                break;
            case "덤벨 컬" :
                parts = "팔";
                url = "https://www.youtube.com/watch?v=y3aMzyKqvNM";
                break;
            case "스파이더 컬" :
                parts = "팔";
                url = "https://www.youtube.com/watch?v=vPngGck560I";
                break;
            case "랫풀다운" :
                parts = "등";
                url = "https://www.youtube.com/watch?v=2K2WCGstHOY";
                break;
            case "시티드로우" :
                parts = "등";
                url = "https://www.youtube.com/watch?v=ZArSjRpaTV4";
                break;
            case "하이케이블로우" :
                url = "https://www.youtube.com/watch?v=nqDGtlWdLho&t=1s";
                parts = "등";
                break;
            case "풀업" :
                parts = "등";
                url = "https://www.youtube.com/watch?v=9lsqux_WcBo";
                break;
            case "레그익스텐션" :
                parts = "하체";
                url = "https://www.youtube.com/watch?v=mS9iwXhycJI";
                break;
            case "스쿼트" :
                parts = "하체";
                url = "https://www.youtube.com/watch?v=kz84Fc6HGu4";
                break;
            case "레그프레스" :
                parts = "하체";
                url = "https://www.youtube.com/watch?v=EV0F_3S7Sks";
                break;
            case "라잉 레그컬" :
                parts = "하체";
                url = "https://www.youtube.com/watch?v=bsqXhhjnwmY";
                break;
        }
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
            Intent intent = new Intent(SubExerciseWayActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(SubExerciseWayActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(SubExerciseWayActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(SubExerciseWayActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(SubExerciseWayActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(SubExerciseWayActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(SubExerciseWayActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SubExerciseWayActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}