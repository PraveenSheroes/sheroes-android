package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTags;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.ProfileItems;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileHorList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

public enum HolderMapping {

    FEED_COMMUNITY_POST(R.layout.feed_comunity_user_post_normal) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedCommunityPostHolder(view, viewInterface);
        }
    },
    FEED_COMMUNITY(R.layout.feed_community_normal_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeedCommunityHolder(view, viewInterface);
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
    }, HOME_SPINNER_FOOTER(R.layout.spinner_footer) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new HomeSpinnerFooterHolder(view, viewInterface);
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
    SELECTDILOG(R.layout.list_of_community) {
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

    }, COMMUNITY_DETAIL_HEADER(R.layout.community_detail_header_holder) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CommunityCardDetailHeader(view, viewInterface);
        }
    }, SUGGESTED_CARD_HOLDER(R.layout.horizontal_suggestion_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CommunitySuggestedHolder(view, viewInterface);
        }
    }, OWNERLIST(R.layout.owner_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new OwnerListHolder(view, viewInterface);
        }
    }, REQUESTLIST(R.layout.request_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new RequestedHolder(view, viewInterface);
        }
    },
    MEMBERLIST(R.layout.member_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new MemberHolder(view, viewInterface);
        }
    },
    PROFILE_GOODAT(R.layout.profile_goodat_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileGoodAtHolder(view, viewInterface);
        }
    },
    PROFIL_EEDUCATION(R.layout.professional_education_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileEducationHolder(view, viewInterface);
        }
    },
    PROFILE_WORK_EXPERIENCE(R.layout.professional_education_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileWorkExperienceHolder(view, viewInterface);
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
    PROFILE_BASIC_DETAILS(R.layout.profile_basicdetails_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileProfessionalBasicDetailsHolder(view, viewInterface);
        }
    },
    PROFILE_OTHER(R.layout.professional_other_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileOtherHolder(view, viewInterface);
        }
    },

    PROFILE_LOOK_IN_FOR(R.layout.personal_lookingfor_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileLookingForHolder(view, viewInterface);
        }
    },
    PROFILE_I_CAN_HELP(R.layout.personal_lookingfor_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileICanHelpWithHolder(view, viewInterface);
        }
    }, PROFILE_ABOUTME(R.layout.personal_lookingfor_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileAboutMeHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_BASICDETAILS(R.layout.profile_basicdetails_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfilePersonalBasicDetailsHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_INTERESTING(R.layout.profile_interesting_in_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new ProfileIAmInterestingInHolder(view, viewInterface);
        }
    }, PROFILE_PERSONAL_VISITINGCARD(R.layout.profile_my_visiting_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new Visiting_card_holder1(view, viewInterface);
        }
    }, PROFFESTIONAL_VISITINGCARD(R.layout.profile_my_visiting_card) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new VisitingCardholder(view, viewInterface);
        }
    },
    FILTERLIST(R.layout.filter_list) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FilterHolder(view, viewInterface);
        }
    },
    JOBLOCATIONLIST(R.layout.joblocationlist) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new JobLocationHolder(view, viewInterface);
        }
    }, COMMUNITY_SUGGESTED_BY_HOLDER(R.layout.community_suggested_by_layout) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CommunityWithInSggestedHolder(view, viewInterface);
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
    },;
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
        int returnView = 0;
        if (null != item) {
            if (callFromType.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE)) {
                if (item instanceof FeedDetail) {
                    String feedType = ((FeedDetail) item).getSubType().toUpperCase();
                    switch (feedType) {
                        case AppConstants.FEED_ARTICLE:
                            returnView = FEED_ARTICLE.ordinal();
                            break;
                        case AppConstants.FEED_COMMUNITY:
                            returnView = FEED_COMMUNITY.ordinal();
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
                        default:

                    }
                }
            }
            else if(callFromType.equalsIgnoreCase(AppConstants.OWNER_SUB_TYPE))
            {
                returnView = FEED_USER.ordinal();

            }
            else if(callFromType.equalsIgnoreCase(AppConstants.COMMUNITY_NAME_SUB_TYPE))
            {
                    returnView = SELECTDILOG.ordinal();
            }
            else if(callFromType.equalsIgnoreCase(AppConstants.ALL_DATA_SUB_TYPE))
            {
                returnView = COMMUNITY_TAG_SEARCH.ordinal();

            }

            else if (callFromType.equalsIgnoreCase(AppConstants.ALL_SEARCH)) {
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
                            if (isFeatured) {
                                returnView = FEATURE_CARD.ordinal();
                                //   return SUGGESTED_CARD_HOLDER.ordinal();
                            }
                            else {
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

                        default:


                    }
                }
                else if (item instanceof DrawerItems) {
                    returnView = DRAWER_ITEMS.ordinal();
                } else if (item instanceof CommentReactionDoc) {
                    CommentReactionDoc commentReactionDoc = ((CommentReactionDoc) item);
                    if (commentReactionDoc.getLikeValue() > AppConstants.NO_REACTION_CONSTANT) {
                        returnView = REACTION.ordinal();
                    } else {
                        returnView = COMMENT.ordinal();
                    }
                } else if (item instanceof HomeSpinnerItem) {
                    String id = ((HomeSpinnerItem) item).getId();
                    if (id.equalsIgnoreCase(String.valueOf(totalCount))) {
                        return HOME_SPINNER_FOOTER.ordinal();
                    } else {
                        return HOME_SPINNER_ITEMS.ordinal();
                    }
                } else if (item instanceof ArticleDetailPojo) {
                    returnView = ARTICLE_DETAIL_HOLDER.ordinal();//TODO: Home related changes
                } else if (item instanceof ProfileHorList) {
                    return PROFILE_HORIZONTAL_LIST.ordinal();
                } else if (item instanceof ProfileViewList) {

                    ProfileViewList profileViewList = ((ProfileViewList) item);

                    String tagType = (profileViewList.getTag().toString());


                    if (tagType.equals("EDUCATION")) {
                        return PROFIL_EEDUCATION.ordinal();
                    } else if (tagType.equals("Good At")) {
                        return PROFILE_GOODAT.ordinal();
                    } else if (tagType.equals("WORK EXPERIENCE")) {
                        return PROFILE_WORK_EXPERIENCE.ordinal();
                    } else if (tagType.equals("Horizontal")) {
                        return PROFILE_HORIZONTAL_RECYCLER_LIST.ordinal();
                    } else if (tagType.equals("BASIC DETAILS")) {
                        return PROFILE_BASIC_DETAILS.ordinal();
                    } else if (tagType.equals("OTHER")) {
                        return PROFILE_OTHER.ordinal();

                    } else if (tagType.equals("My Contact Card")) {
                        return PROFFESTIONAL_VISITINGCARD.ordinal();
                    }

                } else if (item instanceof ProfilePersonalViewList)

                {

                    ProfilePersonalViewList profilePersonalViewList = ((ProfilePersonalViewList) item);

                    String tagType = (profilePersonalViewList.getTag().toString());


                    if (tagType.equals("Looking For")) {

                        return PROFILE_LOOK_IN_FOR.ordinal();

                    } else if (tagType.equals("I Can Help With")) {

                        return PROFILE_I_CAN_HELP.ordinal();

                    } else if (tagType.equals("About Me")) {

                        return PROFILE_ABOUTME.ordinal();

                    } else if (tagType.equals("Basic Details")) {
                        return PROFILE_PERSONAL_BASICDETAILS.ordinal();

                    } else if (tagType.equals("My Contact Card")) {
                        return PROFILE_PERSONAL_VISITINGCARD.ordinal();
                    } else if (tagType.equals("I AM INTERESTED IN")) {
                        return PROFILE_PERSONAL_INTERESTING.ordinal();

                    }

                }
                else if (item instanceof ListOfInviteSearch) {
                    return INVITE_SEARCH_MODULE.ordinal();
                }
                else if (item instanceof Member) {
                    return MEMBER_MODULE.ordinal();
                }
                else if (item instanceof OnBoardingData) {
                    return ON_BOARDING_HOLDER.ordinal();
                } else if (item instanceof RequestedList) {
                    return REQUESTLIST.ordinal();
                } else if (item instanceof OwnerList) {
                    return OWNERLIST.ordinal();
                } else if (item instanceof MembersList) {
                    return MEMBERLIST.ordinal();
                } else if (item instanceof CommunityList) {
                    return SELECTDILOG.ordinal();
                } else if (item instanceof CommunityTags) {
                    return SEARCHTAGS.ordinal();
                } else if (item instanceof CommunitySuggestion) {
                    return COMMUNITY_SUGGESTED_BY_HOLDER.ordinal();
                } else if (item instanceof JobLocationList) {
                    return JOBLOCATIONLIST.ordinal();
                } else if (item instanceof FilterList) {
                    return FILTERLIST.ordinal();
                } else if (item instanceof JobDetailPojo) {
                    return JOB_DETAIL_HOLDER.ordinal();
                } else if (item instanceof ProfileItems) {
                    return PROFILE_HOLDER.ordinal();
                }
            }
        }
        return returnView;
    }

}
