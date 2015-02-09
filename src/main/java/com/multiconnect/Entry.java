package com.multiconnect;

/**
 *
 * @author Novikov Dmitry
 */
public class Entry {

    private String content;

    private String creationDate;

    public Entry() {
    }

    public Entry(String content, String creationDate) {
        this.content = content;
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Entry {" + "content=" + content + ", creationDate=" + creationDate + '}';
    }

}
