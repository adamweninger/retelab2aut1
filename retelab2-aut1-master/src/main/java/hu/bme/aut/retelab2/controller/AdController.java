package hu.bme.aut.retelab2.controller;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import hu.bme.aut.retelab2.services.SecretGenerator;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ad")
public class AdController {
    @Autowired
    private AdRepository adRepository;


    @PostMapping
    public String create(@RequestBody Ad ad) {
        ad.setSecretCode(SecretGenerator.generate());
        return adRepository.save(ad).getSecretCode();
    }

    @GetMapping
    public List<Ad> getAllByPriceRange(@RequestParam Optional<Integer> minPrice, @RequestParam Optional<Integer> maxPrice){
        List<Ad> adList = adRepository.findAllByPriceRange(minPrice.orElseGet(()->0), maxPrice.orElseGet(()->10000));
        adList.forEach(x->x.setSecretCode(null));
        return adList;
    }

    @PutMapping
    public ResponseEntity updateAd(@RequestBody Ad ad){
        try {
            adRepository.update(ad);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{tag}")
    public List<Ad> getAllByTag(@PathVariable String tag){
        return adRepository.findAll().stream().filter(x->x.getTags().contains(tag)).collect(Collectors.toList());
    }
}
