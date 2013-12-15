package app.zelper.controller;

public class JsonTreeResponse {

    private Object children;
    private String text;

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
