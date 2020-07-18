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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.logInfo;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/log")
public class LogServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/afterlog"; //back to comment.html
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

      logInfo t = new logInfo(userEmail,logoutUrl);
      Gson gson = new Gson();
      String json = gson.toJson(t);
      // Send the JSON as the response
      response.setContentType("application/json;");
      response.getWriter().println(json);
    } else {
      String urlToRedirectToAfterUserLogsIn = "/afterlog";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      String userEmail = "n";
      logInfo t = new logInfo(userEmail,loginUrl);
      Gson gson = new Gson();
      String json = gson.toJson(t);

      // Send the JSON as the response
      response.setContentType("application/json;");
      response.getWriter().println(json);

    }
  }
}