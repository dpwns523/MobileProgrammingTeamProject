package koreatech.teamproject_propt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/*
    자신이 기록한 데이터를 가지고 그래프로 구현
    일일 운동 기록에 따른 운동 부위 비율을 보여줌.
    DateButton으로 년도, 월을 선택하여 한달 단위로 운동 부위 비율을 볼 수 있다.
 */
public class RecordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    private Context mContext = this;
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
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
            TextView date = (TextView) findViewById(R.id.datefill);
            date.setText(year + "-" + monthOfYear);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
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
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(35f, "Back"));
        yValues.add(new PieEntry(30f, "Arm"));
        yValues.add(new PieEntry(45f, "Chest"));
        yValues.add(new PieEntry(25f, "lowerBody"));

        Description description = new Description();
        description.setText("운동 비율"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues, "Part");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
    }

    public void onClick(View v) {
        MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
        pd.setListener(d);
        pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
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
            Intent intent = new Intent(RecordActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Community) {  // 커뮤니티 게시판 이동
            Intent intent = new Intent(RecordActivity.this, PostListActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {   // 운동 방법 이동
            Intent intent = new Intent(RecordActivity.this, ExerciseWayActivity.class);
            startActivity(intent);
        } else if (id == R.id.set_goal) {  // 일일 운동 기록 이동
            Intent intent = new Intent(RecordActivity.this, ExerciseReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.timer_item) { // 타이머 이동
            Intent intent = new Intent(RecordActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (id == R.id.record_user_exercise) {    // 사용자 운동 기록 그래프화면

        } else if( id == R.id.record_user_spec){    // 사용자 일일 스펙 기록 화면
            Intent intent = new Intent(RecordActivity.this, SpecActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout){   // 사용자 로그아웃 -> 로그인 페이지 이동
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(RecordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    public void onClickButton(View v){
//        switch(v.getId()){
//            case R.id.specButton:
//                break;
//            case R.id.exerciseButton:
//                PieChart pieChart = (PieChart) findViewById(R.id.piechart);
//
//                pieChart.setUsePercentValues(true);
//                pieChart.getDescription().setEnabled(false);
//                pieChart.setExtraOffsets(5, 10, 5, 5);
//
//                pieChart.setDragDecelerationFrictionCoef(0.95f);
//
//                pieChart.setDrawHoleEnabled(false);
//                pieChart.setHoleColor(Color.WHITE);
//                pieChart.setTransparentCircleRadius(61f);
//
//                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//                yValues.add(new PieEntry(34f, "Back"));
//                yValues.add(new PieEntry(23f, "Arm"));
//                yValues.add(new PieEntry(45f, "Chest"));
//                yValues.add(new PieEntry(10f, "lowerBody"));
//
//                Description description = new Description();
//                description.setText("운동 비율"); //라벨
//                description.setTextSize(15);
//                pieChart.setDescription(description);
//
//                pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션
//
//                PieDataSet dataSet = new PieDataSet(yValues, "Part");
//                dataSet.setSliceSpace(3f);
//                dataSet.setSelectionShift(5f);
//                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//
//                PieData data = new PieData((dataSet));
//                data.setValueTextSize(10f);
//                data.setValueTextColor(Color.YELLOW);
//                pieChart.setData(data);
//                break;
//
//        }
//    }
}