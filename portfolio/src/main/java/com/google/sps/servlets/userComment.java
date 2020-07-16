package com.google.sps.data;

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