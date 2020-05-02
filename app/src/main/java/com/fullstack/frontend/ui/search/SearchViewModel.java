package com.fullstack.frontend.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.SearchRequest;
import com.fullstack.frontend.Retro.SearchResponse;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;

    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void setKey(String key) {
        searchRepository.setKey(key);
    }

    public MutableLiveData<List<SearchResponse>> getSearchResult() {
        return searchRepository.searchResult();
    }
}
