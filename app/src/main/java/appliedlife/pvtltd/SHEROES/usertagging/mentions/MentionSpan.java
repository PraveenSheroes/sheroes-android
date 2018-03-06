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

import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.ui.MentionsEditText;

/**
 * Class representing a spannable {@link Mentionable} in an {@link EditText}. This class is
 * specifically used by the {@link MentionsEditText}.
 */
public class MentionSpan extends ClickableSpan implements Parcelable {

    private final Mentionable mention;
    private MentionSpanConfig config;
    private long startIndex;
    private long endIndex;
    private String userMentionUrl;
    private boolean isSelected = false;
    private Mentionable.MentionDisplayMode mDisplayMode = Mentionable.MentionDisplayMode.FULL;

    public MentionSpan(@NonNull Mentionable mention) {
        super();
        this.mention = mention;
        this.config = new MentionSpanConfig.Builder().build();
    }

    public MentionSpan(@NonNull Mentionable mention, @NonNull MentionSpanConfig config) {
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

    public Mentionable getMention() {
        return mention;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull final Parcel dest, int flags) {
        dest.writeInt(config.NORMAL_TEXT_COLOR);
        dest.writeInt(config.NORMAL_TEXT_BACKGROUND_COLOR);
        dest.writeInt(config.SELECTED_TEXT_COLOR);
        dest.writeInt(config.SELECTED_TEXT_BACKGROUND_COLOR);
        dest.writeLong(this.startIndex);
        dest.writeLong(this.endIndex);
        dest.writeString(this.userMentionUrl);
        dest.writeInt(getDisplayMode().ordinal());
        dest.writeInt(isSelected() ? 1 : 0);
        dest.writeParcelable(getMention(), flags);
    }

    public MentionSpan(Parcel in) {
        int normalTextColor = in.readInt();
        int normalTextBackgroundColor = in.readInt();
        int selectedTextColor = in.readInt();
        int selectedTextBackgroundColor = in.readInt();
        config = new MentionSpanConfig(normalTextColor, normalTextBackgroundColor,
                                       selectedTextColor, selectedTextBackgroundColor);

        mDisplayMode = Mentionable.MentionDisplayMode.values()[in.readInt()];
        setSelected((in.readInt() == 1));
        startIndex = in.readInt();
        endIndex = in.readInt();
        userMentionUrl = in.readString();
        mention = in.readParcelable(Mentionable.class.getClassLoader());
    }

    public static final Creator<MentionSpan> CREATOR
            = new Creator<MentionSpan>() {
        public MentionSpan createFromParcel(Parcel in) {
            return new MentionSpan(in);
        }

        public MentionSpan[] newArray(int size) {
            return new MentionSpan[size];
        }
    };

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public String getUserMentionUrl() {
        return userMentionUrl;
    }

    public void setUserMentionUrl(String userMentionUrl) {
        this.userMentionUrl = userMentionUrl;
    }
}
