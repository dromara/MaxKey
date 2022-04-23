import format from 'date-fns/format';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

export class PageResults {
    page: number = 0;
    total: number = 0;
    totalPage: number = 0;
    records: number = 0;
    rows: any[] = [];

    init(data: any): void {
        Object.assign(this, data);
    }
}
