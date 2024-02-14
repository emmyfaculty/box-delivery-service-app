package com.emmy.boxdeliveryserviceapi.model;

import lombok.Getter;

@Getter
public enum State {
  IDLE("Idle"),
  LOADING("Loading"),
  LOADED("Loaded"),
  DELIVERING("Delivering"),
  DELIVERED("Delivered"),
  RETURNING("Returning");

  final String display;

  State(String display) {
    this.display = display;
  }

}
