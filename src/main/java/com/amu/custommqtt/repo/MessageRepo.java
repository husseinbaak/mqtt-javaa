package com.amu.custommqtt.repo;

import com.amu.custommqtt.dto.MessageMqtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<MessageMqtt,Integer> {
}
