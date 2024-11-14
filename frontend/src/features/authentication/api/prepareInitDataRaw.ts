export function prepareInitDataRaw(initDataRaw: string | undefined): any {
    if (initDataRaw == undefined)
        throw new Error("initDataRaw is undefined")
    let hash: string | undefined;

    const pairs: string[] = [];

    new URLSearchParams(initDataRaw).forEach((value, key) => {
        if (key === 'hash') {
            hash = value;
            return;
        }

        pairs.push(`${key}=${value}`);
    });

    pairs.sort();

    const tgInitDataRaw = pairs.join('\n')
    return {tgInitDataRaw, hash}
}
