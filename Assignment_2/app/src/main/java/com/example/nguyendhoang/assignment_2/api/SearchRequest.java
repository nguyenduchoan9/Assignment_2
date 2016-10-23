package com.example.nguyendhoang.assignment_2.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nguyendhoang.assignment_2.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class SearchRequest implements Parcelable {
    private int page = 0;

    private String beginDate;
    private String endDate;
    private String keySearch;
    private String order = "newest";
    private boolean hasArts = false;
    private boolean hasFashionStyle = false;
    private boolean hasSports = false;

    public SearchRequest() {

    }

    public void resetPage() {
        this.page = 0;
    }

    public void nextPage() {
        this.page += 1;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public String getOrder() {
        return order;
    }

    public boolean isHasArts() {
        return hasArts;
    }

    public boolean isHasFashionStyle() {
        return hasFashionStyle;
    }

    public boolean isHasSports() {
        return hasSports;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setHasArts(boolean hasArts) {
        this.hasArts = hasArts;
    }

    public void setHasFashionStyle(boolean hasFashionStyle) {
        this.hasFashionStyle = hasFashionStyle;
    }

    public void setHasSports(boolean hasSports) {
        this.hasSports = hasSports;
    }

    protected SearchRequest(Parcel in) {
        beginDate = in.readString();
        endDate = in.readString();
        keySearch = in.readString();
        order = in.readString();
        hasArts = in.readByte() != 0;
        hasFashionStyle = in.readByte() != 0;
        hasSports = in.readByte() != 0;
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel in) {
            return new SearchRequest(in);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };

    private String getNewDesk() {
        if (!hasSports && !hasArts && !hasFashionStyle) return null;

        String desk = "";
        if (hasArts) desk += "\"Arts\"";
        if (hasSports) desk += " \"Sports\"";
        if (hasFashionStyle) desk += " \"Fashion & Style\"";

        return desk;
    }

    public Map<String, String> toQuery() {
        Map<String, String> queryUrl = new HashMap<>();
        if (keySearch != null) queryUrl.put("q", keySearch);
        if (beginDate != null) queryUrl.put("begin_date", StringUtil.convertToApiDate(beginDate));
        if (endDate != null) queryUrl.put("end_date", endDate);
        if (order != null) queryUrl.put("sort", order.toLowerCase());
        if (getNewDesk() != null) queryUrl.put("fq", "news_desk:(" + getNewDesk() + ")");
        queryUrl.put("page", String.valueOf(page));

        return queryUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(beginDate);
        dest.writeString(endDate);
        dest.writeString(keySearch);
        dest.writeString(order);
        dest.writeByte((byte) (hasArts ? 1 : 0));
        dest.writeByte((byte) (hasFashionStyle ? 1 : 0));
        dest.writeByte((byte) (hasSports ? 1 : 0));
    }
}
