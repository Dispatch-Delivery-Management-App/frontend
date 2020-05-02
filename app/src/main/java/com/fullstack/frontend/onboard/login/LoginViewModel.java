package com.fullstack.frontend.onboard.login;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.base.RemoteRequestListener;
import com.fullstack.frontend.util.Util;

public class LoginViewModel extends BaseViewModel<LoginModel> {

    private RemoteRequestListener remoteRequestListener = null;

    protected LoginViewModel(LoginModel loginModel) {
        super(loginModel);
    }

    public void setRemoteRequestListener(RemoteRequestListener remoteRequestListener) {
        this.remoteRequestListener = remoteRequestListener;
    }

    public void setUserName(String userName) {
        repository.setUserName(userName);
    }

    public void setPassword(String password) {
        repository.setPassWord(password);
    }

    public void Login() {
        final String userName = repository.getUserName();
        final String password = repository.getPassWord();
        if (userName == null) {
            remoteRequestListener.onFailure("please enter user name");
            return;
        }
        if (password.isEmpty()) {
            remoteRequestListener.onFailure("Please enter password");
            return;
        }
        remoteRequestListener.onSuccess(repository.userLogin());

    }
}
