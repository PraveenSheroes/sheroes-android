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

package appliedlife.pvtltd.SHEROES.usertagging.suggestions;


import java.util.List;

import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;

/**
 * Class representing the results of a query for suggestions.
 */
public class UserTagSuggestionsResult {

    private final QueryToken mQueryToken;
    private final List<? extends Suggestible> mSuggestions;

    public UserTagSuggestionsResult(QueryToken queryToken, List<? extends Suggestible> suggestions) {
        mQueryToken = queryToken;
        mSuggestions = suggestions;
    }

    /**
     * Get the {@link QueryToken} used to generate the mention suggestions.
     *
     * @return a {@link QueryToken}
     */
    public QueryToken getQueryToken() {
        return mQueryToken;
    }

    /**
     * Get the list of mention suggestions corresponding to the {@link QueryToken}.
     *
     * @return a List of {@link Suggestible} representing mention suggestions
     */
    public List<? extends Suggestible> getSuggestions() {
        return mSuggestions;
    }
}
