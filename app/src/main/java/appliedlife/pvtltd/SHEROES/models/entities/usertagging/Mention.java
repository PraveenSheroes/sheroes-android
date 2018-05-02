/*
* Copyright 2015 LinkedIn Corp. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*/

package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.usertagging.mentions.Mentionable;

public class Mention implements Mentionable {

    @SerializedName("user_id")
    @Expose
    public int userId;

    @SerializedName("user_profile_url")
    @Expose
    public String userProfileDeepLinkUrl;

    @SerializedName("user_profile_image_url")
    @Expose
    public String authorImageUrl;

    @SerializedName("end")
    @Expose
    public int endIndex;

    @SerializedName("start")
    @Expose
    public int startIndex;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("user_type_id")
    @Expose
    public long userType;


    public Mention(int userId, String name, String userProfileDeepLinkUrl, String authorImageUrl, long userType) {
        this.userId = userId;
        this.name = name;
        this.userProfileDeepLinkUrl = userProfileDeepLinkUrl;
        this.authorImageUrl = authorImageUrl;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserProfileDeepLinkUrl() {
        return userProfileDeepLinkUrl;
    }

    public void setUserProfileDeepLinkUrl(String userProfileDeepLinkUrl) {
        this.userProfileDeepLinkUrl = userProfileDeepLinkUrl;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }


    // --------------------------------------------------
    // Mentionable/Suggestible Implementation
    // --------------------------------------------------

    @NonNull
    @Override
    public String getTextForDisplayMode(MentionDisplayMode mode) {
        switch (mode) {
            case FULL:
                return getName();
            case PARTIAL:
                String[] words = getName().split(" ");
                return (words.length > 1) ? words[0] : "";
            case NONE:
            default:
                return "";
        }
    }

    @Override
    public MentionDeleteStyle getDeleteStyle() {
        // People support partial deletion
        // i.e. "John Doe" -> DEL -> "John" -> DEL -> ""
        return MentionDeleteStyle.PARTIAL_NAME_DELETE;
    }

    @Override
    public int getSuggestibleId() {
        return getName().hashCode();
    }

    @Override
    public String getSuggestiblePrimaryText() {
        return getName();
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserType() {
        return userType;
    }

    public void setUserType(long userType) {
        this.userType = userType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userProfileDeepLinkUrl);
        dest.writeString(this.authorImageUrl);
        dest.writeInt(this.endIndex);
        dest.writeInt(this.startIndex);
        dest.writeString(this.name);
        dest.writeLong(this.userType);
    }

    protected Mention(Parcel in) {
        this.userId = in.readInt();
        this.userProfileDeepLinkUrl = in.readString();
        this.authorImageUrl = in.readString();
        this.endIndex = in.readInt();
        this.startIndex = in.readInt();
        this.name = in.readString();
        this.userType = in.readLong();
    }

    public static final Creator<Mention> CREATOR = new Creator<Mention>() {
        @Override
        public Mention createFromParcel(Parcel source) {
            return new Mention(source);
        }

        @Override
        public Mention[] newArray(int size) {
            return new Mention[size];
        }
    };
}
