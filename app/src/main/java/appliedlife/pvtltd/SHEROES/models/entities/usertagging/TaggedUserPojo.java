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

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.Mentionable;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;

/**
 * Model representing a basic, mentionable city.
 */
public class TaggedUserPojo implements Mentionable {

   private final String mFirstName;
    private final String mLastName;
    private final String mUserProfileURL;

    public TaggedUserPojo(String firstName, String lastName, String pictureURL) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserProfileURL = pictureURL;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getUserProfileURL() {
        return mUserProfileURL;
    }

    // --------------------------------------------------
    // Mentionable/Suggestible Implementation
    // --------------------------------------------------

    @NonNull
    @Override
    public String getTextForDisplayMode(MentionDisplayMode mode) {
        switch (mode) {
            case FULL:
                return getFullName();
            case PARTIAL:
                String[] words = getFullName().split(" ");
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
        return getFullName().hashCode();
    }

    @Override
    public String getSuggestiblePrimaryText() {
        return getFullName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mUserProfileURL);
    }

    public TaggedUserPojo(Parcel in) {
        mFirstName = in.readString();
        mLastName = in.readString();
        mUserProfileURL = in.readString();
    }

    public static final Parcelable.Creator<TaggedUserPojo> CREATOR
            = new Parcelable.Creator<TaggedUserPojo>() {
        public TaggedUserPojo createFromParcel(Parcel in) {
            return new TaggedUserPojo(in);
        }

        public TaggedUserPojo[] newArray(int size) {
            return new TaggedUserPojo[size];
        }
    };

    // --------------------------------------------------
    // PersonLoader Class (loads people from JSON file)
    // --------------------------------------------------

    public static class CityLoader extends MentionsLoader<TaggedUserPojo> {
        private static final String TAG = TaggedUserPojo.CityLoader.class.getSimpleName();

        public CityLoader(Resources res) {
            super(res, R.raw.people);
        }

        @Override
        public TaggedUserPojo[] loadData(JSONArray arr) {
            TaggedUserPojo[] data = new TaggedUserPojo[arr.length()];
            try {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String first = obj.getString("first");
                    String last = obj.getString("last");
                    String url = obj.getString("picture");
                    data[i] = new TaggedUserPojo(first, last, url);
                }
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception while parsing person JSONArray", e);
            }

            return data;
        }

        // Modified to return suggestions based on both first and last name
        @Override
        public List<TaggedUserPojo> getSuggestions(QueryToken queryToken) {
            String[] namePrefixes = queryToken.getKeywords().toLowerCase().split(" ");
            List<TaggedUserPojo> suggestions = new ArrayList<>();
            if (mData != null) {
                for (TaggedUserPojo suggestion : mData) {
                    String firstName = suggestion.getFirstName().toLowerCase();
                    String lastName = suggestion.getLastName().toLowerCase();
                    if (namePrefixes.length == 2) {
                        if (firstName.startsWith(namePrefixes[0]) && lastName.startsWith(namePrefixes[1])) {
                            suggestions.add(suggestion);
                        }
                    } else {
                        if (firstName.startsWith(namePrefixes[0]) || lastName.startsWith(namePrefixes[0])) {
                            suggestions.add(suggestion);
                        }
                    }
                }
            }
            return suggestions;
        }
    }
}
