package appliedlife.pvtltd.SHEROES;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class MockService {
    //TODO:: Mock service request
    public static ListOfFeed makeCityRequest()
    {
        ListOfFeed listOfFeed=new ListOfFeed();
        return  listOfFeed;
    }

    public static List<HomeSpinnerItem> makeSpinnerListRequest()
    {
        List<HomeSpinnerItem> homeSpinnerItems=new ArrayList<>();
        HomeSpinnerItem homeSpinnerItem1=new HomeSpinnerItem();
        homeSpinnerItem1.setId("1");
        homeSpinnerItem1.setName("All ");
        homeSpinnerItem1.setDescription("This first mock city discription");
        homeSpinnerItem1.setChecked(false);

        HomeSpinnerItem homeSpinnerItem2=new HomeSpinnerItem();
        homeSpinnerItem2.setId("2");
        homeSpinnerItem2.setName("Comminties ");
        homeSpinnerItem2.setDescription("This second mock city discription");
        homeSpinnerItem2.setChecked(false);

        HomeSpinnerItem homeSpinnerItem3=new HomeSpinnerItem();
        homeSpinnerItem3.setId("3");
        homeSpinnerItem3.setName("Article");
        homeSpinnerItem3.setDescription("This third mock city discription");
        homeSpinnerItem3.setChecked(false);

        HomeSpinnerItem homeSpinnerItem4=new HomeSpinnerItem();
        homeSpinnerItem4.setId("4");
        homeSpinnerItem4.setName("Feeds");
        homeSpinnerItem4.setDescription("This fourth mock city discription");
        homeSpinnerItem4.setChecked(false);

        HomeSpinnerItem homeSpinnerItem5=new HomeSpinnerItem();
        homeSpinnerItem5.setId("5");
        homeSpinnerItem5.setName("Job");
        homeSpinnerItem5.setDescription("This fifth mock city discription");
        homeSpinnerItem5.setChecked(false);

        HomeSpinnerItem homeSpinnerItem6=new HomeSpinnerItem();
        homeSpinnerItem6.setId("6");
        homeSpinnerItem6.setName("Process");
        homeSpinnerItem6.setDescription("This six mock city discription");
        homeSpinnerItem6.setChecked(false);

        HomeSpinnerItem homeSpinnerItem7=new HomeSpinnerItem();
        homeSpinnerItem7.setId("7");
        homeSpinnerItem7.setName("All ");
        homeSpinnerItem7.setDescription("This seven mock city discription");
        homeSpinnerItem7.setChecked(false);

        homeSpinnerItems.add(homeSpinnerItem1);
        homeSpinnerItems.add(homeSpinnerItem2);
        homeSpinnerItems.add(homeSpinnerItem3);
        homeSpinnerItems.add(homeSpinnerItem4);
        homeSpinnerItems.add(homeSpinnerItem5);
        homeSpinnerItems.add(homeSpinnerItem6);
        homeSpinnerItems.add(homeSpinnerItem7);
        return  homeSpinnerItems;
    }
}
