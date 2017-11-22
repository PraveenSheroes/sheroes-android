package appliedlife.pvtltd.SHEROES.views;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class CustomeDataList {
    //TODO:: Remove static content
    public static List<DrawerItems> makeDrawerItemList(boolean isSheUser) {
        List<DrawerItems> drawerItemsList = new ArrayList<>();
        DrawerItems firstDrawerItem = new DrawerItems();
        firstDrawerItem.setId(1);
        firstDrawerItem.setName("Profile");
        firstDrawerItem.setIconName("ic_profile");
        DrawerItems secondDrawerItem = new DrawerItems();
        secondDrawerItem.setId(2);
        secondDrawerItem.setName("Article");
        secondDrawerItem.setIconName("ic_article");
       // DrawerItems thirdDrawerItem = new DrawerItems();
      //  thirdDrawerItem.setId(3);
      //  thirdDrawerItem.setName("Jobs");
      //  thirdDrawerItem.setIconName("ic_job");
        DrawerItems fourthDrawerItem = new DrawerItems();
        fourthDrawerItem.setId(4);
        fourthDrawerItem.setName("Bookmarks");
        fourthDrawerItem.setIconName("ic_bookmark");
        DrawerItems fifthDrawerItem = new DrawerItems();
        fifthDrawerItem.setId(5);
        fifthDrawerItem.setName("Settings");
        fifthDrawerItem.setIconName("ic_setting");
        DrawerItems sixthDrawerItem = new DrawerItems();
        sixthDrawerItem.setId(6);
        sixthDrawerItem.setName("AskSheroes");
        sixthDrawerItem.setIconName("ic_setting");
        DrawerItems seventhDrawerItem = new DrawerItems();
        seventhDrawerItem.setId(7);
        seventhDrawerItem.setName("Ask Sheroes");
        seventhDrawerItem.setIconName("ic_helpline");
        DrawerItems eighthDrawerItem = new DrawerItems();
        eighthDrawerItem.setId(8);
        eighthDrawerItem.setName("ICC Members");
        eighthDrawerItem.setIconName("ic_icc_members");
        DrawerItems ninthDrawerItem = new DrawerItems();
        ninthDrawerItem.setId(9);
        ninthDrawerItem.setName("FAQ");
        ninthDrawerItem.setIconName("ic_faq");
        DrawerItems tenthDrawerItem = new DrawerItems();
        tenthDrawerItem.setId(10);
        tenthDrawerItem.setName("Feed");
        tenthDrawerItem.setIconName("ic_feed");

        DrawerItems elevenDrawerItem = new DrawerItems();
        elevenDrawerItem.setId(11);
        elevenDrawerItem.setName("Login");
        elevenDrawerItem.setIconName("ic_feed");

        DrawerItems twelveDraerItem = new DrawerItems();
        twelveDraerItem.setIconName("ic_bookmark");
        twelveDraerItem.setId(12);
        twelveDraerItem.setName("Settings");
        twelveDraerItem.setIconName("ic_setting");

        DrawerItems thirteenItem = new DrawerItems();
        thirteenItem.setId(13);
        DrawerItems fourteenItem = new DrawerItems();
        fourteenItem.setId(14);

        DrawerItems fifteenDrawerItem = new DrawerItems();
        fifteenDrawerItem.setName("Contest");
        fifteenDrawerItem.setIconName("ic_setting");
        fifteenDrawerItem.setId(15);


        if (isSheUser) {
            drawerItemsList.add(seventhDrawerItem);
            drawerItemsList.add(eighthDrawerItem);
            drawerItemsList.add(ninthDrawerItem);
            //  drawerItemsList.add(tenthDrawerItem);
        } else {
            drawerItemsList.add(firstDrawerItem);
            drawerItemsList.add(fourteenItem);
            drawerItemsList.add(secondDrawerItem);
         //   drawerItemsList.add(thirdDrawerItem);
            //  drawerItemsList.add(fourthDrawerItem);
            drawerItemsList.add(sixthDrawerItem);
            drawerItemsList.add(twelveDraerItem);
            drawerItemsList.add(fifteenDrawerItem);
            // drawerItemsList.add(thirteenItem);
        }
        drawerItemsList.add(elevenDrawerItem);

        return drawerItemsList;
    }

}
