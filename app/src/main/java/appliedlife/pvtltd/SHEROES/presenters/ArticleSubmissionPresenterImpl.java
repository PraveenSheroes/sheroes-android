package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Created by avinash on 28/01/16.
 */
public class ArticleSubmissionPresenterImpl extends BasePresenter<IArticleSubmissionView> {
    SheroesAppServiceApi sheroesAppServiceApi;
    SheroesApplication mSheroesApplication;
    //region Constructor

    @Inject
    public ArticleSubmissionPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi,SheroesApplication sheroesApplication) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    public void prepareArticle(ArticleSubmissionRequest articleSubmissionRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        /*sheroesAppServiceApi.reportSpamPostOrComment(spamPostRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<SpamResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<SpamResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamResponse, userPostSolrObj);
                        getMvpView().stopProgressBar();
                    }
                });*/
    }

   /* public void uploadFile(Uri fullPath, Context applicationContext) {
        final String reportImagePath = FileUtil.getExternalStorageDirectory() + File.separator + new Date().getTime() + Math.floor(Math.random() * 10000) + ".jpg";
        if (fullPath != null) {
            // Generate slide
            Observable<UpLoadImageResponse> observable = CompressImageUtil.compressImage(applicationContext, fullPath,
                    reportImagePath, 816)
                    .flatMap(new Function<Boolean, Observable<UpLoadImageResponse>>() {
                        @Override
                        public Observable<UpLoadImageResponse> apply(Boolean success) {
                            if (!success) {
                                Observable.just(new UpLoadImageResponse());
                            }

                            File file = new File(reportImagePath);
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            String encodedImageUrl = CompressImageUtil.setImageOnHolder(photo);

                            UploadImageRequest uploadImageRequest = new UploadImageRequest();
                            uploadImageRequest.images = new ArrayList<>();
                            uploadImageRequest.images.add(encodedImageUrl);

                            return sheroesAppServiceApi.uploadImage(uploadImageRequest);
                        }
                    })
                    .map(new Function<UpLoadImageResponse, UpLoadImageResponse>() {
                        @Override
                        public UpLoadImageResponse apply(UpLoadImageResponse upLoadImageResponse) {
                            FileUtil.deleteFile(reportImagePath);
                            return upLoadImageResponse;
                        }
                    })
                    .compose(this.<UpLoadImageResponse>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
            observable.subscribe(new DisposableObserver<UpLoadImageResponse>() {
                @Override
                public void onNext(UpLoadImageResponse upLoadImageResponse) {
                    String finalImageUrl = upLoadImageResponse.images.get(0).imageUrl;
                    getMvpView().showImage(finalImageUrl);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }*/

    public void uploadFile(String encodedImage, Context applicationContext) {
        UploadImageRequest uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.images = new ArrayList<>();
        uploadImageRequest.images.add(encodedImage);
                    sheroesAppServiceApi.uploadImage(uploadImageRequest)
                    .compose(this.<UpLoadImageResponse>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<UpLoadImageResponse>() {
                @Override
                public void onNext(UpLoadImageResponse upLoadImageResponse) {
                    String finalImageUrl = upLoadImageResponse.images.get(0).imageUrl;
                    getMvpView().showImage(finalImageUrl);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }
    //endregion

    //region IArticlePresenter methods

}
