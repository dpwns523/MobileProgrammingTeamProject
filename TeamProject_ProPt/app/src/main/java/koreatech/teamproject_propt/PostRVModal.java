package koreatech.teamproject_propt;

import android.os.Parcel;
import android.os.Parcelable;

/*
    게시판 글 생성 시 정보 출력 위한 모달 클래스
 */

public class PostRVModal implements Parcelable {
    private String postId;
    private String postName;
    private String postCategory;
    private String postDesc;

    // 빈 생성자
    public PostRVModal() {

    }

    // Parcel 객체에서 값 읽어와서 초기값 설정
    protected PostRVModal(Parcel in) {
        postId = in.readString();
        postName = in.readString();
        postCategory = in.readString();
        postDesc = in.readString();
    }

    public static final Creator<PostRVModal> CREATOR = new Creator<PostRVModal>() {
        @Override
        public PostRVModal createFromParcel(Parcel in) {
            return new PostRVModal(in);
        }

        @Override
        public PostRVModal[] newArray(int size) {
            return new PostRVModal[size];
        }
    };

    // 각 필드에 대한 getter, setter
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }
    public void setPostName(String courseName) {
        this.postName = courseName;
    }

    public String getPostCategory() { return postCategory; }
    public void setPostCategory(String postCategory) { this.postCategory = postCategory; }

    public String getPostDesc() {
        return postDesc;
    }
    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public PostRVModal(String postId, String postName, String postCategory, String postDesc) {
        this.postId = postId;
        this.postName = postName;
        this.postCategory = postCategory;
        this.postDesc = postDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(postName);
        dest.writeString(postCategory);
        dest.writeString(postDesc);
    }
}
