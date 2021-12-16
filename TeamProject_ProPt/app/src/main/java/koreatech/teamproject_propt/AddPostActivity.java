package koreatech.teamproject_propt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
    private TextInputEditText postName, postCategory, postDesc, postImgLink;
    private String postID;
    private int seq = 0;

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        registerPostBtn = findViewById(R.id.registerPostBtn);
        postName = findViewById(R.id.postName);
        postCategory = findViewById(R.id.postCategory);
        postDesc = findViewById(R.id.postDesc);
        postImgLink = findViewById(R.id.postImageLink);
        loadingPB = findViewById(R.id.PBLoading);

        // firebase 객체 사용을 위한 firebase database, database reference 객체 초기화
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        registerPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingPB.setVisibility(View.VISIBLE);

                postID = String.valueOf(++seq);
                String postNameText = postName.getText().toString();
                String postCategoryText = postCategory.getText().toString();
                String postDescText = postDesc.getText().toString();
                String postImgLinkText = postImgLink.getText().toString();

                // 모달 객체 생성
                PostRVModal postRVModal = new PostRVModal(postID, postNameText, postCategoryText, postDescText, postImgLinkText);

                // firebase DB에 데이터 전달하기 위해 databaseReference 객체에 value event 등록
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // 데이터 값 설정
                        databaseReference.child(postID).setValue(postRVModal);

                        // 게시글 등록 완료 토스트 메시지 출력
                        Toast.makeText(AddPostActivity.this, "게시글 등록 완료", Toast.LENGTH_SHORT).show();

                        // MainActivity 실행(나중에 다른 액티비티로 수정)
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
