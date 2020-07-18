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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function jump_to_github(){
    window.location.href='https://github.com/luckylucy060';
}
function jump_to_resume(){
    window.location.href='JiayuLu.pdf';
}

function jump_to_comment(){
    window.location.href='/comment.html';
}

var hasLogIn = true;
function loadAllComments() {

   fetch('/data').then(response => response.json()).then((comments) => {

    const ListElement = document.getElementById('comment-container');
    ListElement.innerHTML = '';
    for (i=0; i<comments.length; i++) {       
        ListElement.appendChild(
        createListElement(comments[i]));
    }
   });

   fetch('/log').then(response => response.json()).then((info) => {
      if (info.email === "n"){
        //not login
        var x=document.getElementById('log');
        x.innerHTML="Please login <a href=\"" + info.logUrl + "\">here</a> to make comments." 
        var y=document.getElementById('submit');
        y.style.opacity=0.6;
        y.style.cursor="not-allowed";
        
      }
      else {
        //login
        document.getElementsByName("username")[0].value = info.email;
        document.getElementsByName("useremail")[0].value = info.email;
        var x=document.getElementById('log');
        x.innerHTML="Login Successfully! You can edit your name and email by yourself. Logout <a href=\"" + info.logUrl + "\">here</a>.";
        var y=document.getElementById('submit');
        y.style.opacity=1.0;
        y.style.cursor="pointer";
      }
   }); 
    
}

function createListElement(comment) {
  const liElement = document.createElement('li');
  liElement.innerText = comment.timestamp+" "+comment.name+":  "+comment.content;
  return liElement;
}
