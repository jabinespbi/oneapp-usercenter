import {Component, Input, OnInit} from '@angular/core';
import {SidebarItem} from "../../models/sidebar-item";
import {DashboardComponent} from "../dashboard/dashboard.component";
import {UserProfileComponent} from "../user-profile/user-profile.component";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.less']
})
export class SidebarComponent implements OnInit {

  @Input() initialActiveItem: string = '';

  items: SidebarItem[];

  constructor() {
    this.items = [
      new SidebarItem('Dashboard', 'Dashboard', 'bi-x-diamond', '/' + DashboardComponent.navigationPath),
      new SidebarItem('User Profile', 'Paul Jabines', 'bi-alarm', '/' + UserProfileComponent.navigationPath),
    ]
  }

  ngOnInit(): void {
    if (!this.initialActiveItem) {
      throw new Error('Undefined initialActiveItem!');
    }

    this.setActiveByName(this.initialActiveItem);
  }

  setActive(item: SidebarItem) {
    this.items.forEach(i => i.setInactive());
    item.setActive();
  }

  setActiveByName(itemName: string) {
    this.items.forEach(i => i.setInactive());
    this.items.find(i => i.name == itemName)?.setActive();
  }
}
