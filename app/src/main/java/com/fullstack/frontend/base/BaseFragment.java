package com.fullstack.frontend.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<VM extends BaseViewModel<R>, R extends BaseRepository>
    extends Fragment {
  protected VM viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = getViewModel();
  }

  protected abstract VM getViewModel();

  protected abstract ViewModelProvider.Factory getFactory();

  protected abstract R getRepository();

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    viewModel.onCancel();
  }
}
