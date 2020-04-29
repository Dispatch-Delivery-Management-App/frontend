package com.fullstack.frontend.onboard.register;

import android.util.Log;
import android.widget.Toast;

import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.base.RemoteRequestListener;
import com.fullstack.frontend.util.Util;

public class RegisterViewModel extends BaseViewModel<RegisterModel> {

    private RemoteRequestListener remoteRequestListener = null;

    protected RegisterViewModel(RegisterModel registerModel) {
        super(registerModel);
    }

    public void setRemoteRequestListener(RemoteRequestListener remoteRequestListener) {
        this.remoteRequestListener = remoteRequestListener;
    }

    public void setUsername(String username) { repository.setUsername(username); }

    public void setEmail(String email) {
        repository.setEmail(email);
    }

    public void setPassword(String password) {
        repository.setPassword(password);
    }

//    public void setCpassword(String cpassword) { repository.setcPassword(cpassword); }

    public void Register() {
        final String username = repository.getUsername();
        final String email = repository.getEmail();
        final String password = repository.getPassword();
//        final String cpassword = repository.getcPassword();
        if (username == null) {
            remoteRequestListener.onFailure("please enter user Name");
            return;
        } else if (email == null || !Util.isValidEmail(email)) {
            remoteRequestListener.onFailure("please enter a valid email");
            return;
        } else if (password == null) {
            remoteRequestListener.onFailure("please enter password");
            return;
//        } else if (cpassword != password) {
//            remoteRequestListener.onFailure("password doesn't match");
//            return;
        } else {
            remoteRequestListener.onSuccess(repository.userRegister());
        }
    }
}



















