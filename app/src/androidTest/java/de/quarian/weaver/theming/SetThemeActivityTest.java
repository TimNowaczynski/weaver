package de.quarian.weaver.theming;

import android.content.Intent;

import junit.framework.TestCase;

import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import de.quarian.weaver.WeaverTestRule;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ActivityModuleMocks;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.SharedPreferencesModule;
import de.quarian.weaver.test.EventRecorder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class SetThemeActivityTest extends TestCase {

    private static final EventRecorder eventRecorder = new EventRecorder();
    private static final DBConverters.ImageBlobConverter imageBlobConverter = new DBConverters.ImageBlobConverter();
    private static final long DUMMY_CAMPAIGN_ID = 1L;

    @Rule
    public WeaverTestRule<SetThemeActivity> activityTestRule = new WeaverTestRule<>(SetThemeActivity.class, true);

    private static ActivityModuleMocks<SetThemeActivity> setThemeActivityModuleMocks;

    @Mock
    public ThemeProvider themeProvider;

    private Theme theme;

    @BeforeClass
    public static void setUpBeforeClass() {
        EventBus.getDefault().register(eventRecorder);

        final DependencyInjector dependencyInjector = DependencyInjector.get();
        setThemeActivityModuleMocks = ActivityModuleMocks.create(SetThemeActivity.class);
        dependencyInjector.setApplicationMockModuleProvider(() -> setThemeActivityModuleMocks.applicationModuleMock);
        dependencyInjector.setActivityMockModuleProvider(() -> setThemeActivityModuleMocks.activityModuleMock);
        dependencyInjector.setSharedPreferencesMockModuleProvider(() -> mock(SharedPreferencesModule.class));
    }

    @AfterClass
    public static void tearDownAfterClass() {
        final DependencyInjector dependencyInjector = DependencyInjector.get();
        dependencyInjector.setUseMocks(false);

        EventBus.getDefault().unregister(eventRecorder);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(setThemeActivityModuleMocks.themeProviderMock.getThemeForCampaign(DUMMY_CAMPAIGN_ID)).thenReturn(theme);
        prepareCustomTheme();
    }

    @After
    public void after() {
        setThemeActivityModuleMocks.resetMocks();
        eventRecorder.reset();
    }

    private void prepareCustomTheme() {
        theme = new Theme();
        theme.presetId = Theme.PRESET_ID_CUSTOM;

        theme.fontId = 2; // = DEFAULT_PRESET

        theme.bannerBackgroundImage = imageBlobConverter.convertPrimitiveToBytes("image".getBytes());
        theme.bannerBackgroundImageType = "image/png";

        theme.actionColorA = 0;
        theme.actionColorR = 1;
        theme.actionColorG = 2;
        theme.actionColorB = 3;

        theme.screenBackgroundColorA = 10;
        theme.screenBackgroundColorR = 20;
        theme.screenBackgroundColorG = 30;
        theme.screenBackgroundColorB = 40;

        theme.itemBackgroundColorA = 100;
        theme.itemBackgroundColorR = 200;
        theme.itemBackgroundColorG = 300;
        theme.itemBackgroundColorB = 400;

        theme.backgroundFontColorA = 1000;
        theme.backgroundFontColorR = 2000;
        theme.backgroundFontColorG = 3000;
        theme.backgroundFontColorB = 4000;

        theme.itemFontColorA = 10000;
        theme.itemFontColorR = 20000;
        theme.itemFontColorG = 30000;
        theme.itemFontColorB = 40000;

        when(themeProvider.getThemeForCampaign(DUMMY_CAMPAIGN_ID)).thenReturn(theme);
    }

    @Test
    public void testGetThemeAndPostInitializeTask() {
        when(setThemeActivityModuleMocks.applicationModuleMock.themeProvider(setThemeActivityModuleMocks.weaverDBMock, setThemeActivityModuleMocks.loggingProviderMock).getThemeForCampaign(DUMMY_CAMPAIGN_ID)).thenReturn(theme);

        final Intent intent = new Intent();
        intent.putExtra(SetThemeActivity.EXTRA_CAMPAIGN_ID, DUMMY_CAMPAIGN_ID);
        activityTestRule.launchActivity(intent);
        verify(setThemeActivityModuleMocks.applicationModuleMock.themeProvider(setThemeActivityModuleMocks.weaverDBMock, setThemeActivityModuleMocks.loggingProviderMock)).getThemeForCampaign(DUMMY_CAMPAIGN_ID);
    }
}
