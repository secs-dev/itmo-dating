import { Section } from '@telegram-apps/telegram-ui'
import { useEffect, useState } from 'react'
import { AddPictureButton } from '@/entities/add-picture-button'
import { RegistrationPictureElement } from '@/entities/registration-picture-element'
import { UploadedFile } from '../model/UploadedFile.ts'
import { handleUploadingPhotos } from '@/features/upload-pictures/api/uploadPictures.ts'
import { deletePictureInUpload } from '@/features/upload-pictures/api/deletePictureInUpload.ts'
import {
  $registrationDataStore,
  registrationDataFx,
} from '@/entities/registration-data/api/registrationDataStore.ts'
import { useEffectOnce } from '@/shared/api'
import { getPicture } from '@/features/get-picture/api/getPicture.ts'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { deletePicture } from '@/features/delete-picture/api/deletePicture.ts'

export const RegistrationPictures = () => {
  // TODO spinner of loading pictures const [files, setFiles] = useState<Array<File>>([]);
  const [uploadedFiles, setUploadedFiles] = useState<Array<UploadedFile>>([])

  useEffectOnce(() => {
    $registrationDataStore.getState().pictures?.forEach(async (p) => {
      const pictureBase = await getPicture(
        Number($authStore.getState().userId),
        p.id,
      )
      setUploadedFiles((prevState) => [
        ...prevState,
        { id: p.id, src: pictureBase },
      ])
    })
  })

  useEffect(() => {
    console.log('picture changed state', $registrationDataStore.getState())

    registrationDataFx({
      ...$registrationDataStore.getState(),
      pictures: uploadedFiles.map((f) => {
        return { id: f.id }
      }),
    })
  }, [uploadedFiles])

  return (
    <Section header="Your photos" style={{}}>
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(2, 1fr)',
          gridTemplateRows: 'auto-fill',
          rowGap: '5px',
          columnGap: '5px',
        }}
      >
        {uploadedFiles.map((uploadedFile) => (
          <RegistrationPictureElement
            key={uploadedFile.id}
            id={uploadedFile.id}
            src={uploadedFile.src}
            onDeleteButtonClick={(id: number) => {
              deletePicture(id)
              deletePictureInUpload({ uploadedFiles, setUploadedFiles }, id)
            }}
          />
        ))}
        <AddPictureButton
          handleUploadingPhotos={(e) => {
            ;[...e.target.files] // eslint-disable-line
              .map((file) => {
                handleUploadingPhotos({ setUploadedFiles }, file)
              })
            e.target.value = null
          }}
        />
      </div>
    </Section>
  )
}
