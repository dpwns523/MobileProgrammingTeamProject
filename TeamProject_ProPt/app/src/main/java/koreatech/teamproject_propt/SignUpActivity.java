package koreatech.teamproject_propt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/*
    로그인 액티비티에서 '신규 회원가입' 텍스트 누를 시 이동하는 회원가입 액티비티
    - 회원가입을 진행
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextAddress;
    Button buttonSignup;
    TextView textviewSignin;
    TextView textviewMessage;
    ProgressDialog progressDialog;

    // 인증을 위해 FirebaseAuth 객체 생성
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        // 이미 로그인 인증돼있는 유저가 있을 경우
//        if(firebaseAuth.getCurrentUser() != null){
//
//            // 현재 액티비티 종료
//            finish();
//
//            // profile 액티비티로 이동
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        textviewSignin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        buttonSignup.setOnClickListener(this);
        textviewSignin.setOnClickListener(this);
    }

    // 유저가 로그인하는 firebase 메소드
    private void registerUser(){
        // 사용자가 textView에 입력한 email, password
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // email과 password가 비었으면 토스트 메시지 출력
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        // email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        // 새로운 유저 생성하는 firebase 메소드
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uEmail = user.getEmail();
                            String uid = user.getUid();
                            String uName = name;
                            String uPhone = phone;
                            String uAddress = address;

                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("uid",uid);
                            hashMap.put("uEmail",uEmail);
                            hashMap.put("uName",uName);
                            hashMap.put("uPhone",uPhone);
                            hashMap.put("uAddress",uAddress);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            finish();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            //에러 발생 시 에러 메시지 출력
                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(SignUpActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //TODO
            registerUser();
        }

        if(view == textviewSignin) {
            //TODO
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}