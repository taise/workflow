package models;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import models.User;

import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.*;

@Entity
public class Request extends Model {

  @Id
  public Long id;

  @Required
  public String title;

  @Required
  public String description;

  @Required
  @Formats.DateTime(pattern="yyyy-MM-dd")
  public String targetDate;

  @OneToOne
  @PrimaryKeyJoinColumn
  public User requester;

  public Date createdAt;
  public Date updatedAt;


  public Request(String title, String description, String targetDate, User requester) {
    this.title = title;
    this.description = description;
    this.targetDate = targetDate;

    this.requester = requester;

    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  //public List<ValidationError> validate() {
  //  List<ValidationError> errors = new ArrayList<ValidationError>();
  //  if (title == null || title.length() == 0) {
  //  errors.add(
  //      new ValidationError("title", "タイトルが入力されていません。")
  //      );
  //  }
  //  return errors.isEmpty() ? null : errors;
  //}

  @Override
  public void save() {
    this.createdAt = new Date();
    this.updatedAt = new Date();
    super.save();
  }

  public static Finder<Long, Request> find
    = new Finder<Long, Request>(Long.class, Request.class);
  
  public static List<Request> all() {
    return find.findList();
  }

  public static Integer count() {
    return find.findList().size();
  }
}
