package koreatech.teamproject_propt;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
    .user_info 사용자 프로필 구성.

    기능구현 :
    로그인에서 받은 정보 일부와 사용자가 입력한 정보를 합쳐 프로필 완성
 */
public class user_info extends AppCompatActivity {
    //변수 선언
    FirebaseAuth mAuth;
    EditText id,email,name,phone_number,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //파이어베이스 로그인한 유저 정보 받게함
        setContentView(R.layout.user_info);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        id = (EditText) findViewById(R.id.user_info_id);


        id.setText(user.getEmail());
    }
}
