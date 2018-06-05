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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.models.entities.usertagging.Mention;
import appliedlife.pvtltd.SHEROES.usertagging.ui.MentionsEditText;

/**
 * Class representing a spannable {@link Mentionable} in an {@link EditText}. This class is
 * specifically used by the {@link MentionsEditText}.
 */
public class MentionSpan extends ClickableSpan implements Parcelable {
    @SerializedName("mention")
    @Expose
    public Mention mention;
    @SerializedName("config")
    @Expose
    public MentionSpanConfig config;
    @SerializedName("isSelected")
    @Expose
    public boolean isSelected = false;
    @SerializedName("mDisplayMode")
    @Expose
    public Mention.MentionDisplayMode mDisplayMode = Mention.MentionDisplayMode.FULL;

    public MentionSpan(@NonNull Mention mention) {
        super();
        this.mention = mention;
        this.config = new MentionSpanConfig.Builder().build();
    }

    public MentionSpan(@NonNull Mention mention, @NonNull MentionSpanConfig config) {
        super();
        this.mention = mention;
        this.config = config;
    }

    @Override
    public void onClick(@NonNull final View widget) {
        if (!(widget instanceof MentionsEditText)) {
            return;
        }

        // Get reference to the MentionsEditText
        MentionsEditText editText = (MentionsEditText) widget;
        Editable text = editText.getText();

        if (text == null) {
            return;
        }

        // Set cursor behind span in EditText
        int newCursorPos = text.getSpanEnd(this);
        editText.setSelection(newCursorPos);

        // If we are going to select this span, deselect all others
        boolean isSelected = isSelected();
        if (!isSelected) {
            editText.deselectAllSpans();
        }

        // Toggle whether the view is selected
        setSelected(!isSelected());

        // Update the span (forces it to redraw)
        editText.updateSpan(this);
    }

    @Override
    public void updateDrawState(@NonNull final TextPaint tp) {
        if (isSelected()) {
            tp.setColor(config.SELECTED_TEXT_COLOR);
            tp.bgColor = config.SELECTED_TEXT_BACKGROUND_COLOR;
        } else {
            tp.setColor(config.NORMAL_TEXT_COLOR);
            tp.bgColor = config.NORMAL_TEXT_BACKGROUND_COLOR;
        }
        tp.setUnderlineText(false);
    }

    public Mention getMention() {
        return mention;
    }

    public void setMention(Mention mention) {
        this.mention = mention;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Mentionable.MentionDisplayMode getDisplayMode() {
        return mDisplayMode;
    }

    public void setDisplayMode(Mentionable.MentionDisplayMode mode) {
        mDisplayMode = mode;
    }

    public String getDisplayString() {
        return mention.getTextForDisplayMode(mDisplayMode);
    }

    public MentionSpanConfig getConfig() {
        return config;
    }

    public void setConfig(MentionSpanConfig config) {
        this.config = config;
    }

    public Mention.MentionDisplayMode getmDisplayMode() {
        return mDisplayMode;
    }

    public void setmDisplayMode(Mention.MentionDisplayMode mDisplayMode) {
        this.mDisplayMode = mDisplayMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mention, flags);
        dest.writeParcelable(this.config, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mDisplayMode == null ? -1 : this.mDisplayMode.ordinal());
    }

    protected MentionSpan(Parcel in) {
        this.mention = in.readParcelable(Mention.class.getClassLoader());
        this.config = in.readParcelable(MentionSpanConfig.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
        int tmpMDisplayMode = in.readInt();
        this.mDisplayMode = tmpMDisplayMode == -1 ? null : Mention.MentionDisplayMode.values()[tmpMDisplayMode];
    }

    public static final Creator<MentionSpan> CREATOR = new Creator<MentionSpan>() {
        @Override
        public MentionSpan createFromParcel(Parcel source) {
            return new MentionSpan(source);
        }

        @Override
        public MentionSpan[] newArray(int size) {
            return new MentionSpan[size];
        }
    };
}
