import {Button} from "@telegram-apps/telegram-ui";
import {authenticate} from "../api";

export const AuthTest = () => {

    return (
        <Button onClick={() => authenticate()}>Auth Test (logging to console)</Button>
    )
}