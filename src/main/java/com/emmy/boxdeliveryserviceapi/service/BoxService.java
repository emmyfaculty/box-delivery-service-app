package com.emmy.boxdeliveryserviceapi.service;

import com.emmy.boxdeliveryserviceapi.dto.BoxRequest;
import com.emmy.boxdeliveryserviceapi.model.Box;
import com.emmy.boxdeliveryserviceapi.model.Item;
import java.util.List;
import java.util.Map;

public interface BoxService {

  Box createBox(BoxRequest boxRequest);

  public void loadBoxWithItems(long boxId, List<Item> items);
  public List<Item> getLoadedItems(Long boxId);

  public List<Box> getAvailableBoxesForLoading();

  public int getBatteryLevel(Long boxId);

  public Box getBoxById(Long boxId);

}
