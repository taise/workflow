package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class User extends Model {

  @Id
  public Long id;
  public String userId;
  public String name;
  public String password;
  public String company;
  public String division;
  public String post;

  public Date createdAt;
  public Date updatedAt;

  public User(String userId, String name, String password,
              String company, String division, String post) {
    this.userId = userId;
    this.name = name;
    this.password = password;
    this.company = company;
    this.division = division;
    this.post = post;

    this.createdAt = new Date();
    this.updatedAt = new Date();
  }
}
