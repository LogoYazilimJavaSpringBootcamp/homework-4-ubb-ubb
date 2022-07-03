## ActiveMQ, Kafka ve RabbitMQ karşılaştırın. Örnek kodlar ile nasıl çalıştığını anlatın. (10 Puan)

Message Queue, makinelerin asenkron şekilde iletişim halinde olmasını sağlar. Bu iletişim sistemi makienler arasındaki sıralı mesaj transferi işlerini düzenler.
Her uygulama MQ uygulaması, reply request ve alertler ile kontrol edilir.
MQ, tek bir mesajı işlemek yerine, queuedaki tüm işlemleri kontrol eder. Gönderici, response almadan operasyonuna devam edebilir.
Asenkron çalışma dışında da queuedaki tüm mesaj queuelerin depolama, transfer etme ve silme işlemlerinden de sorumludur. Ayrıca tüm mesajlaşma bilgilerini de dökümante eder.

Popüler olarak kullanılan bir çok MQ servisi vardır. 

### Apache Kafka

Linkedin tarafından geliştirilmiş, sonrasında Apache tarafından satın alınmış açık kaynak kodlu data streaming platformudur. Java ve Scala dilinde yazılmıştır.
Öncelikle message queue olarak tasarlanan servis sonrasında event streaming platforma dönüşmüştür.

- Herhangi mesaj kuyruklama sistemi gibi, Kafka kayıt akışlarının yayınlanmasına ve izlenmesine izin verir.
- Kayıt akışları hata tolerans yaklaşımı (tek bir hatada sistemin ara vermeden devam etmesi) ile depolar.
- Uygulamalara decouplinge uygun olduğu için ölçeklenebilir.
- Kolayca ulaşılabilir.
- Sisteme giren yüksek verinin kontrol edilmesini sağlar.

Kafka kullanmanın en büyük avantajı, her bölümün farklı makinelerde barındırılmasıyla tüm topiclerin paralel şekilde kullanılmasını sağlar. Kullanıcılar paralel şekilde topiclere ulaşır.
Birden çok kullanıcı, birçok bölüme erişimine izin vererek ölçekleme kolaylaşır. Bu özellik MQ işlemlerinin kapasitesini arttırır.
Ölçekleme kapasitesi yüksektir. Yüksek işyükünü yönetebilir. LinkedIn günlük 300 milyar eventi yönetmek için Kafka kullanıyor. Gerçek zamanlı data pipelineları için genellikle tercih edilir.

Spring Kafka kullanımı ve abstraction için KafkaTemplate sağlar.

pom.xml dependency:
```
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.7.2</version>
</dependency>
```

Application.properties te config ayarları özellikleri: 
```
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
```

Mesaj gönderme:
```
@Component
public class MyBean {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MyBean(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

}
```
Mesaj alma: Herhangi bean @KafkaListener ile anote edilebilir. Listener endpointi yaratır.  Aşağıdaki komponent, listener endpointini someTopic topicinde oluşturur.
```
@Component
public class MyBean {

    @KafkaListener(topics = "someTopic")
    public void processMessage(String content) {
        // ...
    }
}
```

Kafka Streams: StreamsBuilder ile streamleri yönetir.  
```
@Configuration(proxyBeanMethods = false)
@EnableKafkaStreams
public class MyKafkaStreamsConfiguration {

    @Bean
    public KStream<Integer, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<Integer, String> stream = streamsBuilder.stream("ks1In");
        stream.map(this::uppercaseValue).to("ks1Out", Produced.with(Serdes.Integer(), new JsonSerde<>()));
        return stream;
    }

    private KeyValue<Integer, String> uppercaseValue(Integer key, String value) {
        return new KeyValue<>(key, value.toUpperCase());
    }

}
```

### ActiveMQ

ActiveMQ popüler açık kaynak kodlu mesaj servisidir. Java dilinde yazılmıştır. Diğer servisler gibi, ayrı serverlarda bulunan birden fazla uygulamanın iletişimini sağlar.
JMS (Java Message Service) API'sinin implementasyonudur. AMQP-MQTT protokolünü kullanır.

- Birden fazla bağlantı protokolü destekliyor.
- Gecikmeli mesaj gönderimi imkanı bulunmaktadır. (Kafka da bu özellik mevcut değil.)
- Yüksek kullanılabilirlik için satır düzeyinde kilitleme tekniği, dosya sistemi ve diğer modları kullanır.
- API'si temel authentication ve JAAS authentication ile beraber, özelleştirilebilir doğrulama pluginleri kullanmamızı sağlar.
- Dikey yönde ölçeklenebilirliğinin yanı sıra yatay yönde de ölçeklenebilmesi adına built in Network Of Brokers fonksiyonu bulunur.

Kafkaya göre daha az hacimli verilerin kontrol edilmesinde kullanılır. Daha çok kompleks ve küçük hacimli mesajlaşmada kullanılır.

Activemq Docker container çalıştırma:
```
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq:5.14.3
```
Maven dependency: 
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
Server uygulaması:
```
@Bean 
CabBookingService bookingService() {
    return new CabBookingServiceImpl();
}
```
```
@Bean 
Queue queue() {
    return new ActiveMQQueue("remotingQueue");
}
```

```
@Bean 
JmsInvokerServiceExporter exporter(CabBookingService implementation) {
    JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
    exporter.setServiceInterface(CabBookingService.class);
    exporter.setService(implementation);
    return exporter;
}
```

```
@Bean SimpleMessageListenerContainer listener(
  ConnectionFactory factory, 
  JmsInvokerServiceExporter exporter) {
 
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(factory);
    container.setDestinationName("remotingQueue");
    container.setConcurrentConsumers(1);
    container.setMessageListener(exporter);
    return container;
}
```

Client uygulaması: 
```
@Bean 
Queue queue() {
    return new ActiveMQQueue("remotingQueue");
}
```

```
@Bean 
FactoryBean invoker(ConnectionFactory factory, Queue queue) {
    JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
    factoryBean.setConnectionFactory(factory);
    factoryBean.setServiceInterface(CabBookingService.class);
    factoryBean.setQueue(queue);
    return factoryBean;
}
```

```
CabBookingService service = context.getBean(CabBookingService.class);
out.println(service.bookRide("13 Seagate Blvd, Key Largo, FL 33037"));
```


### RabbitMQ

RabbitMQ ise diğer çok kullanılan message queue uygulamasıdır. Erlang dilinde yazılmıştır. AMQP, STOMP, MQTT protokollerini içerir. 


- Birçok mesajlama tekniğini destekler, pub-sub, point-to-point, request-reply...
- Senkron ve asenkron iletişim kurabilir.
- Mesaj teslim kabulleri güvenilir bir servis sağlar.
- Kontrol arayüzü kullanıcıya kolay kontrol ve izleme sağlar.

Kompleks mesajlaşma desteği sebebiyle çok büyük firmalar tarafından kullanılır (NASA). Mesaj güvenliği kritik servislerde tercih edilir. Built in pluginlerle beraber custom pluginler de bulunur. 
Ölçeklenebilirliği düşüktür.

Spring RabbitTemplate ile abstractionı sağlar. Mesajları MessageListenerAdapter ile dinler.

RabbitMQ Docker container çalıştırma:  
```
docker run -d --hostname my-rabbit --name myrabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=123456 -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```


Mesaj Alıcısı oluşturma: 
```
package com.example.messagingrabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}
```

Listener kaydetme ve mesaj gönderme:
```
package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingRabbitmqApplication {

  static final String topicExchangeName = "spring-boot-exchange";

  static final String queueName = "spring-boot";

  @Bean
  Queue queue() {
    return new Queue(queueName, false);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(topicExchangeName);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Receiver receiver) {
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
  }

}
```

RabbitTemplate, RabbitMQ ile mesaj gönderme ve alma işlemlerini sağlar. Kullanmadan önce, mesaj alıcı containerının ayarlarının yapılması, queue, exchange ve binding tanımlanması ve mesaj gönderecek komponentin ayarlanması gereklidir.

## Microservis ve monolith mimariyi karşılaştırın.(10 Puan)

### Microservis mimarisi

Çok büyük uygulamaların küçük ve bağımsız parçalara bölünüp sorumluluklarının kendilerine dağıtan uygulama mimarisidir. 
Tek bir kullanıcı isteği içerdeki birçok mikroservis ile harmanlanarak geri dönüş yapar. Bakımı ve test edilebilirliği kolaydır. Bağımlılık derecesi düşüktür. Bağımsız deploy edilebilir. İş yeri kapasitesine göre organize edilir.
Her servis küçük geliştirici ekipleri tarafından takip edilebilir. Her servis bağımsızdır. Tüm servisler birbirleriyle API yardımı ile iletişim kurar. Farklı dil ve framework kullanımını destekler.

Dezavantajları:  
- Monolithic mimariye göre daha kompleks bir yapıdır. 
- Kompleks olduğu için sistemi anlayacak ve servisler arası iletişimi sağlayabilecek kalifiye işgücü gerekliliği fazladır. 
- Mikroservislerin bağımsız deployları komplekstir.
- Bağlantı kullanımı yüksektir. Bu sebeple monolithic uygulamalara göre daha az güvenilirdir.
- Birçok servis birbirine bağlı olduğu için hatayı bulmak adına debug kontrolü daha zordur.

### Monolithic mimari

Programın tüm modellerinin birleştirildiği yazılım mimarisidir. Komponent arasında yüksek bağımlılık derecesine sahiptir. Bu mimaride programın çalışabilmesi için tüm komponentlerin mevcut olması gerekmektedir.
Monolithic mimari single-tier, bir çok komponentin tek bir uygulamada toplanmasıdır. Bu sebeple de çok büyük kod temeli bulunur. Bakımı ve kontrolü zor olmaktadır. 
Bağımlılık derecesinin çok yüksek olması sebebiyle de  herhangi bir komponentte yapılan değişiklik diğer komponentlerin de değişmesine, tüm sistemin tekrar derlenmesine, teste tabi tutulmasına neden olabilir.
Geliştirilmesi kolaydır. Deploy edilmesi kolaydır, sadece tek bir jar/war ile deploy edilebilir. Bağlantı gecikmesi daha düşüktür ve bağlantılar düşük olduğu için daha güvenlidir. Geliştirici farklı farklı uygulamaları öğrenmek yerine tek bir uygulamaya odaklanabilir.

Dezavantajları: 
- Zamanla büyüdüğü için kontrol edilmesi güç olmaktadır.
- En küçük değişiklik için bile tüm sistemin redeploy edilmesi gereklidir.
- Şirkete yeni başlayan developer'ın sadece bir kaç fonksiyondan sorumlu olmasına rağmen, monolithic mimaride yazılmış büyük bir kod temeli olan uygulamayı anlaması zor olabilir.
- Yeni teknolojileri uygulamak zordur.
- Yatay ölçeklenebilirliğe uygun değildir.
- Tek bir bug tüm sistemi durdurabilir.

## SOAP - RESTful karşılaştırın (10 Puan)

SOAP (Simple Object Access Protocol)  HTTP ve XML ile sistemlerin haberleşmesini sağlayan protokoldur. Protokol olduğu için standartize edilmiştir. XML kullanır.

SOAP Avantajları:
- Dil, platform bağımsız çalışmaktadır. (REST, HTTP kullanımını zorunlu tutar.)
- Distributed enterprise environmentte sağlıklı çalışır. (REST direkt point-to-point iletişimi gerektirir.)
- Standardize edilmiştir.
- Kendi içinde hata kontrolü sağlar.
- Belirli dillerde otomasyon sağlar.


REST(Representational State Transfer) mimarisini kullanan servislere RESTful service denmektedir. HTTP üzerine kuruludur. Tipik web servislerle yapılabilecek fonksiyonlar RESTful servisleri ile de yapılabilir.
RESTful servisler için çoğunlukla JSON kullanılmakta ancak XML,CSV HTML ile de kullanılabilir.

REST avantajları:
- Kolayca anlaşılabilir standartlar kullanır. (Swagger, OpenAPI, Postman)
- Öğrenme eğrisi düşüktür.
- Daha verimlidir(SOAP'ın tüm mesajlarda XML kullanır, REST ise çoğunlukla daha küçük JSON formatını kullanır)
- Daha hızlıdır.
- Diğer web teknoloji felsefelerine daha yakındır.
- SOAP gibi bizi proxy kullanmaya, bir WSDL’e zorlamıyor olmasıdır.

## isbasi.com üye olup sisteme 3 yeni model ekleyin ve mimariye uygun şekilde CRUD işlemleri yapan endpointleri yazın. (50 Puan)

Derste takip edilen ve üzerine  çalışma yaptığımız Logo İşbaşı uygulaması temel alınmış ve uygulama içindeki 4 farklı model için CRUD işlemi özelliği eklenmiştir.
Repository management için JPA kullanılmıştır. İmplemantasyonu için ise Hibernate kullanıldı. 
JPA/Hibernate default olarak hafızadaki H2 veritabanını kullandı. H2 database geçici bellekte depolandığı için veriler uygulama kapatıldığında silinmektedir.
Repository sınıfları CrudRepository interface'inden extend edilmiştir.

### Accounts UML

<p align="center">
  <img src="https://i.ibb.co/09Hbwx3/account.png" />
</p>

### Invoice UML

<p align="center">
  <img src="https://i.ibb.co/yf4ygh2/invoice.png" />
</p>

### Customer UML

<p align="center">
  <img src="https://i.ibb.co/QNKpfPG/customer.png" />
</p>

### Address UML

<p align="center">
  <img src="https://i.ibb.co/ZfgXX3s/adres.png" />
</p>


Dört model için yazılan API servislerinin işlevlerini basit bir şekilde açıklayan [API dökümantasyonunu](https://documenter.getpostman.com/view/19776700/UzBsGiqV) inceleyebilirsiniz.

## CustomerServisi için gerekli tüm endpoint’leri yazın. (10 Puan)

Customer servisi için yazılan endpointleri [API dökümantasyonunda](https://documenter.getpostman.com/view/19776700/UzBsGiqV#f24fbce4-40b0-4d6e-bc1f-626efc213c83) inceleyebilirsiniz.

## Aktif ve pasif müşterileri listeleyen endpoint için gerekli kodu yazın. (10 Puan)

Customer servisi için yazılan aktif ve pasif müşterileri listeleyen isteği  [API dökümantasyonunda](https://documenter.getpostman.com/view/19776700/UzBsGiqV#7f00080d-b592-4333-a0d6-a3ac9f4adc16)  inceleyebilirsiniz.
