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
  public String name;
  public String email;
  public String password;
  public String company;
  public String division;
  public String post;

  public Date createdAt;
  public Date updatedAt;

  public User(String name, String email, String password,
              String company, String division, String post) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.company = company;
    this.division = division;
    this.post = post;

    this.createdAt = new Date();
    this.updatedAt = new Date();
  }
}
