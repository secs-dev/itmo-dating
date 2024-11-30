import axios from "axios";
import {UploadedFile} from "@/widgets/registration-pictures/model/UploadedFile.ts";
import React from "react";

interface SendPictureProps {
    setUploadedFiles:  React.Dispatch<React.SetStateAction<Array<UploadedFile>>>
}
let globalPhotoIterator = 0

export function handleUploadingPhotos({setUploadedFiles}: SendPictureProps, files: Array<File>) {
    const url = 'http://localhost:3000/uploadFiles';
    const formData = new FormData();
    files.forEach((file, index) => {
        formData.append(`file${index}`, file);
    });

    const config = {
        headers: {
            'content-type': 'multipart/form-data',
        },
    };

    axios.post(url, formData, config)
        .then((response) => {
            console.log(response.data);
            //FIXME after integration with backend
            // setUploadedFiles(response.data.files);
        })
        .catch((error) => {
            console.error("Error uploading files: ", error);
            //TODO remove after integration with backend
            setUploadedFiles(
                (prevState) => [
                    ...prevState,
                    ...files.map(_ =>
                    {
                        globalPhotoIterator++
                        return {id: globalPhotoIterator, src: "https://avatars.githubusercontent.com/u/93886405"}})
                ])
        })
}