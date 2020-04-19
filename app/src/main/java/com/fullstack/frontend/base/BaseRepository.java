package com.fullstack.frontend.base;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;

public abstract class BaseRepository {
    protected final ApiInterface plansRequestApi =
            ApiClient.getClient().create(ApiInterface.class);
//    protected final AppDatabase database = TinApplication.getDatabase();

    private AsyncTask asyncTask;


//    @SuppressLint("StaticFieldLeak")
//    public LiveData<FavoriteEvent> favoriteNews(FavoriteEvent favoriteEvent) {
//        MutableLiveData<FavoriteEvent> liveData = new MutableLiveData<>();
//        asyncTask =
//                new AsyncTask<Void, Void, Boolean>() {
//                    @Override
//                    protected Boolean doInBackground(Void... voids) {
//                        try {
//                            database.newsDao().insertNews(favoriteEvent.news);
//                        } catch (Exception e) {
//                            return false;
//                        }
//                        return true;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Boolean isSuccess) {
//                        favoriteEvent.setSuccess(isSuccess);
//                        liveData.postValue(favoriteEvent);
//                    }
//                }.execute();
//        return liveData;
//    }

protected void onCancel() {
    if (asyncTask != null) {
        asyncTask.cancel(true);
    }
}

}
