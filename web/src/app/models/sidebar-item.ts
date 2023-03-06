export class SidebarItem {
  name: string;
  displayName: string;
  icon: string;
  routerLink: string;
  active: string;

  constructor(name: string,
              displayName: string,
              icon: string,
              routerLink: string,
              active: string = '') {
    this.name = name;
    this.displayName = displayName;
    this.icon = icon;
    this.routerLink = routerLink;
    this.active = active;
  }

  setActive() {
    this.active = 'active';
  }

  setInactive() {
    this.active = '';
  }
}
