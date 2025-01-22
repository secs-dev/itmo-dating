import { UploadedFile } from '@/widgets/registration-pictures/model/UploadedFile.ts'
import React from 'react'

interface DeletePictureProps {
  uploadedFiles: Array<UploadedFile>
  setUploadedFiles: React.Dispatch<React.SetStateAction<Array<UploadedFile>>>
}

export function deletePictureInUpload(props: DeletePictureProps, id: number) {
  props.setUploadedFiles((prev) =>
    prev.filter((uploadedFile) => uploadedFile.id != id),
  )
}
