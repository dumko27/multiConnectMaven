package com.multiconnect.mapping;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * POJO для тела тега <Entry>.
 *
 * @author Novikov Dmitry
 */
@Entity
@Table(name = "entries")
public class Entry implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Cтрока длиной до 1024 символов.
     */
    @Column(name = "content", length = 1024)
    private String content;
    /**
     * Дата создания записи.
     */
    @Column(name = "date")
    @Temporal(value = TemporalType.DATE)
    private Date date;

    public Entry() {
    }

    public Entry(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Entry {" + "content=" + content + ", date=" + date + '}';
    }

}
