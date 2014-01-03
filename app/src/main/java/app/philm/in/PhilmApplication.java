package app.philm.in;


import android.app.Application;
import android.content.Context;

import app.philm.in.controllers.MainController;
import app.philm.in.controllers.MovieController;
import app.philm.in.controllers.UserController;
import app.philm.in.state.ApplicationState;
import app.philm.in.util.TypefaceManager;

public class PhilmApplication extends Application {

    public static PhilmApplication from(Context context) {
        return (PhilmApplication) context.getApplicationContext();
    }

    private ApplicationState mApplicationState;
    private MainController mMainController;

    @Override
    public void onCreate() {
        super.onCreate();

        final Container container = Container.getInstance(this);
        mApplicationState = new ApplicationState(container.getEventBus());

        UserController userController = new UserController(
                mApplicationState,
                container.getTraktClient(),
                container.getMultiThreadExecutor(),
                container.getAccountManagerHelper(),
                container.getAsyncDatabaseHelper());

        MovieController movieController = new MovieController(
                mApplicationState,
                container.getTraktClient(),
                container.getMultiThreadExecutor(),
                container.getAsyncDatabaseHelper());

        mMainController = new MainController(
                mApplicationState,
                userController,
                movieController,
                container.getAsyncDatabaseHelper());
    }

    public MainController getMainController() {
        return mMainController;
    }
}
