package koreatech.teamproject_propt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/*
    MyAdapter.java (어댑터임)
    기능설명
    : main_exercise_way에서 recyclerview와 cardview를 adapter를 통해서 연결함
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {

    // 변수 선언
    String data1[],data2[];
    int images[];
    Context context;


    //생성자
    public MyAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    //cardview에 있는 textview,imageview 값을 찾아서 지정
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;
        ImageView imageView;
        ConstraintLayout cl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.name_tv);
            textView2 = itemView.findViewById(R.id.content_tv);
            imageView = itemView.findViewById(R.id.imageIcon);
            cl = itemView.findViewById(R.id.con1);
        }
    }

    //Oncreate처럼 cardview를 recyclerview에 생성
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_cardview,parent,false);
        return new MyViewHolder(view);
    }

    //holder를 통하여 textview,imageview의 값을 재설정하게 됨
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView1.setText(data1[position]);
        holder.textView2.setText(data2[position]);
        holder.imageView.setImageResource(images[position]);
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SubExerciseWayActivity.class);
                i.putExtra("title",data1[holder.getAdapterPosition()]);
                i.putExtra("image",images[holder.getAdapterPosition()]);
                i.putExtra("description",data2[holder.getAdapterPosition()]);
                context.startActivity(i);
            }
        });
    }
    //아이템 개수가 몇 개 있는지 반환
    @Override
    public int getItemCount() {
        return images.length;
    }
}
