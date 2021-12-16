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

import java.util.HashMap;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText editPostName, editPostCategory, editPostDesc, editPostImgLink;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PostRVModal postRVModal;
    private ProgressBar editPBloading;
    // creating a string for our course id.
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        // initializing all our variables on below line.
        Button updatePostBtn = findViewById(R.id.updatePostBtn);
        editPostName = findViewById(R.id.editPostName);
        editPostCategory = findViewById(R.id.editPostCategory);
        editPostDesc = findViewById(R.id.editPostDesc);
        editPostImgLink = findViewById(R.id.editPostImageLink);
        editPBloading = findViewById(R.id.editPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        postRVModal = getIntent().getParcelableExtra("post");
        Button deleteCourseBtn = findViewById(R.id.deletePostBtn);

        if (postRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            editPostName.setText(postRVModal.getPostName());
            editPostCategory.setText(postRVModal.getPostCategory());
            editPostImgLink.setText(postRVModal.getPostImgLink());
            editPostDesc.setText(postRVModal.getPostDesc());
            postId = postRVModal.getPostId();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Posts").child(postId);
        // on below line we are adding click listener for our add course button.
        updatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                editPBloading.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String postName = editPostName.getText().toString();
                String postCategory = editPostCategory.getText().toString();
                String postDesc = editPostDesc.getText().toString();
                String postImg = editPostImgLink.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("postId", postId);
                map.put("postName", postName);
                map.put("postCategory", postCategory);
                map.put("postDesc", postDesc);
                map.put("postImg", postImg);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        editPBloading.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditPostActivity.this, "게시글 수정 완료", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditPostActivity.this, PostListActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditPostActivity.this, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteCourse();
            }
        });

    }

    private void deleteCourse() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditPostActivity.this, PostListActivity.class));
    }
}
