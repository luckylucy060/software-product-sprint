// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.sps.data.userComment;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private List<userComment> comments;

  @Override
  public void init() {
    ;
  }
    
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    comments = new ArrayList<>();

    Query query = new Query("userComment");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {

      boolean ispublic = (boolean) entity.getProperty("public");
        String text = (String) entity.getProperty("content");
        String uname = (String) entity.getProperty("name");
        String timestamp=(String) entity.getProperty("time");
        userComment c = new userComment(timestamp, uname, text);
        comments.add(c);
    }   

    Gson gson = new Gson();
    String json = gson.toJson(comments);
    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonUsingGson(List<String> s) {
    Gson gson = new Gson();
    String json = gson.toJson(s);
    return json;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form. 
 
    String text = getParameter(request, "text-input", "");
    String name = getParameter(request, "username", "");
    String email = getParameter(request, "useremail", "");
    boolean ispublic = Boolean.parseBoolean(getParameter(request, "public", "false"));
    boolean anonymous = Boolean.parseBoolean(getParameter(request, "anonymous", "false"));
    if(anonymous){
        name="Someone";
        email="";
    }

    Entity commentEntity = new Entity("userComment");
    commentEntity.setProperty("content", text);
    commentEntity.setProperty("name", name);
    commentEntity.setProperty("email", email);
    commentEntity.setProperty("public", ispublic);

    Date day=new Date();    
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    commentEntity.setProperty("time", df.format(day));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/comment.html");

  }
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}

