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

import java.util.HashMap;
import java.util.Map;

/*
    커뮤니티에 생성된 글 수정하기 위한 액티비티
    - 게시판 글 수정
    - 게시판 글 삭제
 */

public class EditPostActivity extends AppCompatActivity {
    private TextInputEditText editPostName, editPostDesc;
    private String postId;
    private RadioButton editPostCategoryBtn;
    PostRVModal postRVModal;

    // firebase 객체 사용을 위한 firebase database, database reference 객체
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // 진행상태 나타내기 위한 ProgressBar 객체
    private ProgressBar editPBloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Button updatePostBtn = findViewById(R.id.updatePostBtn);
        Button deletePostBtn = findViewById(R.id.deletePostBtn);
        editPostName = findViewById(R.id.editPostName);
        editPostDesc = findViewById(R.id.editPostDesc);
        editPBloading = findViewById(R.id.editPBLoading);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // 인탠트에서 'post'에 해당하는 값을 읽어옴
        postRVModal = getIntent().getParcelableExtra("post");

        if (postRVModal != null) {
            // Modal에 값이 있다면, Modal에서 값 추출 후 갱신
            editPostName.setText(postRVModal.getPostName());
            editPostDesc.setText(postRVModal.getPostDesc());
            postId = postRVModal.getPostId();

            RadioGroup categoryRadioGroup = findViewById(R.id.editCategoryRadio);

            // 모달에서 카테고리의 텍스트값을 읽어와 해당하는 라디오 버튼에 체크
            switch(postRVModal.getPostCategory()) {
                case "자유":
                    categoryRadioGroup.check(R.id.freeRadioBtn);
                    break;
                case "질문":
                    categoryRadioGroup.check(R.id.questionRadioBtn);
                    break;
                case "운동 팁":
                    categoryRadioGroup.check(R.id.tipRadioBtn);
                    break;
                case "식단 공유":
                    categoryRadioGroup.check(R.id.dietRadioBtn);
                    break;
            }
        }

        // 데이터베이스 객체 초기화 및 child 생성
        databaseReference = firebaseDatabase.getReference("Posts").child(postId);

        // 게시글 수정 버튼에 이벤트 리스너 등록
        updatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup categoryRadioGroup = (RadioGroup) (findViewById(R.id.editCategoryRadio));
                editPostCategoryBtn = (RadioButton) (findViewById(categoryRadioGroup.getCheckedRadioButtonId()));

                editPBloading.setVisibility(View.VISIBLE);
                String postName = editPostName.getText().toString();
                String postCategory = editPostCategoryBtn.getText().toString();
                String postDesc = editPostDesc.getText().toString();

                // 자료 전달하기 위해 map에 데이터 저장
                Map<String, Object> map = new HashMap<>();
                map.put("postId", postId);
                map.put("postName", postName);
                map.put("postCategory", postCategory);
                map.put("postDesc", postDesc);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        editPBloading.setVisibility(View.GONE);

                        // 데이터베이스에 map 저장
                        databaseReference.updateChildren(map);

                        // 게시글 수정 완료 토스트 메시지 출력
                        Toast.makeText(EditPostActivity.this, "게시글 수정 완료", Toast.LENGTH_SHORT).show();

                        // PostListActivity 실행
                        startActivity(new Intent(EditPostActivity.this, PostListActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 게시글 수정을 취소할 경우 이에 해당하는 토스트 메시지 출력
                        Toast.makeText(EditPostActivity.this, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 삭제 버튼에 이벤트 리스너 추가
        deletePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시글 삭제
                deletePost();
            }
        });

    }

    private void deletePost() {
        databaseReference.removeValue();

        // 게시글 삭제 완료 토스트 메시지 출력
        Toast.makeText(this, "게시글 삭제 완료", Toast.LENGTH_SHORT).show();

        // PostListActivity 실행
        startActivity(new Intent(EditPostActivity.this, PostListActivity.class));
    }
}
