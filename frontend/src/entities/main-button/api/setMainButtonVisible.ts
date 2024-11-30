import {mainButton} from "@telegram-apps/sdk";

export function setMainButtonVisible(isVisible: boolean) {
    if (mainButton.isMounted()) {
        if (mainButton.setParams.isAvailable()) {
            mainButton.setParams({
                isVisible: isVisible
            });
        }
    }
}