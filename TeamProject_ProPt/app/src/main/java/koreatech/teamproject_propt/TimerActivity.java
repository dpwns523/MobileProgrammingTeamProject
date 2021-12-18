package koreatech.teamproject_propt;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;
/*
    타이머 액티비티
    Interver, 30초, 1분, 직접 입력 기능 구현
    Interver은 1분 30초로 고정되어있으며 1분 30초 동안 쉬지않고 빠르게 운동하는 방법을 의미
    30sec와 1min버튼은 30초와 1분을 설정하는 기능으로 30초, 1초씩 타이머를 추가하는 기능이 아님.
    보통 운동은 한 세트에 30초와 1분내로 진행되기 때문에 간편하게 이용하기 위함.
    직접 입력가능 ex) 1분 30초 -> 1.5
 */
//, NavigationView.OnNavigationItemSelectedListener
public class TimerActivity extends AppCompatActivity
        implements View.OnClickListener ,NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    private Context mContext = this;

    //운동목표같은 리스트 누르면 이동하게끔 받는 변수선언
    View exercise_way,exercise_report;
    View community_card;

    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private ImageView imageViewReset;
    private ImageView imageViewStartStop;
    private CountDownTimer countDownTimer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

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

        // 뷰 초기화
        initViews();

        // 리스너 초기화
        initListeners();

    }

    /**
     *  뷰 초기화
     */
    private void initViews() {
        progressBarCircle =  findViewById(R.id.progressBarCircle);
        editTextMinute = findViewById(R.id.editTextMinute);
        textViewTime = findViewById(R.id.textViewTime);
        imageViewReset = findViewById(R.id.imageViewReset);
        imageViewStartStop = findViewById(R.id.imageViewStartStop);
    }

    /**
     * 리스너 초기화
     */
    private void initListeners() {
        imageViewReset.setOnClickListener(this);
        imageViewStartStop.setOnClickListener(this);
    }

    /**
     * 클릭 이벤트 설정
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewReset:   // 타이머 실행 시 시간초 리셋 버튼
                reset();
                break;
            case R.id.imageViewStartStop:   // 맨 아래 시작버튼 -> 타이머 진행 시 스탑버튼으로 변경됨
                // 직접입력란에 원하는 시간을 넣으면 우선순위는 직접입력한 시간이 됨.
                double time = 0.0;
                if (!editTextMinute.getText().toString().isEmpty()) {
                    time = Double.parseDouble(editTextMinute.getText().toString().trim());
                    timeCountInMilliSeconds = (long) (time * 60 * 1000);
                    setProgressBarValues();
                }
                startStop();
                break;
            case R.id.button:   // Interval -> 인터벌트레이닝 -> 빠르게 운동하고 휴식하는 운동방법(유산소 운동의 일종) 무조건 1분 30초
                setTimerValues(1.5);
                setProgressBarValues();

                break;
            case R.id.button2:  // 30SEC
                setTimerValues(0.5);
                setProgressBarValues();

                break;
            case R.id.button3:  // 1Min
                setTimerValues(1);
                setProgressBarValues();

                break;
        }
    }
    /**
     * 카운트 다운 시간을 리셋하고 재시작하는 기능
     */
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    /**
     * 타이머가 시작하고 멈추는 기능
     */
    private void startStop() {

        if (timerStatus == TimerStatus.STOPPED) {
            imageViewReset.setVisibility(View.VISIBLE);
            imageViewStartStop.setImageResource(R.drawable.ic_baseline_stop_circle_24);
            editTextMinute.setEnabled(false);

            timerStatus = TimerStatus.STARTED;

            startCountDownTimer();

        } else {

            imageViewReset.setVisibility(View.GONE);
            imageViewStartStop.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            editTextMinute.setEnabled(true);

            timerStatus = TimerStatus.STOPPED;

            stopCountDownTimer();

        }

    }
    /**
     * 타이머에 시간이 설정 되어있는지 체크하크
     *  - 있는 경우 : 타이머에 시간 세팅
     *  - 없는 경우 : 시간을 설정해달라는 안내 토스트 메세지
     */
    private void setTimerValues(double time) {
        timeCountInMilliSeconds = (long) (time * 60 * 1000);
    }

    /**
     * 카운트다운 시작 기능
     */
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));

                setProgressBarValues();

                imageViewReset.setVisibility(View.GONE);

                imageViewStartStop.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);

                editTextMinute.setEnabled(true);

                timerStatus = TimerStatus.STOPPED;
            }

        }.start();
        countDownTimer.start();
    }

    /**
     *  카운트 다운 정지 및 초기화
     */
    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    /**
     * 원형 프로그레스 바에 값 세팅
     */
    private void setProgressBarValues() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
        textViewTime.setText(hmsTimeFormatter((long) timeCountInMilliSeconds));
    }


    /**
     * 밀리언 초를 시간으로 포멧해주는 기능
     *
     * @param milliSeconds
     * @return HH:mm:ss 시간 포멧
     */
    private String hmsTimeFormatter(long milliSeconds) {

        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
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
            Intent intent = new Intent(TimerActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(TimerActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(TimerActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(TimerActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동

        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면
            Intent intent = new Intent(TimerActivity.this, RecordActivity.class);
            startActivity(intent);
        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(TimerActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(TimerActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}