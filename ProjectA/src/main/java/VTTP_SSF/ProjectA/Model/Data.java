package VTTP_SSF.ProjectA.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Data {

    private Double intake;
    private Double burnt;
    private Double net;
    private LocalDate date;

    public JsonObject toJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return Json.createObjectBuilder()
                .add("date", this.date.format(formatter))
                .add("intake", this.intake)
                .add("burnt", this.burnt)
                .add("net", this.net)
                .build();
    }

    public static Data fromJson(JsonObject json) {
        Data data = new Data();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        data.setDate(LocalDate.parse(json.getString("date"), formatter));
        data.setIntake(json.getJsonNumber("intake").doubleValue());
        data.setBurnt(json.getJsonNumber("burnt").doubleValue());

        return data;
    }

    public Data(){
        this.intake = 0.0;
        this.burnt = 0.0;
        this.net = 0.0;
        this.date =LocalDate.now();
    }


    public Double getIntake() {
        return Double.valueOf(String.format("%.2f", intake)); 
    }

    public void setIntake(Double intake) {
        this.intake += intake;
        this.net += intake;
    }

    public Double getBurnt() {
        return Double.valueOf(String.format("%.2f", burnt)); 
    }

    public void setBurnt(Double burnt) {
        this.burnt += burnt;
        this.net -= burnt;
    }

    public Double getNet() {
        return Double.valueOf(String.format("%.2f", net)); 
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Data [intake=" + intake + ", burnt=" + burnt + ", net=" + net + ", date=" + date + "]";
    }

    

}
