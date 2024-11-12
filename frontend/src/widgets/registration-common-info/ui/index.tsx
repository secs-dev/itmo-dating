import {Input, List, Section, Select, Slider} from "@telegram-apps/telegram-ui";
import {RegistrationData} from "@/entities";

interface RegistrationCommonInfoProps {
    registrationData: RegistrationData,
    changeRD: React.Dispatch<React.SetStateAction<RegistrationData>>
}
export const RegistrationCommonInfo = ({registrationData, changeRD}: RegistrationCommonInfoProps) => {
    return (
        <List>
            <Input header="Name" value={registrationData.name} onChange={e => changeRD((previous) => ({...previous, name: e.target.value} ))}/>
            <Input header="Surname" value={registrationData.surname} onChange={e => changeRD((previous) => ({...previous, surname: e.target.value} ))}/>
            <Section header={`Your height ${registrationData.height} cm`}>
                <Slider step={1} defaultValue={Number(registrationData.height)} onChange={e => changeRD((previous) => ({...previous, height: e} ))} min={50} max={300} />
            </Section>
            <Select header="Faculty" onChange={e => changeRD((previous) => ({...previous, faculty: e.target.value} ))}>
                <option>ПИиКТ</option>
                <option>Другое</option>
            </Select>
        </List>
    )
}