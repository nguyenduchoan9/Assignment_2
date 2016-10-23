package com.example.nguyendhoang.assignment_2.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.util.StringUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class Article extends BaseObservable implements Parcelable   {

    @Bindable @SerializedName("snippet") private String snippet;

    @Bindable @SerializedName("web_url") private String webUrl;

    @Bindable @SerializedName("lead_paragraph") private String leadParagraph;

    @Bindable @SerializedName("source") private String source;

    public String getSource() {
        return source;
    }

    @Bindable @SerializedName("print_page") private int printPage;

    @Bindable @SerializedName("_id") private String id;

    @Bindable @SerializedName("pub_date") private String publicDate;

    @Bindable @SerializedName("multimedia") private List<Media> multiMedias;



    public boolean isHaveImage() {
        boolean rs = multiMedias != null && !multiMedias.isEmpty() ? true : false;
        return rs;
    }

    protected Article(Parcel in) {
        snippet = in.readString();
        webUrl = in.readString();
        leadParagraph = in.readString();
        printPage = in.readInt();
        id = in.readString();
        publicDate = in.readString();
        multiMedias = in.createTypedArrayList(Media.CREATOR);
        source= in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getSnippet() {
        return snippet;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public int getPrintPage() {
        return printPage;
    }

    public String getId() {
        return id;
    }

    public String getPublicDate() {
        return StringUtil.convertToDateString(publicDate);
    }

    public List<Media> getMultiMedias() {
        return multiMedias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(snippet);
        dest.writeString(webUrl);
        dest.writeString(leadParagraph);
        dest.writeInt(printPage);
        dest.writeString(id);
        dest.writeString(publicDate);
        dest.writeTypedList(multiMedias);
        dest.writeString(source);
    }

    public static class Media implements Parcelable {
        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private int height;

        @SerializedName("width")
        private int width;

        @SerializedName("type")
        private String type;

        protected Media(Parcel in) {
            url = in.readString();
            height = in.readInt();
            width = in.readInt();
            type = in.readString();
        }

        public static final Creator<Media> CREATOR = new Creator<Media>() {
            @Override
            public Media createFromParcel(Parcel in) {
                return new Media(in);
            }

            @Override
            public Media[] newArray(int size) {
                return new Media[size];
            }
        };

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {

            return Constant.BASE_URL_IMAGE + url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeInt(height);
            dest.writeInt(width);
            dest.writeString(type);
        }
    }

}
