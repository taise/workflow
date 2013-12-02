package models;

import java.util.*;

import models.User;
import org.junit.*;
import play.libs.Yaml;
import com.avaje.ebean.*;
import static org.fest.assertions.api.Assertions.assertThat;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class UserTest {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }
  
  @Test
  public void constructor() {
    Map<String,String> params = defaultParams();

    User user = new User(
        params.get("name"),
        params.get("email"),
        params.get("password"),
        params.get("company"),
        params.get("division"),
        params.get("post")
        );
    assertUserModel(user, params);
  }

  @Test
  public void findByEmail() {
    Ebean.save((List) Yaml.load("testData/users.yml"));
    String email = "alice@email.com";

    User user = User.findByEmail(email);
    assertNotNull(user);
    assertEquals(email, user.email);
  }

  @Test
  public void successLogin() {
    Ebean.save((List) Yaml.load("testData/users.yml"));
    String email = "alice@email.com";
    String password = "alice1234!";

    User user = User.login(email, password);
    assertNotNull(user);
    assertEquals(email, user.email);
  }

  @Test
  public void faildLogin() {
    Ebean.save((List) Yaml.load("testData/users.yml"));
    String email = "alice1@email.com";
    String password = "alice1234!";

    User user = User.login(email, password);
    assertNull(user);
  }

  public Map<String,String> defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("name","Alice");
    params.put("email","alice@email.com");
    params.put("password","a1234pass!");
    params.put("company","StarBucks");
    params.put("division","Shinjuku branch");
    params.put("post","boss");
    return params;
  }

  public void assertUserModel(User user, Map<String,String> params) {
    assertThat(user.name).isEqualTo(params.get("name"));
    assertThat(user.email).isEqualTo(params.get("email"));
    assertThat(user.password).isEqualTo(params.get("password"));
    assertThat(user.company).isEqualTo(params.get("company"));
    assertThat(user.division).isEqualTo(params.get("division"));
    assertThat(user.post).isEqualTo(params.get("post"));
    assertThat(user.createdAt).isToday();
    assertThat(user.updatedAt).isToday();
  }
}
