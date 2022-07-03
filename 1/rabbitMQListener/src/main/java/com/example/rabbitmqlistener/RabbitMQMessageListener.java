package com.example.rabbitmqlistener;

import com.example.rabbitmqlistener.model.Account;
import com.example.rabbitmqlistener.model.Address;
import com.example.rabbitmqlistener.model.Customer;
import com.example.rabbitmqlistener.model.Invoice;
import com.example.rabbitmqlistener.repository.AccountRepository;
import com.example.rabbitmqlistener.repository.AddressRepository;
import com.example.rabbitmqlistener.repository.CustomerRepository;
import com.example.rabbitmqlistener.repository.InvoiceRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import java.io.DataInput;
import java.io.IOException;

// implements MessageListener
@Slf4j
@Service
/**
 *
 * This class reads messages from RabbitMQ queue and maps to related class.
 */
public class RabbitMQMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    CustomerRepository customerRepository;

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

    @RabbitListener(queues = "addressQueue")
    public void saveAddress(final Message message) {

        try {
            String rawData = new String(message.getBody());
            System.out.println(rawData);
            Address address = objectMapper.readValue(rawData, Address.class);
            addressRepository.save(address);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "invoiceQueue")
    public void saveInvoice(final Message message) {

        try {
            String rawData = new String(message.getBody());
            System.out.println(rawData);
            Invoice invoice = objectMapper.readValue(rawData, Invoice.class);
            invoiceRepository.save(invoice);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "customerQueue")
    public void saveCustomer(final Message message) {

        try {
            String rawData = new String(message.getBody());
            System.out.println(rawData);
            Customer customer = objectMapper.readValue(rawData, Customer.class);
            customerRepository.save(customer);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // receive json object and parse as class.
    @RabbitListener(queues = "NewQueue")
    public void receiveMessage(final Message message) {


//        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        try {
            String rawData = new String(message.getBody());
            // to parse json we need to replace parenthesis.

            rawData = rawData.replace("\"", "").replace("\\", "\"");

            SimpleMessage simpleMessage1 = objectMapper.readValue(rawData, SimpleMessage.class);
            log.info("print simplemessage from listener" + simpleMessage1.toString());

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
