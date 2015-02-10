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
@Table(name = "Entries")
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * Cтрока длиной до 1024 символов.
     */
    @Column(name = "content", length = 1024)
    private String content;
    /**
     * Дата создания записи.
     */
    @Column(name = "creationDate")
    @Temporal(value = TemporalType.DATE)
    private Date creationDate;

    public Entry() {
    }

    public Entry(String content, Date creationDate) {
        this.content = content;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Entry {" + "content=" + content + ", creationDate=" + creationDate + '}';
    }

}
