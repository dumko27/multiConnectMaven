package com.multiconnect.mapping;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Type;

/**
 * POJO для тела тега <Entry>.
 *
 * @author Novikov Dmitry
 */
//@Entity
public class Entry implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Cтрока длиной до 1024 символов.
     */
    //@Column(name = "content", length = 1024)
    private String content;
    /**
     * Дата создания записи.
     */
//    @Column(name = "creationDate")
//    @Type(type="date")
    private Date creationDate;

    public Entry() {
    }

    public Entry(String content, Date creationDate) {
        this.content = content;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
