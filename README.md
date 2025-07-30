# Micro inventory system

The structure for the two microservices:


```
micro-inventory-system/
├── product-service/
│   ├── src/main/java/com/example/product/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── ProductServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── .env
│   ├── pom.xml
│   └── docker-compose.yml
│
├── inventory-service/
│   ├── src/main/java/com/example/inventory/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── InventoryServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   ├── .env
│   ├── pom.xml
│   └── docker-compose.yml
│
├── docker-compose.yml         <-- root orchestrator
```

