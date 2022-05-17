export function knowHost() {
    let hostArray: string[] = new Array('localhost', 'sso.maxkey.top', 'mgt.maxkey.top', 'sso.maxsso.net', 'mgt.maxsso.net');
    for (var i = 0; i < hostArray.length; i++) {
        if (hostArray[i] == location.hostname) {
            return true;
        }
    }
    return false;
}
