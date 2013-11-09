package models;

import models.User;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.util.*;
import play.libs.Yaml;

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
        params.get("userId"),
        params.get("name"),
        params.get("company"),
        params.get("division"),
        params.get("post")
        );
  }

  public Map defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("userId","000001");
    params.put("name","Alice");
    params.put("company","StarBucks");
    params.put("division","Shinjuku branch");
    params.put("post","boss");
    return params;
  }

  public void assertUserModel(User user, Map<String,String> params) {
    assertEquals(params.get("userId"), user.userId);
    assertEquals(params.get("name"), user.name);
    assertEquals(params.get("company"), user.company);
    assertEquals(params.get("division"), user.division);
    assertEquals(params.get("post"), user.post);
    assertThat(new Date(), greaterThanOrEqualTo(user.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(user.updatedAt));
  }
}
