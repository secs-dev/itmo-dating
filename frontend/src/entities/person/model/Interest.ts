import { Topic } from '@/entities/person/model/Topic.ts'

export interface Interest {
  topic: Topic
  level: number
}

export interface InterestV2 {
  topicId: number
  level: string
}
export interface InterestMessage {
  topicId: number
  level: InterestLevelMessage
}

export enum InterestLevelMessage {
  _1 = '1',
  _2 = '2',
  _3 = '3',
  _4 = '4',
  _5 = '5',
}
