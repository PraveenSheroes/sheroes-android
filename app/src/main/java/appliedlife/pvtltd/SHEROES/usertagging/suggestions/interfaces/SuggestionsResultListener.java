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

package appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces;

import androidx.annotation.NonNull;

import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsResult;


/**
 * Interface used to listen for the results of a mention suggestion query via a {@link IQueryTokenReceiver}.
 */
public interface SuggestionsResultListener {

    /**
     * Callback to return a {@link UserTagSuggestionsResult} so that the suggestions it contains can be added to a
     * {@link SuggestionsAdapter} and rendered accordingly.
     * <p>
     * Note that for any given {@link QueryToken} that the {@link IQueryTokenReceiver} handles, onReceiveSuggestionsResult
     * may be called multiple times. For example, if you can suggest both people and companies, the
     * {@link IQueryTokenReceiver} will receive a single {@link QueryToken}, but it should call onReceiveSuggestionsResult
     * twice (once with people suggestions and once with company suggestions), using a different bucket each time.
     *
     * @param result a {@link UserTagSuggestionsResult} representing the result of the query
     * @param bucket a string representing the type of mention (used for grouping in the {@link SuggestionsAdapter}
     */
    void onReceiveSuggestionsResult(@NonNull final UserTagSuggestionsResult result, @NonNull final String bucket);
}
