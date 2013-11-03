package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Request extends Model {

  @Id
  public Long id;
  public String title;
  public String description;
  public String targetDate;

  public Date createdAt;
  public Date updatedAt;


  public Request(String title, String description, String targetDate) {
    this.title = title;
    this.description = description;
    this.targetDate = targetDate;

    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  public static Finder<Long, Request> find
    = new Finder<Long, Request>(Long.class, Request.class);
  
  public static List<Request> all() {
    return find.findList();
  }
}
