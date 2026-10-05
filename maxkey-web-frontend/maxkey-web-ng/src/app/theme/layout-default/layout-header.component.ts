import { NgTemplateOutlet } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SettingsService } from '@delon/theme';
import { NzIconDirective } from 'ng-zorro-antd/icon';

import { LayoutDefaultHeaderItemComponent } from './layout-header-item.component';
import { LayoutDefaultService } from './layout.service';

@Component({
  selector: 'layout-default-header',
  template: `
    <ng-template #render let-ls>
      @for (i of ls; track i) {
        <li [class.hidden-mobile]="i.hidden() === 'mobile'" [class.hidden-pc]="i.hidden() === 'pc'">
          <ng-container *ngTemplateOutlet="i.host()" />
        </li>
      }
    </ng-template>
    <!--maxkey
    @let _opt = opt();
    <div class="alain-default__header-logo" [style.width.px]="_opt.logoFixWidth">
      @if (_opt.logo) {
        <ng-container *ngTemplateOutlet="_opt.logo" />
      } @else {
        <a [routerLink]="_opt.logoLink" class="alain-default__header-logo-link">
          <img class="alain-default__header-logo-expanded" [attr.src]="_opt.logoExpanded" [attr.alt]="app.name" />
          <img class="alain-default__header-logo-collapsed" [attr.src]="_opt.logoCollapsed" [attr.alt]="app.name" />
        </a>
      }
    </div>
    -->
    <div class="alain-default__nav-wrap">
      <ul class="alain-default__nav"
        ><!--
        @if (!_opt.hideAside && _opt.showHeaderCollapse) {
          <li>
            <div class="alain-default__nav-item alain-default__nav-item--collapse" (click)="srv.toggleCollapsed()">
              <nz-icon [nzType]="srv.collapsedIcon()" />
            </div>
          </li>
        }-->
        <!-- loggo -->
        <ng-template [ngTemplateOutlet]="render" [ngTemplateOutletContext]="{ $implicit: left() }" />
      </ul>

      @if (middle().length > 0) {
        <div class="alain-default__nav alain-default__nav-middle">
          <ng-container *ngTemplateOutlet="middle()[0].host()" />
        </div>
      }
      <ul class="alain-default__nav">
        <ng-template [ngTemplateOutlet]="render" [ngTemplateOutletContext]="{ $implicit: right() }" />
      </ul>
    </div>
  `,
  host: { class: 'alain-default__header' },
  imports: [
    NgTemplateOutlet
    //RouterLink, NzIconDirective
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LayoutDefaultHeaderComponent {
  protected readonly app = inject(SettingsService).app;
  protected readonly srv = inject(LayoutDefaultService);

  readonly items = input.required<readonly LayoutDefaultHeaderItemComponent[]>();

  protected left = computed(() => this.items().filter(i => i.direction() === 'left'));
  protected middle = computed(() => this.items().filter(i => i.direction() === 'middle'));
  protected right = computed(() => this.items().filter(i => i.direction() === 'right'));
  protected opt = this.srv.options;
}
