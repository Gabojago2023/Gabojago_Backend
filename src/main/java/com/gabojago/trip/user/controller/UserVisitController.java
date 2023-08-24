package com.gabojago.trip.user.controller;

import com.gabojago.trip.user.dto.UserVisitDto;
import com.gabojago.trip.user.service.UserVisitService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/attendance")
public class UserVisitController {

    private UserVisitService userVisitService;

    public UserVisitController(UserVisitService userVisitService) {
        this.userVisitService = userVisitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserVisit(@PathVariable Integer id, @RequestParam String type) {
        log.debug("[GET] /attendance/" + id + "?type=" + type);
        List<UserVisitDto> visits = userVisitService.getAllUserVisit(id);

        if ("all".equals(type)) {
            return new ResponseEntity<>(visits.size(), HttpStatus.OK);
        } else if ("continuity".equals(type)) {
            int count = 0;
            // if no visits, return 0 <- should not be executed ideally
            if (visits.isEmpty()) {
                return new ResponseEntity<>(count, HttpStatus.OK);
            }

            // sort visits by UserVisitDto.lastVisit of type LocalDate from current to old date
            visits.sort((v1, v2) -> v2.getLastVisit().compareTo(v1.getLastVisit()));

            // check if user have visited today
            if (visits.get(0).getLastVisit().equals(java.time.LocalDate.now())) {
                // count the number of consequent visits
                for (int i = 0; i < visits.size() - 1; i++) {
                    if (visits.get(i).getLastVisit().minusDays(1).equals(visits.get(i + 1).getLastVisit())) {
                        count++;
                    } else {
                        break;
                    }
                }
            }

            return new ResponseEntity<>(count, HttpStatus.OK);

        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
