package com.amu.custommqtt.service;


import com.amu.custommqtt.dto.MessageMqtt;
import com.amu.custommqtt.dto.Rota;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.amu.custommqtt.repo.MessageRepo;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MQTTService implements MqttCallback  {
	@Autowired
	private  MessageRepo messageRepo;
	private MqttClient client = null;
	MessageMqtt messageMqtt;
	private String mqttUserName = "username", mqttPassword = "password";
	private String mqttIpAddress = "localhost";
	private boolean mqttHaveCredential = false;
	private String mqttPort = "1883";
	Logger LOG = LoggerFactory.getLogger(getClass());


	@Override
	public void connectionLost(Throwable arg0) {
		LOG.info("bağlantı koptu :" + arg0.getMessage()+" :"+arg0.toString());
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		messageMqtt=new MessageMqtt();
		messageMqtt.setTopic(arg0);
		messageMqtt.setMessage(arg1.toString());
		System.out.println(arg1.toString());
		messageRepo.save(messageMqtt);

		try {
			LOG.info("arg0 :" + arg0 + " arg1 :" + arg1.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void startMqtt() {

		MemoryPersistence persistence = new MemoryPersistence();
		try {
			client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
					persistence);

		} catch (MqttException e1) {

			e1.printStackTrace();
		}

		MqttConnectOptions connectOptions = new MqttConnectOptions();
		connectOptions.setCleanSession(true);
		connectOptions.setMaxInflight(3000);
		connectOptions.setAutomaticReconnect(true);
		if (mqttHaveCredential) {
			connectOptions.setUserName(mqttUserName);
			connectOptions.setPassword(mqttPassword.toCharArray());
		}
		client.setCallback(this);
		try {
			IMqttToken mqttConnectionToken = client.connectWithResult(connectOptions);

			LOG.info(" Bağlantı durumu :" + mqttConnectionToken.isComplete());
			client.subscribe("tika_robot");


		} catch (MqttException e) {

			e.printStackTrace();
		}
	}


	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {

		try {
			LOG.info("teslimat Tamamlandı :" );
//			LOG.info("deliveryComplete : " + arg0.getMessage().getPayload().toString());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void publish(String topicSuffix, String content) {
		MqttMessage message = new MqttMessage();
		message.setPayload(content.getBytes());
		message.setQos(2);
		try {
			String topic =  topicSuffix;
			if (client.isConnected()) {
				LOG.info("Connection Status :" + client.isConnected());
			}
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void jsonGonder(String topicSuffix, Rota rota) {
		MqttMessage message = new MqttMessage();

		message.setPayload(new Gson().toJson(rota).getBytes());
		// ikinci yol
//		ObjectMapper mapper = new ObjectMapper();
//		byte[] json = mapper.writeValueAsBytes(rota);
//		message.setPayload(json);

		message.setQos(2);
		try {
			String topic =  topicSuffix;
			if (client.isConnected()) {
				LOG.info("Connection Status :" + client.isConnected());
			}
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<MessageMqtt> KonularinVerileriniGetir(){
		return messageRepo.findAll();
	}

	public Optional<MessageMqtt> getById(Integer id){
		return messageRepo.findById(id);
	}



}
