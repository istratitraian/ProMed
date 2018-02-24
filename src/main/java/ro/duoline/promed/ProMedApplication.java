package ro.duoline.promed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ProMedApplication //        extends SpringBootServletInitializer { //war deployment
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ProMedApplication.class);
//    }
{

    public static void main(String[] args) {
        SpringApplication.run(ProMedApplication.class, args);
    }
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server h2Server() throws SQLException {
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//    }

//    @Bean
//    @Profile("production")
//    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
//        return (ConfigurableEmbeddedServletContainer container) -> {
//            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//            tomcat.addConnectorCustomizers(
//                    (connector) -> {
//                        connector.setPort(81);
//                    }
//            );
//        };
//    }
}
