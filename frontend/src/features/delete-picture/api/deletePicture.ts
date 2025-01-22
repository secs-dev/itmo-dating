import axios, { AxiosResponse } from 'axios'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { IError } from '@/shared/model'

export function deletePicture(
  pictureId: number,
): Promise<AxiosResponse<void | IError>> {
  const url = `${backendUrl}/api/people/${$authStore.getState().userId}/photos/${pictureId}`
  const config = {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }

  return axios.delete(url, config)
}
