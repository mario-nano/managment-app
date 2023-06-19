package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Milestone implements Serializable {
    private final String mileName;
    private final LocalDate mileDate;
    private final String mileStatus;

    public Milestone(String mileName, LocalDate mileDate, String mileStatus) {
        this.mileName = mileName;
        this.mileDate = mileDate;
        this.mileStatus = mileStatus;
    }

    public String getMileName() {
        return mileName;
    }

    public LocalDate getMileDate() {
        return mileDate;
    }

    public String getMileStatus(){

        return mileStatus;
    }

    public String toString() {
        return String.format("[%s]<<>>[%s]<<>>[%s]", getMileName(), getMileDate(), getMileStatus());
    }
}
