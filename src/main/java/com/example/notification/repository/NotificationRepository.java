package com.example.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.notification.entity.NotificationLog; // உங்க என்டிட்டி பெயர்

@Repository
public interface NotificationRepository extends JpaRepository<NotificationLog, Long> {
    
}