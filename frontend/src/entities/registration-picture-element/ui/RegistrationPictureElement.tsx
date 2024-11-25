import {Card, IconButton} from "@telegram-apps/telegram-ui";
import {Icon24Close} from "@telegram-apps/telegram-ui/dist/icons/24/close";

interface RegistrationPictureElementProps {
    id: number,
    src: string,
    onDeleteButtonClick: Function
}
export const RegistrationPictureElement = ({id, src, onDeleteButtonClick}: RegistrationPictureElementProps) => (
    <Card style={{
        background: 'var(--tgui--secondary_bg_color)',
    }}>
        <img src={src} style={{
            width: "100%",
            height: "100%",
            objectFit: "cover",
        }} alt={`Picture ${src}`}/>
        <IconButton
            mode="plain"
            onClick={_ => onDeleteButtonClick(id)}
            style={{
                right: "0",
                top: "0",
                position: "absolute",
                transform: "scale(1.5)"
            }}
        >
            <Icon24Close/>
        </IconButton>
        <div style={{
            left: "10%",
            top: "10%",
            position: "absolute",
            transform: "scale(1.5)"
        }}>id: {id}</div>
    </Card>
)
