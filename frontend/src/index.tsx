import ReactDOM from "react-dom/client";
import { AppRoot } from '@telegram-apps/telegram-ui';
import '@telegram-apps/telegram-ui/dist/styles.css';

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
    <AppRoot>
        Hello, World!
    </AppRoot>
);
