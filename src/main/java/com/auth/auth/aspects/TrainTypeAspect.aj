package com.auth.auth.aspects;
import com.auth.auth.services.Train.DriverAssitances.Ex;
import com.auth.auth.services.Train.DriverAssitances.Os;
import com.auth.auth.services.Train.DriverAssitances.REX;
import com.auth.auth.services.Train.DriverAssitances.IC;
import com.auth.auth.services.Train.IDriverAssistanceSystemsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public aspect TrainTypeAspect {

    pointcut cuckoosConstructor():
            call(com.auth.auth.services.Train.DriverAssistanceSystemsService.new());

    Object around(): cuckoosConstructor() {
        Random random = new Random();
        List<IDriverAssistanceSystemsService> list = new ArrayList<IDriverAssistanceSystemsService>();

        list.add(new IC());
        list.add(new Ex());
        list.add(new Os());
        list.add(new REX());

        int index = random.nextInt(5);

        if (index<4){
            return list.get(index);
        }
        return proceed();
    }
}
