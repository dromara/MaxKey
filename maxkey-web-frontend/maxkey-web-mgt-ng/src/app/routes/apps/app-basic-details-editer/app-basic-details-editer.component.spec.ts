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
 

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppBasicDetailsEditerComponent } from './app-basic-details-editer.component';

describe('AppBasicDetailsEditerComponent', () => {
  let component: AppBasicDetailsEditerComponent;
  let fixture: ComponentFixture<AppBasicDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppBasicDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppBasicDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
