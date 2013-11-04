package models;

import models.Request;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.util.*;
import play.libs.Yaml;

import play.test.WithApplication;
import static play.test.Helpers.*;

public class RequestTest extends WithApplication {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void constructor() {
    Map<String,String> params = defaultParams();

    Request req = new Request(
        params.get("title"),
        params.get("description"),
        params.get("targetDate")
        );

    assertEquals(params.get("title"), req.title);
    assertEquals(params.get("description"), req.description);
    assertEquals(params.get("targetDate"), req.targetDate);

    //TODO: assertDatesAlmostEqual(new Date(), req.createdAt);
    assertThat(new Date(), greaterThanOrEqualTo(req.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(req.updatedAt));
  }

  @Test
  public void all() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    List<Request> requests = Request.all();
    assertEquals(3, requests.size());
    assertEquals("Request test", requests.get(0).title);
  }

  @Test
  public void count() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    assertSame(3, Request.count());
  }

  @Test
  public void save() {
    Map<String,String> params = defaultParams();

    Request req = new Request();
    req.title = params.get("title");
    req.description = params.get("description");
    req.targetDate = params.get("targetDate");
    req.save();

    Request createdReq
      = Request.find.where()
                    .orderBy("id desc")
                    .findUnique();

    assertEquals(params.get("title"), createdReq.title);
    assertEquals(params.get("description"), createdReq.description);
    assertEquals(params.get("targetDate"), createdReq.targetDate);
    assertThat(new Date(), greaterThanOrEqualTo(createdReq.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(createdReq.updatedAt));
  }


  public Map defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("title","Request test");
    params.put("description","tryNewRequest");
    params.put("targetDate","2013-11-03");
    return params;
  }
}
