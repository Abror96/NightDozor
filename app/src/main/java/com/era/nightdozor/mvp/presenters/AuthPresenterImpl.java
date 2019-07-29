package com.era.nightdozor.mvp.presenters;

import com.era.nightdozor.mvp.contracts.AuthContract;

public class AuthPresenterImpl implements AuthContract.Presenter, AuthContract.Interactor.OnFinishedListener {

    private AuthContract.View view;
    private AuthContract.Interactor interactor;

    public AuthPresenterImpl(AuthContract.View view, AuthContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onAuthCalled(String login, String password) {
        if (view != null) {
            view.showProgress();
        }
        interactor.onAuth(this, login, password);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onFinished(String token) {
        if (view != null) {
            view.hideProgress();
            view.authSuccess(token);
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.hideProgress();
            view.showSnackbar(message);
        }
    }
}
