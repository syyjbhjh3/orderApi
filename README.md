## 주문처리 API

사용기술 : Java 11, Spring Boot 3.6.7, JPA, H2, Swagger 3.0

Swagger Url = http://localhost:8080/swagger-ui/index.html


### 1. /inven (재고입력)

###### parameter
```
[
  {
    "itemCode": "1000001",
    "itemName": "동화책",
    "amount": 15000,
    "qty": 2
  },
  {
    "itemCode": "1000002",
    "itemName": "인형",
    "amount": 20000,
    "qty": 2
  },
  {
    "itemCode": "1000003",
    "itemName": "잠옷",
    "amount": 30000,
    "qty": 3
  },
  {
    "itemCode": "1000004",
    "itemName": "뽀로로",
    "amount": 15000,
    "qty": 4
  }
]
```


### 2. /order (주문)

###### parameter

```
{
  "order_No": "10001",
  "tot_amout": 15000,
  "item_code": "1000001",
  "customer_id": "park1234"
}
```


### 3. /orderInfo (주문정보조회)

###### parameter

```
{
  "order_No": "10001"
}
```


### 4. /setStatus (주문상태변경)

###### parameter

```
{
  "order_No": "10001",
  "order_status": "ship",
}
```
