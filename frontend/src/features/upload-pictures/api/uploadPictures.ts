import axios from 'axios'
import { UploadedFile } from '@/widgets/registration-pictures/model/UploadedFile.ts'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { PictureMessage } from '@/features/upload-pictures/model/PictureMessage.ts'
import { getPicture } from '@/features/get-picture/api/getPicture.ts'

interface SendPictureProps {
  setUploadedFiles: React.Dispatch<React.SetStateAction<Array<UploadedFile>>>
}

let globalPhotoIterator = 0

export function handleUploadingPhotos(
  { setUploadedFiles }: SendPictureProps,
  file: File,
) {
  const url = `${backendUrl}/api/people/${$authStore.getState().userId}/photos`
  const basicAuth = `Bearer ${$authStore.getState().token}`
  const config = {
    method: 'POST',
    headers: {
      'content-type': 'image/jpeg',
      Authorization: basicAuth,
    },
  }

  axios
    .post<PictureMessage>(url, file, config)
    .then(async (response) => {
      const resp = response.data as PictureMessage
      const pictureBase = await getPicture(resp.id)

      setUploadedFiles((prevState) => [
        ...prevState,
        { id: resp.id, src: `${pictureBase}` },
      ])
    })
    .catch((error) => {
      console.error('Error uploading files: ', error)
      //TODO remove after integration with backend
      setUploadedFiles((prevState) => [
        ...prevState,
        {
          id: globalPhotoIterator,
          src: 'https://avatars.githubusercontent.com/u/93886405',
        },
      ])
      globalPhotoIterator++
    })

  // const formData = new FormData()
  // files.forEach((file, index) => {
  //   formData.append(`file${index}`, file)
  // })
  //
  // const config = {
  //   headers: {
  //     'content-type': 'multipart/form-data',
  //   },
  // }
  //
  // axios
  //   .post(url, formData, config)
  //   .then((response) => {
  //     console.log(response.data)
  //     //FIXME after integration with backend
  //     // setUploadedFiles(response.data.files);
  //   })
  //   .catch((error) => {
  //     console.error('Error uploading files: ', error)
  //     //TODO remove after integration with backend
  //     setUploadedFiles((prevState) => [
  //       ...prevState,
  //       ...files.map(() => {
  //         globalPhotoIterator++
  //         return {
  //           id: globalPhotoIterator,
  //           src: 'https://avatars.githubusercontent.com/u/93886405',
  //         }
  //       }),
  //     ])
  //   })
}
