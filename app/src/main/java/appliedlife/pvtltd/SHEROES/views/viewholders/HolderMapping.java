package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.home.ArticleCategory;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMember;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

public enum HolderMapping {

    FEED_COMMUNITY_POST(R.layout.feed_comunity_user_post_normal) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedCommunityPostHolder(view, viewInterface);
        }
    }, FEED_ARTICLE(R.layout.feed_article_card_normal) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedArticleHolder(view, viewInterface);
        }
    }, DRAWER_ITEMS(R.layout.drawer_item_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new DrawerViewHolder(view, viewInterface);
        }
    }, HOME_SPINNER_ITEMS(R.layout.home_spinner_selecter_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HomeSpinnerSelectorHolder(view, viewInterface);
        }
    },
    ON_BOARDING_COMMUNITIES_CARD(R.layout.on_boarding_communities_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OnBoardingCommunitiesHolder(view, viewInterface);
        }
    }, ARTICLE_CARD_HOLDER(R.layout.article_card_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ArticleCardHolder(view, viewInterface);
        }
    },
    NO_COMMUNITIES(R.layout.no_community_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new NoCommunityHolder(view, viewInterface);
        }
    },

    FEED_PROGRESS_BAR_HOLDER(R.layout.feed_progress_bar_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedProgressBarHolder(view, viewInterface);
        }
    },
    BLANK_LIST(R.layout.blank_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new BlankHolder(view, viewInterface);
        }
    },
    GET_ALL_DATA_BOARDING_SEARCH(R.layout.get_all_data_boarding_search_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new GetAllDataBoardingSearchHolder(view, viewInterface);
        }
    },
    BELL_NOTIFICATION(R.layout.bell_notification_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new BellNotificationHolder(view, viewInterface);
        }
    }, APP_INTRO_VIEW(R.layout.app_intro_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new AppIntroCardHolder(view, viewInterface);
        }
    }, ONCE_WELCOME_VIEW(R.layout.once_open_circle_support_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OnceWelcomeCardHolder(view, viewInterface);
        }
    }, HELPLINE_CHAT_QUESTION_CARD(R.layout.helpline_question_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HelplineQuestionCardHolder(view, viewInterface);
        }
    }, HELPLINE_CHAT_ANSWER_CARD(R.layout.helpline_answer_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HelplineAnswerCardHolder(view, viewInterface);
        }
    }, ICC_MEMBER_CARD(R.layout.icc_member_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ICCMemberViewHolder(view, viewInterface);
        }
    }, FAQS_CARD(R.layout.faqs_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FAQViewHolder(view, viewInterface);
        }
    }, HEADER_VIEW_HOLDER(R.layout.header_view_layout) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HomeHeaderViewHolder(view, viewInterface);
        }
    },
    FEED_CHALLENGE(R.layout.challenge_feed_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ChallengeFeedHolder(view, viewInterface);
        }
    };
    public Object object;
    public int layout;

    HolderMapping(int layoutId) {
        this.layout = layoutId;
    }

    public abstract BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface)
            throws Exception;

    public View getView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(getLayout(), parent, false);
    }

    public int getLayout() {
        return layout;
    }

    public static int getOrdinal(BaseResponse item, long userId, String callFromType) {
        int returnView = BLANK_LIST.ordinal();
        if (null != item) {
            if (callFromType.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE)) {
                if (item instanceof FeedDetail) {
                    FeedDetail feedDetail = ((FeedDetail) item);
                    if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
                        String feedType = feedDetail.getSubType().toUpperCase();
                        switch (feedType) {
                            case AppConstants.FEED_ARTICLE:
                                returnView = FEED_ARTICLE.ordinal();
                                break;

                            case AppConstants.FEED_COMMUNITY:
                                returnView = ON_BOARDING_COMMUNITIES_CARD.ordinal();
                                break;
                            case AppConstants.FEED_COMMUNITY_POST:
                                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                                if (feedDetail.isSpamPost()) {
                                    if (userId == feedDetail.getAuthorId() || userPostSolrObj.isCommunityOwner()) {
                                        returnView = FEED_COMMUNITY_POST.ordinal();
                                    } else {
                                        returnView = BLANK_LIST.ordinal();
                                    }
                                } else {
                                    returnView = FEED_COMMUNITY_POST.ordinal();
                                }
                                break;
                            case AppConstants.CHALLENGE_SUB_TYPE_NEW:
                                returnView = FEED_CHALLENGE.ordinal();
                                break;
                            case AppConstants.APP_INTRO_SUB_TYPE:
                                returnView = APP_INTRO_VIEW.ordinal();
                                break;
                            case AppConstants.ONCE_WELCOME:
                                returnView = ONCE_WELCOME_VIEW.ordinal();
                                break;
                            case AppConstants.FEED_PROGRESS_BAR:
                                returnView = FEED_PROGRESS_BAR_HOLDER.ordinal();
                                break;
                            case AppConstants.HOME_FEED_HEADER:
                                returnView = HEADER_VIEW_HOLDER.ordinal();
                                break;
                            default:
                        }
                    }
                }
            } else if (callFromType.equalsIgnoreCase(AppConstants.FOR_ALL)) {
                if (item instanceof FeedDetail) {
                    FeedDetail feedDetail = ((FeedDetail) item);
                    if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
                        String feedType = feedDetail.getSubType().toUpperCase();
                        switch (feedType) {
                            case AppConstants.FEED_ARTICLE:
                                returnView = ARTICLE_CARD_HOLDER.ordinal();
                                break;
                            case AppConstants.FEED_COMMUNITY_POST:
                                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                                if (feedDetail.isSpamPost()) {
                                    if (userId == feedDetail.getAuthorId() || userPostSolrObj.isCommunityOwner()) {
                                        returnView = FEED_COMMUNITY_POST.ordinal();
                                    } else {
                                        returnView = BLANK_LIST.ordinal();
                                    }
                                } else {
                                    returnView = FEED_COMMUNITY_POST.ordinal();
                                }
                                break;
                            case AppConstants.NO_COMMUNITIES:
                                returnView = NO_COMMUNITIES.ordinal();
                                break;
                            case AppConstants.FEED_PROGRESS_BAR:
                                returnView = FEED_PROGRESS_BAR_HOLDER.ordinal();
                                break;
                            default:
                        }
                    }
                } else if (item instanceof NavMenuItem) {
                    returnView = DRAWER_ITEMS.ordinal();
                } else if (item instanceof ArticleCategory) {
                    returnView = HOME_SPINNER_ITEMS.ordinal();
                } else if (item instanceof GetAllDataDocument) {
                    returnView = GET_ALL_DATA_BOARDING_SEARCH.ordinal();
                } else if (item instanceof BellNotificationResponse) {
                    returnView = BELL_NOTIFICATION.ordinal();
                } else if (item instanceof HelplineChatDoc) {
                    if (((HelplineChatDoc) item).getSubType().equalsIgnoreCase(AppConstants.HELPLINE_SUB_TYPE_QUESTION)) {
                        HelplineChatDoc helplineChatDoc = (HelplineChatDoc) item;
                        if (StringUtil.isNotNullOrEmptyString(helplineChatDoc.getSearchText())) {
                            returnView = HELPLINE_CHAT_QUESTION_CARD.ordinal();
                        }
                    } else {
                        returnView = HELPLINE_CHAT_ANSWER_CARD.ordinal();
                    }
                } else if (item instanceof ICCMember) {
                    returnView = ICC_MEMBER_CARD.ordinal();
                } else if (item instanceof FAQS) {
                    returnView = FAQS_CARD.ordinal();
                }
            }
        }
        return returnView;
    }

}
