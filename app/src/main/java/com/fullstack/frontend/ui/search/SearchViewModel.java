package com.fullstack.frontend.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.SearchRequest;
import com.fullstack.frontend.Retro.SearchResponse;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;
    private MutableLiveData<SearchRequest> searchResult = new MutableLiveData<>();

    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void setSearchResult(SearchRequest request) {
        searchResult.setValue(request);
    }

    public void setUserId(int userId) {
        searchRepository.setUserId(userId);
    }

    public void setKey(String key) {
        searchRepository.setKey(key);
    }

    public MutableLiveData<List<SearchResponse>> getSearchResult() {
        final int userId = searchRepository.getUserId();
        final String key = searchRepository.getKey();
        return searchRepository.searchResult();
    }



}
