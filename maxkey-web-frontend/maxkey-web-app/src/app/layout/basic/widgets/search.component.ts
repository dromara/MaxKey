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

import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  HostBinding,
  Input,
  OnDestroy,
  Output
} from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs/operators';

@Component({
  selector: 'header-search',
  template: `
    <nz-input-group [nzPrefix]="iconTpl" [nzSuffix]="loadingTpl">
      <ng-template #iconTpl>
        <i nz-icon [nzType]="focus ? 'arrow-down' : 'search'"></i>
      </ng-template>
      <ng-template #loadingTpl>
        <i *ngIf="loading" nz-icon nzType="loading"></i>
      </ng-template>
      <input
        type="text"
        nz-input
        [(ngModel)]="q"
        [nzAutocomplete]="auto"
        (input)="search($event)"
        (focus)="qFocus()"
        (blur)="qBlur()"
        [attr.placeholder]="'menu.search.placeholder' | i18n"
      />
    </nz-input-group>
    <nz-autocomplete nzBackfill #auto>
      <nz-auto-option *ngFor="let i of options" [nzValue]="i">{{ i }}</nz-auto-option>
    </nz-autocomplete>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderSearchComponent implements AfterViewInit, OnDestroy {
  q = '';
  qIpt: HTMLInputElement | null = null;
  options: string[] = [];
  search$ = new BehaviorSubject('');
  loading = false;

  @HostBinding('class.alain-default__search-focus')
  focus = false;
  @HostBinding('class.alain-default__search-toggled')
  searchToggled = false;

  @Input()
  set toggleChange(value: boolean) {
    if (typeof value === 'undefined') {
      return;
    }
    this.searchToggled = value;
    this.focus = value;
    if (value) {
      setTimeout(() => this.qIpt!.focus());
    }
  }
  @Output() readonly toggleChangeChange = new EventEmitter<boolean>();

  constructor(private el: ElementRef<HTMLElement>, private cdr: ChangeDetectorRef) {}

  ngAfterViewInit(): void {
    this.qIpt = this.el.nativeElement.querySelector('.ant-input') as HTMLInputElement;
    this.search$
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        tap({
          complete: () => {
            this.loading = true;
          }
        })
      )
      .subscribe(value => {
        this.options = value ? [value, value + value, value + value + value] : [];
        this.loading = false;
        this.cdr.detectChanges();
      });
  }

  qFocus(): void {
    this.focus = true;
  }

  qBlur(): void {
    this.focus = false;
    this.searchToggled = false;
    this.options.length = 0;
    this.toggleChangeChange.emit(false);
  }

  search(ev: Event): void {
    this.search$.next((ev.target as HTMLInputElement).value);
  }

  ngOnDestroy(): void {
    this.search$.complete();
    this.search$.unsubscribe();
  }
}
