package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Status;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Import(LightDaoImpl.class)
@DataJpaTest
class LightDaoCustomImplTest {

    @Autowired
    private LightDaoCustom lightDao;

    @Test
    public void shouldFindOnLights() {
        Assertions.assertThat(lightDao.findOnLights())
                .hasSize(1)
                .extracting("id", "status")
                .containsExactly(Tuple.tuple(-1L, Status.ON));
    }

   @Test
    public void shouldFindLightRoomById(){
        Assertions.assertThat(lightDao.findLightsByRoomId(-10L))
                .hasSize(2)
                .extracting("id","level","status")
                .contains(Tuple.tuple(-1L,300,100, Status.ON),Tuple.tuple(-2L, 200,40, Status.OFF));
    }

}