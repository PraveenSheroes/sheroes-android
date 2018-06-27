package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleTagResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Created by avinash on 28/01/16.
 */
public class ArticleSubmissionPresenterImpl extends BasePresenter<IArticleSubmissionView> {
    SheroesAppServiceApi mSheroesAppServiceApi;
    SheroesApplication mSheroesApplication;
    //region Constructor

    @Inject
    public ArticleSubmissionPresenterImpl(SheroesAppServiceApi mSheroesAppServiceApi, SheroesApplication sheroesApplication) {
        this.mSheroesAppServiceApi = mSheroesAppServiceApi;
        this.mSheroesApplication = sheroesApplication;
    }

    public void submitAndDraftArticle(final ArticleSubmissionRequest articleSubmissionRequest, final boolean isDraft) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.submitArticle(articleSubmissionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ArticleSubmissionResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ArticleSubmissionResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(ArticleSubmissionResponse articleSubmissionResponse) {
                        getMvpView().stopProgressBar();
                        if (null != articleSubmissionResponse) {
                            switch (articleSubmissionResponse.getStatus()) {
                                case AppConstants.SUCCESS:
                                    getMvpView().articleSubmitResponse(articleSubmissionResponse.getArticleSolrObj(), isDraft);
                                    break;
                                case AppConstants.FAILED:
                                    getMvpView().showError(articleSubmissionResponse.getFieldErrorMessageMap().get(AppConstants.ERROR), ERROR_TAG);
                                    break;
                            }
                        }
                    }
                });
    }

    public void editArticle(final ArticleSubmissionRequest articleSubmissionRequest, final boolean isDraft) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.editArticle(articleSubmissionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ArticleSubmissionResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ArticleSubmissionResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(ArticleSubmissionResponse articleSubmissionResponse) {
                        getMvpView().stopProgressBar();
                        if (null != articleSubmissionResponse) {
                            switch (articleSubmissionResponse.getStatus()) {
                                case AppConstants.SUCCESS:
                                    getMvpView().articleSubmitResponse(articleSubmissionResponse.getArticleSolrObj(), isDraft);
                                    break;
                                case AppConstants.FAILED:
                                    getMvpView().showError(articleSubmissionResponse.getFieldErrorMessageMap().get(AppConstants.ERROR), ERROR_TAG);
                                    break;
                            }
                        }
                    }
                });
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

                            return mSheroesAppServiceApi.uploadImage(uploadImageRequest);
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
        mSheroesAppServiceApi.uploadImage(uploadImageRequest)
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
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getArticleTags() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.getArticleTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ArticleTagResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ArticleTagResponse>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().getArticleTagList(null, false);
                    }

                    @Override
                    public void onNext(ArticleTagResponse articleTagResponse) {
                        if (null != articleTagResponse) {
                            getMvpView().getArticleTagList(articleTagResponse.getTags(), false);
                        }
                    }
                });
    }
    //endregion

    //region IArticlePresenter methods

}
