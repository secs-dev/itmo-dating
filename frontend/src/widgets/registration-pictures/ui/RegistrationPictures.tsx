import { Section } from '@telegram-apps/telegram-ui'
import React, { useEffect, useState } from 'react'
import { AddPictureButton } from '@/entities/add-picture-button'
import { RegistrationPictureElement } from '@/entities/registration-picture-element'
import { UploadedFile } from '../model/UploadedFile.ts'
import { handleUploadingPhotos } from '@/features/upload-pictures/api/uploadPictures.ts'
import { deletePicture } from '@/features/upload-pictures/api/deletePicture.ts'
import { RegistrationData } from '@/entities'

interface RegistrationPicturesProps {
  registrationData: RegistrationData
  changeRD: React.Dispatch<React.SetStateAction<RegistrationData>>
}

export const RegistrationPictures = (
  registrationPicturesProps: RegistrationPicturesProps,
) => {
  // TODO spinner of loading pictures const [files, setFiles] = useState<Array<File>>([]);
  const [uploadedFiles, setUploadedFiles] = useState<Array<UploadedFile>>([])

  useEffect(() => {
    registrationPicturesProps.changeRD((previous) => ({
      ...previous,
      pictures: uploadedFiles.map((f) => {
        return { id: f.id, small: f.src, medium: f.src, large: f.src }
      }),
    }))
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
            onDeleteButtonClick={(id: number) =>
              deletePicture({ uploadedFiles, setUploadedFiles }, id)
            }
          />
        ))}
        <AddPictureButton
          handleUploadingPhotos={(e) => {
            handleUploadingPhotos({ setUploadedFiles }, [...e.target.files])
            e.target.value = null
          }}
        />
      </div>
    </Section>
  )
}
