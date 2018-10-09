package appliedlife.pvtltd.SHEROES;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.Image;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.presenters.ArticleSubmissionPresenterImpl;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit pvtltd, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArticleUnitTest {
    @Mock
    Context mMockContext;
    @Mock
    private SheroesAppServiceApi mSheroesAppServiceApi;

    @Mock
    ArticleSubmissionPresenterImpl mArticleSubmissionPresenter;

    @Mock
    private IArticleSubmissionView view;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchUploadImageShouldLoadOnView() {
        UploadImageRequest uploadImageRequest = new UploadImageRequest();
        List<String> images=new ArrayList<>();
        images.add("one");
        images.add("two");

        uploadImageRequest.images=images;
        uploadImageRequest.communityId=123L;
        uploadImageRequest.isActive=true;
        uploadImageRequest.title="First request";
        uploadImageRequest.description="This is test image";

        UpLoadImageResponse upLoadImageResponse = new UpLoadImageResponse();
        Image image1=new Image();
        image1.uploadedImageUrl="one";
        image1.uploadedImageUrl="image/one";
        Image image2=new Image();
        image2.uploadedImageUrl="two";
        image2.uploadedImageUrl="image/two";
        List<Image> imageResponse=new ArrayList<>();
        imageResponse.add(image1);
        imageResponse.add(image2);
        upLoadImageResponse.images=imageResponse;

        mArticleSubmissionPresenter.uploadFile("Encode image",mMockContext);

        when(mSheroesAppServiceApi.uploadImage(uploadImageRequest))
                .thenReturn(Observable.just(upLoadImageResponse));


        verify(view, never()).showImage("image/one");

    }
}