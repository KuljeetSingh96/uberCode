package com.ubercode.ui.flickerlist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ubercode.network.model.FlickerListResponse;
import com.ubercode.network.repository.Repository;
import com.ubercode.utils.schedulers.SchedulerProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static com.ubercode.network.api.ApiConstants.CALLBACK_FORMAT;
import static com.ubercode.network.api.ApiConstants.FLICKER_API_KEY;
import static com.ubercode.network.api.ApiConstants.METHOD_INPUT;
import static com.ubercode.network.api.ApiConstants.NO_JSON_CALLBACK_FLAG;
import static com.ubercode.network.api.ApiConstants.PER_PAGE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlickerListSearchPresenterTest {
    @Mock
    Repository repository;
    @Mock
    FlickerListSearchViewModel viewModel;

    private TestScheduler testScheduler = new TestScheduler();
    private TestSchedulerProvider testSchedulerProvider;
    private final String SEARCH_QUERY = "kittens";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
    }


    private FlickerListSearchPresenter getFlickerListPresenter()
    {
        viewModel = spy(new FlickerListSearchViewModel());
        viewModel.pageCount=spy((new MutableLiveData<>()));
        viewModel.errorMessageVisibility=spy(new MutableLiveData<>());
        viewModel.errorMessage =spy(new MutableLiveData<>());
        viewModel.loadingVisibility=spy(new MutableLiveData<>());
        viewModel.repoListData=spy(new MutableLiveData<>());
        viewModel.repoListVisibility = spy(new MutableLiveData<>());
        FlickerListSearchPresenter flickerListSearchPresenter =spy(new FlickerListSearchPresenter(viewModel,repository, testSchedulerProvider));
        return flickerListSearchPresenter;
    }

    @Test
    public void testGetFlickerListWithEmptyResponse() throws Exception {
        FlickerListSearchPresenter flickerListSearchPresenter =getFlickerListPresenter();
        FlickerListResponse flickerListResponse = new FlickerListResponse();
        FlickerListResponse.Photos photos = new FlickerListResponse.Photos();
        flickerListResponse.setPhotos(photos);
        try {
            when(viewModel.pageCount.getValue()).thenReturn(0);
            when(repository.getFlickerImages(METHOD_INPUT, FLICKER_API_KEY, CALLBACK_FORMAT,
                    NO_JSON_CALLBACK_FLAG, SEARCH_QUERY, PER_PAGE, 1)).
                    thenReturn(Observable.just(flickerListResponse));
            flickerListSearchPresenter.getFlickerImages(SEARCH_QUERY);
            testScheduler.triggerActions();
            verify(viewModel.errorMessage,times(1)).setValue("No list available.");
            Mockito.verifyZeroInteractions(viewModel.repoListData);
            verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
            verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
            verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());
        }catch (Exception ex)
        {
            Assert.fail("Test failed  : "+ex.getMessage());
        }

    }

    @Test
    public void testGetFlickerListWithValidResponse() throws Exception {
        FlickerListResponse flickerListResponses = getValidList();

        FlickerListSearchPresenter flickerListSearchPresenter =getFlickerListPresenter();
        when(viewModel.pageCount.getValue()).thenReturn(0);
        when(repository.getFlickerImages(METHOD_INPUT, FLICKER_API_KEY,
                CALLBACK_FORMAT, NO_JSON_CALLBACK_FLAG, SEARCH_QUERY,
                PER_PAGE, 1)).thenReturn(Observable.just(flickerListResponses));
        try {
            flickerListSearchPresenter.getFlickerImages(SEARCH_QUERY);
            testScheduler.triggerActions();
            verify(viewModel.errorMessage,never()).setValue("No list available.");
//        verify(viewModel.repoListData,times(1)).postValue(flickerListResponses);
            verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
            verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
            verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());
        }catch (Exception ex)
        {
            Assert.fail("Test failed  : "+ex.getMessage());
        }


    }

    @Test
    public void testGetFlickerListWithNetworkErrorResponse() throws Exception {

        FlickerListSearchPresenter flickerListSearchPresenter =getFlickerListPresenter();
        when(viewModel.pageCount.getValue()).thenReturn(0);
        when(repository.getFlickerImages(METHOD_INPUT, FLICKER_API_KEY,
                CALLBACK_FORMAT, NO_JSON_CALLBACK_FLAG, SEARCH_QUERY,
                PER_PAGE, 1)).thenReturn(Observable.error(new Throwable()));
        try {
            flickerListSearchPresenter.getFlickerImages(SEARCH_QUERY);
            testScheduler.triggerActions();
            Mockito.verifyZeroInteractions(viewModel.repoListData);
            verify(viewModel.errorMessage,times(1)).setValue("Network error. Please try again later.");
            verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
            verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
            verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());
        }catch (Exception ex)
        {
            Assert.fail("Test failed  : "+ex.getMessage());
        }

    }


    private FlickerListResponse getValidList()
    {
        String RESPONSE_STRING ="{\n" +
                "\t\"photos\": {\n" +
                "\t\t\"page\": 1,\n" +
                "\t\t\"pages\": 7798,\n" +
                "\t\t\"perpage\": 20,\n" +
                "\t\t\"total\": \"155949\",\n" +
                "\t\t\"photo\": [{\n" +
                "\t\t\t\"id\": \"45892860585\",\n" +
                "\t\t\t\"secret\": \"f9a7c8fdf1\",\n" +
                "\t\t\t\"server\": \"7865\",\n" +
                "\t\t\t\"farm\": 8,\n" +
                "\t\t\t\"title\": \"CAT 03 KH0105 01\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"id\": \"32932164258\",\n" +
                "\t\t\t\"secret\": \"4c0e55f13c\",\n" +
                "\t\t\t\"server\": \"7839\",\n" +
                "\t\t\t\"farm\": 8,\n" +
                "\t\t\t\"title\": \"Cat\"\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";
        Gson gson = new Gson();
        FlickerListResponse apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<FlickerListResponse>() {
                }.getType());
        return apiResponse;
    }



    public class TestSchedulerProvider extends SchedulerProvider {

        private final TestScheduler testScheduler1;

        public TestSchedulerProvider(TestScheduler testScheduler) {
            this.testScheduler1 = testScheduler;
        }

        @Override
        public Scheduler ui() {
            return testScheduler1;
        }

        @Override
        public Scheduler computation() {
            return testScheduler1;
        }

        @Override
        public Scheduler io() {
            return testScheduler1;
        }

    }
}