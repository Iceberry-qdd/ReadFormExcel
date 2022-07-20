package model;

public class DataScheme {
    Coordinate coordinate;
    String type;
    String pattern;

    public DataScheme() {
    }

    public DataScheme(Coordinate coordinate, String type, String pattern) {
        this.coordinate = coordinate;
        this.type = type;
        this.pattern = pattern;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
