import axios, { AxiosResponse } from 'axios'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { IError } from '@/shared/model'
import { InterestLevel } from '@/entities/interest-level/model/InterestLevel.ts'

export function putTopic(
  topicId: number,
  level: number,
): Promise<AxiosResponse<void | IError>> {
  const url = `${backendUrl}/api/people/${$authStore.getState().userId}/interests/${topicId}`
  const config = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }
  const newLevel: InterestLevel = { level: level.toString() }

  return axios.put(url, JSON.stringify(newLevel), config)
}
