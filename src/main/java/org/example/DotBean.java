package org.example;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Setter
@Getter
@Named("dotBean")
@SessionScoped
public class DotBean implements Serializable {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    private CreateDto dot;

    private ViewDto lastDot;

    private List<ViewDto> dotsList;

    private int timezone;

    @Inject
    private DotService dotService;

    @PostConstruct
    public void init() {
        dot = new CreateDto();
        refreshDots();
    }

    public void add() {
        try {
            lastDot = dotService.processAndSaveDot(dot);

            refreshDots();

            CreateDto newDot = new CreateDto();
            if (lastDot != null) {
                newDot.setR(lastDot.getR());
            }
            dot = newDot;

        } catch (Exception e) {
            System.err.println(BUNDLE.getString("error.add") + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            dotService.clearAllDots();
            refreshDots();
            dot = new CreateDto();
        } catch (Exception e) {
            System.err.println(BUNDLE.getString("error.clear") + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * JSON для canvas.
     *
     * @return JSON-массив точек
     */
    public String getDotsJson() {
        if (dotsList == null || dotsList.isEmpty()) {
            return "[]";
        }

        return dotsList.stream()
                .map(ViewDto::toJSON)
                .collect(java.util.stream.Collectors.joining(",", "[", "]"));
    }

    /**
     * Setter нужен для JSF-привязки свойства dotsJson.
     *
     * @param ignored входное значение от JSF
     */
    public void setDotsJson(String ignored) {
        // JSF setter
    }

    private void refreshDots() {
        dotsList = dotService.getAllDots();
    }
}