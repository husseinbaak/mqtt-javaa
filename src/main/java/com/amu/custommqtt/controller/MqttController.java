package com.amu.custommqtt.controller;




import com.amu.custommqtt.dto.Rota;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amu.custommqtt.dto.MessageMqtt;
import com.amu.custommqtt.service.MQTTService;


import java.util.List;
import java.util.Optional;


@RestController
public class MqttController {

	@Autowired
	private MQTTService mqttService;
	@PostMapping("/yayinla/{topic}")
	public Rota Yayınla(@RequestBody Rota messageMqtt,@PathVariable String topic) {

		mqttService.jsonGonder(topic,messageMqtt);
		return messageMqtt;
	}

	@GetMapping("/oku")
	public List<MessageMqtt> KonularinVerileriniGetir(){
		return mqttService.KonularinVerileriniGetir();
	}
	@GetMapping("/get-byid/{id}")
	public Optional<MessageMqtt> getById(@PathVariable Integer id){
		return mqttService.getById(id);
	}



}
