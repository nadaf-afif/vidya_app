package com.sudosaints.cmavidya.util;

import android.app.Application;

import com.sudosaints.cmavidya.CMAVidyaApp;

import de.greenrobot.event.EventBus;

public final class BusProvider {

    private static BusProvider instance;
    private EventBus eventBus;
    private CMAVidyaApp application;

    private BusProvider() {
    }

    public static synchronized BusProvider init(CMAVidyaApp application) {
        if(null == instance) {
            instance = new BusProvider();
            instance.setApplication(application);
            instance.setEventBus(new EventBus());
        }
        return instance;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(CMAVidyaApp application) {
        this.application = application;
    }
}