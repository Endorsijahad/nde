/**
 * Copyright (c) 2014 InMotion Innovation Technology. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of
 * InMotion Innovation Technology. ("Confidential Information").<BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall
 * only be used in accordance with the terms of the license agreement
 * entered into with IMI; other than in accordance with the written
 * permission of IMI. <BR>
 * 
 **/

package com.imi.dolphin.sdkwebservice;

import com.imi.dolphin.sdkwebservice.service.IService;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author reja
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.imi.dolphin.sdkwebservice"})
public class SdkWebserviceApplication {

    @Autowired
    static IService iService;
	public static void main(String[] args) {
		SpringApplication.run(SdkWebserviceApplication.class, args);
                
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        iService.ping();
//                    }
//                },0,1000L * 60L * 30L);
	}
}
