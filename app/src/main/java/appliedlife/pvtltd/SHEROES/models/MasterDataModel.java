package appliedlife.pvtltd.SHEROES.models;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.Response;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterDataTypes;
import appliedlife.pvtltd.SHEROES.database.dbtables.MasterDataTable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MasterDataModel {

  private final StorIOSQLite storIOSQLite;
  private final SheroesAppServiceApi sheroesAppServiceApi;

  @Inject
  public MasterDataModel(SheroesAppServiceApi sheroesAppServiceApi,
                         StorIOSQLite storIOSQLite) {
    this.sheroesAppServiceApi = sheroesAppServiceApi;
    this.storIOSQLite = storIOSQLite;
  }

  public boolean isCached() {
    return storIOSQLite.get()
        .numberOfResults()
        .withQuery(MasterDataTable.getAllQuery())
        .prepare()
        .executeAsBlocking() > 0;
  }

  public Observable<List<MasterData>> saveMasterTypes() {
    /*if (isCached()) {
      return fromCache();
    }*/
    return fromNetwork().flatMap(new Func1<List<MasterData>, Observable<List<MasterData>>>() {
      @Override
      public Observable<List<MasterData>> call(List<MasterData> masterDataList) {
        return saveToCache(masterDataList);
      }
    })  .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<MasterData>> fetchMultipleMasterTypes(MasterDataTypes... masterDataTypes){

    String queryString = "";
    ArrayList<String> stringList = new ArrayList<>();
    for (MasterDataTypes masterDataType : masterDataTypes) {
      stringList.add("'" + masterDataType.id+"'");
    }
    queryString = "Select * from "+MasterDataTable.TABLE+" WHERE "+MasterDataTable.COL_MASTER_DATA_TYPE +" in ";
    queryString = queryString + stringList.toString().replace('[','(').replace(']',')') + ";";
    RawQuery query = RawQuery.builder().query(queryString).build();

    return storIOSQLite.get()
        .listOfObjects(MasterData.class)
        .withQuery(query)
        .prepare()
        .asRxObservable();
  }

  public Observable<List<MasterData>> fromNetwork() {
    return sheroesAppServiceApi.getMasterData()
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .map(new Func1<Response<List<MasterData>>, List<MasterData>>() {
          @Override
          public List<MasterData> call(Response<List<MasterData>> listResponse) {
            return listResponse.getResults();
          }
        });
  }
 /* public Observable<List<MasterData>> fromNetwork(MasterDataTypes type) {
    return sheroesAppServiceApi.getMasterData()
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .map(new Func1<Response<List<MasterData>>, List<MasterData>>() {
          @Override
          public List<MasterData> call(Response<List<MasterData>> allMasterData) {
            return
          }
        });
  }*/

  public Observable<List<MasterData>> fromCache() {
    return storIOSQLite.get()
            .listOfObjects(MasterData.class)
            .withQuery(MasterDataTable.getAllQuery())
            .prepare()
            .asRxObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  public Observable<List<MasterData>> fromCache(int id) {
    List<Integer> ids=new ArrayList<>();
    ids.add(1);
    ids.add(2);
    return storIOSQLite.get()
        .listOfObjects(MasterData.class)
       // .withQuery(MasterDataTable.getQueryFor(id))
            .withQuery(MasterDataTable.getQueryForList())
        .prepare()
        .asRxObservable();
  }

  public Observable<List<MasterData>> saveToCache(final List<MasterData> masterDatas) {
    return storIOSQLite.put()
        .objects(masterDatas)
        .useTransaction(true)
        .prepare()
        .asRxObservable()
        .flatMap(new Func1<PutResults<MasterData>, Observable<List<MasterData>>>() {
          @Override
          public Observable<List<MasterData>> call(PutResults<MasterData> results) {
            return Observable.just(masterDatas);
          }
        });
  }
 /* public Observable<List<MasterData>> getMasterDataForIds(List<Integer> ids){
    return storIOSQLite.get()
        .listOfObjects(MasterData.class)
        .withQuery(MasterDataTable.getQueryForList(ids))
        .prepare()
        .asRxObservable()
        .observeOn(AndroidSchedulers.mainThread());
  }*/


  public static List<Integer> getIntegerList(List<MasterData> masterDataList){
    List<Integer> integerList = new ArrayList<>();
    for (int i = 0; i < masterDataList.size(); i++) {
      integerList.add(masterDataList.get(i).getId());
    }
    return integerList;
  }
  public static List<String> getNameList(List<MasterData> masterDataList){
    List<String> stringList = new ArrayList<>();
    for (int i = 0; i < masterDataList.size(); i++) {
      stringList.add(masterDataList.get(i).getDisplayName());
    }
    return stringList;
  }
}
