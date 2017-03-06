package appliedlife.pvtltd.SHEROES.models;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.database.dbtables.RecentSearchDataTable;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RecentSearchDataModel {

    private final StorIOSQLite storIOSQLite;
    private final SheroesAppServiceApi sheroesAppServiceApi;

    @Inject
    public RecentSearchDataModel(SheroesAppServiceApi sheroesAppServiceApi,
                                 StorIOSQLite storIOSQLite) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.storIOSQLite = storIOSQLite;
    }

    public boolean isCached() {
        return storIOSQLite.get()
                .numberOfResults()
                .withQuery(RecentSearchDataTable.getAllQuery())
                .prepare()
                .executeAsBlocking() > 0;
    }


   /* public Observable<List<RecentSearchData>> saveRecentSearchTypes(final List<RecentSearchData> recentSearchPassedDataList, final long entitiyOrParticipantID) {
        return getAllRecentSearch().flatMap(new Func1<List<RecentSearchData>, Observable<List<RecentSearchData>>>() {
            @Override
            public Observable<List<RecentSearchData>> call(List<RecentSearchData> recentSearchDataList) {
                if (StringUtil.isNotEmptyCollection(recentSearchDataList)) {
                    for (RecentSearchData recentSearchData : recentSearchDataList) {
                        if (recentSearchData.getEntityOrParticipantId() != entitiyOrParticipantID) {
                            recentSearchPassedDataList.add(recentSearchData);
                        }
                    }
                }
                return saveRecentSearch(recentSearchPassedDataList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
*/
   public Observable<List<RecentSearchData>> saveRecentSearchTypes(final List<RecentSearchData> recentSearchPassedDataList, final long entitiyOrParticipantID) {
       return  saveRecentSearch(recentSearchPassedDataList);
   }
    public Observable<List<RecentSearchData>> getAllRecentSearch() {
        return storIOSQLite.get()
                .listOfObjects(RecentSearchData.class)
                .withQuery(RecentSearchDataTable.getQueryForList())
                .prepare()
                .asRxObservable().subscribeOn(Schedulers.io());
    }

    public Observable<List<RecentSearchData>> saveRecentSearch(final List<RecentSearchData> recentSearchDatas) {
        return storIOSQLite.put()
                .objects(recentSearchDatas)
                .useTransaction(true)
                .prepare()
                .asRxObservable()
                .flatMap(new Func1<PutResults<RecentSearchData>, Observable<List<RecentSearchData>>>() {
                    @Override
                    public Observable<List<RecentSearchData>> call(PutResults<RecentSearchData> results) {
                        return Observable.just(recentSearchDatas);
                    }
                });
    }

}
