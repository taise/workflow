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
  //TODO: requestDate => targetDate
  public String requestDate;

  public Date createdAt;
  public Date updatedAt;


  public Request(String title, String description, String requestDate) {
    this.title = title;
    this.description = description;
    this.requestDate = requestDate;

    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  public static Finder<Long, Request> find
    = new Finder<Long, Request>(Long.class, Request.class);
  
  public static List<Request> all() {
    return find.findList();
  }
}
