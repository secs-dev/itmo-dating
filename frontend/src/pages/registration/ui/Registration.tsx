import {RegistrationCommonInfo} from "@/widgets";
import {Placeholder, Section} from "@telegram-apps/telegram-ui";
import {useEffect, useState} from "react";
import {fetchTgUser} from "@/features/fetch-tg-user/api/fetchTgUser.ts";
import {RegistrationData} from "@/entities";
import {setMainButtonParams, setMainButtonVisible} from "@/entities/main-button";
import {setMainButtonOnClick} from "@/entities/main-button/api/mainButtonOnClick.ts";
import {RegistrationPictures} from "@/widgets/registration-pictures";
import {setBackButtonVisible} from "@/entities/back-button";
import {setBackButtonOnClick} from "@/entities/back-button/api/backButtonOnClick.ts";
import {router} from "@/app/routes/api";
import {RegistrationInterests} from "@/widgets/registration-interests";
import {useEffectOnce} from "@/shared/api";

export const Registration = () => {
    const registrationOrder = ["registration-common", "registration-pictures", "registration-interests"]

     const [key, changeKey] = useState(0)

    const tgUser = fetchTgUser()
    const [registrationData, changeRD] = useState<RegistrationData>(
        {
            tgId: tgUser?.id?.toString() || "-1",
            name: tgUser?.firstName || "",
            surname: tgUser?.lastName!! || "",
            height: 175,
            faculty: "ПИиКТ",
            birthday: new Date(),
            pictures: [],
            interests: []
        })




    const mainButtonFun = () => {
        changeKey((prev) => prev + 1)
    }
    const backButtonFun = () => {
        changeKey((prev) => prev - 1)
    }

    const backButtonHomeFun = () => {
        changeKey(0)
        setBackButtonVisible(false)
        setMainButtonVisible(false)
        router.back()
    }

    useEffectOnce(() => {
         setMainButtonParams("Next", false, true)
         setMainButtonVisible(true)
         setBackButtonVisible(true)
    })

    function setButtons(localKey: number) {
        if (localKey == 0) {
            const offBack = setBackButtonOnClick(() => {
                backButtonHomeFun()
                offBack()
                offMain()
            })
            const offMain = setMainButtonOnClick(() => {
                mainButtonFun()
                offMain()
                offBack()
            })
        } else {
            const offBack = setBackButtonOnClick(() => {
                backButtonFun()
                offBack()
                offMain()
            })
            const offMain = setMainButtonOnClick(() => {
                mainButtonFun()
                offMain()
                offBack()
            })
        }
    }

    useEffect(() => {
        setButtons(key)
    }, [key]);

    function renderSwitchWidget(key: number) {
        switch (registrationOrder[key]) {
            case "registration-common": {
                return <RegistrationCommonInfo registrationData={registrationData} changeRD={changeRD}/>
            }
            case "registration-pictures": {
                return <RegistrationPictures registrationData={registrationData} changeRD={changeRD}/>
            }
            case "registration-interests": {
                return <RegistrationInterests registrationData={registrationData} changeRD={changeRD}/>
            }
            default:
                return <Placeholder description="Bonus level" header="Congratulations">
                    <img
                        alt="Telegram sticker"
                        className="blt0jZBzpxuR4oDhJc8s"
                        src="https://xelene.me/telegram.gif"
                        style={{"display": "block", "width": "50%", "height": "50%"}}
                    /></Placeholder>
        }
    }

    return (
        <Section header="Registration" style={{display: "block", height: "50%"}}>
            {renderSwitchWidget(key)}
        </Section>
    )
}
