package com.axelor.apps.events.service;

import com.axelor.apps.events.db.Event;
import com.axelor.apps.events.db.repo.EventRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    @Inject
    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void startEvent(Event event) {
        event = eventRepository.find(event.getId());
        event.setStatus(EventRepository.STATUS_START);

        eventRepository.save(event);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void endEvent(Event event) {
        event = eventRepository.find(event.getId());
        event.setStatus(EventRepository.STATUS_END);

        eventRepository.save(event);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void cancelEvent(Event event) {
        event = eventRepository.find(event.getId());
        event.setStatus(EventRepository.STATUS_CANCEL);

        eventRepository.save(event);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void postponeEvent(Event event) {
        event = eventRepository.find(event.getId());
        event.setStatus(EventRepository.STATUS_POSTPONE);

        eventRepository.save(event);
    }
}
