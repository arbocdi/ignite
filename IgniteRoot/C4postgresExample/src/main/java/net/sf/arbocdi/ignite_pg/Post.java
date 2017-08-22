/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ignite_pg;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author root
 */
@Data
public class Post implements Serializable {

    private static final long serialVersionUID = 0L;
    private String id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private String author;

    public Post() {
    }

    public Post(String id, String title, String description, LocalDate creationDate, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.author = author;
    }

}
