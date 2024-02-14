package com.emmy.boxdeliveryserviceapi.model;

import static com.emmy.boxdeliveryserviceapi.utils.constant.Constants.CODE_REGEX;
import static com.emmy.boxdeliveryserviceapi.utils.constant.Constants.NAME_REGEX;

import com.emmy.boxdeliveryserviceapi.utils.annotation.ExcelColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  @ExcelColumn(name = "Email Address", col = 7,errMsg = "Invalid name", regexp = NAME_REGEX)
  private String name;
  @Column(nullable = false)
  private int weight;
  @Column(nullable = false)
  @ExcelColumn(name = "Email Address", col = 7,errMsg = "Invalid code", regexp = CODE_REGEX)
  private String code;
  @ManyToOne
  @JoinColumn(name = "box_id")
  private Box box;

}
