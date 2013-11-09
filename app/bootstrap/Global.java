package bootstrap;

import play.*;
import play.libs.*;

import java.util.*;
import com.avaje.ebean.*;

import models.User;


public class Global extends GlobalSettings {
  public void onStart(Application app) {
    InitialData.insert(app);
  }

  static class InitialData {
    public static void insert(Application app) {
      if(User.find.findList().size() == 0) {
        Ebean.save((List) Yaml.load("initial-data.yml"));
      }
    }
  }
}

