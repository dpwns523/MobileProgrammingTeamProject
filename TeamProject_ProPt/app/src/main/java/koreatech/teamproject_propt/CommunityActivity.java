package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
<<<<<<< HEAD
    일일 운동 목표 설정 Activity
    -기능 설명..@
=======
    커뮤니티 게시판 Activity
    -게시판 목록 출력
>>>>>>> origin/community
 */

public class CommunityActivity extends AppCompatActivity {
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 4;
    private Button addPostBtn;

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

        // 글 작성하기 버튼에 onClickListener 등록
        addPostBtn = findViewById(R.id.addPostBtn);
//        addPostBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(CommunityActivity.this, AddPostActivity.class);
//            startActivity(intent);
//        });

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }

}