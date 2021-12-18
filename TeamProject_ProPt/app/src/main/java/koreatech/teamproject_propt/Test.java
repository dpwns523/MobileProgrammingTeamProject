package koreatech.teamproject_propt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Test extends AppCompatActivity {

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // firebase 객체 사용을 위한 firebase database, database reference 객체 초기화
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 수정필요

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // 날짜마다 운동 부위 기록 -> 캘린더 뷰에서 날짜를 읽어옴 -> 팔, 가슴, 등, 하체 부위별 기록-> 기록이있으면 -> 1
        // databaseReference.child("user").child("record").child("2021-12").child("팔").setValue(원래값 읽어온다음+1);

        //그래프 그리기
        // addListenerForSingleValueEvent를 사용해서
        // databaseReference.child("user").child("record").child(선택한 년-달).addListenerForSingleValueEvent(
        // new ValueEventListener(){    // DataSnapshot을 통해 반복적으로 읽을 수 있음. -> 팔 가슴 등  하체?
        // 값을 가져와서 Integer로 변경.
    }
    public class Excercise{
        public String arm;
        public String chest;
        public String back;
        public String lowerBody;

        public Excercise(){}
        public Excercise(String arm, String chest, String back, String lowerBody){
            // 0과 1로 이루어짐.
            this.arm = arm;
            this.chest = chest;
            this.back = back;
            this.lowerBody = lowerBody;
        }
        @Override
        public String toString(){
            return "arm : "+arm +
                    "chest : "+ chest;
        }
    }
}
