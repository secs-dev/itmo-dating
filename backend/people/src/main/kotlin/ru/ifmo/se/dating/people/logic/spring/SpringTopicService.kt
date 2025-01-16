package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.logic.basic.BasicTopicService
import ru.ifmo.se.dating.people.logic.logging.LoggingTopicService
import ru.ifmo.se.dating.people.storage.TopicStorage

@Service
class SpringTopicService(
    storage: TopicStorage,
) : TopicService by
LoggingTopicService(
    BasicTopicService(storage)
)
