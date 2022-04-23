import format from 'date-fns/format';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

export class Message<NzSafeAny> {
    code: number = 0;
    message: string = '';
    data: any;
}
