import {Icon28AddCircle} from "@telegram-apps/telegram-ui/dist/icons/28/add_circle";
import {Card} from "@telegram-apps/telegram-ui";
import {ChangeEventHandler} from "react";

interface addPictureButtonProps {
    handleUploadingPhotos: ChangeEventHandler<any>
}
export const AddPictureButton = ({handleUploadingPhotos}: addPictureButtonProps) => (
    <Card style={{
        background: 'var(--tgui--secondary_bg_color)',
        display: "block"
    }}>
        <Icon28AddCircle style={{
            display: "block",
            margin: "auto",
            // marginRight: "auto",
            marginBottom: "50%",
            marginTop: "50%",
            transform: "scale(5)"
        }} mode="plain"/>
        <input type="file" multiple accept="image/*" onInput={handleUploadingPhotos} style={{
            display: "block",
            height: "100%",
            width: "100%",
            position: "absolute",
            top: "0",
            bottom: "0",
            left: "0",
            right: "0",
            opacity: "0",
            cursor: "pointer"
        }}/>
    </Card>
)
