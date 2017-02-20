package appliedlife.pvtltd.SHEROES.views;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class CustomeDataList {
    //TODO:: Remove static content
    public static List<DrawerItems> makeDrawerItemList()
    {
        List<DrawerItems> drawerItemsList = new ArrayList<>();
        DrawerItems firstDrawerItem = new DrawerItems();
        firstDrawerItem.setId(1);
        firstDrawerItem.setName("Profile");
        firstDrawerItem.setIconName("ic_profile");
        DrawerItems secondDrawerItem = new DrawerItems();
        secondDrawerItem.setId(2);
        secondDrawerItem.setName("Article");
        secondDrawerItem.setIconName("ic_article");
        DrawerItems thirdDrawerItem = new DrawerItems();
        thirdDrawerItem.setId(3);
        thirdDrawerItem.setName("Jobs");
        thirdDrawerItem.setIconName("ic_job");
        DrawerItems fourthDrawerItem = new DrawerItems();
        fourthDrawerItem.setId(4);
        fourthDrawerItem.setName("Bookmarks");
        fourthDrawerItem.setIconName("ic_bookmark");
        DrawerItems fifthDrawerItem = new DrawerItems();
        fifthDrawerItem.setId(5);
        fifthDrawerItem.setName("Settings");
        fifthDrawerItem.setIconName("ic_setting");
        drawerItemsList.add(firstDrawerItem);
        drawerItemsList.add(secondDrawerItem);
        drawerItemsList.add(thirdDrawerItem);
        drawerItemsList.add(fourthDrawerItem);
        drawerItemsList.add(fifthDrawerItem);
        return  drawerItemsList;
    }
    public static List<HomeSpinnerItem> makeSpinnerListRequest()
    {
        List<HomeSpinnerItem> homeSpinnerItems=new ArrayList<>();
        HomeSpinnerItem homeSpinnerItem1=new HomeSpinnerItem();
        homeSpinnerItem1.setId("1");
        homeSpinnerItem1.setName("All ");
        homeSpinnerItem1.setDescription("All");
        homeSpinnerItem1.setChecked(false);

        HomeSpinnerItem homeSpinnerItem2=new HomeSpinnerItem();
        homeSpinnerItem2.setId("2");
        homeSpinnerItem2.setName("Relationship ");
        homeSpinnerItem2.setDescription("From");
        homeSpinnerItem2.setChecked(false);

        HomeSpinnerItem homeSpinnerItem3=new HomeSpinnerItem();
        homeSpinnerItem3.setId("3");
        homeSpinnerItem3.setName("Meet The Sheroes");
        homeSpinnerItem3.setDescription("This third mock city discription");
        homeSpinnerItem3.setChecked(false);

        HomeSpinnerItem homeSpinnerItem4=new HomeSpinnerItem();
        homeSpinnerItem4.setId("4");
        homeSpinnerItem4.setName("story of the day");
        homeSpinnerItem4.setDescription("This fourth mock city discription");
        homeSpinnerItem4.setChecked(false);

        HomeSpinnerItem homeSpinnerItem5=new HomeSpinnerItem();
        homeSpinnerItem5.setId("5");
        homeSpinnerItem5.setName("womens of the day");
        homeSpinnerItem5.setDescription("This fifth mock city discription");
        homeSpinnerItem5.setChecked(false);
        HomeSpinnerItem homeSpinnerItem6=new HomeSpinnerItem();
        homeSpinnerItem6.setId("6");
        homeSpinnerItem6.setName("footer");
        homeSpinnerItem6.setDescription("This fifth mock city discription");
        homeSpinnerItem6.setChecked(false);

        homeSpinnerItems.add(homeSpinnerItem1);
        homeSpinnerItems.add(homeSpinnerItem2);
        homeSpinnerItems.add(homeSpinnerItem3);
        homeSpinnerItems.add(homeSpinnerItem4);
        homeSpinnerItems.add(homeSpinnerItem5);
        homeSpinnerItems.add(homeSpinnerItem6);
        return  homeSpinnerItems;
    }
}
