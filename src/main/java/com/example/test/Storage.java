package com.example.test;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {
    @Getter
    private final Queue<QueryHistory> histories = new ConcurrentLinkedQueue<>();
    private final ComponentEventBus eventBus = new ComponentEventBus(new Div());

    @Getter
    @Setter
    @AllArgsConstructor
    public static class QueryHistory {
        private String number;
    }

    public static class QueryEvent extends ComponentEvent<Div> {
        public QueryEvent() {
            super(new Div(), false);
        }
    }

    public void addRecord(String number) {
        histories.add(new QueryHistory(number));
        eventBus.fireEvent(new QueryEvent());
    }

    public Registration attachListener(ComponentEventListener<QueryEvent> messageListener) {
        return eventBus.addListener(QueryEvent.class, messageListener);
    }
}