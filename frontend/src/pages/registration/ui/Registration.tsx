import {RegistrationCommonInfo} from "@/widgets";
import {Section} from "@telegram-apps/telegram-ui";
import {useState} from "react";
import {fetchTgUser} from "@/features/fetch-tg-user/api/fetchTgUser.ts";
import {RegistrationData} from "@/entities";
import {mountRegTg} from "../api/mountMainButton.ts";
export const Registration = () => {
    const tgUser = fetchTgUser()
    mountRegTg()
    const [registrationData, changeRD] = useState<RegistrationData>(
        {
            tgId: tgUser?.id?.toString() || "-1",
            name: tgUser?.firstName || "",
            surname:  tgUser?.lastName!! || "",
            height: 175,
            faculty: "ПИиКТ"
        })
    return (
        <Section header="Registration">
            <RegistrationCommonInfo registrationData={registrationData} changeRD={changeRD}/>
        </Section>
    )
}