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

package appliedlife.pvtltd.SHEROES.usertagging.mentions;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class used to configure various options for the {@link MentionSpan}. Instantiate using the
 * {@link MentionSpanConfig.Builder} class.
 */
public class MentionSpanConfig implements Parcelable {

    @SerializedName("NORMAL_TEXT_COLOR")
    @Expose
    @ColorInt
    public final int NORMAL_TEXT_COLOR;
    @SerializedName("NORMAL_TEXT_BACKGROUND_COLOR")
    @Expose
    @ColorInt
    public final int NORMAL_TEXT_BACKGROUND_COLOR;
    @SerializedName("SELECTED_TEXT_COLOR")
    @Expose
    @ColorInt
    public final int SELECTED_TEXT_COLOR;

    @SerializedName("SELECTED_TEXT_BACKGROUND_COLOR")
    @Expose
    @ColorInt
    public final int SELECTED_TEXT_BACKGROUND_COLOR;

    MentionSpanConfig(@ColorInt final int normalTextColor,
                      @ColorInt final int normalTextBackgroundColor,
                      @ColorInt final int selectedTextColor,
                      @ColorInt final int selectedTextBackgroundColor) {
        this.NORMAL_TEXT_COLOR = normalTextColor;
        this.NORMAL_TEXT_BACKGROUND_COLOR = normalTextBackgroundColor;
        this.SELECTED_TEXT_COLOR = selectedTextColor;
        this.SELECTED_TEXT_BACKGROUND_COLOR = selectedTextBackgroundColor;
    }

    public int getNORMAL_TEXT_COLOR() {
        return NORMAL_TEXT_COLOR;
    }

    public int getNORMAL_TEXT_BACKGROUND_COLOR() {
        return NORMAL_TEXT_BACKGROUND_COLOR;
    }

    public int getSELECTED_TEXT_COLOR() {
        return SELECTED_TEXT_COLOR;
    }

    public int getSELECTED_TEXT_BACKGROUND_COLOR() {
        return SELECTED_TEXT_BACKGROUND_COLOR;
    }

    public static class Builder {

        // Default colors
        @ColorInt
        private int normalTextColor = Color.parseColor("#2793e7");
        @ColorInt
        private int normalTextBackgroundColor = Color.TRANSPARENT;
        @ColorInt
        private int selectedTextColor = Color.WHITE;
        @ColorInt
        private int selectedTextBackgroundColor = Color.parseColor("#2793e7");

        public Builder setMentionTextColor(@ColorInt int normalTextColor) {
            if (normalTextColor != -1) {
                this.normalTextColor = normalTextColor;
            }
            return this;
        }

        public Builder setMentionTextBackgroundColor(@ColorInt int normalTextBackgroundColor) {
            if (normalTextBackgroundColor != -1) {
                this.normalTextBackgroundColor = normalTextBackgroundColor;
            }
            return this;
        }

        public Builder setSelectedMentionTextColor(@ColorInt int selectedTextColor) {
            if (selectedTextColor != -1) {
                this.selectedTextColor = selectedTextColor;
            }
            return this;
        }

        public Builder setSelectedMentionTextBackgroundColor(@ColorInt int selectedTextBackgroundColor) {
            if (selectedTextBackgroundColor != -1) {
                this.selectedTextBackgroundColor = selectedTextBackgroundColor;
            }
            return this;
        }

        public MentionSpanConfig build() {
            return new MentionSpanConfig(normalTextColor, normalTextBackgroundColor,
                    selectedTextColor, selectedTextBackgroundColor);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.NORMAL_TEXT_COLOR);
        dest.writeInt(this.NORMAL_TEXT_BACKGROUND_COLOR);
        dest.writeInt(this.SELECTED_TEXT_COLOR);
        dest.writeInt(this.SELECTED_TEXT_BACKGROUND_COLOR);
    }

    protected MentionSpanConfig(Parcel in) {
        this.NORMAL_TEXT_COLOR = in.readInt();
        this.NORMAL_TEXT_BACKGROUND_COLOR = in.readInt();
        this.SELECTED_TEXT_COLOR = in.readInt();
        this.SELECTED_TEXT_BACKGROUND_COLOR = in.readInt();
    }

    public static final Creator<MentionSpanConfig> CREATOR = new Creator<MentionSpanConfig>() {
        @Override
        public MentionSpanConfig createFromParcel(Parcel source) {
            return new MentionSpanConfig(source);
        }

        @Override
        public MentionSpanConfig[] newArray(int size) {
            return new MentionSpanConfig[size];
        }
    };
}
