package koreatech.teamproject_propt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
    recyclerView에 등록할 adapter 클래스
 */

public class PostRVAdapter extends RecyclerView.Adapter<PostRVAdapter.ViewHolder> {
    private ArrayList<PostRVModal> postRVModalArrayList;
    private Context context;
    private PostClickInterface postClickInterface;
    int lastPos = -1;

    // 생성자
    public PostRVAdapter(ArrayList<PostRVModal> postRVModalArrayList, Context context, PostClickInterface postClickInterface) {
        this.postRVModalArrayList = postRVModalArrayList;
        this.context = context;
        this.postClickInterface = postClickInterface;
    }

    @NonNull
    @Override
    public PostRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // layout 파일 inflate
        View view = LayoutInflater.from(context).inflate(R.layout.post_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRVAdapter.ViewHolder holder, int position) {
        // recycler view item에 필요한 값
        PostRVModal postRVModal = postRVModalArrayList.get(holder.getAdapterPosition());
        holder.postNameTV.setText(postRVModal.getPostName());
        holder.postCategoryTV.setText(postRVModal.getPostCategory());

        if (holder.postImgIV == null) {
            holder.postImgIV.setImageResource(R.drawable.community);
        } else{
            Picasso.get().load(postRVModal.getPostImgLink()).into(holder.postImgIV);
        }


        // recycler view item에 애니메이션 추가
        setAnimation(holder.itemView, holder.getAdapterPosition());
        holder.postImgIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postClickInterface.onPostClick(holder.getAdapterPosition());
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    // 게시글 개수 구하는 메소드
    @Override
    public int getItemCount() {
        return postRVModalArrayList.size();
    }

    // ViewHolder 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView postImgIV;
        private TextView postNameTV, postCategoryTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImgIV = itemView.findViewById(R.id.postImgIV);
            postNameTV = itemView.findViewById(R.id.postNameTV);
            postCategoryTV = itemView.findViewById(R.id.postCategoryTV);
        }
    }

    // post에 onclick 등록하기 위한 인터페이스
    public interface PostClickInterface {
        void onPostClick(int position);
    }
}
