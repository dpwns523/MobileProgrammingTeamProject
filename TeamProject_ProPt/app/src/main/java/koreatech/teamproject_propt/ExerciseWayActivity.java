package koreatech.teamproject_propt;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

/*
    .main_exercise_way 액티비티

    기능설명 :
    1.tabview를 활용하여 가슴,팔,등,하체의 tab목록을 생성
    2.recyclerview와 cardview를 adapter를 이용해서 연결해줌
    3.tabview의 tab목록을 누르게되면 그에 맞는 cardview를 생성하게됨
 */

public class ExerciseWayActivity extends AppCompatActivity {
    //변수들을 선언해줍니다.
    RecyclerView recyclerView;
    String s1[],s2[];
    int images[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_exercise_way);


        //recyclerview,tabview 기본 설정 (레이아웃 매니저,변수 지정)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        recyclerView = findViewById(R.id.recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //tabview에 있는 tab목록 누르기 전에 기본으로 생성되는 cardview목록
        s1 = getResources().getStringArray(R.array.chest);
        s2 = getResources().getStringArray(R.array.chest_description);
        images = new int[] {R.drawable.pic_chest1,R.drawable.pic_chest2,
                R.drawable.pic_chest3,R.drawable.pic_chest4};

        //adapter연결
        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);

        /*tablayout listener를 설정하게됨*/
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //탭 선택된다면 실행되는 메소드
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
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

        if(pos==0) {
            s1 = getResources().getStringArray(R.array.chest);
            s2 = getResources().getStringArray(R.array.chest_description);
            images = new int[] {R.drawable.pic_chest1,R.drawable.pic_chest2,
                    R.drawable.pic_chest3,R.drawable.pic_chest4};
        }
        else if(pos==1) {
            s1 = getResources().getStringArray(R.array.arm);
            s2 = getResources().getStringArray(R.array.arm_description);
            images = new int[] {R.drawable.pic_arm1,R.drawable.pic_arm2,
                    R.drawable.pic_arm3,R.drawable.pic_arm4};
        }
        else if(pos==2) {
            s1 = getResources().getStringArray(R.array.back);
            s2 = getResources().getStringArray(R.array.back_description);
            images = new int[] {R.drawable.pic_back1,R.drawable.pic_back2,
                    R.drawable.pic_back3,R.drawable.pic_back4};
        }
        else if(pos==3) {
            s1 = getResources().getStringArray(R.array.lower_body);
            s2 = getResources().getStringArray(R.array.lower_body_description);
            images = new int[] {R.drawable.pic_lower_body1,R.drawable.pic_lower_body2,
                    R.drawable.pic_lower_body3,R.drawable.pic_lower_body4};
        }
        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);
    }
}
