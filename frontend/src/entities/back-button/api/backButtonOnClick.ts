import {backButton, offBackButtonClick, onBackButtonClick} from "@telegram-apps/sdk";

export function setBackButtonOnClick(fn: VoidFunction) {
    if (backButton.isMounted()) {
        return onBackButtonClick(fn)
    } else return () => {}
}

export function removeBackButtonOnClick(fn: VoidFunction) {
    if (backButton.isMounted()) {
        offBackButtonClick(fn)
    }
}
