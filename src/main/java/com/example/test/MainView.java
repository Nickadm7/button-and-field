package com.example.test;

import com.example.test.model.InputNumber;
import com.example.test.model.HistoryGrid;
import com.example.test.service.InputNumberService;
import com.github.rjeschke.txtmark.Processor;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route("main")
public class MainView extends VerticalLayout {
    private final InputNumberService inputNumberService;
    private final HistoryGrid historyGrid;
    private final Grid<HistoryGrid.QueryHistory> grid;
    private Registration registration;
    InputNumber inputNumber;

    public MainView(InputNumberService inputNumberService, HistoryGrid historyGrid) {
        this.inputNumberService = inputNumberService;
        this.historyGrid = historyGrid;
        grid = new Grid<>();
        grid.setItems(historyGrid.getHistories());
        grid.addColumn(new ComponentRenderer<>(message -> new Html(renderRow(message)))).setHeader("History");

        Binder<InputNumber> binder = new Binder<>(InputNumber.class);
        TextField titleField = new TextField();
        titleField.setAllowedCharPattern("[0-9-]");
        titleField.setPattern("[-]?[0-9]{1,}");
        titleField.setMaxLength(19);
        titleField.setValue("0");
        binder.forField(titleField)
                .bind(
                        InputNumber::getInputNumber,
                        InputNumber::setInputNumber);
        inputNumber = new InputNumber(0L, "0");

        add(
                new H3("Тестовое задание: Добавить кнопку и текстовое поле на страницу."),
                new NativeLabel("По нажатию на кнопку - значение в поле увеличивается на 1. "),
                new NativeLabel("Значение поля можно изменить руками, вписав нужное значение."),
                new NativeLabel("Изменения должны сохранятся в БД автоматически при каждом изменении."),
                new HorizontalLayout() {{
                    add(titleField,
                            new Button("Plus one") {{
                                addClickListener(click -> {
                                    try {
                                        binder.writeBean(inputNumber);
                                    } catch (ValidationException e) {
                                        throw new RuntimeException(e);
                                    }
                                    inputNumberService.addInputNumber(inputNumber);
                                    historyGrid.addRecord(titleField.getValue());
                                    binder.readBean(inputNumberService.plusOne(inputNumber));
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );
                }},
                grid
        );
    }

    public void onMessage(HistoryGrid.QueryEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();
        }
    }

    private String renderRow(HistoryGrid.QueryHistory message) {
        return Processor.process(String.format("**%s**", message.getNumber()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = historyGrid.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}