## Mysql veya PostgreSQL ile controller katmanlarının çalışabilmesi için gerekli repository katmanlarını yazın. Ayrıca isbasi-email-service kuyruktan veriyi okuduktan sonra gerekli model class’ını oluşturup tabloya kaydedin.(60 Puan)

Producer(isbasi) ve Consumer(rabbitMQListener) uygulamalarını [1 klasöründe](/1) klasöründe inceleyebilirsiniz.


Isbasi uygulaması RestController'dan verileri rabbitMQListener uygulamasına rabbitMQ aracılığı ile gönderir. 

rabbitMQListener uygulaması kuyruktan okuduğu veriyi mapler, mapledikten sonra Postgresql isbasi databaseine objeleri kaydeder.

Bu işlem İsbasi uygulamasındaki, ***Account, Address, Customer ve Invoice*** sınıfları için uygulanmıştır.
Dört model için yazılan API servislerinin işlevlerini basit bir şekilde açıklayan [API dökümantasyonunda](https://documenter.getpostman.com/view/19776700/UzBsGiqV#intro) inceleyebilirsiniz.

![Application Flow Chart](https://images2.imgbox.com/7a/5d/SkKETayw_o.png)

Account sınıfı için;

Controllerdan gelen acount objesi, rabbitTemplate vasıtasıyla RabbitMQ kuyruğuna sokulur;
```
public Account createAccount(Account account) {
        rabbitTemplate.convertAndSend("isbasi-exchange","accountKey",account);
        return account;
    }
```

RabbitMqListener projesinde bulunan RabbitMQMessageListener, gönderilen mesajları dinler.

Gelen json mesaj, objectMapper yardımıyla Account sınıfıyla eşleşir.

Eşleştikten sonra RabbitMqListener projesinde bulunan accountRepository Jpa Interface'ine kayıt gönderir.

```
@RabbitListener(queues = "accountQueue")
    public void saveAccount(final Message message) {

        try {
            String rawData = new String(message.getBody());
            System.out.println(rawData);
            Account account = objectMapper.readValue(rawData, Account.class);
            accountRepository.save(account);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

Uygulamada tanımlı postgresql sunucu ve giriş bilgileri ile, Repository class JPA-Hibernate yardımıyla Postgresql'e verileri göndererek kaydeder.

```
package com.example.rabbitmqlistener.repository;

import com.example.rabbitmqlistener.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
```


Postgresql da örnek olarak oluşturulan account tablosu kayıtları aşağıdadır. 
```
-[ RECORD 1 ]--------------------
id          | 1
balance     | 10000
bank_name   | Anadolu Bankasi
currency    | 1
iban        | 2000 3000 5000 7000
customer_id | 
account_id  | 
-[ RECORD 2 ]--------------------
id          | 2
balance     | 10000
bank_name   | Garanti Bankasi
currency    | 1
iban        | 1000 1000 2000 3000
customer_id | 
account_id  | 
-[ RECORD 3 ]--------------------
id          | 3
balance     | 10000
bank_name   | Garanti Bankasi
currency    | 1
iban        | 1000 1000 2000 3000
customer_id | 
```



## JDBC, JdbcTemplate ve Hibernate ile bir DAO katmanını yazın ve avantajlarını ve dezavantajlarını kendi görüşlerinizle beraber yazın. OOP’nin polimorfizm’den yararlanarak aynı tabloya üç yöntem ile CRUD işlemlerini yapan kodu yazınız. (30 Puan)

JDBC, JdbcTemplate ve Hibernate servislerini uygulayan DAO interface ve implementasyonlarını örnek olarak [2 klasöründe](/2) inceleyebilirsiniz.

![Persistence UML](https://i.ibb.co/5B3gw5v/Persistence.png)

Standart JDBC kullanırken, bağlantıların yönetimi açılıp kapanması manuel olduğu için daha fazla iş yükü oluşturduğunu düşünüyorum. JDBCTemplate basmakalıp kodlardan kurtulmamızı sağlıyor.
Ayrıca JDBCTemplate de olan JDBC standartında olmayan, .update, .execute methodları içerisinde değişken kullanılmaması, uygunsuzluk yaratmaktadır. 
Standart JDBC'de string concatenation yapılacağından injection'a daha açık olduğu söylenebilir. 

Jdbc ve JDBCTemplate aksine Hibernate'de SQL sorgusu yazmadan işlemlerimizi gerçekleştirebiliriz. Hibernate kullanmak diğer iki yönteme göre daha kolay. 
Ancak id'ye göre bir kaydı silmek istediğimde o kaydın bulunmadığı taktirde direkt hata veriyor.
Diğer iki implementasyonda benzer bir şekilde id'si olmayan, ulaşmaya çalıştığım bir kaydın sorgusunu yaptığımda hata vermemektedir. 
JPA interface'inde bu uygulama için kullanmadığım bir çok method da var. Bu sebeple Hibernate performans olarak aşağıda olabilir.



## Aşağıdaki kavramları örneklerle açıklayın ve hangi problemi nasıl çözdüklerini anlatan bir makale yazın.(Medium’da paylaşıp linkini koyabilirsiniz.) (10 Puan)

[Yazıya bu linkten ulaşabilirsiniz.](/3/3.md)