import {backButton} from "@telegram-apps/sdk";

export function setBackButtonVisible(isVisible: boolean) {
    if (backButton.isMounted()) {
        if (backButton.isSupported()) {
            if (isVisible) {
                backButton.show()
            } else {
                backButton.hide()
            }
        }
    }
}