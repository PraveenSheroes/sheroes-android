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
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.usertagging.mentions.Mentionable;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Model representing a basic, mentionable city.
 */
public class TaggedUserPojo implements Mentionable {

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

    @SerializedName("user_type")
    @Expose
    public long userType;


    public TaggedUserPojo(int userId, String name, String userProfileDeepLinkUrl, String authorImageUrl,long userType) {
        this.userId = userId;
        this.name = name;
        this.userProfileDeepLinkUrl = userProfileDeepLinkUrl;
        this.authorImageUrl = authorImageUrl;
        this.userType=userType;
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

    // --------------------------------------------------
    // PersonLoader Class (loads people from JSON file)
    // --------------------------------------------------

    public static class TaggUserDataLoader extends MentionsLoader<TaggedUserPojo> {
        private static final String TAG = TaggUserDataLoader.class.getSimpleName();

        public TaggUserDataLoader(List<TaggedUserPojo> taggedUserPojoList) {
            super(taggedUserPojoList);
        }

        @Override
        public List<TaggedUserPojo> loadData(List<TaggedUserPojo> taggedUserPojoList) {
            return taggedUserPojoList;
        }

        // Modified to return suggestions based on both first and last name
        @Override
        public List<TaggedUserPojo> getSuggestions(QueryToken queryToken) {
            String[] namePrefixes = queryToken.getKeywords().toLowerCase().split(" ");
            List<TaggedUserPojo> suggestions = new ArrayList<>();
            if (StringUtil.isNotEmptyCollection(mData)) {
                for (TaggedUserPojo suggestion : mData) {
                    String firstName = suggestion.getName().toLowerCase();
                    if (namePrefixes.length == 2) {
                        if (firstName.startsWith(namePrefixes[0])) {
                            suggestions.add(suggestion);
                        }
                    } else {
                        if (firstName.startsWith(namePrefixes[0])) {
                            suggestions.add(suggestion);
                        }
                    }
                }
            }
            return suggestions;
        }
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

    protected TaggedUserPojo(Parcel in) {
        this.userId = in.readInt();
        this.userProfileDeepLinkUrl = in.readString();
        this.authorImageUrl = in.readString();
        this.endIndex = in.readInt();
        this.startIndex = in.readInt();
        this.name = in.readString();
        this.userType = in.readLong();
    }

    public static final Creator<TaggedUserPojo> CREATOR = new Creator<TaggedUserPojo>() {
        @Override
        public TaggedUserPojo createFromParcel(Parcel source) {
            return new TaggedUserPojo(source);
        }

        @Override
        public TaggedUserPojo[] newArray(int size) {
            return new TaggedUserPojo[size];
        }
    };
}
