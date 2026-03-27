package org.example;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Named("dotBean")
@SessionScoped
public class DotBean implements Serializable {


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
            System.err.println("Error in add(): " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void clear() {
        try {
            dotService.clearAllDots();
            refreshDots();
            dot = new CreateDto();
        } catch (Exception e) {
            System.err.println("Error in clear(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
      JSON для canvas.
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
     <h:inputHidden value="#{dotBean.dotsJson}">.

    public void setDotsJson(String ignored) {

    }
     */


    private void refreshDots() {
        dotsList = dotService.getAllDots();
    }
}