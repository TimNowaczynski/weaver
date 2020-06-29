package de.quarian.weaver.util;

import android.content.Context;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.R;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ErrorHandlerUnitTest {

    @Mock
    private Context context;

    @Mock
    private AndroidToastHandler androidToastHandler;

    @Mock
    private GenericDialogBuilder genericDialogBuilder;

    private ErrorHandler errorHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        errorHandler = new ErrorHandler(context, androidToastHandler);
    }

    @Test
    public void testRequireHardFails() {
        errorHandler.requireHard(() -> false, genericDialogBuilder);
        verify(genericDialogBuilder, times(1)).showDialog(context);
    }

    @Test
    public void testRequireSoftFails() {
        errorHandler.requireSoft(() -> false, R.string.generic_error_unknown);
        verify(androidToastHandler, times(1)).showToast(R.string.generic_error_unknown, Toast.LENGTH_LONG);
    }

    @Test
    public void testRequireHardSucceeds() {
        errorHandler.requireHard(() -> true, genericDialogBuilder);
        verify(genericDialogBuilder, never()).showDialog(context);
    }

    @Test
    public void testRequireSoftSucceeds() {
        errorHandler.requireSoft(() -> true, R.string.generic_error_unknown);
        verify(androidToastHandler, never()).showToast(R.string.generic_error_unknown, Toast.LENGTH_LONG);
    }
}
