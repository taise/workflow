package models;

import models.Request;
import models.User;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.util.*;
import play.libs.Yaml;

import static play.test.Helpers.*;

public class RequestTest {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
    Ebean.save((List) Yaml.load("testData/requests.yml"));
  }

  @Test
  public void constructor() {
    Map<String,String> params = defaultParams();
    User requester = User.findByEmail("steve@email.com");

    Request req = new Request(
        params.get("title"),
        params.get("description"),
        params.get("targetDate"),
        requester
        );

    assertRequestModel(req, params);
    assertEquals(requester.name, req.requester.name);
  }

  @Test
  public void all() {
    List<Request> requests = Request.all();
    assertEquals(3, requests.size());
    assertEquals("Request test", requests.get(0).title);
  }

  @Test
  public void count() {
    assertSame(3, Request.count());
  }

  @Test
  public void save() {
    Map<String,String> params = defaultParams();
    User requester = User.findByEmail("steve@email.com");

    Request req = new Request();
    req.title = params.get("title");
    req.description = params.get("description");
    req.targetDate = params.get("targetDate");
    req.requester = requester;
    req.save();

    assertNotNull(req.id);
    Request createdReq
      = Request.find.byId(req.id);
    assertRequestModel(createdReq, params);
    assertEquals(requester.name, createdReq.requester.name);
  }

  public Map defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("title","Request test");
    params.put("description","tryNewRequest");
    params.put("targetDate","2013-11-03");
    return params;
  }

  public void assertRequestModel(Request req, Map<String,String> params) {
    assertEquals(params.get("title"), req.title);
    assertEquals(params.get("description"), req.description);
    assertEquals(params.get("targetDate"), req.targetDate);
    assertThat(new Date(), greaterThanOrEqualTo(req.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(req.updatedAt));
  }
}
