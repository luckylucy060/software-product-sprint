package com.google.sps.data;

/** An item on a todo list. */
public final class userComment {

  private final String timestamp;
  private final String name;
  private final String content;

  public userComment(String timestamp, String name, String content) {
    this.timestamp = timestamp;
    this.name = name;
    this.content = content;
  }
}