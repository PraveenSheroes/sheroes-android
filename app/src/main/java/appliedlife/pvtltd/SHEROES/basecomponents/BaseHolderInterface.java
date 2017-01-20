package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Intent;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.home.SheroesListDataItem;


public interface BaseHolderInterface<T extends SheroesListDataItem> {
    void startActivityFromHolder(Intent intent);

    void handleOnClick(SheroesListDataItem sheroesListDataItem, View view);

    void setListData(T data,boolean flag);

    List<T> getListData();
}
