package com.axelor.apps.events.web;

import com.axelor.apps.events.db.Event;
import com.axelor.apps.events.service.EventService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

import javax.inject.Inject;

public class EventController {
    private final EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public void startEvent(ActionRequest request, ActionResponse response){
        Event event = request.getContext().asType(Event.class);
        eventService.startEvent(event);
        response.setReload(true);
    }

    public void endEvent(ActionRequest request, ActionResponse response){
        Event event = request.getContext().asType(Event.class);
        eventService.endEvent(event);
        response.setReload(true);
    }

    public void postponeEvent(ActionRequest request, ActionResponse response){
        Event event = request.getContext().asType(Event.class);
        eventService.postponeEvent(event);
        response.setReload(true);
    }

    public void cancelEvent(ActionRequest request, ActionResponse response){
        Event event = request.getContext().asType(Event.class);
        eventService.cancelEvent(event);
        response.setReload(true);
    }


}
