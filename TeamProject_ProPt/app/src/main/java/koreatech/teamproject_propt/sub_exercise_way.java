package koreatech.teamproject_propt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
    .sub_exercise_way 로그인 화면 구성.

    기능구현 :
    운동목록에서 누른 값을 액티비티로 출력하게 됨
 */
public class sub_exercise_way extends AppCompatActivity {
    ImageView img;
    Button btn;
    TextView title_tv,description,part_tv;
    String titles,descriptions,parts,url;
    int image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_exercise_way);
        img = findViewById(R.id.imageView2);
        title_tv = findViewById(R.id.sub_exercise_title);
        description = findViewById(R.id.description);
        part_tv = findViewById(R.id.part);
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
}