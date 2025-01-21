import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import axios, { type AxiosRequestConfig } from 'axios'

export async function getPicture(
  userId: number,
  pictureId: number,
): Promise<string> {
  const url = `${backendUrl}/api/people/${userId}/photos/${pictureId}`
  const basicAuth = `Bearer ${$authStore.getState().token}`
  const config: AxiosRequestConfig = {
    method: 'GET',
    headers: {
      Authorization: basicAuth,
    },
    responseType: 'arraybuffer',
  }
  const response = await axios.get<ArrayBuffer>(url, config)
  return _imageEncode(response.data)
}

function _imageEncode(arrayBuffer: ArrayBuffer) {
  // let _u8 = new Uint8Array(arrayBuffer)
  const b64encoded = btoa(
    <string>[].reduce.call(
      new Uint8Array(arrayBuffer),
      function (p, c) {
        return p + String.fromCharCode(c)
      },
      '',
    ),
  )
  const mimetype = 'image/jpeg'
  return 'data:' + mimetype + ';base64,' + b64encoded
}
