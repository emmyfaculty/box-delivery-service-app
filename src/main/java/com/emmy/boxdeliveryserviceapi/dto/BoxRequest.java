package com.emmy.boxdeliveryserviceapi.dto;

import com.emmy.boxdeliveryserviceapi.model.Item;
import com.emmy.boxdeliveryserviceapi.model.State;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
public class BoxRequest {

  @Column(nullable = false, unique = true, length = 20)
  private String txref;
  @Column(nullable = false)
  @Size(max = 500)
  private int weightLimit;
  @Column(nullable = false)
  private int currentWeight;
  private int batteryCapacity;
  @Enumerated(EnumType.STRING)
  @Nullable
  private State state;
  @OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
  private List<Item> items;

}

