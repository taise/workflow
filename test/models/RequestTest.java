package models;

import models.Request;
import models.User;

import org.junit.*;
import static org.fest.assertions.api.Assertions.assertThat;

import com.avaje.ebean.*;

import java.util.*;
import play.libs.Yaml;
import play.data.*;

import static play.test.Helpers.*;
import static play.data.Form.*;

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
    assertThat(req.requester.name).isEqualTo(requester.name);
  }

  @Test
  public void all() {
    List<Request> requests = Request.all();
    assertThat(requests.size()).isEqualTo(3);
    assertThat(requests.get(0).title).isEqualTo("Request test");
  }

  @Test
  public void count() {
    assertThat(Request.count()).isEqualTo(3);
  }

  @Test
  public void save() {
    Map<String,String> params = defaultParams();
    User requester = User.findByEmail("steve@email.com");

    Request req = new Request(
        params.get("title"),
        params.get("description"),
        params.get("targetDate"),
        requester
        );
    req.save();

    assertThat(req.id).isNotNull();
    Request createdReq
      = Request.find.byId(req.id);
    assertRequestModel(createdReq, params);
    assertThat(createdReq.requester.name).isEqualTo(requester.name);
  }

  @Test(expected = IllegalStateException.class)
  public void validate() {
    Form<Request> requestForm = form(Request.class);
    requestForm.get();
  }

  public Map<String, String> defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("title","Request test");
    params.put("description","tryNewRequest");
    params.put("targetDate","2013-11-03");
    return params;
  }

  public void assertRequestModel(Request req, Map<String,String> params) {
    assertThat(req.title).isEqualTo(params.get("title"));
    assertThat(req.description).isEqualTo(params.get("description"));
    assertThat(req.targetDate).isEqualTo(params.get("targetDate"));
    assertThat(req.createdAt).isToday();
    assertThat(req.updatedAt).isToday();
  }
}
