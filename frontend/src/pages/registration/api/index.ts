import {mainButton, secondaryButton} from "@telegram-apps/sdk";

export function mountRegTg() {
    if (mainButton.mount.isAvailable()) {
        mainButton.mount()
        if (mainButton.setParams.isAvailable()) {
            mainButton.setParams({
                text: 'Next',
                isEnabled: true,
                hasShineEffect: true,
                isVisible: true,
            });
        }
        // mainButton.
    }
    console.log("mb is vis " + mainButton.isMounted())
}

export function unmountRegTg() {
    if (secondaryButton.mount.isSupported()) {
        secondaryButton.unmount()
    }
}