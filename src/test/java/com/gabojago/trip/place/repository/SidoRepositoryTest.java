package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Sido;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional
class SidoRepositoryTest {

    @Autowired
    SidoRepository sidoRepository;

    @Test
    public void testSidoList() {
        List<Sido> all = sidoRepository.findAll();
        for (Sido s : all) {
            System.out.println(s.getSidoName() + s.getSidoCode() + s.getImageUrl());
        }
    }

}