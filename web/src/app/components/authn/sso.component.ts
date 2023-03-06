import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {UserProfileComponent} from "../user-profile/user-profile.component";

@Component({
  selector: 'app-authn',
  templateUrl: './sso.component.html',
  styleUrls: ['./sso.component.less']
})
export class SsoComponent {

  static navigationPath: string = 'sso';

  SAMLRequest = 'PHNhbWwycDpBdXRoblJlcXVlc3QgeG1sbnM6c2FtbDJwPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6cHJvdG9jb2wiCiAgICAgICAgICAgICAgICAgICAgIEFzc2VydGlvbkNvbnN1bWVyU2VydmljZVVSTD0iaHR0cDovL2xvY2FsaG9zdDo0MjAwL2xvZ2luL3NhbWwyL3Nzby9va3RhIgogICAgICAgICAgICAgICAgICAgICBEZXN0aW5hdGlvbj0iaHR0cHM6Ly9kZXYtNDIwODU4MDcub2t0YS5jb20vYXBwL2Rldi00MjA4NTgwN19teWFuZ3VsYXJfMS9leGs4ajh0c3UxbjltVFpMODVkNy9zc28vc2FtbCIKICAgICAgICAgICAgICAgICAgICAgRm9yY2VBdXRobj0iZmFsc2UiCiAgICAgICAgICAgICAgICAgICAgIElEPSJBUlExZGE1ZTJmLWY0NGQtNDg0NS1iMGJhLTA4NDBlYTQzNjRiOCIKICAgICAgICAgICAgICAgICAgICAgSXNQYXNzaXZlPSJmYWxzZSIKICAgICAgICAgICAgICAgICAgICAgSXNzdWVJbnN0YW50PSIyMDIzLTAzLTAyVDA0OjUwOjE2LjA0OFoiCiAgICAgICAgICAgICAgICAgICAgIFByb3RvY29sQmluZGluZz0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmJpbmRpbmdzOkhUVFAtUE9TVCIKICAgICAgICAgICAgICAgICAgICAgVmVyc2lvbj0iMi4wIgo+CjxzYW1sMjpJc3N1ZXIgeG1sbnM6c2FtbDI9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphc3NlcnRpb24iPmh0dHA6Ly9sb2NhbGhvc3Q6NDIwMC9zYW1sMi9zZXJ2aWNlLXByb3ZpZGVyLW1ldGFkYXRhL29rdGE8L3NhbWwyOklzc3Vlcj4KPC9zYW1sMnA6QXV0aG5SZXF1ZXN0Pgo=';

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.createSamlFormAndSubmit();
  }

  createSamlFormAndSubmit() {
    const samlForm = document.createElement("form");
    samlForm.setAttribute("method", "post");
    samlForm.setAttribute("action", "https://dev-42085807.okta.com/app/dev-42085807_myangular_1/exk8j8tsu1n9mTZL85d7/sso/saml");

    const relayStateInput = document.createElement("input");
    relayStateInput.setAttribute("type", "hidden");
    relayStateInput.setAttribute("name", "RelayState");
    relayStateInput.setAttribute("value", "40aca189-6054-4aa7-bec3-a733ec78b31e");

    const samlRequestInput = document.createElement("input");
    samlRequestInput.setAttribute("type", "hidden");
    samlRequestInput.setAttribute("name", "SAMLRequest");
    samlRequestInput.setAttribute("value", this.SAMLRequest);

    samlForm.appendChild(relayStateInput);
    samlForm.appendChild(samlRequestInput);

    document.getElementsByTagName("body")[0]
      .appendChild(samlForm);

    samlForm.submit();
  }
}
