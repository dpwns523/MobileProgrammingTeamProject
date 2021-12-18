package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
    UserProfileActivity 사용자 프로필 구성.

    기능구현 :
    로그인에서 받은 정보 일부와 사용자가 입력한 정보를 합쳐 프로필 완성
 */
public class UserProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 2;
    private DatabaseReference mDatabase;
    private UserModel userModel;
    //변수 선언
    FirebaseAuth mAuth;
    EditText id,email,name,phone_number,address;
    static class UserModel {
        // 사용자 정보를 읽어올 데이터 모델
        private String name;
        private String email;
        private String phone_num;
        private String address;
        private String uid;
        public UserModel(String name, String email, String phone_num, String address, String uid){
            this.name =name;
            this.email = email;
            this.phone_num = phone_num;
            this.address = address;
            this.uid = uid;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNum() {
            return phone_num;
        }

        public void setPhoneNum(String phone_num) {
            this.phone_num = phone_num;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
    // BottomNavigation
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
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                    return true;
                case  R.id.navigationMenu:
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
        setContentView(R.layout.user_info);

        // Bottom Navigation에 대한 처리.
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

        //파이어베이스 로그인한 유저 정보 받게함
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        id = (EditText) findViewById(R.id.user_info_id);
        name = (EditText) findViewById(R.id.user_info_name);
        phone_number = (EditText) findViewById(R.id.user_info_phonum);
        address = (EditText) findViewById(R.id.user_info_address);
        email = (EditText) findViewById(R.id.user_info_email);
        String uid = user.getUid();
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    userModel = snapshot.getValue(UserModel.class);
                }
                name.setText(userModel.getName());
                email.setText(userModel.getEmail());
                phone_number.setText(userModel.getPhoneNum());
                address.setText(userModel.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
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

        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(UserProfileActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(UserProfileActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(UserProfileActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(UserProfileActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(UserProfileActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(UserProfileActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
