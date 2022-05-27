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
@Table(name = "orders")
public class Order {
	@Id
	@Column(nullable = false, length = 10) //주문번호
    private String order_No;
	
	@Column(nullable = false, length = 20) //주문일자
	private String order_Date;

    @Column(nullable = false, length = 20) //주문총금액
    private Integer tot_amout;
    
    @Column(nullable = false, length = 10) //물품코드
    private String item_code;
    
    @Column(nullable = false, length = 10) //주문상태
    private String order_status;
    
    @Column(length = 10) //고객 Id (비회원)
    private String customer_id;
}
