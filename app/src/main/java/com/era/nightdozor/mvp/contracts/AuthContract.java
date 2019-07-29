package com.era.nightdozor.mvp.contracts;

public interface AuthContract {

    interface View {

        void authSuccess(String token);

        void showSnackbar(String message);

        void showProgress();

        void hideProgress();

    }

    interface Presenter {

        void onAuthCalled(String login, String password);

        void onDestroy();

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished(String token);

            void onFailure(String message);

        }

        void onAuth(OnFinishedListener onFinishedListener, String login, String password);

    }

}
