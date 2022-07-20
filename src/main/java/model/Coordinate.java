package model;

/**
 * @author Iceberry
 * @date 2022/7/20
 * @desc
 * @since 1.0
 */
public class Coordinate {
    String title;
    String content;

    public Coordinate() {
    }

    public Coordinate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
