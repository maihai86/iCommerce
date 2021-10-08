docker volume create --name pgdata -d local

docker-compose -f docker-compose-windows.yml up -d

```bash
curl -X GET \
    -H "Content-type: application/json" \
    -H "Accept: application/json" \
    "http://localhost:8080/api/v1/products?category-id=1&brand-id=1&color-id=1&price-min=0&price-max=5100000"
```

```bash
curl -X POST \
    -H "Content-type: application/json" \
    -H "Accept: application/json" \
    -d '{"productId": 18, "quantity": 2}' \
    "http://localhost:8080/api/v1/carts"
```

mvn clean package 
mvn clean install