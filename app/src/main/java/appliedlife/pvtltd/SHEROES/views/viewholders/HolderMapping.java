package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.models.entities.home.CityListData;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.SheroesListDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleRequest;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.FeatResponse;

public enum HolderMapping {
    COLLECTIONS(R.layout.dashboard_list_item) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new CollectionHolder(view, viewInterface);
        }
    }, FOOTER(R.layout.home_footer) {
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
    }, FEATURE(R.layout.feature) {
        @Override
        public BaseViewHolder getViewHolder(View view, BaseHolderInterface viewInterface) {
            return new FeatureHolder(view, viewInterface);
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

    public static int getOrdinal(SheroesListDataItem item, int totalCount) {
        if (item instanceof CityListData) {
            String id = ((CityListData) item).getId();
            if (id.equalsIgnoreCase(String.valueOf(totalCount))) {
                return FOOTER.ordinal();
            } else {
                return COLLECTIONS.ordinal();
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
        } else if (item instanceof ArticleRequest) {
            return SEARCH_MODULE.ordinal();
        }else if (item instanceof FeatResponse) {
            return FEATURE.ordinal();
        }
        return COLLECTIONS.ordinal();
    }


}
