package models;

import java.util.Date;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Request extends Model {

  @Id
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
}
