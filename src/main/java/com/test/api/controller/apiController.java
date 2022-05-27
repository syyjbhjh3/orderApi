package com.test.api.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.entity.Inventory;
import com.test.api.entity.Order;
import com.test.api.mapping.Message;
import com.test.api.mapping.Message.StatusEnum;
import com.test.api.repo.InventoryJpaRepo;
import com.test.api.repo.OrderJpaRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Order Processing"})
@RequiredArgsConstructor
@RestController
public class apiController {
	
	@Autowired
	private OrderJpaRepo order_repo;
	
	@Autowired
	private InventoryJpaRepo inven_repo;
	
	@ApiOperation(value = "재고입력", notes = "재고입력")
	@RequestMapping(value="/inven", method=RequestMethod.POST, produces="application/json; charset=UTF8") //회원가입
	public Message signup(@ApiParam(value = "재고정보", required = true) @RequestBody List<Map> param) throws Exception {
		Message message = new Message();
		
		for(Map info : param) {
			Inventory inven = Inventory.builder()
					 .item_Code(info.get("itemCode").toString())
					 .item_Name(info.get("itemName").toString())
					 .qty(Integer.parseInt(info.get("qty").toString()))
					 .amount(Integer.parseInt(info.get("amount").toString()))
	                .build();		
			inven_repo.save(inven);
		}
		
		message.setStatus(StatusEnum.OK);
        message.setMessage("재고입력");
        return message;
	}

	@ApiOperation(value = "주문", notes = "주문")
	@RequestMapping(value="/order", method=RequestMethod.POST, produces="application/json; charset=UTF8")
	public Message order(@ApiParam(value = "주문", required = true) @RequestBody Map<String, String> param) throws Exception {
		Message message = new Message();
		
		//repo.save라 주문번호 중복 확인
		try {
			message.setStatus(StatusEnum.OK);
	        message.setMessage("잘못된 주문번호입니다.");
			Order order_info = order_repo.findById(param.get("order_No")).get();
		}catch (NoSuchElementException e) {
			//재고확인
			Inventory inven_info = inven_repo.findById(param.get("item_code")).get();
			
			if (inven_info != null) {
				int inven_qty = inven_info.getQty();
				
				if(inven_qty != 0) {
					//Order저장
					Order order = Order.builder()
						  .order_No(param.get("order_No"))
						  .order_Date(toYYYY_MM_DD())
						  .tot_amout(Integer.parseInt(param.get("tot_amout")))
						  .item_code(param.get("item_code"))
						  .order_status("order")
						  .customer_id(param.get("customer_id"))
			             .build();		
					order_repo.save(order);
					
					//재고수정
					inven_info.setQty(inven_info.getQty()-1);
					inven_repo.save(inven_info);
					
					message.setStatus(StatusEnum.OK);
			        message.setMessage("주문완료");
				}else {
					message.setStatus(StatusEnum.OK);
			        message.setMessage(inven_info.getItem_Name() + "의 재고가 존재하지 않습니다.");
				}
				
			}else {
				message.setStatus(StatusEnum.OK);
		        message.setMessage("판매하지 않는 상품입니다.");
			}
		}
		return message;
	}

	@ApiOperation(value = "주문정보확인", notes = "주문정보확인")
	@RequestMapping(value="/orderInfo", method=RequestMethod.POST, produces="application/json; charset=UTF8")
	public Message orderInfo(@ApiParam(value = "주문정보확인", required = true) @RequestBody Map<String, String> param) throws Exception {
		Message message = new Message();
		
		Order order_info = order_repo.findById(param.get("order_No")).get();
		
		if (order_info != null) {
			message.setStatus(StatusEnum.OK);
	        message.setMessage("주문정보확인");
			message.setData(order_info); 
		}else {
			message.setStatus(StatusEnum.OK);
	        message.setMessage("주문번호를 확인해주세요.");
		}
		return message;
	}
	
	@ApiOperation(value = "주문상태변경", notes = "주문상태변경")
	@RequestMapping(value="/setStatus", method=RequestMethod.POST, produces="application/json; charset=UTF8")
	public Message setStatus(@ApiParam(value = "주문상태변경", required = true) @RequestBody Map<String, String> param) throws Exception {
		Message message = new Message();
		
		Order order_info = order_repo.findById(param.get("order_No")).get();
		order_info.setOrder_status(param.get("order_status"));
		order_repo.save(order_info);
		
		message.setStatus(StatusEnum.OK);
        message.setMessage("주문상태변경");
		return message;
	}
	
	public String toYYYY_MM_DD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar c1 = Calendar.getInstance();
		String strToday = sdf.format(c1.getTime());
        
		return strToday;
    }
}
