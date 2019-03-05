package com.lulian.Zaiyunbao.di.component;

import com.google.gson.Gson;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.di.module.AppModule;
import com.lulian.Zaiyunbao.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, HttpModule.class})
@Singleton
public interface AppComponent {

    ApiService getApiService();

    MyApplication getApplication();

    Gson getGson();
}
