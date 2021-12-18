package koreatech.teamproject_propt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
    게시글 목록 출력하는 액티비티
    - 게시글 목록 출력
 */

public class PostListActivity extends AppCompatActivity implements PostRVAdapter.PostClickInterface {
    private Context mContext = this;
    private static final int ACTIVITY_NUM = 2;
    private Button addPostBtn;



    // firebase의 database, auth, reference 사용
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    // list와 recycler view 사용
    private RecyclerView postRV;
    private ArrayList<PostRVModal> postRVModalArrayList;
    private PostRVAdapter postRVAdapter;
    private RelativeLayout homeRL;

    // floating action 버튼, progressBar 사용
    private FloatingActionButton addPostFAB;
    private ProgressBar PBloadingRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        // initializing all our variables.
        postRV = findViewById(R.id.postRV);
        homeRL = findViewById(R.id.bottomSheetRL);
        PBloadingRV = findViewById(R.id.PBLoadingRV);
        addPostFAB = findViewById(R.id.addPostFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Posts");
        // on below line adding a click listener for our floating action button.
        addPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent intent = new Intent(PostListActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
        // on below line initializing our adapter class.
        postRVAdapter = new PostRVAdapter(postRVModalArrayList, this, this::onPostClick);
        // setting layout malinger to recycler view on below line.
        postRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        postRV.setAdapter(postRVAdapter);
        // on below line calling a method to fetch courses from database.
        getPosts();
    }

    private void getPosts() {
        // on below line clearing our list.
        postRVModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
//                PBloadingRV.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                postRVModalArrayList.add(snapshot.getValue(PostRVModal.class));
                // notifying our adapter that data has changed.
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                PBloadingRV.setVisibility(View.GONE);
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                postRVAdapter.notifyDataSetChanged();
                PBloadingRV.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                postRVAdapter.notifyDataSetChanged();
                PBloadingRV.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPostClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(postRVModalArrayList.get(position));
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        // adding a click listener for option selected on below line.
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.idLogOut:
//                // displaying a toast message on user logged out inside on click.
//                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
//                // on below line we are signing out our user.
//                mAuth.signOut();
//                // on below line we are opening our login activity.
//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(i);
//                this.finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // on below line we are inflating our menu
//        // file for displaying our menu options.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    private void displayBottomSheet(PostRVModal modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView postNameTV = layout.findViewById(R.id.bottomSheetPostName);
        TextView postDescTV = layout.findViewById(R.id.bottomSheetPostDesc);

//        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);

        TextView postCategoryTV = layout.findViewById(R.id.bottomSheetPostCategory);
        ImageView postImgIV = layout.findViewById(R.id.bottomSheetPostImg);
        // on below line we are setting data to different views on below line.
        postNameTV.setText(modal.getPostName());
        postDescTV.setText(modal.getPostDesc());

//        suitedForTV.setText("Suited for " + modal.getBestSuitedFor());

        postCategoryTV.setText(modal.getPostCategory());
        Picasso.get().load(modal.getPostImgLink()).into(postImgIV);
        Button viewBtn = layout.findViewById(R.id.editPostBtn);
        Button editBtn = layout.findViewById(R.id.detailPostBtn);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent intent = new Intent(PostListActivity.this, EditPostActivity.class);
                // on below line we are passing our course modal
                intent.putExtra("post", modal);
                startActivity(intent);
            }
        });
        // adding click listener for our view button on below line.
//        viewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on below line we are navigating to browser
//                // for displaying course details from its url
//                Intent intent = new Intent(PostListActivity.this, EditPostActivity.class);
//                intent.putExtra("post", modal);
//                startActivity(intent);
//            }
//        });
    }
}
