
package com.ibm.mobileappbuilder.examino20160923051552.ds;
import android.graphics.Bitmap;
import android.net.Uri;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class QuestionsDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("subject") public String subject;
    @SerializedName("type") public String type;
    @SerializedName("questionType") public String questionType;
    @SerializedName("question") public String question;
    @SerializedName("figure") public String figure;
    @SerializedName("id") public String id;
    @SerializedName("figureUri") public transient Uri figureUri;

    @Override
    public String getIdentifiableId() {
      return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subject);
        dest.writeString(type);
        dest.writeString(questionType);
        dest.writeString(question);
        dest.writeString(figure);
        dest.writeString(id);
    }

    public static final Creator<QuestionsDSItem> CREATOR = new Creator<QuestionsDSItem>() {
        @Override
        public QuestionsDSItem createFromParcel(Parcel in) {
            QuestionsDSItem item = new QuestionsDSItem();

            item.subject = in.readString();
            item.type = in.readString();
            item.questionType = in.readString();
            item.question = in.readString();
            item.figure = in.readString();
            item.id = in.readString();
            return item;
        }

        @Override
        public QuestionsDSItem[] newArray(int size) {
            return new QuestionsDSItem[size];
        }
    };

}


