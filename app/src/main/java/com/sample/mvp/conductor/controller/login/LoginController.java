package com.sample.mvp.conductor.controller.login;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.sample.mvp.conductor.R;
import com.sample.mvp.conductor.base.BaseController;
import com.sample.mvp.conductor.controller.ViewPagerController;
import com.sample.mvp.conductor.controller.second.SecondController;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginController extends BaseController implements LoginView {

    LoginPresenter presenter;

    @BindView(R.id.et_userName)
    EditText etUserName;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_login, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        presenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.bt_ok)
    public void buttonClick(){
        String userName = etUserName.getText().toString();
        LoginModel model = new LoginModel(userName);
        presenter.validateInput(model);
    }


    @Override
    public void goToNext() {
        getRouter().pushController(RouterTransaction.with(new ViewPagerController())
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
