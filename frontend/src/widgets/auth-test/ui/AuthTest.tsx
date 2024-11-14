import {Button} from "@telegram-apps/telegram-ui";
import {authenticate} from "../api/authenticate.ts";

export const AuthTest = () => {

    return (
        <Button onClick={() => authenticate()}>Auth Test (logging to console)</Button>
    )
}