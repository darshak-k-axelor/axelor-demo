package com.axelor.apps.events.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.events.service.EventService;
import com.axelor.apps.events.service.EventServiceImpl;

public class EventsModuleConfiguration extends AxelorModule {
    @Override
    protected void configure() {
        bind(EventService.class).to(EventServiceImpl.class);
    }

}
