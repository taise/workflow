package models;

import java.util.*;

import models.User;
import org.junit.*;
import play.libs.Yaml;
import com.avaje.ebean.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
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

  public Map defaultParams() {
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
    assertEquals(params.get("name"), user.name);
    assertEquals(params.get("email"), user.email);
    assertEquals(params.get("password"), user.password);
    assertEquals(params.get("company"), user.company);
    assertEquals(params.get("division"), user.division);
    assertEquals(params.get("post"), user.post);
    assertThat(new Date(), greaterThanOrEqualTo(user.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(user.updatedAt));
  }
}
