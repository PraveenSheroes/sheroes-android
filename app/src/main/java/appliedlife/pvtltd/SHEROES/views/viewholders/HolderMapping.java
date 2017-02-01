package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentsList;
import appliedlife.pvtltd.SHEROES.models.entities.comment.ReactionList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.MyCommunities;
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

    public static int getOrdinal(BaseResponse item, int totalCount) {
        int returnView = FEED_JOB.ordinal();
        if (item instanceof ListOfFeed) {
            String feedType = ((ListOfFeed) item).getFeedType();
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
                default:
            }
        } else if (item instanceof HomeSpinnerItem) {
            String id = ((HomeSpinnerItem) item).getId();
            if (id.equalsIgnoreCase(String.valueOf(totalCount))) {
                return HOME_SPINNER_FOOTER.ordinal();
            } else {
                return HOME_SPINNER_ITEMS.ordinal();
            }
        } else if (item instanceof DrawerItems) {
            return DRAWER_ITEMS.ordinal();
        } else if (item instanceof ArticleCardResponse) {
            // return SEARCH_MODULE.ordinal();
            return ARTICLE_CARD_HOLDER.ordinal();
        } else if (item instanceof MyCommunities) {
            return MY_COMMUNITIES_CARD.ordinal();
        } else if (item instanceof Feature) {
            return FEATURE_CARD.ordinal();
        } else if (item instanceof CommentsList) {
            return COMMENT.ordinal();

        } else if (item instanceof ReactionList) {
            return REACTION.ordinal();

        }
        else if(item instanceof CommunityList)
        {
            return SELECTDILOG.ordinal();
        }
        return returnView;
    }


}
