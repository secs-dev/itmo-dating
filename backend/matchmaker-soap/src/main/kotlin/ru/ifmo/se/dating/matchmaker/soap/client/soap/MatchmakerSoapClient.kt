package ru.ifmo.se.dating.matchmaker.soap.client.soap

import org.springframework.stereotype.Component
import ru.ifmo.se.dating.matchmaker.ITMODatingMatchmakerPortType
import ru.ifmo.se.dating.matchmaker.ITMODatingMatchmakerService
import java.net.URL


@Component
class MatchmakerSoapClient : ITMODatingMatchmakerPortType
by ITMODatingMatchmakerService(
    URL("http://localhost:8080/services/matchmaker?wsdl")
).itmoDatingMatchmakerPort
