package hu.bme.aut.retelab2.services;

import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class SimpleScheduler {
    @Autowired
    private AdRepository adRepository;

    @Scheduled(fixedDelay= 6000)
    private void deleteExpired(){
        adRepository.bulkDelete(adRepository.findAll().stream().filter(x->x.getExpiry().isBefore(LocalDateTime.now())).collect(Collectors.toList()));
    }
}
