export function set2String(set: Set<String>): string {
    let setValues = '';
    set.forEach(value => {
        setValues = `${setValues + value},`;
    });
    return setValues;
}
