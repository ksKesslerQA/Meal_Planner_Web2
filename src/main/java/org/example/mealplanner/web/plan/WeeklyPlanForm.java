package org.example.mealplanner.web.plan;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeeklyPlanForm {

    private Map<String, DayPlanForm> days = new LinkedHashMap<>();

    public Map<String, DayPlanForm> getDays() {
        return days;
    }

    public void setDays(Map<String, DayPlanForm> days) {
        this.days = days;
    }
}
