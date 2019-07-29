package com.era.nightdozor.mvp.contracts;

public interface ScannerContract {

    interface View {

        void onSendQrSuccess();

        void showSnackbar(String message);

        void showProgress();

        void hideProgress();

    }

    interface Presenter {

        void onDestroy();

        void onSendQrCalled(String qrCode, String token);

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished();

            void onFailure(String message);

        }

        void sendQr(OnFinishedListener onFinishedListener, String qrCode, String token);

    }

}
