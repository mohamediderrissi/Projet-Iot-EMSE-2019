package com.emse.spring.faircorp.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
//@Import(RoomDaoImpl.class)
@DataJpaTest
public class RoomDaoCustomTest {

     @Autowired
    private RoomDaoImpl roomDao;



    @Test
    public void shouldFindRoomByName() {
        Assertions.assertThat(roomDao.findRoomByName("Room1").getName())
                .isEqualTo("Room1");
    }


}
