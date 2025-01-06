import { backButton } from '@telegram-apps/sdk'

export function mountBackButton() {
  if (backButton.mount.isAvailable()) {
    backButton.mount()
  }
}
