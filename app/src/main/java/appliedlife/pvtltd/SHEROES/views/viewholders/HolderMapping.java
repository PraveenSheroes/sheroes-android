package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTags;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.ProfileItems;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestJobSearch;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileHorList;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
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
    },
    FEED_JOB(R.layout.feed_job_normal_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedJobHolder(view, viewInterface);
        }
    },
    FEED_USER(R.layout.owner_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new UserHolder(view, viewInterface);
        }
    },
    FEED_ARTICLE(R.layout.feed_article_card_normal) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedArticleHolder(view, viewInterface);
        }
    },
    FOOTER(R.layout.home_footer) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FooterViewHolder(view, viewInterface);
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
    }, SEARCH_MODULE(R.layout.search_module_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new SearchModuleHolder(view, viewInterface);
        }
    },
    MEMBER_MODULE(R.layout.community_open_about_owner_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OwnerHolder(view, viewInterface);
        }
    },
    INVITE_MEMBER_MODULE(R.layout.initvite_member_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new InviteMemberHolder(view, viewInterface);
        }
    },
    COMMUNITY_TAG_SEARCH(R.layout.community_tag_search) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new TagSearchHolder(view, viewInterface);
        }
    },
    INVITE_SEARCH_MODULE(R.layout.invitesearch_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new InviteSearchHolder(view, viewInterface);
        }
    }, FEATURE_CARD(R.layout.featured_card_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeatureCardHolder(view, viewInterface);
        }
    }, MY_COMMUNITIES_CARD(R.layout.my_communities_card_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new MyCommunitiesCardHolder(view, viewInterface);
        }
    }, COMMENT(R.layout.all_comments_list_layout) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CommentHolder(view, viewInterface);
        }
    }, REACTION(R.layout.all_reaction_list_layout) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ReactionHolder(view, viewInterface);
        }
    }, ARTICLE_CARD_HOLDER(R.layout.article_card_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ArticleCardHolder(view, viewInterface);
        }
    },
    SELECT_DIALOG(R.layout.list_of_community) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new SelectDilogHolder(view, viewInterface);
        }

    },
    SEARCHTAGS(R.layout.list_of_tags) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new TagsHolder(view, viewInterface);
        }

    }, ON_BOARDING_HOLDER(R.layout.on_boarding_holder_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OnBoardingHolder(view, viewInterface);
        }

    },
    POPULAR_TAG_HOLDER(R.layout.popular_tag_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new PopularTagHolder(view, viewInterface);
        }

    },
    CURRENT_STATUS_HOLDER(R.layout.current_status_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CurrentStatusHolder(view, viewInterface);
        }

    }, COMMUNITY_DETAIL_HEADER(R.layout.community_detail_header_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CommunityCardDetailHeader(view, viewInterface);
        }
    }, NO_COMMUNITIES(R.layout.no_community_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new NoCommunityHolder(view, viewInterface);
        }
    }, OWNER_LIST(R.layout.owner_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OwnerListHolder(view, viewInterface);
        }
    }, REQUEST_LIST(R.layout.request_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new RequestedHolder(view, viewInterface);
        }
    },
    MEMBER_LIST(R.layout.member_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new MemberHolder(view, viewInterface);
        }
    },
    PANDING_REQUEST_LIST(R.layout.panding_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new PandingRequestHolder(view, viewInterface);
        }
    },
    PROFILE_GOOD_AT(R.layout.profile_goodat_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileGoodAtHolder(view, viewInterface);
        }
    },
    PROFILE_EDUCATION(R.layout.professional_education_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileEducationHolder(view, viewInterface);
        }
    },
    PROFILE_WORK_EXPERIENCE(R.layout.professtional_work_exp_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileWorkExperienceHolder(view, viewInterface);
        }
    },
    EDUCATION_LIST(R.layout.education_list_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new EducationListHolder(view, viewInterface);
        }
    },
    PROFILE_HORIZONTAL_LIST(R.layout.profile_hor_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileHorizantalListHolder(view, viewInterface);
        }
    },
    PROFILE_HORIZONTAL_RECYCLER_LIST(R.layout.profile_horizontal_recycler) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileHorizontalViewHolder(view, viewInterface);
        }
    },
    PROFILE_BASIC_DETAILS(R.layout.professional_basic_details_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileProfessionalBasicDetailsHolder(view, viewInterface);
        }
    },

    PROFILE_LOOK_IN_FOR(R.layout.personal_lookingfor_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileLookingForHolder(view, viewInterface);
        }
    },
    PROFILE_ABOUT_ME(R.layout.profile_about_me_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileAboutMeHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_BASIC_DETAILS(R.layout.profile_basicdetails_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfilePersonalBasicDetailsHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_INTERESTING(R.layout.profile_interesting_in_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileIAmInterestingInHolder(view, viewInterface);
        }
    }, CAN_HELP_IN(R.layout.profile_can_help_in) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CanHelpInHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_VISITING_CARD(R.layout.profile_my_visiting_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new Visiting_card_holder1(view, viewInterface);
        }
    }, PROFFESTIONAL_VISITING_CARD(R.layout.profile_my_visiting_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new VisitingCardholder(view, viewInterface);
        }
    },
    GOOD_AT(R.layout.ggodat_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new GoodAtHolder(view, viewInterface);
        }
    },
    JOB_LOCATION_LIST(R.layout.joblocationlist) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new JobLocationHolder(view, viewInterface);
        }
    }, JOB_DETAIL_HOLDER(R.layout.job_detail_page) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new JobDetailHolder(view, viewInterface);
        }
    }, ARTICLE_DETAIL_HOLDER(R.layout.article_detail_page_reaction_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ArticleDetailHolder(view, viewInterface);
        }
    }, ARTICLE_DETAIL_SUGGESTED_HOLDER(R.layout.article_detail_suggested_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ArticleDetailSuggestedHolder(view, viewInterface);
        }
    }, ARTICLE_DETAIL_WITHIN_SUGGESTED_HOLDER(R.layout.article_detail_within_suggested) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ArticleDetailWithInSuggestedHolder(view, viewInterface);
        }
    }, PROFILE_HOLDER(R.layout.invitesearch_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileViewHolder(view, viewInterface);
        }
    },
    BLANK_LIST(R.layout.blank_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new BlankHolder(view, viewInterface);
        }
    }, GET_ALL_DATA_BOARDING_SEARCH(R.layout.get_all_data_boarding_search_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new GetAllDataBoardingSearchHolder(view, viewInterface);
        }
    }, JOB_LOCATION_SEARCH(R.layout.job_location_search) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new JobLocationSearchHolder(view, viewInterface);
        }
    }, INTEREST_SEARCH(R.layout.interest_job_search_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new InterestSearchHolder(view, viewInterface);
        }
    }, JOB_SEARCH(R.layout.interest_job_search_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new JobSearchHolder(view, viewInterface);
        }
    }, BELL_NOTIFICATION(R.layout.bel_notification_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new BellNotificationHolder(view, viewInterface);
        }
    }, WORK_EXPERIENCE_DETAIl_CARD(R.layout.professional_work_experience_viewcard) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new WorkExperienceCardHolder(view, viewInterface);
        }

    }, CHALLENGE_HORIZONTAL_VIEW(R.layout.challenge_horizontal_view) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ChallengeHorizontalView(view, viewInterface);
        }
    }, ONCE_WELCOME_VIEW(R.layout.once_open_circle_support_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OnceWelcomeCardHolder(view, viewInterface);
        }
    }, CHALLENGE_LIST_ITEM_HOLDER(R.layout.challenge_list_item_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ChallengeItemCardHolder(view, viewInterface);
        }

    },
    HELPLINE_CHAT_QUESTION_CARD(R.layout.helpline_question_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HelplineQuestionCardHolder(view, viewInterface);
        }
    },
    HELPLINE_CHAT_ANSWER_CARD(R.layout.helpline_answer_card) {
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

    public static int getOrdinal(BaseResponse item, int totalCount, String callFromType) {
        int returnView = BLANK_LIST.ordinal();
        ;
        if (null != item) {
            if (callFromType.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE)) {
                if (item instanceof FeedDetail) {
                    String feedType = ((FeedDetail) item).getSubType().toUpperCase();
                    switch (feedType) {
                        case AppConstants.FEED_ARTICLE:
                            returnView = FEED_ARTICLE.ordinal();
                            break;
                        case AppConstants.FEED_JOB:
                            returnView = FEED_JOB.ordinal();
                            break;
                        case AppConstants.FEED_COMMUNITY_POST:
                            returnView = FEED_COMMUNITY_POST.ordinal();
                            break;
                        case AppConstants.USER_SUB_TYPE:
                            returnView = INVITE_MEMBER_MODULE.ordinal();
                            break;
                        case AppConstants.CHALLENGE_SUB_TYPE:
                            returnView = CHALLENGE_HORIZONTAL_VIEW.ordinal();
                            break;
                        case AppConstants.ONCE_WELCOME:
                            returnView = ONCE_WELCOME_VIEW.ordinal();
                            break;
                        default:
                    }
                } else if (item instanceof BoardingInterestJobSearch) {
                    returnView = INTEREST_SEARCH.ordinal();
                } else if (item instanceof GetAllDataDocument) {
                    returnView = JOB_LOCATION_SEARCH.ordinal();
                }
            } else if (item instanceof BoardingInterestJobSearch) {
                returnView = JOB_SEARCH.ordinal();
            } else if (callFromType.equalsIgnoreCase(AppConstants.OWNER_SUB_TYPE)) {
                if (item instanceof FeedDetail) {
                    returnView = FEED_USER.ordinal();
                }
            } else if (callFromType.equalsIgnoreCase(AppConstants.COMMUNITY_NAME_SUB_TYPE)) {
                if (item instanceof CommunityPostResponse) {
                    returnView = SELECT_DIALOG.ordinal();
                }
            } else if (callFromType.equalsIgnoreCase(AppConstants.ALL_DATA_SUB_TYPE)) {
                returnView = COMMUNITY_TAG_SEARCH.ordinal();

            } else if (callFromType.equalsIgnoreCase(AppConstants.ALL_SEARCH)) {
                if (item instanceof FeedDetail) {
                    returnView = SEARCH_MODULE.ordinal();
                }
            } else if (callFromType.equalsIgnoreCase(AppConstants.FOR_ALL)) {
                if (item instanceof FeedDetail) {
                    FeedDetail feedDetail = ((FeedDetail) item);
                    String feedType = feedDetail.getSubType().toUpperCase();
                    switch (feedType) {
                        case AppConstants.FEED_ARTICLE:
                            returnView = ARTICLE_CARD_HOLDER.ordinal();
                            break;
                        case AppConstants.FEED_COMMUNITY:
                            boolean isFeatured = feedDetail.isFeatured();
                            if (isFeatured && !feedDetail.isOwner() && !feedDetail.isMember()) {
                                returnView = FEATURE_CARD.ordinal();
                            } else {
                                returnView = MY_COMMUNITIES_CARD.ordinal();
                            }
                            break;
                        case AppConstants.FEED_JOB:
                            returnView = FEED_JOB.ordinal();
                            break;
                        case AppConstants.FEED_COMMUNITY_POST:
                            returnView = FEED_COMMUNITY_POST.ordinal();
                            break;
                        case AppConstants.MY_COMMUNITIES_HEADER:
                            returnView = COMMUNITY_DETAIL_HEADER.ordinal();
                            break;
                        case AppConstants.NO_COMMUNITIES:
                            returnView = NO_COMMUNITIES.ordinal();
                            break;
                        default:
                    }
                } else if (item instanceof CommunityPostResponse) {
                    returnView = SELECT_DIALOG.ordinal();
                } else if (item instanceof DrawerItems) {
                    returnView = DRAWER_ITEMS.ordinal();
                } else if (item instanceof CommentReactionDoc) {
                    CommentReactionDoc commentReactionDoc = ((CommentReactionDoc) item);
                    if (commentReactionDoc.getLikeValue() > AppConstants.NO_REACTION_CONSTANT) {
                        returnView = REACTION.ordinal();
                    } else {
                        returnView = COMMENT.ordinal();
                    }
                } else if (item instanceof HomeSpinnerItem) {
                    returnView = HOME_SPINNER_ITEMS.ordinal();
                } else if (item instanceof ArticleDetailPojo) {
                    returnView = ARTICLE_DETAIL_HOLDER.ordinal();//TODO: Home related changes
                } else if (item instanceof JobDetailPojo) {
                    returnView = JOB_DETAIL_HOLDER.ordinal();
                } else if (item instanceof ProfileHorList) {
                    returnView = PROFILE_HORIZONTAL_LIST.ordinal();
                } else if (item instanceof MyProfileView) {
                    String tagType = ((MyProfileView) item).getType();
                    if (tagType.equalsIgnoreCase(AppConstants.GOOD_AT_SKILL_PROFILE)) {
                        returnView = PROFILE_GOOD_AT.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.ABOUT_ME_PROFILE)) {
                        returnView = PROFILE_ABOUT_ME.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.USER_PROFILE)) {
                        returnView = PROFILE_PERSONAL_BASIC_DETAILS.ordinal();

                    } else if (tagType.equalsIgnoreCase(AppConstants.USER_PROFILE1)) {

                        returnView = PROFILE_BASIC_DETAILS.ordinal();

                    } else if (tagType.equalsIgnoreCase(AppConstants.INTEREST_PROFILE)) {
                        returnView = PROFILE_PERSONAL_INTERESTING.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.CANHELP_IN)) {
                        returnView = CAN_HELP_IN.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.OPPORTUNITY_PROFILE)) {
                        returnView = PROFILE_LOOK_IN_FOR.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.EDUCATION_PROFILE)) {
                        returnView = PROFILE_EDUCATION.ordinal();
                    } else if (tagType.equalsIgnoreCase(AppConstants.EXPERIENCE_PROFILE)) {
                        returnView = PROFILE_WORK_EXPERIENCE.ordinal();

                    } else if (tagType.equalsIgnoreCase(AppConstants.CLIENTSIDE)) {
                        returnView = PROFILE_HORIZONTAL_RECYCLER_LIST.ordinal();

                    } else if (tagType.equalsIgnoreCase(AppConstants.USER_VISITING_CARD)) {
                        returnView = PROFFESTIONAL_VISITING_CARD.ordinal();

                    } else if (tagType.equalsIgnoreCase(AppConstants.USER_VISITING_CARD1)) {
                        returnView = PROFILE_PERSONAL_VISITING_CARD.ordinal();
                    }
                } else if (item instanceof EducationEntity) {
                    returnView = EDUCATION_LIST.ordinal();
                } else if (item instanceof GoodAt) {
                    returnView = GOOD_AT.ordinal();
                } else if (item instanceof ListOfInviteSearch) {
                    returnView = INVITE_SEARCH_MODULE.ordinal();
                } else if (item instanceof Member) {
                    returnView = MEMBER_MODULE.ordinal();
                } else if (item instanceof OnBoardingData) {
                    returnView = ON_BOARDING_HOLDER.ordinal();
                } else if (item instanceof PopularTag) {
                    returnView = POPULAR_TAG_HOLDER.ordinal();
                } else if (item instanceof LabelValue) {
                    returnView = CURRENT_STATUS_HOLDER.ordinal();
                } else if (item instanceof GetAllDataDocument) {
                    returnView = GET_ALL_DATA_BOARDING_SEARCH.ordinal();
                } else if (item instanceof RequestedList) {
                    returnView = REQUEST_LIST.ordinal();
                } else if (item instanceof OwnerList) {
                    returnView = OWNER_LIST.ordinal();
                } else if (item instanceof MembersList) {
                    returnView = MEMBER_LIST.ordinal();
                } else if (item instanceof PandingMember) {
                    returnView = PANDING_REQUEST_LIST.ordinal();
                } else if (item instanceof CommunityTags) {
                    returnView = SEARCHTAGS.ordinal();
                } else if (item instanceof JobLocationList) {
                    returnView = JOB_LOCATION_LIST.ordinal();
                } else if (item instanceof ProfileItems) {
                    returnView = PROFILE_HOLDER.ordinal();
                } else if (item instanceof BellNotificationResponse) {
                    returnView = BELL_NOTIFICATION.ordinal();
                } else if (item instanceof ExprienceEntity) {
                    returnView = WORK_EXPERIENCE_DETAIl_CARD.ordinal();
                } else if (item instanceof ChallengeDataItem) {
                    returnView = CHALLENGE_LIST_ITEM_HOLDER.ordinal();
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
