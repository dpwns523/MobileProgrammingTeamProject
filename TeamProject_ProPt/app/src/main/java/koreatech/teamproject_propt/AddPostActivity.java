package koreatech.teamproject_propt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
    커뮤니티에 글 생성하기 위한 액티비티
    - 게시판 글 생성
 */

public class AddPostActivity extends AppCompatActivity {
    private Button registerPostBtn;
    private TextInputEditText postName, postDesc;
    private String postID;
    private RadioButton postCategoryBtn;
    private static long seq = 0;

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // 진행상태 나타내기 위한 ProgressBar 객체
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        registerPostBtn = findViewById(R.id.registerPostBtn);
        postName = findViewById(R.id.postName);
        postDesc = findViewById(R.id.editPostDesc);
        loadingPB = findViewById(R.id.PBLoading);

        // firebase 객체 사용을 위한 firebase database, database reference 객체 초기화
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        registerPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카테고리 정하는 라디오 그룹에서 선택된 라디오 버튼(카테고리) 가져옴
                RadioGroup categoryRadioGroup = (RadioGroup) (findViewById(R.id.editCategoryRadio));
                postCategoryBtn = (RadioButton) (findViewById(categoryRadioGroup.getCheckedRadioButtonId()));

                loadingPB.setVisibility(View.VISIBLE);
                String postNameText = postName.getText().toString();
                String postCategoryText = postCategoryBtn.getText().toString();
                String postDescText = postDesc.getText().toString();

                // 모달 객체 생성 및 데이터 값 설정
                PostRVModal postRVModal = new PostRVModal(postID, postNameText, postCategoryText, postDescText);

                // firebase DB에 데이터 전달하기 위해 databaseReference 객체에 value event 등록
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // 현재 (db의 자식 수 + 1)으로 게시글 번호 지정
                        long seq = snapshot.getChildrenCount();
                        postID = String.valueOf(seq+1);
                        postRVModal.setPostId(postID);

                        // 모달 객체 전달해 데이터베이스에 값 저장
                        databaseReference.child(postID).setValue(postRVModal);

                        // 게시글 등록 완료 토스트 메시지 출력
                        Toast.makeText(AddPostActivity.this, "게시글 등록 완료", Toast.LENGTH_SHORT).show();

                        finish();
                        // PostListActivity 실행
                        startActivity(new Intent(AddPostActivity.this, PostListActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 게시글 작성을 취소할 경우 이에 해당하는 토스트 메시지 출력
                        Toast.makeText(AddPostActivity.this, "게시글 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
