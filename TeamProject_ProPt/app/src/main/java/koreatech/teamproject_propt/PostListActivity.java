package koreatech.teamproject_propt;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

/*
    게시글 목록 출력하는 액티비티
    - 게시글 목록 출력
 */

public class PostListActivity extends AppCompatActivity
        implements PostRVAdapter.PostClickInterface, NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 2;
    private Button addPostBtn;

    // firebase의 database, auth, reference 사용
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    // list와 recycler view 사용
    private RecyclerView postRV;
    private ArrayList<PostRVModal> postRVModalArrayList;
    private PostRVAdapter postRVAdapter;
    private RelativeLayout homeRL;

    // floating action 버튼 사용
    private FloatingActionButton addPostFAB;
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
        setContentView(R.layout.activity_post_list);
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

        // initializing all our variables.

        postRV = findViewById(R.id.postRV);
        homeRL = findViewById(R.id.bottomSheetRL);
        addPostFAB = findViewById(R.id.addPostFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        postRVModalArrayList = new ArrayList<>();

        // 데이터베이스 객체 가져옴
        databaseReference = firebaseDatabase.getReference("Posts");

        addPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 시 게시글 작성
                Intent intent = new Intent(PostListActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

        // 어댑터 생성
        postRVAdapter = new PostRVAdapter(postRVModalArrayList, this, this::onPostClick);

        // recycle view를 위한 layout 설정
        postRV.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 등록
        postRV.setAdapter(postRVAdapter);

        // 데이터베이스에서 게시글 불러옴
        getPosts();
    }

    private void getPosts() {
        postRVModalArrayList.clear();

        // 데이터베이스의 child에 이벤트 리스너 등록
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // array list에 스냅샷 더함
                postRVModalArrayList.add(snapshot.getValue(PostRVModal.class));
                // 어댑터에 데이터가 갱신되었음을 알림
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // child 등록 시 불림
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // 어댑터에 데이터가 삭제되었음을 알림
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 어댑터에 데이터가 이동되었음을 알림
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // 게시글 클릭 시 화면 하단에서 창이 올라와 상세 정보 출력
    @Override
    public void onPostClick(int position) {
        displayBottomSheet(postRVModalArrayList.get(position));
    }

    private void displayBottomSheet(PostRVModal modal) {
        // BottomSheetDialog 생성
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        // bottom sheet에 layout inflating
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        bottomSheetTeachersDialog.setContentView(layout);

        // bottom sheet 빠져나가는 방법 설정
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);

        bottomSheetTeachersDialog.show();

        // bottom sheet에 출력할 정보
        TextView postNameTV = layout.findViewById(R.id.bottomSheetPostName);
        TextView postDescTV = layout.findViewById(R.id.bottomSheetPostDesc);
        TextView postCategoryTV = layout.findViewById(R.id.bottomSheetPostCategory);
        ImageView postImgIV = layout.findViewById(R.id.bottomSheetPostImg);

        // 정보 설정
        postNameTV.setText(modal.getPostName());
        postDescTV.setText(modal.getPostDesc());
        postCategoryTV.setText(modal.getPostCategory());

        // 카테고리에 따라 이미지 변경
        switch(modal.getPostCategory()) {
            case "자유":
                postImgIV.setImageResource(R.drawable.goal);
                break;
            case "질문":
                postImgIV.setImageResource(R.drawable.ic_baseline_emoji_people_24);
                break;
            case "운동 팁":
                postImgIV.setImageResource(R.drawable.ic_baseline_sports_kabaddi_24);
                break;
            case "식단 공유":
                postImgIV.setImageResource(R.drawable.ic_baseline_no_food_24);
                break;
        }

        Button editBtn = layout.findViewById(R.id.editPostBtn);

        // 게시글 수정 버튼에 이벤트 리스너 등록
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostListActivity.this, EditPostActivity.class);

                // 인탠트로 게시글에 대한 모달 전달
                intent.putExtra("post", modal);
                startActivity(intent);
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
            Intent intent = new Intent(PostListActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동

        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(PostListActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(PostListActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(PostListActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(PostListActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(PostListActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PostListActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
