package com.emmy.boxdeliveryserviceapi.serviceimpl;

import com.emmy.boxdeliveryserviceapi.dto.BoxRequest;
import com.emmy.boxdeliveryserviceapi.exception.BoxAlreadyExistsException;
import com.emmy.boxdeliveryserviceapi.exception.BoxNotFoundException;
import com.emmy.boxdeliveryserviceapi.exception.BoxNotIdleException;
import com.emmy.boxdeliveryserviceapi.exception.BoxOverweightException;
import com.emmy.boxdeliveryserviceapi.exception.InvalidBoxDataException;
import com.emmy.boxdeliveryserviceapi.exception.InvalidItemDataException;
import com.emmy.boxdeliveryserviceapi.exception.LowBatteryException;
import com.emmy.boxdeliveryserviceapi.model.Box;
import com.emmy.boxdeliveryserviceapi.model.Item;
import com.emmy.boxdeliveryserviceapi.model.State;
import com.emmy.boxdeliveryserviceapi.repository.BoxRepository;
import com.emmy.boxdeliveryserviceapi.service.BoxService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {

  private final BoxRepository boxRepository;

  @Override
  public Box createBox(BoxRequest boxRequest) {
    validateInputData(boxRequest);

    checkIfBoxExists(boxRequest.getTxref());

    Box box = new Box();
    this.convertDtoToModelEntity(boxRequest, box);
    boxRepository.save(box);

    return box;
  }

  private void convertDtoToModelEntity(BoxRequest boxRequest,Box box) {
    box.setTxref(boxRequest.getTxref());
    box.setWeightLimit(boxRequest.getWeightLimit());
    box.setCurrentWeight(boxRequest.getCurrentWeight());
    box.setBatteryCapacity(boxRequest.getBatteryCapacity());
    box.setState(boxRequest.getState());
  }

  private void validateInputData(BoxRequest boxRequest) {
    if (Objects.isNull(boxRequest.getTxref()) || boxRequest.getTxref().isEmpty() || boxRequest.getTxref().length() > 20) {
      throw new InvalidBoxDataException("Invalid txref. Must be non-empty and have max length of 20 characters");
    }

    if (boxRequest.getWeightLimit() <= 0 || boxRequest.getWeightLimit() > 500) {
      throw new InvalidBoxDataException("Invalid weight limit. Must be between 1 and 500 grams");
    }

    if (boxRequest.getBatteryCapacity() < 0 || boxRequest.getBatteryCapacity() > 100) {
      throw new InvalidBoxDataException("Invalid battery capacity. Must be between 0 and 100%");
    }
  }

  private void checkIfBoxExists(String txref) {
    if (boxRepository.existsByTxref(txref)) {
      throw new BoxAlreadyExistsException("Box with txref " + txref + " already exists");
    }
  }

  @Override
  public void loadBoxWithItems(long boxId, List<Item> items) {
    Box box = getBoxById(boxId);

    validateBoxState(box);
    validateBoxWeightLimit(box, items);
    validateBatteryLevel(box);

    List<Item> validatedItems = validateItemData(items);

    box.loadItems(validatedItems);
    boxRepository.save(box);

  }

  private Box getBoxById(long boxId) {
    return boxRepository.findById(boxId)
        .orElseThrow(() -> new BoxNotFoundException("Box with ID " + boxId + " not found"));
  }

  private void validateBoxState(Box box) {
    if (box.getState() != State.IDLE) {
      throw new BoxNotIdleException("Box is not in IDLE state");
    }
  }

  private void validateBoxWeightLimit(Box box, List<Item> items) {
    int totalWeight = items.stream()
        .mapToInt(Item::getWeight)
        .sum();

    if (totalWeight + box.getCurrentWeight() > box.getWeightLimit()) {
      throw new BoxOverweightException("Adding items exceeds the box's weight limit");
    }
  }

  private void validateBatteryLevel(Box box) {
    if (box.getBatteryCapacity() < 25) {
      throw new LowBatteryException("Box's battery level is below 25%");
    }
  }

  private List<Item> validateItemData(List<Item> items) {
    return items.stream()
        .map(this::validateItem)
        .collect(Collectors.toList());
  }

  private Item validateItem(Item item) {
    if (item.getName().length() > 50) {
      throw new InvalidItemDataException("Item name exceeds maximum length of 50 characters");
    }

    return item;
  }

  @Override
  public List<Item> getLoadedItems(Long boxId) {
    Box box = getBoxById(boxId);
    return box.getItems();
  }

  @Override
  public List<Box> getAvailableBoxesForLoading() {
    return boxRepository.findAll().stream()
        .filter(this::isBoxAvailable)
        .collect(Collectors.toList());
  }

  private boolean isBoxAvailable(Box box) {
    return box.getState() == State.IDLE &&
        box.getBatteryCapacity() > 25 &&
        box.getCurrentWeight() < box.getWeightLimit();
  }

  @Override
  public int getBatteryLevel(Long boxId) {
    Box box = getBoxById(boxId);
    return box.getBatteryCapacity();
  }

  @Override
  public Box getBoxById(Long boxId) {
    return boxRepository.findById(boxId)
        .orElseThrow(() -> new BoxNotFoundException("Box with id " + boxId + " not found"));
  }
}
