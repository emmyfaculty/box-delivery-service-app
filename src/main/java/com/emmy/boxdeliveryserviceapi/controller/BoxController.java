package com.emmy.boxdeliveryserviceapi.controller;

import com.emmy.boxdeliveryserviceapi.dto.BoxRequest;
import com.emmy.boxdeliveryserviceapi.model.Box;
import com.emmy.boxdeliveryserviceapi.model.Item;
import com.emmy.boxdeliveryserviceapi.service.BoxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boxes")
@RequiredArgsConstructor
public class BoxController {

  private final BoxService boxService;
  private final ObjectMapper objectMapper;


  @PostMapping
  public ResponseEntity<?> createBox(@RequestBody BoxRequest box) {
    Box createBox = boxService.createBox(box);
    return new ResponseEntity<>(createBox, HttpStatus.CREATED);
  }

  @PostMapping("/{boxId}/load")
  public ResponseEntity<String> loadItems(@PathVariable Long boxId, @RequestBody List<Item> items) {
    boxService.loadBoxWithItems(boxId, items);
    return new ResponseEntity<>("Items loaded successfully", HttpStatus.OK);
  }

  @GetMapping("/{boxId}/loaded-items")
  public ResponseEntity<List<Item>> getLoadedItems(@PathVariable Long boxId) {
    List<Item> loadedItems = boxService.getLoadedItems(boxId);
    return new ResponseEntity<>(loadedItems, HttpStatus.OK);
  }

  @GetMapping("/available-for-loading")
  public ResponseEntity<List<Box>> getAvailableBoxesForLoading() {
    List<Box> availableBoxes = boxService.getAvailableBoxesForLoading();
    return new ResponseEntity<>(availableBoxes, HttpStatus.OK);
  }

  @GetMapping("/{boxId}/battery-level")
  public ResponseEntity<Integer> getBatteryLevel(@PathVariable Long boxId) {
    int batteryLevel = boxService.getBatteryLevel(boxId);
    return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
  }
}
