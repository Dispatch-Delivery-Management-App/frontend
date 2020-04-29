package com.fullstack.frontend.base;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<R extends BaseRepository> extends ViewModel {
  protected final R repository;

  protected BaseViewModel(R baseRepository) {
    repository = baseRepository;
  }

  public void onCancel(){
    repository.onCancel();
  }
}
