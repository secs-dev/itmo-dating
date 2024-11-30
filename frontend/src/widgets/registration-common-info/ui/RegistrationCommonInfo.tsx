import {Input, List, Section, Select, Slider} from "@telegram-apps/telegram-ui";
import {RegistrationData} from "@/entities";
import {getAmountOfDays} from "@/shared/api";
import {useEffect, useState} from "react";

interface RegistrationCommonInfoProps {
    registrationData: RegistrationData,
    changeRD: React.Dispatch<React.SetStateAction<RegistrationData>>
}

export const RegistrationCommonInfo = ({registrationData, changeRD}: RegistrationCommonInfoProps) => {
    const actualDate = new Date()
    const [day, setDay] = useState<number>(actualDate.getDay())
    const [month, setMonth] = useState<number>(actualDate.getMonth())
    const [year, setYear] = useState<number>(actualDate.getFullYear())

    useEffect(() => {
        changeRD((previous) => ({...previous, birthday: new Date(year, month, day)}))
    }, [day, month, year])

    return (
        <Section header="Common">
            <List>
                <Input header="Name" value={registrationData.name}
                       onChange={e => changeRD((previous) => ({...previous, name: e.target.value}))}/>
                <Input header="Surname" value={registrationData.surname}
                       onChange={e => changeRD((previous) => ({...previous, surname: e.target.value}))}/>
                <Section header={`Your height ${registrationData.height} cm`}>
                    <Slider step={1} defaultValue={Number(registrationData.height)}
                            onChange={e => changeRD((previous) => ({...previous, height: e}))} min={50} max={300}/>
                </Section>
                <Select header="Faculty"
                        onChange={e => changeRD((previous) => ({...previous, faculty: e.target.value}))}>
                    <option>ПИиКТ</option>
                    <option>Другое</option>
                </Select>
                <Section header="Your birthday">
                    {/*<div style={{display: "grid", gridTemplateColumns: "1fr 6fr 1fr"}}>*/}
                        <Select header="Day" onChange={e => setDay(Number(e.target.value))}>
                            {
                                Array.from({length: getAmountOfDays(month, year)}, (_, key) => key)
                                    .map(i => <option key={i + 1} value={i + 1}>{i + 1}</option>)
                            }
                        </Select>
                        <Select header="Month" onChange={e => setMonth(Number(e.target.value))}>
                           <option value={1}>January</option>
                           <option value={2}>February</option>
                           <option value={3}>March</option>
                           <option value={4}>April</option>
                           <option value={5}>May</option>
                           <option value={6}>June</option>
                           <option value={7}>Jule</option>
                           <option value={8}>August</option>
                           <option value={9}>September</option>
                           <option value={10}>October</option>
                           <option value={11}>November</option>
                           <option value={12}>December</option>
                        </Select>
                        <Select header="Year"  onChange={e => setYear(Number(e.target.value))}>
                            {
                                Array.from({length: actualDate.getFullYear()}, (_, key) => key)
                                    .slice(1900 - 1)
                                    .reverse()
                                    .map(i => <option key={i + 1}>{i + 1}</option>)
                            }
                        </Select>
                    {/*</div>*/}
                </Section>
            </List>
        </Section>
    )
}