## Mysql veya PostgreSQL ile controller katmanlarının çalışabilmesi için gerekli repository katmanlarını yazın. Ayrıca isbasi-email-service kuyruktan veriyi okuduktan sonra gerekli model class’ını oluşturup tabloya kaydedin.(60 Puan)

Producer(isbasi) ve Consumer(rabbitMQListener) uygulamalarını [1 klasöründe](/1) klasöründe inceleyebilirsiniz.

Isbasi uygulaması, verileri RabbitMQListener uygulamasına rabbitMQ aracılığı ile mesaj gönderir. 
RabbitMQListener uygulaması kuyruktan okuduğu veriyi mapledikten sonra Postgresql isbasi databesine objeleri kaydeder.

![Application Flow Chart](https://i.ibb.co/pxj4vN8/flow.png)

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