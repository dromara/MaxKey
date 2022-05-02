/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

import { MockRequest } from '@delon/mock';

const DATA = [
  {
    name: '上海',
    id: '310000',
  },
  {
    name: '市辖区',
    id: '310100',
  },
  {
    name: '北京',
    id: '110000',
  },
  {
    name: '市辖区',
    id: '110100',
  },
  {
    name: '浙江省',
    id: '330000',
  },
  {
    name: '杭州市',
    id: '330100',
  },
  {
    name: '宁波市',
    id: '330200',
  },
  {
    name: '温州市',
    id: '330300',
  },
  {
    name: '嘉兴市',
    id: '330400',
  },
  {
    name: '湖州市',
    id: '330500',
  },
  {
    name: '绍兴市',
    id: '330600',
  },
  {
    name: '金华市',
    id: '330700',
  },
  {
    name: '衢州市',
    id: '330800',
  },
  {
    name: '舟山市',
    id: '330900',
  },
  {
    name: '台州市',
    id: '331000',
  },
  {
    name: '丽水市',
    id: '331100',
  },
];

export const GEOS = {
  '/geo/province': () => DATA.filter(w => w.id.endsWith('0000')),
  '/geo/:id': (req: MockRequest) => {
    const pid = (req.params.id || '310000').slice(0, 2);
    return DATA.filter(w => w.id.slice(0, 2) === pid && !w.id.endsWith('0000'));
  },
};
