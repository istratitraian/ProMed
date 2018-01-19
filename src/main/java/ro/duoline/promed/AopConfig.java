/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author I.T.W764
 */
//@ComponentScan  = @Include(MyConfig.class)
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)//AOP
public class AopConfig {

}
