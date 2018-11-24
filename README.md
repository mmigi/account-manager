## Для проверки

### Сборка и запуск 
```bash
mvn clean package
java -jar target/account-manager-0.0.1-SNAPSHOT.jar
```

### Отправка запросов

**Снятие денег**

```bash
curl --request POST \
  --url 'http://localhost:8080/api/v1/account/6455cbe2-c98b-4761-9285-bfd200619a0c/withdraw?amount=10.00'
```

**Положить деньги**

```bash
curl --request POST \
  --url 'http://localhost:8080/api/v1/account/6455cbe2-c98b-4761-9285-bfd200619a0c/deposit?amount=10.00'
```

**Перевести деньги деньги**

```bash
curl --request POST \
  --url http://localhost:8080/api/v1/accounts/transfer \
  --header 'content-type: application/json' \
  --data '{
	"accountFrom": "6455cbe2-c98b-4761-9285-bfd200619a0c",
	"accountTo": "7455cbe2-c98b-4761-9285-bfd200619a0c",
	"amount": "10.00"
}'
```