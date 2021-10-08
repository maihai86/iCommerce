docker volume create --name pgdata -d local

docker-compose -f docker-compose-windows.yml up -d

curl -X GET \
    -H "Content-type: application/json" \
    -H "Accept: application/json" \
    "http://localhost:8080/api/v1/products?category-id=1&brand-id=1&color-id=1&price-min=0&price-max=5100000"
