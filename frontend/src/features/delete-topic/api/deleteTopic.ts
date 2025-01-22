import axios, { AxiosResponse } from 'axios'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { IError } from '@/shared/model'

export function deleteTopic(
  topicId: number,
): Promise<AxiosResponse<void | IError>> {
  console.log(`delete topic ${topicId}`)
  const url = `${backendUrl}/api/people/${$authStore.getState().userId}/interests/${topicId}`
  const config = {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }

  return axios.delete(url, config)
}
