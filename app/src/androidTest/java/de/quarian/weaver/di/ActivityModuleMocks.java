package de.quarian.weaver.di;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.annotation.NonNull;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.converter.CampaignConverter;
import de.quarian.weaver.dev.DemoDataSetInjector;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.theming.ThemeProvider;
import de.quarian.weaver.util.Logger;
import de.quarian.weaver.util.LoggingProvider;
import io.reactivex.Scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

// TODO: Use this class throughout unit tests for mocking
/**
 * Mocks for {@link ActivityModule} and {@link ApplicationModule}
 */
@SuppressWarnings("WeakerAccess")
public class ActivityModuleMocks<T extends Activity> {

    public ApplicationModule applicationModuleMock;
    public Context applicationContextMock; // TODO: This might be the same as activity context, check this
    public Handler globalHandlerMock;
    public WeaverDB weaverDBMock;
    public WeaverLayoutInflater weaverLayoutInflaterMock;
    public CampaignConverter campaignConverterMock;
    public CampaignService campaignServiceMock;
    public ThemeProvider themeProviderMock;
    public DemoDataSetInjector demoDataSetInjectorMock;
    public Scheduler ioSchedulerMock;
    public LoggingProvider loggingProviderMock;
    public Logger loggerMock;

    public T activityMock;
    public ActivityModule activityModuleMock;
    public ActivityPreconditionErrorHandler activityPreconditionErrorHandlerMock;

    public static <T extends Activity> ActivityModuleMocks<T> create(final Class<T> activityClass) {
        final T activityMock = mock(activityClass);
        return new ActivityModuleMocks<>(activityMock);
    }

    private ActivityModuleMocks(@NonNull final T activity) {
        applicationModuleMock = mock(ApplicationModule.class);

        applicationContextMock = mock(Context.class);
        when(applicationModuleMock.context()).thenReturn(applicationContextMock);

        globalHandlerMock = mock(Handler.class);
        when(applicationModuleMock.globalHandler()).thenReturn(globalHandlerMock);

        weaverDBMock = mock(WeaverDB.class);
        when(applicationModuleMock.weaverDB()).thenReturn(weaverDBMock);

        weaverLayoutInflaterMock = mock(WeaverLayoutInflater.class);
        when(applicationModuleMock.weaverLayoutInflater()).thenReturn(weaverLayoutInflaterMock);

        campaignConverterMock = mock(CampaignConverter.class);
        when(applicationModuleMock.campaignConverter()).thenReturn(campaignConverterMock);

        campaignServiceMock = mock(CampaignService.class);
        when(applicationModuleMock.campaignService(any(WeaverDB.class), any(SharedPreferences.class), any(CampaignConverter.class))).thenReturn(campaignServiceMock);

        demoDataSetInjectorMock = mock(DemoDataSetInjector.class);
        when(applicationModuleMock.demoDataSetInjector()).thenReturn(demoDataSetInjectorMock);

        ioSchedulerMock = mock(Scheduler.class);
        when(applicationModuleMock.ioScheduler()).thenReturn(ioSchedulerMock);

        loggerMock = mock(Logger.class);
        loggingProviderMock = mock(LoggingProvider.class);
        when(loggingProviderMock.getLogger(any())).thenReturn(loggerMock);
        when(applicationModuleMock.loggingProvider()).thenReturn(loggingProviderMock);

        // Activity Module
        activityMock = activity;
        activityModuleMock = mock(ActivityModule.class);

        when(activityModuleMock.activity()).thenReturn(activityMock);

        activityPreconditionErrorHandlerMock = mock(ActivityPreconditionErrorHandler.class);
        when(activityModuleMock.errorHandler(loggingProviderMock)).thenReturn(activityPreconditionErrorHandlerMock);

        themeProviderMock = mock(ThemeProvider.class);
        when(applicationModuleMock.themeProvider(weaverDBMock, loggingProviderMock)).thenReturn(themeProviderMock);
    }

    public void resetMocks() {
        reset(applicationModuleMock);
        reset(applicationContextMock);
        reset(globalHandlerMock);
        reset(weaverDBMock);
        reset(weaverLayoutInflaterMock);
        reset(campaignServiceMock);
        reset(themeProviderMock);
        reset(demoDataSetInjectorMock);
        reset(ioSchedulerMock);
        reset(loggingProviderMock);
        reset(loggerMock);

        reset(activityMock); // I don't really get the warning here (Warning:(95, 9) Unchecked generics array creation for varargs parameter)
        reset(activityModuleMock);
        reset(activityPreconditionErrorHandlerMock);
    }
}
