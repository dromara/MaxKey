import { NzSafeAny } from 'ng-zorro-antd/core/types';

import { IModalData } from './IModalData';

export interface IModalDataApp extends IModalData {
  appsCategory: NzSafeAny[];
  protocol: string;
}
