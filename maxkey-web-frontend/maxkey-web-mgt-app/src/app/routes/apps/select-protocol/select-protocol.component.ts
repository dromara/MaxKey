import { Component, OnInit } from '@angular/core';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-select-protocol',
  templateUrl: './select-protocol.component.html',
  styleUrls: ['./select-protocol.component.less']
})
export class SelectProtocolComponent implements OnInit {
  constructor(private modalRef: NzModalRef) {}

  ngOnInit(): void {}

  onSelect(e: MouseEvent, protocol: String): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: true, data: protocol });
  }
}
