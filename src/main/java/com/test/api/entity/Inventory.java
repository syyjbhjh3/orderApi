package com.test.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "inventory")
public class Inventory {
	@Id
	@Column(nullable = false, length = 8)
    private String item_Code;
	
	@Column(nullable = false, length = 20) //물품명
    private String item_Name;
	
    @Column(nullable = false, length = 10) //재고갯수
    private Integer qty;
    
    @Column(nullable = false, length = 10) //단가
    private Integer amount;
}
