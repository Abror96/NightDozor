package com.era.nightdozor.mvp.presenters;

import com.era.nightdozor.mvp.contracts.ScannerContract;

public class ScannerPresenterImpl implements ScannerContract.Presenter, ScannerContract.Interactor.OnFinishedListener {

    private ScannerContract.View view;
    private ScannerContract.Interactor interactor;

    public ScannerPresenterImpl(ScannerContract.View view, ScannerContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onSendQrCalled(String qrCode, String token) {
        if (view != null) {
            view.showProgress();
        }
        interactor.sendQr(this, qrCode, token);
    }

    @Override
    public void onFinished() {
        if (view != null) {
            view.hideProgress();
            view.onSendQrSuccess();
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
