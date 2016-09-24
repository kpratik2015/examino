
package com.ibm.mobileappbuilder.examino20160923051552.ds;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class QuestioncountDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("subject") public String subject;
    @SerializedName("questionsCount") public Long questionsCount;
    @SerializedName("id") public String id;

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
        dest.writeValue(questionsCount);
        dest.writeString(id);
    }

    public static final Creator<QuestioncountDSItem> CREATOR = new Creator<QuestioncountDSItem>() {
        @Override
        public QuestioncountDSItem createFromParcel(Parcel in) {
            QuestioncountDSItem item = new QuestioncountDSItem();

            item.subject = in.readString();
            item.questionsCount = (Long) in.readValue(null);
            item.id = in.readString();
            return item;
        }

        @Override
        public QuestioncountDSItem[] newArray(int size) {
            return new QuestioncountDSItem[size];
        }
    };

}


