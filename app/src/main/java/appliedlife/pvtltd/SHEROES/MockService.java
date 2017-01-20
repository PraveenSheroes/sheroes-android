package appliedlife.pvtltd.SHEROES;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.home.CityListData;
import appliedlife.pvtltd.SHEROES.models.entities.home.CityListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class MockService {
    //TODO:: Mock service request
    public static CityListResponse makeCityRequest()
    {
        CityListResponse cityListResponse=new CityListResponse();
        List<CityListData> listDataList=new ArrayList<>();
        CityListData cityListData1=new CityListData();
        cityListData1.setId("1");
        cityListData1.setBackground("http://www.tnetnoc.com/hotelphotos/591/327591/2631759-The-Cangkringan-Spa-Villas-Hotel-Yogyakarta-Guest-Room-3-RTS.jpg");
        cityListData1.setName("First City");
        cityListData1.setDescription("This first mock city discription");

        CityListData cityListData2=new CityListData();
        cityListData2.setId("2");
        cityListData2.setBackground("https://media-cdn.tripadvisor.com/media/photo-s/08/dc/c3/be/the-park-lane-jakarta.jpg");
        cityListData2.setName("Second City");
        cityListData2.setDescription("This second mock city discription");

        CityListData cityListData3=new CityListData();
        cityListData3.setId("3");
        cityListData3.setBackground("https://media-cdn.tripadvisor.com/media/photo-s/08/dc/c3/be/the-park-lane-jakarta.jpg");
        cityListData3.setName("Third City");
        cityListData3.setDescription("This third mock city discription");

        CityListData cityListData4=new CityListData();
        cityListData4.setId("4");
        cityListData4.setBackground("https://a1.cdn-hotels.com/cos/production48/d1785/10fa68a0-ac68-11e4-99a1-d89d672bd508.jpg");
        cityListData4.setName("Fourth City");
        cityListData4.setDescription("This fourth mock city discription");

        CityListData cityListData5=new CityListData();
        cityListData5.setId("5");
        cityListData5.setBackground("http://balispecialevent.com/images/bali-hotel.jpg");
        cityListData5.setName("Fifth City");
        cityListData5.setDescription("This fifth mock city discription");

        CityListData cityListData6=new CityListData();
        cityListData6.setId("6");
        cityListData6.setBackground("http://www.hotel-r.net/im/hotel/bg/paris-hotel-21.jpg");
        cityListData6.setName("Sixth City");
        cityListData6.setDescription("This six mock city discription");
        listDataList.add(cityListData1);
        listDataList.add(cityListData2);
        listDataList.add(cityListData3);
        listDataList.add(cityListData4);
        listDataList.add(cityListData5);
        listDataList.add(cityListData6);
        cityListResponse.setData(listDataList);
        cityListResponse.setMessage("Mock city list");
        cityListResponse.setStatus(200);
        return  cityListResponse;
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
