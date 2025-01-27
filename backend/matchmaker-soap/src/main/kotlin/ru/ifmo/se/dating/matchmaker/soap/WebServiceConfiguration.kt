package ru.ifmo.se.dating.matchmaker.soap

import jakarta.xml.ws.Endpoint
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.matchmaker.ITMODatingMatchmakerPortType

@Configuration
class WebServiceConfiguration {
    @Bean(Bus.DEFAULT_BUS_ID)
    fun springBus(): SpringBus =
        SpringBus()

    @Bean
    fun matchmakerEndpoint(
        bus: Bus,
        service: ITMODatingMatchmakerPortType,
    ): Endpoint {
        val endpoint = EndpointImpl(bus, service)
        endpoint.publish("/")
        return endpoint
    }
}
